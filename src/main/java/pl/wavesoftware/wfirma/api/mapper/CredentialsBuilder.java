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

import pl.wavesoftware.wfirma.api.OAuthCredentials;
import pl.wavesoftware.wfirma.api.SimpleCredentials;
import pl.wavesoftware.wfirma.api.model.Credentials;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
final class CredentialsBuilder {

    private final String secret;

    private final String key;

    private CredentialsBuilder(final OAuthCredentials credentials) {
        this.key = credentials.getConsumerKey();
        this.secret = credentials.getConsumerSecret();
    }

    private CredentialsBuilder(final SimpleCredentials credentials) {
        this.key = credentials.getLogin();
        this.secret = credentials.getPassword();
    }

    /**
     * Getter for key
     *
     * @return a key
     */
    public String getKey() {
        return key;
    }

    /**
     * A getter for secret
     *
     * @return a secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Builder for credentials
     *
     * @param credentials a credentials
     * @return a builder
     */
    public static CredentialsBuilder from(final Credentials credentials) {
        CredentialsBuilder builder;
        if (credentials instanceof SimpleCredentials) {
            builder = new CredentialsBuilder((SimpleCredentials) credentials);
        } else if (credentials instanceof OAuthCredentials) {
            builder = new CredentialsBuilder((OAuthCredentials) credentials);
        } else {
            throw new UnsupportedOperationException("Credentials `" + credentials + "` is not supported by this SDK.");
        }
        return builder;
    }

}
