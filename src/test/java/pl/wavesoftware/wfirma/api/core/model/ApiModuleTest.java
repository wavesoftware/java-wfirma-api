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
package pl.wavesoftware.wfirma.api.core.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import pl.wavesoftware.wfirma.api.core.model.companies.CompaniesApi;
import pl.wavesoftware.wfirma.api.core.model.contractors.Contractors;
import pl.wavesoftware.wfirma.api.core.model.contractors.ContractorsApi;
import pl.wavesoftware.wfirma.api.core.model.invoices.Invoices;
import pl.wavesoftware.wfirma.api.core.model.invoices.InvoicesApi;
import pl.wavesoftware.wfirma.api.core.model.requests.FindRequest;
import pl.wavesoftware.wfirma.api.core.model.requests.GetRequest;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class ApiModuleTest {

    @Test
    public void testInstantinate() {
        assertThat(new ApiModule()).isNotNull();
    }

    @Test
    public void testCollectRequests() {
        Collection<Class<? extends Request>> result = ApiModule.collectRequests(GetRequest.class, FindRequest.class);
        assertThat(result).hasSize(2);
        assertThat(result.contains(GetRequest.class)).isTrue();
        assertThat(result.contains(FindRequest.class)).isTrue();
    }

    @Test
    public void testCollectRequestsInvalid() {
        try {
            ApiModule.collectRequests(
                    GetRequest.class,
                    this.getClass()
            );
            Assertions.failBecauseExceptionWasNotThrown(RuntimeException.class);
        } catch (RuntimeException ex) {
            assertThat(ex).hasNoCause();
            assertThat(ex).hasMessage("Class `class pl.wavesoftware.wfirma.domain.mapper.ApiModuleTest` is not instance of Request");
        }
    }

    @Test
    public void testCreateSampleApi() {
        Class<? extends ApiEntityElement> entityClass = Contractors.class;
        Api result = ApiModule.createSampleApi(entityClass);
        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(ContractorsApi.class);
    }

    private static class SampleApiEntityElement implements ApiEntityElement {

        @Override
        public Api getApi() {
            return null;
        }

    }

    private static class SampleApi implements Api<SampleApiEntityElement> {

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

    @Test
    public void testCreateSampleApiInvalid() {
        try {
            Class<? extends ApiEntityElement> entityClass = SampleApiEntityElement.class;
            ApiModule.createSampleApi(entityClass);
            Assertions.failBecauseExceptionWasNotThrown(RuntimeException.class);
        } catch (RuntimeException ex) {
            assertThat(ex).hasCauseExactlyInstanceOf(IllegalAccessException.class);
            assertThat(ex).hasMessage("[20150716:113015]: java.lang.IllegalAccessException: Class pl.wavesoftware.wfirma.domain" +
                    ".mapper.ApiModule can not access a member of class pl.wavesoftware.wfirma.domain.mapper" +
                    ".ApiModuleTest$SampleApiEntityElement with modifiers \"private\"");
        }
    }

    @Test
    public void testGetRequestModulePath() {
        String result = ApiModule.getRequestModulePath(CompaniesApi.class);
        assertThat(result).isEqualTo("companies");
    }

    @Test
    public void testGetEntityClass() {
        Class<? extends ApiEntityElement> result = ApiModule.getEntityClass(InvoicesApi.class);
        assertThat(result).isEqualTo(Invoices.class);
    }

    @Test
    public void testGetEntityClassInvalid() {
        try {
            ApiModule.getEntityClass(SampleApi.class);
            Assertions.failBecauseExceptionWasNotThrown(RuntimeException.class);
        } catch (RuntimeException ex) {
            assertThat(ex).hasCauseExactlyInstanceOf(IllegalAccessException.class);
            assertThat(ex).hasMessage(
                    "[20150716:113030]: java.lang.IllegalAccessException: Class pl.wavesoftware.wfirma.domain.mapper."
                    + "ApiModule can not access a member of class pl.wavesoftware.wfirma.domain.mapper.ApiModuleTest"
                    + "$SampleApi with modifiers \"private\"");
        }
    }

    @Test
    public void testGetModuleFor_ApiEntityElement() {
        ApiEntityElement entity = new Invoices();
        Class<? extends Api> result = ApiModule.getModuleFor(entity);
        assertThat(result).isEqualTo(InvoicesApi.class);
    }

    @Test
    public void testGetModuleFor_ApiEntityElement_Invalid() {
        try {
            ApiEntityElement entity = new SampleApiEntityElement();
            ApiModule.getModuleFor(entity);
            Assertions.failBecauseExceptionWasNotThrown(RuntimeException.class);
        } catch (RuntimeException ex) {
            assertThat(ex).hasCauseExactlyInstanceOf(IllegalAccessException.class);
            assertThat(ex).hasMessage("[20150716:113042]: java.lang.IllegalAccessException: Class pl.wavesoftware.wfirma.domain" +
                    ".mapper.ApiModule can not access a member of class pl.wavesoftware.wfirma.domain.mapper" +
                    ".ApiModuleTest$SampleApiEntityElement with modifiers \"private\"");
        }
    }

    @Test
    public void testGetModuleFor_Class() {
        Class<? extends Api> result = ApiModule.getModuleFor(Invoices.class);
        assertThat(result).isEqualTo(InvoicesApi.class);
    }

    @Test
    public void testGetModuleFor_Class_Invalid() {
        try {
            ApiModule.getModuleFor(SampleApiEntityElement.class);
            Assertions.failBecauseExceptionWasNotThrown(RuntimeException.class);
        } catch (RuntimeException ex) {
            assertThat(ex).hasCauseExactlyInstanceOf(IllegalAccessException.class);
            assertThat(ex).hasMessage("[20150716:113042]: java.lang.IllegalAccessException: Class pl.wavesoftware.wfirma.domain" +
                    ".mapper.ApiModule can not access a member of class pl.wavesoftware.wfirma.domain.mapper" +
                    ".ApiModuleTest$SampleApiEntityElement with modifiers \"private\"");
        }
    }

}
