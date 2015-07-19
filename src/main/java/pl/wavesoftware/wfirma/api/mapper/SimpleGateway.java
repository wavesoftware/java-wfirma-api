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

import com.google.common.base.Charsets;
import java.io.IOException;
import java.net.URI;
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
import pl.wavesoftware.wfirma.api.runtime.FatalSdkException;
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

    private final CredentialsBuilder credentials;

    private final URI gateway;

    private final ResponseChecker statusParser = new ResponseChecker();

    private final Set<ResponseListener> listeners = new HashSet<>();

    /**
     * A default constructor
     *
     * @param credentials a credentials
     * @param gateway a gateway address
     */
    SimpleGateway(CredentialsBuilder credentials, URI gateway) {
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
            new UsernamePasswordCredentials(credentials.getKey(), credentials.getSecret()));
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
                    throw new FatalSdkException("20150716:113056", "Unsupported URI scheme: "
                        + gateway.getScheme() + ", supporting only: `http` and `https`");
            }
        }
        return new HttpHost(gateway.getHost(), port, gateway.getScheme());
    }

    private String getContent(CloseableHttpResponse response) throws WFirmaException, IOException {
        switch (response.getStatusLine().getStatusCode()) {
            case 200:
                return handleCode200OK(response);
            case 403:
                throw new WFirmaSecurityException("Auth failed for user: `%s`", credentials.getKey());
            default:
                StatusLine status = response.getStatusLine();
                throw new WFirmaException("Connection error: %d - %s", status.getStatusCode(),
                    status.getReasonPhrase());
        }
    }

    private String handleCode200OK(CloseableHttpResponse response) throws IOException, WFirmaException {
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity, Charsets.UTF_8);
        for (ResponseListener responseListener : listeners) {
            responseListener.responseRecived(content);
        }
        return statusParser.checkedForStatus(credentials.getKey(), content);
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
