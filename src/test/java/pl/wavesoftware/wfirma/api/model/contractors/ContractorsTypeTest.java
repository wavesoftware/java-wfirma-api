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
package pl.wavesoftware.wfirma.api.model.contractors;

import java.util.List;
import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import pl.wavesoftware.wfirma.api.mapper.Api;
import pl.wavesoftware.wfirma.api.model.logic.Parameters;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class ContractorsTypeTest {

    private final Api<Contractors> api;

    public ContractorsTypeTest() {
        @SuppressWarnings("unchecked")
        Api<Contractors> mock = mock(Api.class);
        api = mock;
        when(api.getEntityClass()).thenAnswer(new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return Contractors.class;
            }
        });
    }

    @Test
    public void testGetContractor() {
        Contractors instance = new Contractors(api);
        List<Contractor> result = instance.getContractor();
        assertThat(result).isEmpty();
    }

    @Test
    public void testGetParameters() {
        Contractors instance = new Contractors(api);
        Parameters expResult = null;
        Parameters result = instance.getParameters();
        assertThat(result).isEqualTo(expResult);
    }

    @Test
    public void testSetParameters() {
        Parameters value = new Parameters();
        Contractors instance = new Contractors(api);
        instance.setParameters(value);
        assertThat(instance.getParameters()).isEqualTo(value);
    }

    @Test
    public void testGetApi() {
        Contractors instance = new Contractors(api);
        assertThat(instance.getApi()).isEqualTo(api);
    }

//    @Test
//    public void testDepth() {
//        Contractors instance = new Contractors(api);
//        Contractor contractor = new Contractor();
//        instance.getContractor().add(contractor);
//        assertThat(instance.getContractor().iterator().next()).isEqualTo(contractor);
//        contractor.setAccess("a");
//        assertThat(contractor.getAccess()).isEqualTo("a");
//        contractor.setAltname("b");
//        assertThat(contractor.getAltname()).isEqualTo("b");
//        contractor.setBuyer(123);
//        assertThat(contractor.getBuyer()).isEqualTo(123);
//        contractor.setCity("c");
//        assertThat(contractor.getCity()).isEqualTo("c");
//        contractor.setCompanyAccount(new Contractor.CompanyAccount());
//        contractor.getCompanyAccount().setId(234);
//        assertThat(contractor.getCompanyAccount().getId()).isEqualTo(234);
//        contractor.setContactCity("d");
//        assertThat(contractor.getContactCity()).isEqualTo("d");
//        contractor.setContactName("e");
//        assertThat(contractor.getContactName()).isEqualTo("e");
//        contractor.setContactPerson("f");
//        assertThat(contractor.getContactPerson()).isEqualTo("f");
//        contractor.setContactStreet("g");
//        assertThat(contractor.getContactStreet()).isEqualTo("g");
//        contractor.setContactZip("h");
//        assertThat(contractor.getContactZip()).isEqualTo("h");
//        contractor.setCreated("j");
//        assertThat(contractor.getCreated()).isEqualTo("j");
//        contractor.setDescription("k");
//        assertThat(contractor.getDescription()).isEqualTo("j");
//        contractor.setDiscountPercent(345);
//        assertThat(contractor.getDiscountPercent()).isEqualTo(345);
//        contractor.setEmail("l");
//        assertThat(contractor.getEmail()).isEqualTo("l");
//        contractor.setFax("m");
//        assertThat(contractor.getFax()).isEqualTo("m");
//        contractor.setId(456);
//        assertThat(contractor.getId()).isEqualTo(456);
//        contractor.setInvoiceDescription(new Contractor.InvoiceDescription());
//        contractor.getInvoiceDescription().setId(567);
//        assertThat(contractor.getInvoiceDescription().getId()).isEqualTo(567);
//        contractor.setModified("n");
//        assertThat(contractor.getModified()).isEqualTo("m");
//        contractor.set("j");
//        assertThat(contractor.getCreated()).isEqualTo("j");
//    }
}
