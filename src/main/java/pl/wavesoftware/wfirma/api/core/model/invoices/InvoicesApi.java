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

import pl.wavesoftware.wfirma.api.core.mapper.xml.UsesXmlCustomFormatter;
import pl.wavesoftware.wfirma.api.core.mapper.xml.UsesXmlCustomFormatter.Param;
import pl.wavesoftware.wfirma.api.core.mapper.xml.XsiTypeToObjectPropertyFormatter;
import pl.wavesoftware.wfirma.api.core.model.Api;
import pl.wavesoftware.wfirma.api.core.model.Request;
import pl.wavesoftware.wfirma.api.core.model.requests.AddRequest;
import pl.wavesoftware.wfirma.api.core.model.requests.DeleteRequest;
import pl.wavesoftware.wfirma.api.core.model.requests.EditRequest;
import pl.wavesoftware.wfirma.api.core.model.requests.FindRequest;
import pl.wavesoftware.wfirma.api.core.model.requests.GetRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;

import static pl.wavesoftware.wfirma.api.core.model.ApiModule.collectRequests;

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
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "invoicesApiType")
@XmlRootElement(name = Api.ROOT_ELEMENT)
@UsesXmlCustomFormatter(
        value = XsiTypeToObjectPropertyFormatter.class,
        parameters = {
            @Param(key = XsiTypeToObjectPropertyFormatter.FIELD, value = "type")
        }
)
public class InvoicesApi implements Api<Invoices> {

    /**
     * a invoices main element in domain
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
