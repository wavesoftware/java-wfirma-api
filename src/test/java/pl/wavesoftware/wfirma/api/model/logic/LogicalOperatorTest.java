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
