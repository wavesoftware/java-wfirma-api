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
package pl.wavesoftware.wfirma.api.model.utils;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.utils.IdentityHandlerStub;
import com.openpojo.validation.utils.ValidationHelper;
import java.util.Random;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class PojoGettersSettersTester implements Tester {

    private final Random random = new Random();

    @Override
    public void run(PojoClass pojoClass) {
        final Object instance = ValidationHelper.getBasicInstance(pojoClass);
        PojoClass actual = pojoClass;
        while (true) {
            run(actual, instance);
            actual = actual.getSuperClass();
            if (actual == null) {
                break;
            }
        }
    }

    private void run(PojoClass pojoClass, Object instance) {
        for (final PojoField fieldEntry : pojoClass.getPojoFields()) {
            if (fieldEntry.hasGetter()) {
                Object value = fieldEntry.get(instance);

                if (!fieldEntry.isFinal()) {
                    value = getRandomValue(fieldEntry);
                    if (fieldEntry.hasSetter()) {
                        fieldEntry.invokeSetter(instance, value);
                    } else {
                        Affirm.fail("There is no setter for field=[" + fieldEntry + "]");
                    }
                    Object changed = fieldEntry.get(instance);
                    Affirm.affirmEquals("Setter didn't changed value for field=[" + fieldEntry + "]", value,
                            changed);
                }

                IdentityHandlerStub.registerIdentityHandlerStubForValue(value);
                Affirm.affirmEquals("Getter returned non equal value for field=[" + fieldEntry + "]", value,
                        fieldEntry.invokeGetter(instance));
                IdentityHandlerStub.unregisterIdentityHandlerStubForValue(value);
            } else {
                Affirm.fail("There is no getter for field=[" + fieldEntry + "]");
            }
        }
    }

    private Object getRandomValue(PojoField fieldEntry) {
        Class<?> type = fieldEntry.getType();
        Object value;
        if (Money.class.isAssignableFrom(type)) {
            Money money = Money.zero(CurrencyUnit.USD);
            money = money.plusMajor(random.nextInt(100000));
            money = money.plusMinor(random.nextInt(100));
            value = money;
        } else {
            value = RandomFactory.getRandomValue(fieldEntry.getType());
        }
        return value;
    }

}
