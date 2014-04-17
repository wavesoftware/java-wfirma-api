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
