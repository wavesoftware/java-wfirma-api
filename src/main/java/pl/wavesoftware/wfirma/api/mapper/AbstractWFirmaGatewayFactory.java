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
import pl.wavesoftware.wfirma.api.model.Credentials;
import pl.wavesoftware.wfirma.api.model.PostRequest;
import pl.wavesoftware.wfirma.api.model.Request;
import pl.wavesoftware.wfirma.api.model.WFirmaException;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public abstract class AbstractWFirmaGatewayFactory {

    /**
     * Gets a type for simple gateway
     *
     * @return a type
     */
    protected abstract Class<? extends Credentials> getTypeForSimpleGateway();

    /**
     * Gets a type for OAuth gateway
     *
     * @return a type
     */
    protected abstract Class<? extends Credentials> getTypeForOAuthGateway();

    /**
     * Creates a gateway for end user
     *
     * @param credentials a credentials
     * @param gatewayAddress a gateway address
     * @return a created gateway
     */
    protected WFirmaGateway create(Credentials credentials, URI gatewayAddress) {
        WFirmaGateway gateway = null;
        CredentialsBuilder builder = CredentialsBuilder.from(credentials);
        if (getTypeForSimpleGateway().isAssignableFrom(credentials.getClass())) {
            gateway = new SimpleGateway(builder, gatewayAddress);
        } else if (getTypeForOAuthGateway().isAssignableFrom(credentials.getClass())) {
            gateway = new OAuthGateway(builder, gatewayAddress);
        }
        return gateway;
    }

    protected static String execute(WFirmaGateway gateway, Request<?> request) throws WFirmaException {
        String response;
        if (request instanceof PostRequest) {
            response = gateway.post(PostRequest.class.cast(request));
        } else {
            response = gateway.get(request);
        }
        return response;
    }
}
