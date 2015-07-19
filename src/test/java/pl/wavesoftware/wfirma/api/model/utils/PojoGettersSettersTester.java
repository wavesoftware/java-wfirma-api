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
package pl.wavesoftware.wfirma.api.model.utils;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.utils.IdentityHandlerStub;
import com.openpojo.validation.utils.ValidationHelper;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class PojoGettersSettersTester implements Tester {

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
                Object value = fieldEntry.invokeGetter(instance);

                if (!fieldEntry.isFinal()) {
                    value = RandomFactory.getRandomValue(fieldEntry.getType());
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

}
