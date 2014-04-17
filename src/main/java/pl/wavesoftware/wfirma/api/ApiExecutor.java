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

import java.net.URI;
import pl.wavesoftware.wfirma.api.mapper.AbstractWFirmaGatewayFactory;
import pl.wavesoftware.wfirma.api.mapper.Api;
import pl.wavesoftware.wfirma.api.mapper.ApiModule;
import pl.wavesoftware.wfirma.api.mapper.WFirmaGateway;
import pl.wavesoftware.wfirma.api.mapper.xml.JaxbMarshaller;
import pl.wavesoftware.wfirma.api.model.ApiEntityElement;
import pl.wavesoftware.wfirma.api.model.Credentials;
import pl.wavesoftware.wfirma.api.model.Request;
import pl.wavesoftware.wfirma.api.model.Response;
import pl.wavesoftware.wfirma.api.model.WFirmaException;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class ApiExecutor extends AbstractWFirmaGatewayFactory {

    private final ApiContext context;

    private WFirmaGateway gatewayInstance;

    protected String address = WFirmaGateway.GATEWAY_ADDRESS;

    /**
     * Default constructor for API executor
     *
     * @param context a API context
     */
    public ApiExecutor(ApiContext context) {
        super();
        this.context = context;
    }

    /**
     * Execute a {@link Request} throuth WFirma API2 and fetches a {@link Response} with objects that are being unpacked from response XML
     *
     * @param <T> a type of API entity element - the main element type.
     * @param request a request to be executed
     * @return a response from API
     * @throws WFirmaException if anything goes wrong. {@link pl.wavesoftware.wfirma.api.model.WFirmaSecurityException} is thrown if there
     * ware problem with security credentials.
     */
    public <T extends ApiEntityElement> Response<T> execute(Request<T> request) throws WFirmaException {
        String responseOutput = execute(getGateway(), request);
        JaxbResponse<T> response = new JaxbResponse<>(request.getEntityClass(), responseOutput);
        return response;
    }

    private WFirmaGateway getGateway() {
        if (gatewayInstance == null) {
            URI gatewayAddress = URI.create(address);
            gatewayInstance = create(context.getCredentials(), gatewayAddress);
        }
        return gatewayInstance;
    }

    @Override
    protected Class<? extends Credentials> getTypeForSimpleGateway() {
        return SimpleCredentials.class;
    }

    @Override
    protected Class<? extends Credentials> getTypeForOAuthGateway() {
        return OAuthCredentials.class;
    }

    private static class JaxbResponse<T extends ApiEntityElement> implements Response<T> {

        private final Class<T> entityClass;

        private final String response;

        private T entity;

        private boolean fetched = false;

        JaxbResponse(Class<T> entityClass, String response) {
            this.entityClass = entityClass;
            this.response = response;
        }

        @Override
        public T getEntity() {
            if (!fetched) {
                Class<? extends Api<T>> module = ApiModule.getModuleFor(entityClass);
                JaxbMarshaller<? extends Api> marshaller = JaxbMarshaller.create(module);
                Api api = marshaller.unmarshal(response);
                @SuppressWarnings("unchecked")
                T en = (T) api.getEntityElement();
                entity = en;
                fetched = true;
            }
            return entity;
        }

    }
}
