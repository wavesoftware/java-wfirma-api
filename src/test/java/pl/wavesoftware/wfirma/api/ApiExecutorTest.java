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
package pl.wavesoftware.wfirma.api;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;
import pl.wavesoftware.wfirma.api.core.model.Gateway;
import pl.wavesoftware.wfirma.api.core.model.GatewayFactory;
import pl.wavesoftware.wfirma.api.core.model.PostRequest;
import pl.wavesoftware.wfirma.api.core.model.Response;
import pl.wavesoftware.wfirma.api.core.model.exceptions.WFirmaException;
import pl.wavesoftware.wfirma.api.core.model.contractors.Contractors;
import pl.wavesoftware.wfirma.api.core.model.invoices.AbstractInvoice;
import pl.wavesoftware.wfirma.api.core.model.invoices.Invoices;
import pl.wavesoftware.wfirma.api.core.model.invoices.NormalInvoice;
import pl.wavesoftware.wfirma.api.core.model.requests.FindRequest;
import pl.wavesoftware.wfirma.api.core.model.requests.GetRequest;
import pl.wavesoftware.wfirma.api.simple.model.SimpleCredentials;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class ApiExecutorTest {

    private static final int PORT;

    static {
        try (ServerSocket socket = new ServerSocket(0)) {
            PORT = socket.getLocalPort();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(PORT);

    private final String mockAddress = "http://localhost:" + PORT;

    @Before
    public void before() {
        String expResultGateway = resource("sample-invoises-response.xml");
        ResponseDefinitionBuilder response1 = aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "text/xml")
                .withBody(expResultGateway);
        MappingBuilder builder1 = get(urlEqualTo("/invoices/get/6"))
                .withHeader("Accept", equalTo("text/xml"))
                .withHeader("Authorization", equalTo("Basic bG9naW4xOnBhc3Mx"))
                .willReturn(response1);
        stubFor(builder1);
    }

    @Test
    public void testExecute() throws Exception {
        // given
        ApiContext ctx = new ApiContext(new SimpleCredentials("login1", "pass1"));
        ApiExecutor instance = new ApiExecutor(ctx);
        instance.setAddress(mockAddress);
        GetRequest<Invoices> request = GetRequest.create(Invoices.class, 6L);

        // when
        Response<Invoices> result = instance.execute(request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getEntity()).isNotNull();
        Invoices entity = result.getEntity();
        assertThat(entity.getInvoice()).hasSize(1);
        AbstractInvoice invoice = entity.getInvoice().iterator().next();
        assertThat(invoice).isExactlyInstanceOf(NormalInvoice.class);
        assertThat(invoice.getId()).isEqualTo(6);
        assertThat(invoice.getPaymentState()).isEqualTo(AbstractInvoice.PaymentState.unpaid);
        assertThat(invoice.getNetto()).isEqualTo(Money.of(CurrencyUnit.of("PLN"), 765.45d));
    }

    @Test
    public void testExecute_Gateway_Request() throws WFirmaException {
        // given
        PostRequest<Contractors> request = FindRequest.create(Contractors.class);
        Gateway gateway = mock(Gateway.class);
        String unitTestResponse = "Unit Test Response";
        when(gateway.post(request)).thenReturn(unitTestResponse);

        // when
        String response = ApiExecutor.execute(gateway, request);

        // then
        assertThat(response).isEqualTo(unitTestResponse);
    }

    @Test
    public void testExecute_MockContext() throws WFirmaException {
        // given
        ApiContext ctx = mock(ApiContext.class);
        ApiExecutor instance = new ApiExecutor(ctx);
        GetRequest<Invoices> request = GetRequest.create(Invoices.class, 13L);
        GatewayFactory factory = mock(GatewayFactory.class);
        when(ctx.getGatewayFactory()).thenReturn(factory);
        Gateway gateway = mock(Gateway.class);
        when(factory.produce()).thenReturn(gateway);
        String unitTestResponse = resource("sample-invoises-response.xml");
        when(gateway.get(request)).thenReturn(unitTestResponse);

        // when
        Response<Invoices> response = instance.execute(request);

        // then
        assertThat(response).isNotNull();
        Invoices invoices = response.getEntity();
        assertThat(invoices).isNotNull();
        assertThat(invoices.getInvoice()).hasSize(1);
    }

    private static String resource(String name) {
        try (InputStream inputStream = ApiExecutorTest.class.getResourceAsStream(name)) {
            java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        } catch (IOException e) {
            throw new EidIllegalStateException("20151123:223050", e);
        }
    }

}
