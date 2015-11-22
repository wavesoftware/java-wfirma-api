/*
 * Copyright (c) 2015 Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
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

package pl.wavesoftware.wfirma.api.simple.mapper;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.wavesoftware.eid.utils.EidPreconditions;
import pl.wavesoftware.wfirma.api.core.model.Gateway;
import pl.wavesoftware.wfirma.api.core.model.GatewayFactory;
import pl.wavesoftware.wfirma.api.simple.model.SimpleCredentials;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 * @since 2015-09-25
 */
@RequiredArgsConstructor
public class SimpleGatewayFactory implements GatewayFactory {

    private final SimpleCredentials credentials;

    @Setter
    private URI alternativeAddress;

    @Override
    public Gateway produce() {
        return new SimpleGateway(credentials, getAddress());
    }

    private URI getAddress() {
        return alternativeAddress != null ? alternativeAddress : getDefaultAddress();
    }

    private URI getDefaultAddress() {
        return EidPreconditions.tryToExecute(new EidPreconditions.UnsafeSupplier<URI>() {
            @Override
            public URI get() throws URISyntaxException {
                return new URI(Gateway.GATEWAY_ADDRESS);
            }
        }, "20150927:210839");
    }
}
