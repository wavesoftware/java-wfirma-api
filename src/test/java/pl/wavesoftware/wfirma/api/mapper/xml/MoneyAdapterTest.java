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
package pl.wavesoftware.wfirma.api.mapper.xml;

import static org.assertj.core.api.Assertions.assertThat;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class MoneyAdapterTest {

    private static final CurrencyUnit PLN = CurrencyUnit.of("PLN");

    /**
     * Test of unmarshal method, of class MoneyAdapter.
     */
    @Test
    public void testUnmarshal() {
        String input = "1932.01";
        MoneyAdapter instance = new MoneyAdapter();
        Money result = instance.unmarshal(input);
        Money expected = Money.of(PLN, 1932.01d);
        assertThat(result).isEqualTo(expected);

        input = "0";
        instance = new MoneyAdapter();
        result = instance.unmarshal(input);
        expected = Money.of(PLN, 0d);
        assertThat(result).isEqualTo(expected);

        input = "0.00";
        instance = new MoneyAdapter();
        result = instance.unmarshal(input);
        expected = Money.of(PLN, 0d);
        assertThat(result).isEqualTo(expected);

        input = "0.01";
        instance = new MoneyAdapter();
        result = instance.unmarshal(input);
        expected = Money.of(PLN, 0.01d);
        assertThat(result).isEqualTo(expected);
    }

    /**
     * Test of marshal method, of class MoneyAdapter.
     */
    @Test
    public void testMarshal() {
        Money money = Money.of(CurrencyUnit.USD, 1231932.01d);
        MoneyAdapter instance = new MoneyAdapter();
        String result = instance.marshal(money);
        assertThat(result).isEqualTo("1231932.01");

        money = Money.of(CurrencyUnit.EUR, 54.00d);
        instance = new MoneyAdapter();
        result = instance.marshal(money);
        assertThat(result).isEqualTo("54.00");

        money = Money.of(PLN, 124.10d);
        instance = new MoneyAdapter();
        result = instance.marshal(money);
        assertThat(result).isEqualTo("124.10");
    }

}
