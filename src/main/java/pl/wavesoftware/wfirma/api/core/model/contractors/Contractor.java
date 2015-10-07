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

import lombok.Getter;
import lombok.Setter;
import pl.wavesoftware.wfirma.api.core.mapper.xml.BooleanToIntegerAdapter;
import pl.wavesoftware.wfirma.api.core.mapper.xml.DateWithTimeAdapter;
import pl.wavesoftware.wfirma.api.core.model.utils.ReadOnly;
import pl.wavesoftware.wfirma.api.core.model.utils.Reference;
import pl.wavesoftware.wfirma.api.core.util.Copier;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * <p>
 * Java class for contractorType complex type.
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
@Getter
@Setter
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
     * Gets the value of the created property.
     *
     * @return possible object is {@link Date}
     */
    public Date getCreated() {
        return Copier.from(created).copy();
    }

    /**
     * Sets the value of the created property.
     *
     * @param value allowed object is {@link Date}
     *
     */
    public void setCreated(Date value) {
        this.created = Copier.from(value).copy();
    }

    /**
     * Gets the value of the modified property.
     *
     * @return possible object is {@link Date}
     */
    public Date getModified() {
        return Copier.from(modified).copy();
    }

    /**
     * Sets the value of the modified property.
     *
     * @param value allowed object is {@link Date}
     *
     */
    public void setModified(Date value) {
        this.modified = Copier.from(value).copy();
    }

}
