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
