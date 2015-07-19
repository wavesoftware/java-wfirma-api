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

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.Setter;
import static pl.wavesoftware.wfirma.api.mapper.Copier.copy;
import pl.wavesoftware.wfirma.api.mapper.xml.BooleanToIntegerAdapter;
import pl.wavesoftware.wfirma.api.mapper.xml.DateWithTimeAdapter;
import pl.wavesoftware.wfirma.api.model.utils.ReadOnly;
import pl.wavesoftware.wfirma.api.model.utils.Reference;

/**
 * <p>
 * Java class for contractorType complex type.
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contractorType")
public class Contractor {

    @Getter
    @Setter
    @ReadOnly
    @XmlElement
    private Long id;

    @Getter
    @Setter
    @XmlElement
    private String access;

    @Getter
    @Setter
    @XmlElement(required = true)
    private String name;

    @Getter
    @Setter
    @XmlElement
    private String altname;

    @Getter
    @Setter
    @XmlElement
    private String nip;

    @Getter
    @Setter
    @XmlElement
    private String regon;

    @Getter
    @Setter
    @XmlElement
    private String street;

    @Getter
    @Setter
    @XmlElement
    private String zip;

    @Getter
    @Setter
    @XmlElement
    private String city;

    @Getter
    @Setter
    @XmlJavaTypeAdapter(BooleanToIntegerAdapter.class)
    @XmlElement(name = "different_contact_address")
    private Boolean differentContactAddress;

    @Getter
    @Setter
    @XmlElement(name = "contact_name")
    private String contactName;

    @Getter
    @Setter
    @XmlElement(name = "contact_street")
    private String contactStreet;

    @Getter
    @Setter
    @XmlElement(name = "contact_zip")
    private String contactZip;

    @Getter
    @Setter
    @XmlElement(name = "contact_city")
    private String contactCity;

    @Getter
    @Setter
    @XmlElement(name = "contact_person")
    private String contactPerson;

    @Getter
    @Setter
    @XmlElement
    private String phone;

    @Getter
    @Setter
    @XmlElement
    private String fax;

    @Getter
    @Setter
    @XmlElement
    private String email;

    @Getter
    @Setter
    @XmlElement
    private String url;

    @Getter
    @Setter
    @XmlElement
    private String description;

    @Getter
    @Setter
    @XmlJavaTypeAdapter(BooleanToIntegerAdapter.class)
    @XmlElement
    private Boolean buyer;

    @Getter
    @Setter
    @XmlJavaTypeAdapter(BooleanToIntegerAdapter.class)
    @XmlElement
    private Boolean seller;

    @Getter
    @Setter
    @XmlElement(name = "discount_percent")
    private Integer discountPercent;

    @Getter
    @Setter
    @XmlElement(name = "payment_days")
    private Integer paymentDays;

    @Getter
    @Setter
    @XmlElement(name = "payment_method")
    private String paymentMethod;

    @Getter
    @Setter
    @XmlElement
    private Integer remind;

    @Getter
    @Setter
    @XmlElement
    private String tags;

    @Getter
    @Setter
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

    @Getter
    @Setter
    @XmlElement
    private Integer provider;

    @Getter
    @Setter
    @ReadOnly
    @XmlElement(name = "reference_company")
    private Reference referenceCompany;

    @Getter
    @Setter
    @ReadOnly
    @XmlElement(name = "translation_language")
    private Reference translationLanguage;

    @Getter
    @Setter
    @ReadOnly
    @XmlElement(name = "company_account")
    private Reference companyAccount;

    @Getter
    @Setter
    @ReadOnly
    @XmlElement(name = "good_price_group")
    private Reference goodPriceGroup;

    @Getter
    @Setter
    @ReadOnly
    @XmlElement(name = "invoice_description")
    private Reference invoiceDescription;

    @Getter
    @Setter
    @ReadOnly
    @XmlElement(name = "shop_buyer")
    private Reference shopBuyer;

    /**
     * Gets the value of the created property.
     *
     * @return possible object is {@link Date}
     */
    public Date getCreated() {
        return copy(created);
    }

    /**
     * Sets the value of the created property.
     *
     * @param value allowed object is {@link Date}
     *
     */
    public void setCreated(Date value) {
        this.created = copy(value);
    }

    /**
     * Gets the value of the modified property.
     *
     * @return possible object is {@link Date}
     */
    public Date getModified() {
        return copy(modified);
    }

    /**
     * Sets the value of the modified property.
     *
     * @param value allowed object is {@link Date}
     *
     */
    public void setModified(Date value) {
        this.modified = copy(value);
    }

}
