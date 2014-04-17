/*
 * The MIT License
 *
 * Copyright 2014 Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>.
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
