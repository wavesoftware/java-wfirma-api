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

package pl.wavesoftware.wfirma.api.core.model;

import java.net.URI;

/**
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 * @since 2015-09-25
 */
public interface GatewayFactory {

    /**
     * Sets alternative address for gateway
     * @param alternativeAddress an alternative address to be used for connection thru produced gateway
     */
    void setAlternativeAddress(URI alternativeAddress);

    /**
     * Produce a WFirma gateway for all connections
     * @return a gateway to be used for connections
     */
    Gateway produce();
}
