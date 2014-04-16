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
        WFirmaGateway gateway;
        if (getTypeForSimpleGateway().isAssignableFrom(credentials.getClass())) {
            gateway = new SimpleGateway(credentials, gatewayAddress);
        } else if (getTypeForOAuthGateway().isAssignableFrom(credentials.getClass())) {
            gateway = new OAuthGateway(credentials, gatewayAddress);
        } else {
            throw new UnsupportedOperationException("credentials `" + credentials + "` is not supported by this SDK.");
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
