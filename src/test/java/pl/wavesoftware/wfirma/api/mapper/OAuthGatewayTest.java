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

    private final CredentialsBuilder creds;

    public OAuthGatewayTest() {
        creds = CredentialsBuilder.from(new OAuthCredentials("key", "value"));
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
