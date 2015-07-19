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

import com.openpojo.random.RandomGenerator;
import com.openpojo.random.service.RandomGeneratorService;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.registry.ServiceRegistrar;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.NoPrimitivesRule;
import com.openpojo.validation.rule.impl.NoPublicFieldsRule;
import com.openpojo.validation.rule.impl.NoStaticExceptFinalRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public final class PojoValidationFactory {

    private static class MoneyRandomGenerator implements RandomGenerator {

        private static final Random gererator = new Random();

        @Override
        public Collection<Class<?>> getTypes() {
            List<Class<?>> ret = new ArrayList<>();
            ret.add(Money.class);
            return ret;
        }

        @Override
        public Object doGenerate(Class<?> type) {
            Object output;
            if (Money.class.isAssignableFrom(type)) {
                List<CurrencyUnit> currs = CurrencyUnit.registeredCurrencies();
                int index = gererator.nextInt(currs.size());
                Money money = Money.zero(currs.get(index));
                money = money.plusMajor(gererator.nextInt(100000));
                money = money.plusMinor(gererator.nextInt(100));
                output = money;
            } else {
                RandomGeneratorService service = ServiceRegistrar.getInstance().getRandomGeneratorService();
                output = service.getDefaultRandomGenerator().doGenerate(type);
            }
            return output;
        }

    }

    public static PojoValidator createPojoValidator() {
        PojoValidator pojoValidator = new PojoValidator();
        // Create Rules to validate structure for POJO_PACKAGE
        pojoValidator.addRule(new NoPublicFieldsRule());
        pojoValidator.addRule(new NoPrimitivesRule());
        pojoValidator.addRule(new NoStaticExceptFinalRule());
        pojoValidator.addRule(new GetterMustExistRule());
        pojoValidator.addRule(new SetterMustExistRule());

        // Create Testers to validate behaviour for POJO_PACKAGE
        pojoValidator.addTester(new PojoGettersSettersTester());

        RandomGeneratorService service = ServiceRegistrar.getInstance().getRandomGeneratorService();
        if (!service.getRegisteredTypes().contains(Money.class)) {
            MoneyRandomGenerator moneyGenerator = new MoneyRandomGenerator();
            service.registerRandomGenerator(moneyGenerator);
        }
        return pojoValidator;
    }

    public static Collection<Object[]> createPojoClassList(Class<?>... classes) {
        List<Object[]> ret = new ArrayList<>();
        for (Class<?> cls : classes) {
            ret.add(new Object[]{cls.getSimpleName(), PojoClassFactory.getPojoClass(cls)});
        }
        return ret;
    }

}
