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

import pl.wavesoftware.wfirma.api.model.AbstractFindRequest;
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
     * @param address a address to fetch
     * @return a string with a XML Response from WFirma
     * @throws WFirmaException if some error occured while fetching data
     */
    String get(RequestPath address) throws WFirmaException;

    /**
     * Fetches data from WFirma API2 by sending data with find request object
     *
     * @param address a address to fetch
     * @param findRequest a find request
     * @return a string with a XML Response from WFirma
     * @throws WFirmaException if some error occured while fetching data
     */
    String post(RequestPath address, AbstractFindRequest findRequest) throws WFirmaException;
}
