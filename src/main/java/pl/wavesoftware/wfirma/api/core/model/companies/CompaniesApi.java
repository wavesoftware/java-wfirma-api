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
package pl.wavesoftware.wfirma.api.core.model.companies;

import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import pl.wavesoftware.wfirma.api.core.model.Api;
import static pl.wavesoftware.wfirma.api.core.model.ApiModule.collectRequests;
import pl.wavesoftware.wfirma.api.core.model.Request;

/**
 * <p>
 * Java class for apiType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="apiType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="companies" type="{}companiesType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "companiesApiType")
@XmlRootElement(name = "domain")
public class CompaniesApi implements Api<Companies> {

    @XmlElement(nillable = false)
    private Companies companies;

    /**
     * Constructor
     */
    public CompaniesApi() {
        companies = new Companies(this);
    }

    public void setCompanies(Companies companies) {
        this.companies = companies;
    }

    public Companies getCompanies() {
        return companies;
    }

    @Override
    @XmlTransient
    public Collection<Class<? extends Request>> getSupportedRequests() {
        return collectRequests(CompaniesGetRequest.class);
    }

    @Override
    @XmlTransient
    public Class<Companies> getEntityClass() {
        return Companies.class;
    }

    @Override
    @XmlTransient
    public Companies getEntityElement() {
        return getCompanies();
    }

    @Override
    public void setEntityElement(Companies entityElement) {
        this.companies = entityElement;
    }

}
