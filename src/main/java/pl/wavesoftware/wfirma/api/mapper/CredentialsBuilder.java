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

import pl.wavesoftware.wfirma.api.OAuthCredentials;
import pl.wavesoftware.wfirma.api.SimpleCredentials;
import pl.wavesoftware.wfirma.api.model.Credentials;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public final class CredentialsBuilder {

    private final String password;

    private final String login;

    private final Credentials credentials;

    private CredentialsBuilder(final OAuthCredentials credentials) {
        this.login = credentials.getConsumerKey();
        this.password = credentials.getConsumerSecret();
        this.credentials = credentials;
    }

    private CredentialsBuilder(final SimpleCredentials credentials) {
        this.login = credentials.getLogin();
        this.password = credentials.getPassword();
        this.credentials = credentials;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public static CredentialsBuilder from(final OAuthCredentials credentials) {
        return new CredentialsBuilder(credentials);
    }

    public static CredentialsBuilder from(final SimpleCredentials credentials) {
        return new CredentialsBuilder(credentials);
    }

}
