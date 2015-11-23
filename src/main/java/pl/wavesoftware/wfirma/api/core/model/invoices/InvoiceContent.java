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

import lombok.Getter;
import lombok.Setter;
import org.joda.money.Money;
import pl.wavesoftware.wfirma.api.core.util.Copier;
import pl.wavesoftware.wfirma.api.core.mapper.xml.DateWithTimeAdapter;
import pl.wavesoftware.wfirma.api.core.mapper.xml.MoneyAdapter;
import pl.wavesoftware.wfirma.api.core.model.utils.ReadOnly;
import pl.wavesoftware.wfirma.api.core.model.utils.Reference;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "invoicecontentType")
@Setter
@Getter
public class InvoiceContent {

    @ReadOnly
    @XmlElement
    private Long id;

    @XmlElement
    private String name;

    @XmlElement
    private String classification;

    @XmlElement
    private String unit;

    @XmlElement
    private Integer count;

    @XmlElement
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    private Money price;

    @XmlElement
    private Boolean discount;

    @XmlElement(name = "discount_percent")
    private Double discountPercent;

    @ReadOnly
    @XmlElement
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    private Money netto;

    @ReadOnly
    @XmlElement
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    private Money brutto;

    @XmlElement
    private Integer vat;

    @XmlElement
    private Integer lumpcode;

    @XmlElement
    private Reference good;

    @ReadOnly
    @XmlElement
    private Reference invoice;

    @ReadOnly
    @XmlElement(name = "fixed_asset")
    private Reference fixedAsset;

    @ReadOnly
    @XmlElement
    private Reference equipment;

    @ReadOnly
    @XmlElement(name = "warehouse_document_content")
    private Reference warehouseDocumentContent;

    @ReadOnly
    @XmlJavaTypeAdapter(DateWithTimeAdapter.class)
    @XmlElement
    private Date created;

    @ReadOnly
    @XmlJavaTypeAdapter(DateWithTimeAdapter.class)
    @XmlElement
    private Date modified;

    public Date getCreated() {
        return Copier.from(created).copy();
    }

    public void setCreated(Date created) {
        this.created = Copier.from(created).copy();
    }

    public Date getModified() {
        return Copier.from(modified).copy();
    }

    public void setModified(Date modified) {
        this.modified = Copier.from(modified).copy();
    }

}
