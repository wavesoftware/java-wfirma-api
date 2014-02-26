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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for contractorType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="contractorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="access" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="altname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nip" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="regon" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="street" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="zip" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contact_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contact_street" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contact_zip" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contact_city" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contact_person" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="phone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fax" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="buyer" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="seller" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="discount_percent" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="payment_days" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="payment_method" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="remind" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="tags" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="created" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="modified" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="provider" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="translation_language">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="company_account">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="invoice_description">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contractorType", propOrder = {
    "id",
    "access",
    "name",
    "altname",
    "nip",
    "regon",
    "street",
    "zip",
    "city",
    "contactName",
    "contactStreet",
    "contactZip",
    "contactCity",
    "contactPerson",
    "phone",
    "fax",
    "email",
    "url",
    "description",
    "buyer",
    "seller",
    "discountPercent",
    "paymentDays",
    "paymentMethod",
    "remind",
    "tags",
    "notes",
    "created",
    "modified",
    "provider",
    "translationLanguage",
    "companyAccount",
    "invoiceDescription"
})
public class ContractorType {

    protected int id;

    @XmlElement(required = false)
    protected String access;

    @XmlElement(required = true)
    protected String name;

    @XmlElement(required = false)
    protected String altname;

    @XmlElement(required = false)
    protected String nip;

    @XmlElement(required = false)
    protected String regon;

    @XmlElement(required = false)
    protected String street;

    @XmlElement(required = false)
    protected String zip;

    @XmlElement(required = false)
    protected String city;

    @XmlElement(name = "contact_name", required = false)
    protected String contactName;

    @XmlElement(name = "contact_street", required = false)
    protected String contactStreet;

    @XmlElement(name = "contact_zip", required = false)
    protected String contactZip;

    @XmlElement(name = "contact_city", required = false)
    protected String contactCity;

    @XmlElement(name = "contact_person", required = false)
    protected String contactPerson;

    @XmlElement(required = false)
    protected String phone;

    @XmlElement(required = false)
    protected String fax;

    @XmlElement(required = false)
    protected String email;

    @XmlElement(required = false)
    protected String url;

    @XmlElement(required = false)
    protected String description;

    protected int buyer;

    protected int seller;

    @XmlElement(name = "discount_percent")
    protected int discountPercent;

    @XmlElement(name = "payment_days")
    protected int paymentDays;

    @XmlElement(name = "payment_method", required = false)
    protected String paymentMethod;

    protected int remind;

    @XmlElement(required = false)
    protected String tags;

    protected int notes;

    @XmlElement(required = false)
    protected String created;

    @XmlElement(required = false)
    protected String modified;

    protected int provider;

    @XmlElement(name = "translation_language", required = false)
    protected ContractorType.TranslationLanguage translationLanguage;

    @XmlElement(name = "company_account", required = false)
    protected ContractorType.CompanyAccount companyAccount;

    @XmlElement(name = "invoice_description", required = false)
    protected ContractorType.InvoiceDescription invoiceDescription;

    /**
     * Gets the value of the id property.
     *
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     */
    public void setId(int value) {
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
     */
    public int getBuyer() {
        return buyer;
    }

    /**
     * Sets the value of the buyer property.
     *
     */
    public void setBuyer(int value) {
        this.buyer = value;
    }

    /**
     * Gets the value of the seller property.
     *
     */
    public int getSeller() {
        return seller;
    }

    /**
     * Sets the value of the seller property.
     *
     */
    public void setSeller(int value) {
        this.seller = value;
    }

    /**
     * Gets the value of the discountPercent property.
     *
     */
    public int getDiscountPercent() {
        return discountPercent;
    }

    /**
     * Sets the value of the discountPercent property.
     *
     */
    public void setDiscountPercent(int value) {
        this.discountPercent = value;
    }

    /**
     * Gets the value of the paymentDays property.
     *
     */
    public int getPaymentDays() {
        return paymentDays;
    }

    /**
     * Sets the value of the paymentDays property.
     *
     */
    public void setPaymentDays(int value) {
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
    public int getRemind() {
        return remind;
    }

    /**
     * Sets the value of the remind property.
     *
     */
    public void setRemind(int value) {
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
    public int getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     *
     */
    public void setNotes(int value) {
        this.notes = value;
    }

    /**
     * Gets the value of the created property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCreated() {
        return created;
    }

    /**
     * Sets the value of the created property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCreated(String value) {
        this.created = value;
    }

    /**
     * Gets the value of the modified property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getModified() {
        return modified;
    }

    /**
     * Sets the value of the modified property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setModified(String value) {
        this.modified = value;
    }

    /**
     * Gets the value of the provider property.
     *
     */
    public int getProvider() {
        return provider;
    }

    /**
     * Sets the value of the provider property.
     *
     */
    public void setProvider(int value) {
        this.provider = value;
    }

    /**
     * Gets the value of the translationLanguage property.
     *
     * @return possible object is {@link ContractorType.TranslationLanguage }
     *
     */
    public ContractorType.TranslationLanguage getTranslationLanguage() {
        return translationLanguage;
    }

    /**
     * Sets the value of the translationLanguage property.
     *
     * @param value allowed object is {@link ContractorType.TranslationLanguage }
     *
     */
    public void setTranslationLanguage(ContractorType.TranslationLanguage value) {
        this.translationLanguage = value;
    }

    /**
     * Gets the value of the companyAccount property.
     *
     * @return possible object is {@link ContractorType.CompanyAccount }
     *
     */
    public ContractorType.CompanyAccount getCompanyAccount() {
        return companyAccount;
    }

    /**
     * Sets the value of the companyAccount property.
     *
     * @param value allowed object is {@link ContractorType.CompanyAccount }
     *
     */
    public void setCompanyAccount(ContractorType.CompanyAccount value) {
        this.companyAccount = value;
    }

    /**
     * Gets the value of the invoiceDescription property.
     *
     * @return possible object is {@link ContractorType.InvoiceDescription }
     *
     */
    public ContractorType.InvoiceDescription getInvoiceDescription() {
        return invoiceDescription;
    }

    /**
     * Sets the value of the invoiceDescription property.
     *
     * @param value allowed object is {@link ContractorType.InvoiceDescription }
     *
     */
    public void setInvoiceDescription(ContractorType.InvoiceDescription value) {
        this.invoiceDescription = value;
    }

    /**
     * <p>
     * Java class for anonymous complex type.
     *
     * <p>
     * The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "id"
    })
    public static class CompanyAccount {

        protected int id;

        /**
         * Gets the value of the id property.
         *
         */
        public int getId() {
            return id;
        }

        /**
         * Sets the value of the id property.
         *
         */
        public void setId(int value) {
            this.id = value;
        }

    }

    /**
     * <p>
     * Java class for anonymous complex type.
     *
     * <p>
     * The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "id"
    })
    public static class InvoiceDescription {

        @XmlElement(required = true)
        protected Object id;

        /**
         * Gets the value of the id property.
         *
         * @return possible object is {@link Object }
         *
         */
        public Object getId() {
            return id;
        }

        /**
         * Sets the value of the id property.
         *
         * @param value allowed object is {@link Object }
         *
         */
        public void setId(Object value) {
            this.id = value;
        }

    }

    /**
     * <p>
     * Java class for anonymous complex type.
     *
     * <p>
     * The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "id"
    })
    public static class TranslationLanguage {

        protected int id;

        /**
         * Gets the value of the id property.
         *
         */
        public int getId() {
            return id;
        }

        /**
         * Sets the value of the id property.
         *
         */
        public void setId(int value) {
            this.id = value;
        }

    }

}
