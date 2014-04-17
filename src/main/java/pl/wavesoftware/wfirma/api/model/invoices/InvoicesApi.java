/*
 * The MIT License
 *
 * Copyright 2014 Krzysztof Suszyński <krzysztof.suszynski@gmail.com>.
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

import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import pl.wavesoftware.wfirma.api.mapper.Api;
import static pl.wavesoftware.wfirma.api.mapper.ApiModule.collectRequests;
import pl.wavesoftware.wfirma.api.mapper.xml.UsesXmlCustomFormatter;
import pl.wavesoftware.wfirma.api.mapper.xml.UsesXmlCustomFormatter.Param;
import pl.wavesoftware.wfirma.api.mapper.xml.XsiTypeToObjectPropertyFormatter;
import pl.wavesoftware.wfirma.api.model.Request;
import pl.wavesoftware.wfirma.api.model.requests.AddRequest;
import pl.wavesoftware.wfirma.api.model.requests.DeleteRequest;
import pl.wavesoftware.wfirma.api.model.requests.EditRequest;
import pl.wavesoftware.wfirma.api.model.requests.FindRequest;
import pl.wavesoftware.wfirma.api.model.requests.GetRequest;

/**
 * <p>
 * Java class for parametersType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="invoicesApiType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="invoices" type="{}invoicesType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "invoicesApiType", propOrder = {
    "invoices"
})
@XmlRootElement(name = "api")
@UsesXmlCustomFormatter(value = XsiTypeToObjectPropertyFormatter.class, parameters = {
    @Param(key = XsiTypeToObjectPropertyFormatter.FIELD, value = "type")
})
public class InvoicesApi implements Api<Invoices> {

    /**
     * a invoices main element in api
     */
    @XmlElement(nillable = false)
    private Invoices invoices;

    /**
     * Constructor
     */
    public InvoicesApi() {
        invoices = new Invoices(this);
    }

    /**
     * @return {@link  #invoices}
     */
    public Invoices getInvoices() {
        return invoices;
    }

    /**
     * @param invoices {@link #invoices}
     */
    public void setInvoices(Invoices invoices) {
        this.invoices = invoices;
    }

    @Override
    @XmlTransient
    public Collection<Class<? extends Request>> getSupportedRequests() {
        return collectRequests(
                GetRequest.class,
                AddRequest.class,
                FindRequest.class,
                DeleteRequest.class,
                EditRequest.class,
                DownloadRequest.class
        );
    }

    @Override
    @XmlTransient
    public Class<Invoices> getEntityClass() {
        return Invoices.class;
    }

    @Override
    @XmlTransient
    public Invoices getEntityElement() {
        return getInvoices();
    }

    @Override
    public void setEntityElement(Invoices entityElement) {
        this.invoices = entityElement;
    }

}
