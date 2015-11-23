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
package pl.wavesoftware.wfirma.api.simple.model;

import pl.wavesoftware.wfirma.api.core.model.Credentials;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class SimpleCredentials implements Credentials {

    private final String password;

    private final String login;

    /**
     * Default constructor
     *
     * @param login a login
     * @param password a password
     */
    public SimpleCredentials(final String login, final String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String getConsumerKey() {
        return login;
    }

    @Override
    public String getConsumerSecret() {
        return password;
    }
}
