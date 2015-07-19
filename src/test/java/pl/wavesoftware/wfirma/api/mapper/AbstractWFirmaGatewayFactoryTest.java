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
package pl.wavesoftware.wfirma.api.mapper;

import java.net.URI;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import static org.mockito.Mockito.*;
import pl.wavesoftware.wfirma.api.OAuthCredentials;
import pl.wavesoftware.wfirma.api.SimpleCredentials;
import pl.wavesoftware.wfirma.api.model.Credentials;
import pl.wavesoftware.wfirma.api.model.PostRequest;
import pl.wavesoftware.wfirma.api.model.Request;
import pl.wavesoftware.wfirma.api.model.contractors.Contractors;
import pl.wavesoftware.wfirma.api.model.requests.AddRequest;
import pl.wavesoftware.wfirma.api.model.requests.GetRequest;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class AbstractWFirmaGatewayFactoryTest {

    @Test
    public void testGetTypeForSimpleGateway() {
        AbstractWFirmaGatewayFactory instance = new AbstractWFirmaGatewayFactoryImpl();
        Class<? extends Credentials> result = instance.getTypeForSimpleGateway();
        assertThat(result).isExactlyInstanceOf(Class.class);
    }

    @Test
    public void testGetTypeForOAuthGateway() {
        AbstractWFirmaGatewayFactory instance = new AbstractWFirmaGatewayFactoryImpl();
        Class<? extends Credentials> result = instance.getTypeForOAuthGateway();
        assertThat(result).isExactlyInstanceOf(Class.class);
    }

    @Test
    public void testCreate() {
        Credentials credentials = new SimpleCredentials("login", "password");
        URI gatewayAddress = URI.create("https://api.sandbox.wfirma.pl");
        AbstractWFirmaGatewayFactory instance = new AbstractWFirmaGatewayFactoryImpl();
        WFirmaGateway result = instance.create(credentials, gatewayAddress);
        assertThat(result).isExactlyInstanceOf(SimpleGateway.class);

        credentials = new OAuthCredentials("key", "secret");
        result = instance.create(credentials, gatewayAddress);
        assertThat(result).isExactlyInstanceOf(OAuthGateway.class);

        try {
            instance.create(new Credentials() {

                @Override
                public String getConsumerKey() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public String getConsumerSecret() {
                    throw new UnsupportedOperationException();
                }
            }, gatewayAddress);
            Assertions.failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
        } catch (RuntimeException ex) {
            assertThat(ex).isExactlyInstanceOf(UnsupportedOperationException.class);
            assertThat(ex).hasNoCause();
            assertThat(ex.getLocalizedMessage()).contains(
                    "Credentials `pl.wavesoftware.wfirma.api.mapper.AbstractWFirmaGatewayFactoryTest$1",
                    "` is not supported by this SDK.");

        }
    }

    @Test
    public void testExecute() throws Exception {
        WFirmaGateway gateway = mock(WFirmaGateway.class);
        Request<Contractors> request = GetRequest.create(Contractors.class, 5L);
        String expResult = "<expected>\n"
                + "    <response type=\"get\" />\n"
                + "</expected>";
        when(gateway.get(request)).thenReturn(expResult);

        String result = AbstractWFirmaGatewayFactory.execute(gateway, request);
        assertThat(result).isEqualTo(expResult);
        result = AbstractWFirmaGatewayFactory.execute(gateway, request);
        assertThat(result).isEqualTo(expResult);

    }

    @Test
    public void testExecutePost() throws Exception {
        WFirmaGateway gateway = mock(WFirmaGateway.class);
        Contractors contractors = new Contractors();
        PostRequest<Contractors> request = AddRequest.create(contractors);
        String expResult = "<expected>\n"
                + "    <response type=\"post\" />\n"
                + "</expected>";
        when(gateway.post(request)).thenReturn(expResult);

        String result = AbstractWFirmaGatewayFactory.execute(gateway, request);
        assertThat(result).isEqualTo(expResult);
    }

    private static class AbstractWFirmaGatewayFactoryImpl extends AbstractWFirmaGatewayFactory {

        public Class<? extends Credentials> getTypeForSimpleGateway() {
            return SimpleCredentials.class;
        }

        public Class<? extends Credentials> getTypeForOAuthGateway() {
            return OAuthCredentials.class;
        }
    }

}
