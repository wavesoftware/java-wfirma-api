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

import java.util.Locale;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.money.format.GroupingStyle;
import org.joda.money.format.MoneyAmountStyle;
import org.joda.money.format.MoneyFormatter;
import org.joda.money.format.MoneyFormatterBuilder;
import org.joda.money.format.MoneyParseContext;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class MoneyAdapter extends XmlAdapter<String, Money> {

    private final MoneyFormatter formater;

    /**
     * Default constructor
     */
    public MoneyAdapter() {
        MoneyFormatterBuilder builder = new MoneyFormatterBuilder();
        formater = builder
                .appendAmount(MoneyAmountStyle.of(Locale.US).withGroupingStyle(GroupingStyle.NONE))
                .toFormatter();
    }

    @Override
    public Money unmarshal(String input) {
        MoneyParseContext ctx = formater.parse(input, 0);
        ctx.setCurrency(CurrencyUnit.of("PLN"));
        return ctx.toBigMoney().toMoney();
    }

    @Override
    public String marshal(Money money) {
        return formater.print(money);
    }

}
