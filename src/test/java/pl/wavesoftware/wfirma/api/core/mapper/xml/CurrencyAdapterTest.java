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
package pl.wavesoftware.wfirma.api.core.mapper.xml;

import static org.assertj.core.api.Assertions.assertThat;
import org.joda.money.CurrencyUnit;
import org.junit.Test;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class CurrencyAdapterTest {

    @Test
    public void testUnmarshal() {
        String input = "EUR";
        CurrencyAdapter instance = new CurrencyAdapter();
        CurrencyUnit result = instance.unmarshal(input);
        assertThat(result).isEqualTo(CurrencyUnit.EUR);

        input = "PLN";
        instance = new CurrencyAdapter();
        result = instance.unmarshal(input);
        assertThat(result).isEqualTo(CurrencyUnit.ofCountry("PL"));
    }

    @Test
    public void testMarshal() {
        CurrencyUnit currency = CurrencyUnit.ofCountry("PL");
        CurrencyAdapter instance = new CurrencyAdapter();
        String result = instance.marshal(currency);
        assertThat(result).isEqualTo("PLN");

        currency = CurrencyUnit.CHF;
        instance = new CurrencyAdapter();
        result = instance.marshal(currency);
        assertThat(result).isEqualTo("CHF");
    }

}
