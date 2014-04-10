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
package pl.wavesoftware.wfirma.api.model.companies;

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
 * Java class for companiesType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="companiesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="company" type="{}companyType" maxOccurs="unbounded"/>
 *         &lt;element name="parameters" type="{}parametersType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "companiesType", propOrder = {
    "company",
    "parameters"
})
public class Companies implements Parametrizable, ApiEntityElement {

    @XmlTransient
    private final Api api;

    @Override
    public Long getId() {
        if (getCompany().size() != 1) {
            throw new IllegalStateException("Can't get ID for multiple elements!");
        }
        Long id = getCompany().iterator().next().getId();
        if (id == null) {
            throw new IllegalStateException("ID is not set!");
        }
        return id;
    }

    public Companies(Api api) {
        this.api = api;
    }

    public Companies() {
        api = new CompaniesApi();
        setApi((CompaniesApi) api);
    }

    @XmlElement(required = true)
    protected List<Company> company;

    @XmlElement(required = false)
    protected Parameters parameters;

    /**
     * Gets the value of the company property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the company property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCompany().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Company }
     *
     *
     * @return a list of companys
     */
    public List<Company> getCompany() {
        if (company == null) {
            company = new ArrayList<>();
        }
        return this.company;
    }

    /**
     * Sets the value of the company property.
     *
     * @param companys a list of companys to set
     */
    public void setCompany(List<Company> companys) {
        this.company = companys;
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
    public Api getApi() {
        return api;
    }

    private void setApi(CompaniesApi companysApi) {
        companysApi.setCompanies(this);
    }

}
