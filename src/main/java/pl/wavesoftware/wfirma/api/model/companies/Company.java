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
package pl.wavesoftware.wfirma.api.model.companies;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import pl.wavesoftware.wfirma.api.mapper.xml.BooleanToIntegerAdapter;
import pl.wavesoftware.wfirma.api.model.utils.ReadOnly;

/**
 * <p>
 * Java class for companyType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="companyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="access" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="altname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nip" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "companyType")
public class Company {

    @ReadOnly
    @XmlElement(required = false)
    protected Long id;

    @ReadOnly
    @XmlElement(required = true)
    protected String name;

    @ReadOnly
    @XmlElement(required = false)
    protected String altname;

    @ReadOnly
    @XmlElement(required = false)
    protected String nip;

    @ReadOnly
    @XmlJavaTypeAdapter(BooleanToIntegerAdapter.class)
    @XmlElement(required = false, name = "vat_payer")
    protected Boolean vatPayer;

    @ReadOnly
    @XmlElement(required = false)
    protected TaxType tax;

    @ReadOnly
    @XmlJavaTypeAdapter(BooleanToIntegerAdapter.class)
    @XmlElement(required = false, name = "is_registered")
    private Boolean registered;

    /**
     * Gets the value of the id property.
     *
     * @return a ID property
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value a setter for a ID property
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the altname property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getAltname() {
        return altname;
    }

    /**
     * Sets the value of the altname property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setAltname(String value) {
        this.altname = value;
    }

    /**
     * Gets the value of the nip property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getNip() {
        return nip;
    }

    /**
     * Sets the value of the nip property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setNip(String value) {
        this.nip = value;
    }

    /**
     * Gets the value of the vatPayer property.
     *
     * @return possible object is {@link Boolean}
     *
     */
    public Boolean getVatPayer() {
        return vatPayer;
    }

    /**
     * Sets the value of the vatPayer property.
     *
     * @param value allowed object is {@link Boolean}
     *
     */
    public void setVatPayer(Boolean value) {
        this.vatPayer = value;
    }

    /**
     * Gets the value of the tax property.
     *
     * @return possible object is {@link TaxType}
     */
    public TaxType getTax() {
        return tax;
    }

    /**
     * Sets the value of the tax property.
     *
     * @param tax allowed object is {@link TaxType}
     */
    public void setTax(TaxType tax) {
        this.tax = tax;
    }

    /**
     * Gets the value of the registered property.
     *
     * @return possible object is {@link Boolean}
     */
    public Boolean isRegistered() {
        return registered;
    }

    /**
     * Sets the value of the registered property.
     *
     * @param registered allowed object is {@link Boolean}
     */
    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    /**
     * Company tax calculate method
     */
    public enum TaxType {

        taxregister, lumpregister
    }

}
