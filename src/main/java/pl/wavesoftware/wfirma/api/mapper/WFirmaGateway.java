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
