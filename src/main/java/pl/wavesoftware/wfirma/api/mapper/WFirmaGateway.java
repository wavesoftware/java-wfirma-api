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

import javax.annotation.Nonnull;
import pl.wavesoftware.wfirma.api.model.PostRequest;
import pl.wavesoftware.wfirma.api.model.Request;
import pl.wavesoftware.wfirma.api.model.WFirmaException;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public interface WFirmaGateway {

    /**
     * Address of WFirma API2 gateway
     */
    String GATEWAY_ADDRESS = "https://api2.wfirma.pl";

    /**
     * Fetches data from WFirma API2
     *
     * @param request a get request
     * @return a string with a XML Response from WFirma
     * @throws WFirmaException if some error occured while fetching data
     */
    String get(Request<?> request) throws WFirmaException;

    /**
     * Fetches data from WFirma API2 by sending data with find request object
     *
     * @param request a post request
     * @return a string with a XML Response from WFirma
     * @throws WFirmaException if some error occured while fetching data
     */
    String post(PostRequest<?> request) throws WFirmaException;

    /**
     * Adds listener for gateway
     *
     * @param listener a response listener
     */
    void addListener(@Nonnull ResponseListener listener);

    /**
     * Removes a response listener for gateway
     *
     * @param listener a response listener
     */
    void removeListener(@Nonnull ResponseListener listener);
}
