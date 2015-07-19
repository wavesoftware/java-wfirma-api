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
package pl.wavesoftware.wfirma.api.mapper;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class CopierTest {

    @Test
    public void testInstantinate() {
        assertThat(new Copier()).isNotNull();
    }

    @Test
    public void testCopy() {
        Date input = new Date();
        Date result = Copier.copy(input);
        assertThat(result).isEqualTo(input);
        assertThat(result).isNotSameAs(input);
    }

    @Test
    public void testCopyNull() {
        Date input = null;
        Date result = Copier.copy(input);
        assertThat(result).isEqualTo(input);
        assertThat(result).isNull();
    }

    @Test
    public void testCopyOnInvalid() {
        try {
            Abc input = new Abc();
            input.value = 9;
            Copier.copy(input);
            Assertions.failBecauseExceptionWasNotThrown(IllegalStateException.class);
        } catch (IllegalStateException ex) {
            assertThat(ex).hasCauseExactlyInstanceOf(NoSuchMethodException.class);
            assertThat(ex).hasMessage("java.lang.NoSuchMethodException: pl.wavesoftware.wfirma.api.mapper."
                    + "CopierTest$Abc.clone()");
        }
    }

    @Test
    public void testCopyOnInvalid2() {
        try {
            Bcd input = new Bcd();
            input.value = 9;
            Copier.copy(input);
            Assertions.failBecauseExceptionWasNotThrown(IllegalStateException.class);
        } catch (IllegalStateException ex) {
            assertThat(ex).hasCauseExactlyInstanceOf(InvocationTargetException.class);
        }
    }

    private static class Abc implements Cloneable {

        public int value;
    }

    private static class Bcd implements Cloneable {

        public int value;

        @Override
        public Object clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException("Yea!");
        }
    }

}
