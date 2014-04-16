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
class OAuthGateway implements WFirmaGateway {

    private final Credentials credentials;

    private final URI gateway;

    /**
     * Getaway for OAuth
     *
     * @param credentials OAuth cerentials
     */
    OAuthGateway(Credentials credentials, URI gateway) {
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
