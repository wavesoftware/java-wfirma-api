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
package pl.wavesoftware.wfirma.api.model.contractors;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import static pl.wavesoftware.wfirma.api.mapper.Copier.copy;
import pl.wavesoftware.wfirma.api.mapper.xml.BooleanToIntegerAdapter;
import pl.wavesoftware.wfirma.api.mapper.xml.DateWithTimeAdapter;
import pl.wavesoftware.wfirma.api.model.utils.ReadOnly;
import pl.wavesoftware.wfirma.api.model.utils.Reference;

/**
 * <p>
 * Java class for contractorType complex type.
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contractorType")
public class Contractor {

    @ReadOnly
    @XmlElement
    private Long id;

    @XmlElement
    private String access;

    @XmlElement(required = true)
    private String name;

    @XmlElement
    private String altname;

    @XmlElement
    private String nip;

    @XmlElement
    private String regon;

    @XmlElement
    private String street;

    @XmlElement
    private String zip;

    @XmlElement
    private String city;

    @XmlJavaTypeAdapter(BooleanToIntegerAdapter.class)
    @XmlElement(name = "different_contact_address")
    private Boolean differentContactAddress;

    @XmlElement(name = "contact_name")
    private String contactName;

    @XmlElement(name = "contact_street")
    private String contactStreet;

    @XmlElement(name = "contact_zip")
    private String contactZip;

    @XmlElement(name = "contact_city")
    private String contactCity;

    @XmlElement(name = "contact_person")
    private String contactPerson;

    @XmlElement
    private String phone;

    @XmlElement
    private String fax;

    @XmlElement
    private String email;

    @XmlElement
    private String url;

    @XmlElement
    private String description;

    @XmlJavaTypeAdapter(BooleanToIntegerAdapter.class)
    @XmlElement
    private Boolean buyer;

    @XmlJavaTypeAdapter(BooleanToIntegerAdapter.class)
    @XmlElement
    private Boolean seller;

    @XmlElement(name = "discount_percent")
    private Integer discountPercent;

    @XmlElement(name = "payment_days")
    private Integer paymentDays;

    @XmlElement(name = "payment_method")
    private String paymentMethod;

    @XmlElement
    private Integer remind;

    @XmlElement
    private String tags;

    @ReadOnly
    @XmlElement
    private Integer notes;

    @ReadOnly
    @XmlJavaTypeAdapter(DateWithTimeAdapter.class)
    @XmlElement
    private Date created;

    @ReadOnly
    @XmlJavaTypeAdapter(DateWithTimeAdapter.class)
    @XmlElement
    private Date modified;

    @XmlElement
    private Integer provider;

    @ReadOnly
    @XmlElement(name = "reference_company")
    private Reference referenceCompany;

    @ReadOnly
    @XmlElement(name = "translation_language")
    private Reference translationLanguage;

    @ReadOnly
    @XmlElement(name = "company_account")
    private Reference companyAccount;

    @ReadOnly
    @XmlElement(name = "good_price_group")
    private Reference goodPriceGroup;

    @ReadOnly
    @XmlElement(name = "invoice_description")
    private Reference invoiceDescription;

    @ReadOnly
    @XmlElement(name = "shop_buyer")
    private Reference shopBuyer;

    /**
     * Gets the value of the id property.
     *
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the access property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getAccess() {
        return access;
    }

    /**
     * Sets the value of the access property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setAccess(String value) {
        this.access = value;
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
     * Gets the value of the regon property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getRegon() {
        return regon;
    }

    /**
     * Sets the value of the regon property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setRegon(String value) {
        this.regon = value;
    }

    /**
     * Gets the value of the street property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the value of the street property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setStreet(String value) {
        this.street = value;
    }

    /**
     * Gets the value of the zip property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the value of the zip property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setZip(String value) {
        this.zip = value;
    }

    /**
     * Gets the value of the city property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Is diffrent contact address?
     *
     * @return true, if the contact address is diffrent
     */
    public Boolean isDifferentContactAddress() {
        return differentContactAddress;
    }

    /**
     * Sets if contact addres is diffrent
     *
     * @param value a setting for differentContactAddress property
     */
    public void setDifferentContactAddress(Boolean value) {
        this.differentContactAddress = value;
    }

    /**
     * Gets the value of the contactName property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the value of the contactName property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setContactName(String value) {
        this.contactName = value;
    }

    /**
     * Gets the value of the contactStreet property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getContactStreet() {
        return contactStreet;
    }

    /**
     * Sets the value of the contactStreet property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setContactStreet(String value) {
        this.contactStreet = value;
    }

    /**
     * Gets the value of the contactZip property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getContactZip() {
        return contactZip;
    }

    /**
     * Sets the value of the contactZip property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setContactZip(String value) {
        this.contactZip = value;
    }

    /**
     * Gets the value of the contactCity property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getContactCity() {
        return contactCity;
    }

    /**
     * Sets the value of the contactCity property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setContactCity(String value) {
        this.contactCity = value;
    }

    /**
     * Gets the value of the contactPerson property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getContactPerson() {
        return contactPerson;
    }

    /**
     * Sets the value of the contactPerson property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setContactPerson(String value) {
        this.contactPerson = value;
    }

    /**
     * Gets the value of the phone property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * Gets the value of the fax property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getFax() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setFax(String value) {
        this.fax = value;
    }

    /**
     * Gets the value of the email property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the url property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the description property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the buyer property.
     *
     * @return is buyer?
     */
    public Boolean isBuyer() {
        return buyer;
    }

    /**
     * Sets the value of the buyer property.
     *
     * @param value a setting for buyer
     */
    public void setBuyer(Boolean value) {
        this.buyer = value;
    }

    /**
     * Gets the value of the seller property.
     *
     * @return is seller?
     */
    public Boolean isSeller() {
        return seller;
    }

    /**
     * Sets the value of the seller property.
     *
     * @param value a setting for seller
     */
    public void setSeller(Boolean value) {
        this.seller = value;
    }

    /**
     * Gets the value of the discountPercent property.
     *
     */
    public Integer getDiscountPercent() {
        return discountPercent;
    }

    /**
     * Sets the value of the discountPercent property.
     *
     */
    public void setDiscountPercent(Integer value) {
        this.discountPercent = value;
    }

    /**
     * Gets the value of the paymentDays property.
     *
     */
    public Integer getPaymentDays() {
        return paymentDays;
    }

    /**
     * Sets the value of the paymentDays property.
     *
     */
    public void setPaymentDays(Integer value) {
        this.paymentDays = value;
    }

    /**
     * Gets the value of the paymentMethod property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets the value of the paymentMethod property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPaymentMethod(String value) {
        this.paymentMethod = value;
    }

    /**
     * Gets the value of the remind property.
     *
     */
    public Integer getRemind() {
        return remind;
    }

    /**
     * Sets the value of the remind property.
     *
     */
    public void setRemind(Integer value) {
        this.remind = value;
    }

    /**
     * Gets the value of the tags property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTags() {
        return tags;
    }

    /**
     * Sets the value of the tags property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTags(String value) {
        this.tags = value;
    }

    /**
     * Gets the value of the notes property.
     *
     */
    public Integer getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     *
     */
    public void setNotes(Integer value) {
        this.notes = value;
    }

    /**
     * Gets the value of the created property.
     *
     * @return possible object is {@link String }
     *
     */
    public Date getCreated() {
        return copy(created);
    }

    /**
     * Sets the value of the created property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCreated(Date value) {
        this.created = copy(value);
    }

    /**
     * Gets the value of the modified property.
     *
     * @return possible object is {@link String }
     *
     */
    public Date getModified() {
        return copy(modified);
    }

    /**
     * Sets the value of the modified property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setModified(Date value) {
        this.modified = copy(value);
    }

    /**
     * Gets the value of the provider property.
     *
     */
    public Integer getProvider() {
        return provider;
    }

    /**
     * Sets the value of the provider property.
     *
     */
    public void setProvider(Integer value) {
        this.provider = value;
    }

    /**
     * Gets the value of the translationLanguage property.
     *
     * @return possible object is {@link Reference}
     *
     */
    public Reference getTranslationLanguage() {
        return translationLanguage;
    }

    /**
     * Sets the value of the translationLanguage property.
     *
     * @param value allowed object is {@link Reference}
     *
     */
    public void setTranslationLanguage(Reference value) {
        this.translationLanguage = value;
    }

    /**
     * Gets the value of the companyAccount property.
     *
     * @return possible object is {@link Reference}
     *
     */
    public Reference getCompanyAccount() {
        return companyAccount;
    }

    /**
     * Sets the value of the companyAccount property.
     *
     * @param value allowed object is {@link Reference}
     *
     */
    public void setCompanyAccount(Reference value) {
        this.companyAccount = value;
    }

    /**
     * Gets the value of the invoiceDescription property.
     *
     * @return possible object is {@link Reference}
     *
     */
    public Reference getInvoiceDescription() {
        return invoiceDescription;
    }

    /**
     * Sets the value of the invoiceDescription property.
     *
     * @param value allowed object is {@link Reference}
     *
     */
    public void setInvoiceDescription(Reference value) {
        this.invoiceDescription = value;
    }

    public Reference getReferenceCompany() {
        return referenceCompany;
    }

    public void setReferenceCompany(Reference referenceCompany) {
        this.referenceCompany = referenceCompany;
    }

    public Reference getGoodPriceGroup() {
        return goodPriceGroup;
    }

    public void setGoodPriceGroup(Reference goodPriceGroup) {
        this.goodPriceGroup = goodPriceGroup;
    }

    public Reference getShopBuyer() {
        return shopBuyer;
    }

    public void setShopBuyer(Reference shopBuyer) {
        this.shopBuyer = shopBuyer;
    }

}
