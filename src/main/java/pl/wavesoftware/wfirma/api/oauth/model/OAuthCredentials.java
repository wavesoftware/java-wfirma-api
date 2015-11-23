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
package pl.wavesoftware.wfirma.api.oauth.model;

import pl.wavesoftware.wfirma.api.core.model.Credentials;

/**
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class OAuthCredentials implements Credentials {

    private final String consumerSecret;

    private final String consumerKey;

    /**
     * Default constructor
     *
     * @param consumerKey a customer key
     * @param consumerSecret a customer secret
     */
    public OAuthCredentials(final String consumerKey, final String consumerSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }

    @Override
    public String getConsumerKey() {
        return consumerKey;
    }

    @Override
    public String getConsumerSecret() {
        return consumerSecret;
    }
}
