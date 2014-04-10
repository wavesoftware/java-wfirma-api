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

package pl.wavesoftware.wfirma.api.mapper.xml;

import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class BooleanToIntegerAdapterTest {

    @Test
    public void testUnmarshal() throws Exception {
        BooleanToIntegerAdapter instance = new BooleanToIntegerAdapter();
        Boolean result = instance.unmarshal(1);
        assertThat(result).isTrue();
        result = instance.unmarshal(0);
        assertThat(result).isFalse();
        Random rand = new Random();
        for (int i = 1; i <= 10; i++) {
            int test = rand.nextInt(1000) + 1;
            result = instance.unmarshal(test);
            assertThat(result).isTrue();
        }
    }

    @Test
    public void testMarshal() throws Exception {
        BooleanToIntegerAdapter instance = new BooleanToIntegerAdapter();
        Integer result = instance.marshal(Boolean.TRUE);
        assertThat(result).isEqualTo(1);
        result = instance.marshal(Boolean.FALSE);
        assertThat(result).isEqualTo(0);
    }

}
