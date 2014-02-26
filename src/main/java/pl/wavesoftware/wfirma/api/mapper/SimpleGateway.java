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

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import pl.wavesoftware.wfirma.api.SimpleCredentials;
import pl.wavesoftware.wfirma.api.model.WFirmaException;
import pl.wavesoftware.wfirma.api.model.WFirmaSercurityException;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class SimpleGateway implements WFirmaGateway {

    private final SimpleCredentials credentials;

    private final URI gateway;

    private final ResponseChecker statusParser = new ResponseChecker();

    public SimpleGateway(SimpleCredentials credentials) {
        this.credentials = credentials;
        gateway = URI.create(GATEWAY_ADDRESS);
    }

    public SimpleGateway(SimpleCredentials credentials, URI gateway) {
        this.credentials = credentials;
        this.gateway = gateway;
    }

    @Override
    public String get(RequestPath requestPath) throws WFirmaException {
        String path;
        if ("/".equals(requestPath.path.substring(0, 1))) {
            path = requestPath.path;
        } else {
            path = "/" + requestPath.path;
        }
        HttpHost target = getTargetHost();
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(target.getHostName(), target.getPort()),
                new UsernamePasswordCredentials(credentials.getLogin(), credentials.getPassword()));
        try (CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build()) {

            // Create AuthCache instance
            AuthCache authCache = new BasicAuthCache();
            // Generate BASIC scheme object and add it to the local
            // auth cache
            BasicScheme basicAuth = new BasicScheme();
            authCache.put(target, basicAuth);

            // Add AuthCache to the execution context
            HttpClientContext localContext = HttpClientContext.create();
            localContext.setAuthCache(authCache);

            HttpGet httpget = new HttpGet(path);
            httpget.setHeader("Accept", "text/xml");

            try (CloseableHttpResponse response = httpclient.execute(target, httpget, localContext)) {
                return getContent(response);
            } catch (IOException ioe) {
                throw new WFirmaException(ioe);
            }
        } catch (IOException ioe) {
            throw new WFirmaException(ioe);
        }
    }

    private HttpHost getTargetHost() {
        int port = gateway.getPort();
        if (port < 1) {
            switch (gateway.getScheme().toLowerCase()) {
                case "http":
                    port = 80;
                    break;
                case "https":
                    port = 443;
                    break;
                default:
                    throw new RuntimeException("Unsupported URI scheme: "
                            + gateway.getScheme() + ", supporting only: `http` and `https`");
            }
        }
        return new HttpHost(gateway.getHost(), port, gateway.getScheme());
    }

    private String getContent(CloseableHttpResponse response) throws WFirmaException {
        switch (response.getStatusLine().getStatusCode()) {
            case 200:
                HttpEntity entity = response.getEntity();
                try {
                    String content = EntityUtils.toString(entity, Charset.forName("UTF-8"));
                    return statusParser.checkedForStatus(credentials.getLogin(), content);
                } catch (IOException | IllegalStateException ex) {
                    throw new RuntimeException(ex);
                }
            case 403:
                throw new WFirmaSercurityException("Auth failed for user: `%s`", credentials.getLogin());
            default:
                StatusLine status = response.getStatusLine();
                throw new WFirmaException("Connection error: %d - %s", status.getStatusCode(),
                        status.getReasonPhrase());
        }
    }

}
