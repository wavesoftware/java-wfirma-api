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

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import java.io.IOException;
import java.net.ServerSocket;
import static org.assertj.core.api.Assertions.assertThat;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import pl.wavesoftware.wfirma.api.model.Credentials;
import pl.wavesoftware.wfirma.api.model.Response;
import pl.wavesoftware.wfirma.api.model.invoices.AbstractInvoice;
import pl.wavesoftware.wfirma.api.model.invoices.Invoices;
import pl.wavesoftware.wfirma.api.model.invoices.NormalInvoice;
import pl.wavesoftware.wfirma.api.model.requests.GetRequest;

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
        String expResultGateway = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<api>\n"
                + "    <invoices>\n"
                + "        <invoice>\n"
                + "            <id>6</id>\n"
                + "            <type>normal</type>\n"
                + "            <netto>765.45</netto>\n"
                + "            <paymentstate>unpaid</paymentstate>\n"
                + "        </invoice>\n"
                + "    </invoices>\n"
                + "    <status>\n"
                + "        <code>OK</code>\n"
                + "    </status>\n"
                + "</api>\n"
                + "\n";
        stubFor(get(urlEqualTo("/invoices/get/6"))
                .withHeader("Accept", equalTo("text/xml"))
                .withHeader("Authorization", equalTo("Basic bG9naW4xOnBhc3Mx"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/xml")
                        .withBody(expResultGateway)));
    }

    @Test
    public void testExecute() throws Exception {
        ApiContext ctx = new ApiContext(new SimpleCredentials("login1", "pass1"));
        ApiExecutor instance = new ApiExecutor(ctx);
        instance.address = mockAddress;
        GetRequest<Invoices> request = GetRequest.create(Invoices.class, 6L);
        Response<Invoices> result = instance.execute(request);
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
    public void testGetTypeForSimpleGateway() {
        ApiContext ctx = new ApiContext(new SimpleCredentials("login1", "pass1"));
        ApiExecutor instance = new ApiExecutor(ctx);
        Class<? extends Credentials> expResult = SimpleCredentials.class;
        Class<? extends Credentials> result = instance.getTypeForSimpleGateway();
        assertThat(result).isEqualTo(expResult);
    }

    @Test
    public void testGetTypeForOAuthGateway() {
        ApiContext ctx = new ApiContext(new SimpleCredentials("login1", "pass1"));
        ApiExecutor instance = new ApiExecutor(ctx);
        Class<? extends Credentials> expResult = OAuthCredentials.class;
        Class<? extends Credentials> result = instance.getTypeForOAuthGateway();
        assertThat(result).isEqualTo(expResult);
    }

}
