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
package pl.wavesoftware.wfirma.api.core.model.contractors;

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
 * &lt;complexType name="contractorsApiType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contractors" type="{}contractorsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contractorsApiType")
@XmlRootElement(name = Api.ROOT_ELEMENT)
public class ContractorsApi implements Api<Contractors> {

    @XmlElement(nillable = false)
    private Contractors contractors;

    /**
     * Default constructor
     */
    public ContractorsApi() {
        contractors = new Contractors(this);
    }

    public Contractors getContractors() {
        return contractors;
    }

    public void setContractors(Contractors contractors) {
        this.contractors = contractors;
    }

    @Override
    @XmlTransient
    public Collection<Class<? extends Request>> getSupportedRequests() {
        return collectRequests(
                GetRequest.class,
                AddRequest.class,
                FindRequest.class,
                DeleteRequest.class,
                EditRequest.class
        );
    }

    @Override
    @XmlTransient
    public Class<Contractors> getEntityClass() {
        return Contractors.class;
    }

    @Override
    @XmlTransient
    public Contractors getEntityElement() {
        return getContractors();
    }

    @Override
    public void setEntityElement(Contractors entityElement) {
        this.contractors = entityElement;
    }

}
