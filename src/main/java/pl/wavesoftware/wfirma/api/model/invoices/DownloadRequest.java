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
package pl.wavesoftware.wfirma.api.model.invoices;

import pl.wavesoftware.wfirma.api.mapper.ApiModule;
import pl.wavesoftware.wfirma.api.mapper.RequestPath;
import pl.wavesoftware.wfirma.api.mapper.xml.JaxbMarshaller;
import pl.wavesoftware.wfirma.api.model.PostRequest;
import pl.wavesoftware.wfirma.api.model.logic.Parameter;
import pl.wavesoftware.wfirma.api.model.logic.Parameters;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public final class DownloadRequest implements PostRequest<Invoices> {

    private final Long wFirmaId;

    private final Invoices invoices;

    private String dump(Boolean bool) {
        Integer value = bool ? 1 : 0;
        return value.toString();
    }

    /**
     * Creates a DownloadRequest for an invoice
     *
     * @param wFirmaId a ID of wfirma
     * @return a request
     */
    public static DownloadRequest create(Long wFirmaId) {
        return new DownloadRequest(wFirmaId);
    }

    /**
     * Creates a DownloadRequest for an invoice
     *
     * @param wFirmaId a ID of wfirma
     * @param page print the original and copy, or print only the original or print only the copy
     * @param printAddress mailing address of the buyer on the back of the original invoice, placed in such a position that the submission
     * of the invoice to the size of the DL in Z, the address was at the height of the window in the envelope
     * @param printLeaflet leaflet transfer is generated only for invoices with payment method "transfer" in PLN.
     * @param printAsDuplicate when our contractor loses an invoice from us, you should give him a duplicate
     * @return a request
     */
    public static DownloadRequest create(Long wFirmaId, InvoicePage page, boolean printAddress, boolean printLeaflet,
            boolean printAsDuplicate) {
        return new DownloadRequest(wFirmaId, page, printAddress, printLeaflet, printAsDuplicate);
    }

    private DownloadRequest(Long wFirmaId) {
        this(wFirmaId, InvoicePage.invoice, false, false, false);
    }

    private DownloadRequest(Long wFirmaId, InvoicePage page, boolean printAddress, boolean printLeaflet,
            boolean printAsDuplicate) {
        this.wFirmaId = wFirmaId;
        this.invoices = new Invoices();
        Parameters params = new Parameters();
        params.getParameter().add(new Parameter("page", page.name()));
        params.getParameter().add(new Parameter("address", dump(printAddress)));
        params.getParameter().add(new Parameter("leaflet", dump(printLeaflet)));
        params.getParameter().add(new Parameter("duplicate", dump(printAsDuplicate)));
        invoices.setParameters(params);
    }

    @Override
    public Invoices getEntity() {
        return invoices;
    }

    @Override
    public String getBody() {
        return JaxbMarshaller.createFor(invoices.getApi()).marshal(invoices.getApi());
    }

    @Override
    public RequestPath getAddress() {
        return RequestPath.fromString(ApiModule.getRequestModulePath(InvoicesApi.class), "download", wFirmaId.toString());
    }

    @Override
    public Class<Invoices> getEntityClass() {
        return Invoices.class;
    }

    /**
     * A print type of invoice
     */
    public static enum InvoicePage {

        all, invoice, copy
    }

}
