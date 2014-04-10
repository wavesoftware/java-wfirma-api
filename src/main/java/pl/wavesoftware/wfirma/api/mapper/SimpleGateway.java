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

import com.google.common.base.Charsets;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import pl.wavesoftware.wfirma.api.SimpleCredentials;
import pl.wavesoftware.wfirma.api.model.PostRequest;
import pl.wavesoftware.wfirma.api.model.Request;
import pl.wavesoftware.wfirma.api.model.WFirmaException;
import pl.wavesoftware.wfirma.api.model.WFirmaSecurityException;
import pl.wavesoftware.wfirma.api.model.validation.RequestValidator;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
class SimpleGateway implements WFirmaGateway {

    private final SimpleCredentials credentials;

    private final URI gateway;

    private final ResponseChecker statusParser = new ResponseChecker();

    private final Set<ResponseListener> listeners = new HashSet<>();

    /**
     * Constructor
     *
     * @param credentials a simple credentials
     */
    public SimpleGateway(SimpleCredentials credentials) {
        this.credentials = credentials;
        gateway = URI.create(GATEWAY_ADDRESS);
    }

    /**
     * A junit overwritable method
     *
     * @param credentials a simple credentials
     * @param gateway a gateway address
     */
    protected SimpleGateway(SimpleCredentials credentials, URI gateway) {
        this.credentials = credentials;
        this.gateway = gateway;
    }

    @Override
    public void addListener(@Nonnull ResponseListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(@Nonnull ResponseListener listener) {
        listeners.remove(listener);
    }

    private String get(@Nonnull HttpRequest httpRequest) throws WFirmaException {
        httpRequest.setHeader("Accept", "text/xml");
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

            try (CloseableHttpResponse response = httpclient.execute(target, httpRequest, localContext)) {
                return getContent(response);
            } catch (IOException ioe) {
                throw new WFirmaException(ioe);
            }
        } catch (IOException ioe) {
            throw new WFirmaException(ioe);
        }
    }

    @Override
    @Nonnull
    public String get(@Nonnull Request request) throws WFirmaException {
        new RequestValidator(request).validate();
        HttpGet httpget = new HttpGet(request.getAddress().getCorrectedPath());
        return get(httpget);
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
                    for (ResponseListener responseListener : listeners) {
                        responseListener.responseRecived(content);
                    }
                    return statusParser.checkedForStatus(credentials.getLogin(), content);
                } catch (IOException | IllegalStateException ex) {
                    throw new RuntimeException(ex);
                }
            case 403:
                throw new WFirmaSecurityException("Auth failed for user: `%s`", credentials.getLogin());
            default:
                StatusLine status = response.getStatusLine();
                throw new WFirmaException("Connection error: %d - %s", status.getStatusCode(),
                        status.getReasonPhrase());
        }
    }

    @Override
    @Nonnull
    public String post(@Nonnull PostRequest<?> request) throws WFirmaException {
        new RequestValidator(request).validate();
        String body = request.getBody();
        HttpPost post = new HttpPost(request.getAddress().getCorrectedPath());
        post.setEntity(new StringEntity(body, Charsets.UTF_8));
        return get(post);
    }

}
