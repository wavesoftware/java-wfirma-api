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

import com.google.common.annotations.VisibleForTesting;
import pl.wavesoftware.wfirma.api.core.mapper.xml.JaxbResponse;
import pl.wavesoftware.wfirma.api.core.model.ApiEntityElement;
import pl.wavesoftware.wfirma.api.core.model.Gateway;
import pl.wavesoftware.wfirma.api.core.model.GatewayFactory;
import pl.wavesoftware.wfirma.api.core.model.PostRequest;
import pl.wavesoftware.wfirma.api.core.model.Request;
import pl.wavesoftware.wfirma.api.core.model.Response;
import pl.wavesoftware.wfirma.api.core.model.exceptions.WFirmaException;
import pl.wavesoftware.wfirma.api.core.model.exceptions.WFirmaSecurityException;

import java.net.URI;

/**
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class ApiExecutor {

    private final ApiContext context;

    private Gateway gatewayInstance;

    private String address;

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
     * Execute a {@link Request} throuth WFirma API2 and fetches a {@link Response} with objects that are being unpacked from
     * response XML
     *
     * @param <T>     a type of API entity element - the main element type.
     * @param request a request to be executed
     * @return a response from API
     * @throws WFirmaException if anything goes wrong. {@link WFirmaSecurityException} is thrown
     *      if there ware problem with security credentials.
     */
    public <T extends ApiEntityElement> Response<T> execute(Request<T> request) throws WFirmaException {
        String responseOutput = execute(getGateway(), request);
        return new JaxbResponse<>(request.getEntityClass(), responseOutput);
    }

    @VisibleForTesting
    protected static String execute(Gateway gateway, Request<?> request) throws WFirmaException {
        String response;
        if (request instanceof PostRequest) {
            response = gateway.post(PostRequest.class.cast(request));
        } else {
            response = gateway.get(request);
        }
        return response;
    }

    @VisibleForTesting
    protected void setAddress(String address) {
        this.address = address;
    }

    private Gateway getGateway() {
        if (gatewayInstance == null) {
            GatewayFactory factory = prepare(context.getGatewayFactory());
            gatewayInstance = factory.produce();
        }
        return gatewayInstance;
    }

    private GatewayFactory prepare(GatewayFactory factory) {
        if (address != null) {
            URI gatewayAddress = URI.create(address);
            factory.setAlternativeAddress(gatewayAddress);
        }
        return factory;
    }

}
