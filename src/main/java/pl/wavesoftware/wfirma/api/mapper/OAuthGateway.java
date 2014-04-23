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
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
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

    private String getAddress(Request<?> request) {
        StringBuilder builder = new StringBuilder(gateway.toString());
        builder.append(request.getAddress());
        return builder.toString();
    }

    @Override
    public String get(Request<?> request) throws WFirmaException {
        OAuthService service = new ServiceBuilder()
            .provider(WFirmaProvider.class)
            .apiKey(credentials.getKey())
            .apiSecret(credentials.getSecret())
            .build();
        Token token = service.getRequestToken();
        Verifier v = new Verifier("verifier you got from the user");
        Token accessToken = service.getAccessToken(token, v);
        OAuthRequest orequest = new OAuthRequest(Verb.GET, getAddress(request));
        service.signRequest(accessToken, orequest);
        Response response = orequest.send();
        return response.getBody();
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

    private static class WFirmaProvider extends DefaultApi10a {

        @Override
        public String getRequestTokenEndpoint() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getAccessTokenEndpoint() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getAuthorizationUrl(Token requestToken) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

}
