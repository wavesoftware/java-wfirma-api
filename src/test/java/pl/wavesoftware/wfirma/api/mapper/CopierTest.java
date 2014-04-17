/*
 * The MIT License
 *
 * Copyright 2014 Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
