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

import com.openpojo.reflection.PojoClass;
import com.openpojo.validation.PojoValidator;
import java.util.Collection;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.wavesoftware.wfirma.api.model.utils.PojoValidationFactory;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
@RunWith(Parameterized.class)
public class LogicPojoValidationTest {

    private final PojoClass pojoClass;

    private final PojoValidator pojoValidator = PojoValidationFactory.createPojoValidator();

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return PojoValidationFactory.createPojoClassList(
                Parameters.class, Order.class, Or.class, Conditions.class, Condition.class, And.class
        );
    }

    public LogicPojoValidationTest(String label, PojoClass pojoClass) {
        this.pojoClass = pojoClass;
    }

    @Test
    public void testPojoStructureAndBehavior() {
        assertThat(pojoClass).isNotNull();
        pojoValidator.runValidation(pojoClass);
    }
}
