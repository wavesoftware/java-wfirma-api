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

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.mockito.Mockito;
import pl.wavesoftware.wfirma.api.OAuthCredentials;
import pl.wavesoftware.wfirma.api.model.Request;
import pl.wavesoftware.wfirma.api.model.companies.Companies;
import pl.wavesoftware.wfirma.api.model.requests.FindRequest;
import pl.wavesoftware.wfirma.api.model.requests.GetRequest;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class OAuthGatewayTest {

    private final OAuthCredentials creds;

    public OAuthGatewayTest() {
        creds = new OAuthCredentials("key", "value");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGet() throws Exception {
        Request<Companies> request = GetRequest.create(Companies.class, 5L);
        OAuthGateway instance = new OAuthGateway(creds, null);
        assertThat(instance.get(request)).isNotNull();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testPost() throws Exception {
        FindRequest<Companies> request = FindRequest.create(Companies.class);
        OAuthGateway instance = new OAuthGateway(creds, null);
        assertThat(instance.post(request)).isNotNull();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddListener() {
        ResponseListener listener = Mockito.mock(ResponseListener.class);
        OAuthGateway instance = new OAuthGateway(creds, null);
        instance.addListener(listener);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveListener() {
        ResponseListener listener = Mockito.mock(ResponseListener.class);
        OAuthGateway instance = new OAuthGateway(creds, null);
        instance.removeListener(listener);
    }

}
