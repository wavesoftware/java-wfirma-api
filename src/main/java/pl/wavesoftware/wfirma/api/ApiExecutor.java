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
