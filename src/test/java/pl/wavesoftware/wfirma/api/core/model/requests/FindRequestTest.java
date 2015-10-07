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
package pl.wavesoftware.wfirma.api.core.model.requests;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.wavesoftware.wfirma.api.core.model.Api;
import pl.wavesoftware.wfirma.api.core.model.ApiEntityElement;
import pl.wavesoftware.wfirma.api.core.model.Request;
import pl.wavesoftware.wfirma.api.core.model.companies.Companies;
import pl.wavesoftware.wfirma.api.core.model.contractors.Contractors;
import pl.wavesoftware.wfirma.api.core.model.logic.And;
import pl.wavesoftware.wfirma.api.core.model.logic.Condition;
import pl.wavesoftware.wfirma.api.core.model.logic.LogicalOperator;
import pl.wavesoftware.wfirma.api.core.model.logic.Parameters;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class FindRequestTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
                + "<domain>\n"
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
                + "</domain>\n");
    }

    public void testExceptionOnUnimplementd() {
        assertThat(FindRequest.create(Companies.class)).isNotNull();
    }

    @Test
    public void testConstructionInvalid() {
        // then
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("java.lang.IllegalAccessException: Class pl.wavesoftware.wfirma.api.core.model.requests." +
                "FindRequest can not access a member of class pl.wavesoftware.wfirma.api.core.model.requests" +
                ".FindRequestTest$SampleApiEntityElement with modifiers \"private\"");
        thrown.expectCause(CoreMatchers.<Throwable>instanceOf(IllegalAccessException.class));

        // when
        assertThat(FindRequest.create(SampleApiEntityElement.class)).isNotNull();
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
