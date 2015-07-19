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
import pl.wavesoftware.wfirma.api.model.PostRequest;
import pl.wavesoftware.wfirma.api.model.Request;
import pl.wavesoftware.wfirma.api.model.WFirmaException;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
class OAuthGateway implements WFirmaGateway {

    private final CredentialsBuilder credentials;

    private final URI gateway;

    /**
     * Getaway for OAuth
     *
     * @param credentials cerentials
     */
    OAuthGateway(CredentialsBuilder credentials, URI gateway) {
        this.credentials = credentials;
        this.gateway = gateway;
    }

    @Override
    public String get(Request<?> request) throws WFirmaException {
        // FIXME: Not yet implemented!!!
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String post(PostRequest<?> request) throws WFirmaException {
        // FIXME: Not yet implemented!!!
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addListener(ResponseListener listener) {
        // FIXME: Not yet implemented!!!
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeListener(ResponseListener listener) {
        // FIXME: Not yet implemented!!!
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
