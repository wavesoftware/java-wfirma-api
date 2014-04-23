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
package pl.wavesoftware.wfirma.api;

import java.util.Properties;
import pl.wavesoftware.wfirma.api.model.Credentials;
import pl.wavesoftware.wfirma.api.model.Credentials.HasProperties;

/**
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class OAuthCredentials implements Credentials, HasProperties {

    private static final long serialVersionUID = 1L;

    private final String consumerSecret;

    private final String consumerKey;

    private Properties properties;

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

    @Override
    public Properties getProperties() {
        if (this.properties == null) {
            this.properties = new Properties();
        }
        return this.properties;
    }
}
