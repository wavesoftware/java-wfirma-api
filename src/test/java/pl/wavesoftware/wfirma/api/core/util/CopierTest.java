/*
 * Copyright (c) 2014 Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.wavesoftware.wfirma.api.core.util;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.internal.matchers.ThrowableCauseMatcher.hasCause;
import static org.junit.internal.matchers.ThrowableMessageMatcher.hasMessage;

/**
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class CopierTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCopy() {
        Date input = new Date();
        Date result = Copier.from(input).copy();
        assertThat(result).isEqualTo(input);
        assertThat(result).isNotSameAs(input);
    }

    @Test
    public void testCopyNull() {
        Date input = null;
        Date result = Copier.from(input).copy();
        assertThat(result).isEqualTo(input);
        assertThat(result).isNull();
    }

    @Test
    public void testCopyOnInvalid() {
        // given
        Abc input = new Abc();
        input.value = 9;

        // then
        thrown.expect(EidIllegalStateException.class);
        thrown.expect(hasMessage(containsString("20150925:224232")));
        thrown.expectCause(IsInstanceOf.<Throwable>instanceOf(NoSuchMethodException.class));
        thrown.expectCause(hasMessage(containsString("CopierTest$Abc.clone()")));

        // when
        Copier.from(input).copy();
    }

    @Test
    public void testCopyOnInvalid2() {
        // given
        Bcd input = new Bcd();
        input.value = 9;

        // then
        thrown.expect(EidIllegalStateException.class);
        thrown.expect(hasMessage(containsString("20150925:224232")));
        thrown.expectCause(IsInstanceOf.<Throwable>instanceOf(InvocationTargetException.class));
        thrown.expectCause(hasCause(IsInstanceOf.<Throwable>instanceOf(CloneNotSupportedException.class)));
        thrown.expectCause(hasCause(hasMessage(containsString("Yea!"))));

        // when
        Copier.from(input).copy();
    }

    private static class Abc implements Cloneable {

        public int value;
    }

    private static class Bcd implements Cloneable {

        public int value;

        @Override
        public Object clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException("Yea!" + super.clone());
        }
    }

}
