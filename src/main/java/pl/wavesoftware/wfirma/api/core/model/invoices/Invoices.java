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
package pl.wavesoftware.wfirma.api.core.model.invoices;

import pl.wavesoftware.wfirma.api.core.model.Api;
import pl.wavesoftware.wfirma.api.core.model.ApiEntityElement;
import pl.wavesoftware.wfirma.api.core.model.Parametrizable;
import pl.wavesoftware.wfirma.api.core.model.logic.Parameters;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Java class for invoicesType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="invoicesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="invoice" type="{}invoiceType" maxOccurs="unbounded"/>
 *         &lt;element name="parameters" type="{}parametersType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "invoicesType")
public class Invoices implements Parametrizable, ApiEntityElement {

    /**
     * Api wrapper root element
     */
    @XmlTransient
    private Api<Invoices> api;

    /**
     * Constructor with API root
     *
     * @param api {@link #api}
     */
    public Invoices(Api<Invoices> api) {
        setApi(api);
    }

    /**
     * Default constructor
     */
    public Invoices() {
        this(new InvoicesApi());
    }

    @XmlElement(required = true)
    protected List<AbstractInvoice> invoice;

    @XmlElement(required = false)
    protected Parameters parameters;

    /**
     * Gets the value of the invoice property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list
     * will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the invoice property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvoice().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link AbstractInvoice }
     *
     *
     * @return a list of invoices
     */
    public List<AbstractInvoice> getInvoice() {
        if (invoice == null) {
            invoice = new ArrayList<>();
        }
        return this.invoice;
    }

    /**
     * Sets the value of the invoice property.
     *
     * @param invoices a list of invoices to set
     */
    protected void setInvoice(List<AbstractInvoice> invoices) {
        this.invoice = invoices;
    }

    /**
     * Gets the value of the parameters property.
     *
     * @return possible object is {@link Parameters }
     *
     */
    @Override
    public Parameters getParameters() {
        return parameters;
    }

    /**
     * Sets the value of the parameters property.
     *
     * @param value allowed object is {@link Parameters }
     *
     */
    @Override
    public void setParameters(Parameters value) {
        this.parameters = value;
    }

    @Override
    public Api<Invoices> getApi() {
        return api;
    }

    private void setApi(Api<Invoices> invoicesApi) {
        this.api = invoicesApi;
        invoicesApi.setEntityElement(this);
    }

}
