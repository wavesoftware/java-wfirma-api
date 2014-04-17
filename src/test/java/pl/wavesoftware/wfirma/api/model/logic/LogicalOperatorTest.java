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
package pl.wavesoftware.wfirma.api.model.logic;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class LogicalOperatorTest {

    @Test
    public void testValues() {
        List<LogicalOperator> expResult = Arrays.asList(new LogicalOperator[]{
            LogicalOperator.EQ,
            LogicalOperator.GE,
            LogicalOperator.GT,
            LogicalOperator.LE,
            LogicalOperator.LIKE,
            LogicalOperator.LT,
            LogicalOperator.NE,
            LogicalOperator.NOT_LIKE
        });
        LogicalOperator[] result = LogicalOperator.values();
        assertThat(result).containsAll(expResult);
    }

    @Test
    public void testValueOf() {
        LogicalOperator result = LogicalOperator.valueOf("LT");
        assertThat(result).isEqualTo(LogicalOperator.LT);
    }

    @Test
    public void testValue() {
        LogicalOperator instance = LogicalOperator.EQ;
        String result = instance.value();
        assertThat(result).isEqualTo("eq");
    }

    @Test
    public void testFromValue() {
        LogicalOperator result = LogicalOperator.fromValue("not like");
        assertThat(result).isEqualTo(LogicalOperator.NOT_LIKE);
    }

    @Test
    public void testFromValueInvalid() {
        try {
            LogicalOperator.fromValue("this not exists");
            Assertions.failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException iae) {
            assertThat(iae).hasMessage("There is no enum value for `this not exists` string representaion");
        }
    }

}
