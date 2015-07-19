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
package pl.wavesoftware.wfirma.api.model.invoices;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.joda.money.Money;
import static pl.wavesoftware.wfirma.api.mapper.Copier.copy;
import pl.wavesoftware.wfirma.api.mapper.xml.DateWithTimeAdapter;
import pl.wavesoftware.wfirma.api.mapper.xml.MoneyAdapter;
import pl.wavesoftware.wfirma.api.model.utils.Reference;
import pl.wavesoftware.wfirma.api.model.utils.ReadOnly;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "invoicecontentType")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public Boolean isDiscount() {
        return discount;
    }

    public void setDiscount(Boolean discount) {
        this.discount = discount;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Money getNetto() {
        return netto;
    }

    public void setNetto(Money netto) {
        this.netto = netto;
    }

    public Money getBrutto() {
        return brutto;
    }

    public void setBrutto(Money brutto) {
        this.brutto = brutto;
    }

    public Integer getVat() {
        return vat;
    }

    public void setVat(Integer vat) {
        this.vat = vat;
    }

    public Integer getLumpcode() {
        return lumpcode;
    }

    public void setLumpcode(Integer lumpcode) {
        this.lumpcode = lumpcode;
    }

    public Reference getGood() {
        return good;
    }

    public void setGood(Reference good) {
        this.good = good;
    }

    public Reference getInvoice() {
        return invoice;
    }

    public void setInvoice(Reference invoice) {
        this.invoice = invoice;
    }

    public Reference getFixedAsset() {
        return fixedAsset;
    }

    public void setFixedAsset(Reference fixedAsset) {
        this.fixedAsset = fixedAsset;
    }

    public Reference getEquipment() {
        return equipment;
    }

    public void setEquipment(Reference equipment) {
        this.equipment = equipment;
    }

    public Reference getWarehouseDocumentContent() {
        return warehouseDocumentContent;
    }

    public void setWarehouseDocumentContent(Reference warehouseDocumentContent) {
        this.warehouseDocumentContent = warehouseDocumentContent;
    }

    public Date getCreated() {
        return copy(created);
    }

    public void setCreated(Date created) {
        this.created = copy(created);
    }

    public Date getModified() {
        return copy(modified);
    }

    public void setModified(Date modified) {
        this.modified = copy(modified);
    }

}
