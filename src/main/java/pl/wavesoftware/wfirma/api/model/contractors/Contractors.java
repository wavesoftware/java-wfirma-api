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
package pl.wavesoftware.wfirma.api.model.contractors;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import pl.wavesoftware.wfirma.api.mapper.Api;
import pl.wavesoftware.wfirma.api.model.ApiEntityElement;
import pl.wavesoftware.wfirma.api.model.Parametrizable;
import pl.wavesoftware.wfirma.api.model.logic.Parameters;

/**
 * <p>
 * Java class for contractorsType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="contractorsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contractor" type="{}contractorType" maxOccurs="unbounded"/>
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
@XmlType(name = "contractorsType")
public class Contractors implements Parametrizable, ApiEntityElement {

    @XmlTransient
    private Api<Contractors> api;

    /**
     * Constructor with API
     *
     * @param api api
     */
    public Contractors(Api<Contractors> api) {
        setApi(api);
    }

    /**
     * Default constructor
     */
    public Contractors() {
        this(new ContractorsApi());
    }

    @XmlElement(required = true)
    protected List<Contractor> contractor;

    @XmlElement(required = false)
    protected Parameters parameters;

    /**
     * Gets the value of the contractor property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list
     * will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the contractor property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContractor().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Contractor }
     *
     *
     * @return a list of contractors
     */
    public List<Contractor> getContractor() {
        if (contractor == null) {
            contractor = new ArrayList<>();
        }
        return this.contractor;
    }

    /**
     * Sets the value of the contractor property.
     *
     * @param contractors a list of contractors to set
     */
    public void setContractor(List<Contractor> contractors) {
        this.contractor = contractors;
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
    public Api<Contractors> getApi() {
        return api;
    }

    private void setApi(Api<Contractors> contractorsApi) {
        this.api = contractorsApi;
        contractorsApi.setEntityElement(this);
    }

}
