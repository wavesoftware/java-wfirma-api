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
package pl.wavesoftware.wfirma.api.model.requests;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import pl.wavesoftware.wfirma.api.model.companies.Companies;
import pl.wavesoftware.wfirma.api.model.companies.CompaniesApi;
import pl.wavesoftware.wfirma.api.model.contractors.Contractors;
import pl.wavesoftware.wfirma.api.model.contractors.ContractorsApi;
import pl.wavesoftware.wfirma.api.model.logic.And;
import pl.wavesoftware.wfirma.api.model.logic.Condition;
import pl.wavesoftware.wfirma.api.model.logic.LogicalOperator;
import pl.wavesoftware.wfirma.api.model.logic.Parameters;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class FindRequestTest {

    @Test
    public void testGetAddress() {
        Parameters parameters = new Parameters();
        And and = new And();
        Condition cond = new Condition();
        cond.setField("name");
        cond.setOperator(LogicalOperator.LIKE);
        cond.setValue("Coca%");
        and.getCondition().add(cond);
        parameters.getConditions().getAnd().add(and);
        FindRequest<Contractors> instance = new FindRequest<>(ContractorsApi.class, parameters);
        String result = instance.getAddress().getCorrectedPath();
        assertThat(result).isEqualTo("/contractors/find");
    }

    @Test
    public void testGetEntity() {
        Parameters parameters = new Parameters();
        And and = new And();
        Condition cond = new Condition();
        cond.setField("name");
        cond.setOperator(LogicalOperator.LIKE);
        cond.setValue("Coca%");
        and.getCondition().add(cond);
        parameters.getConditions().getAnd().add(and);
        FindRequest<Contractors> instance = new FindRequest<>(ContractorsApi.class, parameters);
        Object result = instance.getEntity();
        assertThat(result).isExactlyInstanceOf(Contractors.class);
    }

    @Test
    public void testGetBody() {
        Parameters parameters = new Parameters();
        And and = new And();
        Condition cond = new Condition();
        cond.setField("name");
        cond.setOperator(LogicalOperator.LIKE);
        cond.setValue("Coca%");
        and.getCondition().add(cond);
        parameters.getConditions().getAnd().add(and);
        FindRequest<Contractors> instance = new FindRequest<>(ContractorsApi.class, parameters);
        String result = instance.getBody();
        assertThat(result).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<api>\n"
                + "    <contractors>\n"
                + "        <parameters>\n"
                + "            <conditions>\n"
                + "                <and>\n"
                + "                    <condition>\n"
                + "                        <field>name</field>\n"
                + "                        <operator>like</operator>\n"
                + "                        <value>Coca%</value>\n"
                + "                    </condition>\n"
                + "                </and>\n"
                + "            </conditions>\n"
                + "            <page>0</page>\n"
                + "            <limit>20</limit>\n"
                + "        </parameters>\n"
                + "    </contractors>\n"
                + "</api>\n");
    }

    public void testExceptionOnUnimplementd() {
        assertThat(new FindRequest<Companies>(CompaniesApi.class)).isNotNull();
    }

}
