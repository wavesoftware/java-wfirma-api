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
package pl.wavesoftware.wfirma.api.mapper;

import java.util.Collection;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import pl.wavesoftware.wfirma.api.model.ApiEntityElement;
import pl.wavesoftware.wfirma.api.model.Request;
import pl.wavesoftware.wfirma.api.model.companies.CompaniesApi;
import pl.wavesoftware.wfirma.api.model.contractors.Contractors;
import pl.wavesoftware.wfirma.api.model.contractors.ContractorsApi;
import pl.wavesoftware.wfirma.api.model.invoices.Invoices;
import pl.wavesoftware.wfirma.api.model.invoices.InvoicesApi;
import pl.wavesoftware.wfirma.api.model.requests.FindRequest;
import pl.wavesoftware.wfirma.api.model.requests.GetRequest;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class ApiModuleTest {

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
            assertThat(ex).hasMessage("Class `class pl.wavesoftware.wfirma.api.mapper.ApiModuleTest` is not instance of Request");
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
            assertThat(ex).hasMessage("java.lang.IllegalAccessException: Class pl.wavesoftware.wfirma.api.mapper."
                    + "ApiModule can not access a member of class pl.wavesoftware.wfirma.api.mapper.ApiModuleTest"
                    + "$SampleApiEntityElement with modifiers \"private\"");
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
            assertThat(ex).hasMessage("java.lang.IllegalAccessException: Class pl.wavesoftware.wfirma.api.mapper."
                    + "ApiModule can not access a member of class pl.wavesoftware.wfirma.api.mapper.ApiModuleTest"
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
            assertThat(ex).hasMessage("java.lang.IllegalAccessException: Class pl.wavesoftware.wfirma.api.mapper."
                    + "ApiModule can not access a member of class pl.wavesoftware.wfirma.api.mapper.ApiModuleTest"
                    + "$SampleApiEntityElement with modifiers \"private\"");
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
            assertThat(ex).hasMessage("java.lang.IllegalAccessException: Class pl.wavesoftware.wfirma.api.mapper."
                    + "ApiModule can not access a member of class pl.wavesoftware.wfirma.api.mapper.ApiModuleTest"
                    + "$SampleApiEntityElement with modifiers \"private\"");
        }
    }

}
