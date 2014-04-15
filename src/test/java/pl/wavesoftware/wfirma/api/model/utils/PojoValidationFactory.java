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
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
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
        pojoValidator.addTester(new SetterTester());
        pojoValidator.addTester(new GetterTester());
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
