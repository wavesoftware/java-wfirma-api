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
package pl.wavesoftware.wfirma.api.model.requests;

import java.util.Collection;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import pl.wavesoftware.wfirma.api.mapper.Api;
import pl.wavesoftware.wfirma.api.model.ApiEntityElement;
import pl.wavesoftware.wfirma.api.model.Request;
import pl.wavesoftware.wfirma.api.model.companies.Companies;
import pl.wavesoftware.wfirma.api.model.contractors.Contractors;
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
        FindRequest<Contractors> instance = FindRequest.create(Contractors.class, parameters);
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
        FindRequest<Contractors> instance = FindRequest.create(Contractors.class, parameters);
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
        FindRequest<Contractors> instance = FindRequest.create(Contractors.class, parameters);
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
        assertThat(FindRequest.create(Companies.class)).isNotNull();
    }

    @Test
    public void testConstructionInvalid() {
        try {
            assertThat(FindRequest.create(SampleApiEntityElement.class)).isNotNull();
            Assertions.failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException ex) {
            assertThat(ex).hasCauseExactlyInstanceOf(IllegalAccessException.class);
            assertThat(ex).hasMessage("java.lang.IllegalAccessException: Class pl.wavesoftware.wfirma.api.model."
                    + "requests.FindRequest can not access a member of class pl.wavesoftware.wfirma.api.model."
                    + "requests.FindRequestTest$SampleApiEntityElement with modifiers \"private\"");
        }
    }

    public static class SampleApiEntityElement implements ApiEntityElement {

        private SampleApiEntityElement() {
        }

        @Override
        public Api getApi() {
            return null;
        }

    }

    public static class SampleApi implements Api<SampleApiEntityElement> {

        @Override
        public Collection<Class<? extends Request>> getSupportedRequests() {
            return null;
        }

        @Override
        public Class<SampleApiEntityElement> getEntityClass() {
            return SampleApiEntityElement.class;
        }

        @Override
        public SampleApiEntityElement getEntityElement() {
            return null;
        }

        @Override
        public void setEntityElement(SampleApiEntityElement entityElement) {
            // empty
        }

    }
}
