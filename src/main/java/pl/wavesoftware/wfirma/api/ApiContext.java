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

import pl.wavesoftware.wfirma.api.core.model.GatewayFactory;
import pl.wavesoftware.wfirma.api.oauth.model.OAuthCredentials;
import pl.wavesoftware.wfirma.api.simple.model.SimpleCredentials;
import pl.wavesoftware.wfirma.api.simple.mapper.SimpleGatewayFactory;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class ApiContext {

    private final GatewayFactory gatewayFactory;

    /**
     * Constructor with OAuth methon
     *
     * @param credentials a OAuth credentials
     */
    public ApiContext(OAuthCredentials credentials) {
        throw new UnsupportedOperationException("OAuth method is not yet implemented");
    }

    /**
     * Constructor with Simple method
     *
     * @param credentials a simple credentials
     */
    public ApiContext(SimpleCredentials credentials) {
        this.gatewayFactory = new SimpleGatewayFactory(credentials);
    }

    /**
     * Gets a gateway factory
     * @return a factory for given context
     */
    public GatewayFactory getGatewayFactory() {
        return gatewayFactory;
    }

}
