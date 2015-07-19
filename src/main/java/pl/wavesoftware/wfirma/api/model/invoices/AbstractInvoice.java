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

import java.util.Collection;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.Setter;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import static pl.wavesoftware.wfirma.api.mapper.Copier.copy;
import pl.wavesoftware.wfirma.api.mapper.xml.BooleanToIntegerAdapter;
import pl.wavesoftware.wfirma.api.mapper.xml.CurrencyAdapter;
import pl.wavesoftware.wfirma.api.mapper.xml.DateAdapter;
import pl.wavesoftware.wfirma.api.mapper.xml.DateWithTimeAdapter;
import pl.wavesoftware.wfirma.api.mapper.xml.MoneyAdapter;
import pl.wavesoftware.wfirma.api.mapper.xml.TagsAdapter;
import pl.wavesoftware.wfirma.api.model.companies.Company;
import pl.wavesoftware.wfirma.api.model.contractors.Contractor;
import pl.wavesoftware.wfirma.api.model.utils.ReadOnly;
import pl.wavesoftware.wfirma.api.model.utils.Reference;

/**
 * <p>
 * A abstracr base Java class for invoiceType complex type.
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
@XmlTransient
public abstract class AbstractInvoice {

    @Setter
    @Getter
    @ReadOnly
    @XmlElement
    private Long id;

    @XmlTransient
    @Setter
    @Getter
    protected TypeOfInvoice type;

    @Setter
    @Getter
    @XmlElement(name = "contractor_detail")
    private Contractor contractorDetail;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement(name = "company_detail")
    private Company companyDetail;

    @Setter
    @Getter
    @XmlElement(name = "paymentmethod")
    private PaymentMethod paymentMethod;

    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlElement(name = "paymentdate")
    private Date paymentDate;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement(name = "paymentstate")
    private PaymentState paymentState;

    @Setter
    @Getter
    @XmlElement(name = "disposaldate_form")
    private DisposalDateForm disposalDateForm;

    @Setter
    @Getter
    @XmlJavaTypeAdapter(BooleanToIntegerAdapter.class)
    @XmlElement(name = "disposaldate_empty")
    private Boolean disposalDateEmpty;

    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlElement(name = "disposaldate")
    private Date disposalDate;

    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlElement
    private Date date;

    @Setter
    @Getter
    @XmlElement
    private Integer period;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money total;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(name = "total_composed")
    private Money totalComposed;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(name = "alreadypaid")
    private Money alreadyPaid;

    @Setter
    @Getter
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(name = "alreadypaid_initial")
    private Money alreadyPaidInitial;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money remaining;

    @Setter
    @Getter
    @XmlElement
    private Integer number;

    @Setter
    @Getter
    @XmlElement
    private Integer day;

    @Setter
    @Getter
    @XmlElement
    private Integer month;

    @Setter
    @Getter
    @XmlElement
    private Integer year;

    @Setter
    @Getter
    @XmlElement(name = "semitemplatenumber")
    private String semiTemplateNumber;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement(name = "correction_type")
    private String correctionType;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement
    private Integer corrections;

    @Setter
    @Getter
    @XmlJavaTypeAdapter(CurrencyAdapter.class)
    @XmlElement
    private CurrencyUnit currency;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement(name = "currency_exchange")
    private Double currencyExchange;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement(name = "currency_label")
    private String currencyLabel;

    @ReadOnly
    @XmlElement(name = "currency_date")
    private Date currencyDate;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement(name = "price_currency_exchange")
    private Double priceCurrencyExchange;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement(name = "good_price_group_currency_exchange")
    private Double goodPriceGroupCurrencyExchange;

    @Setter
    @Getter
    @XmlElement
    private Integer template;

    @Setter
    @Getter
    @XmlJavaTypeAdapter(BooleanToIntegerAdapter.class)
    @XmlElement(name = "auto_send")
    private Boolean autoSend;

    @Setter
    @Getter
    @XmlElement
    private String description;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement
    private String header;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement
    private String footer;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement(name = "user_name")
    private String userName;

    @Setter
    @Getter
    @XmlElement
    private InvoiceSchema schema;

    @Setter
    @Getter
    @XmlJavaTypeAdapter(BooleanToIntegerAdapter.class)
    @XmlElement(name = "schema_bill")
    private Boolean schemaBill;

    @Setter
    @Getter
    @XmlJavaTypeAdapter(BooleanToIntegerAdapter.class)
    @XmlElement(name = "schema_canceled")
    private Boolean schemaCanceled;

    @Setter
    @Getter
    @XmlElement(name = "register_description")
    private String registerDescription;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money netto22;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money netto7;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money netto3;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money netto0;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(name = "nettozw")
    private Money nettoZw;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(name = "nettowdt")
    private Money nettoWdt;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(name = "nettoexp")
    private Money nettoExp;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(name = "nettonp")
    private Money nettoNp;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(name = "nettonpue")
    private Money nettoNpUe;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(name = "nettovat_buyer")
    private Money nettoVatBuyer;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money netto23;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money netto8;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money netto5;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money netto;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money tax22;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money tax7;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money tax3;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money tax23;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money tax8;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money tax5;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money tax;

    @Setter
    @Getter
    @ReadOnly
    @XmlJavaTypeAdapter(BooleanToIntegerAdapter.class)
    @XmlElement
    private Boolean signed;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement
    private String hash;

    @Setter
    @Getter
    @XmlElement
    private String idExternal;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement(name = "warehouse_type")
    private WarehouseType warehouseType;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement
    private Integer notes;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement
    private Integer documents;

    @Setter
    @Getter
    @XmlElement
    @XmlJavaTypeAdapter(TagsAdapter.class)
    private Collection<String> tags;

    @ReadOnly
    @XmlElement
    @XmlJavaTypeAdapter(DateWithTimeAdapter.class)
    private Date created;

    @ReadOnly
    @XmlElement
    @XmlJavaTypeAdapter(DateWithTimeAdapter.class)
    private Date modified;

    @Setter
    @Getter
    @XmlElement(name = "price_type")
    private PriceType priceType;

    @Setter
    @Getter
    @XmlElement(name = "invoicecontents")
    private InvoiceContents invoiceContents;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement
    private Reference series;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement
    private Reference company;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement
    private Reference contractor;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement
    private Reference parent;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement
    private Reference order;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement
    private Reference cronemail;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement
    private Reference cronemail2;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement
    private Reference expenses;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement(name = "company_accounts")
    private Reference companyAccounts;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement(name = "payment_cashboxes")
    private Reference paymentCashboxes;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement(name = "translation_languages")
    private Reference translationLanguages;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement(name = "postivo_shipments")
    private Reference postivoShipments;

    @Setter
    @Getter
    @ReadOnly
    @XmlElement(name = "postivo_shipment_contents")
    private Reference postivoShipmentContents;

    /**
     * Constructor
     *
     * @param type type of invoice
     */
    public AbstractInvoice(TypeOfInvoice type) {
        this.type = type;
    }

    public Date getPaymentDate() {
        return copy(paymentDate);
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = copy(paymentDate);
    }

    public Date getDisposalDate() {
        return copy(disposalDate);
    }

    public void setDisposalDate(Date disposalDate) {
        this.disposalDate = copy(disposalDate);
    }

    public Date getDate() {
        return copy(date);
    }

    public void setDate(Date date) {
        this.date = copy(date);
    }

    public Date getCurrencyDate() {
        return copy(currencyDate);
    }

    public void setCurrencyDate(Date currencyDate) {
        this.currencyDate = copy(currencyDate);
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

    /**
     * Metoda płatności
     */
    public enum PaymentMethod {

        cash, transfer, compensation, cod, payment_card
    }

    /**
     * Stan płatności
     */
    public enum PaymentState {

        paid, unpaid, udefined
    }

    /**
     * Format daty sprzedaży na wydruku faktury
     */
    public enum DisposalDateForm {

        month, day
    }

    /**
     * Type of invoice
     */
    public enum TypeOfInvoice {

        // Vat
        normal,
        proforma,
        receipt_normal,
        receipt_fiscal_normal,
        income_normal,
        // Non-Vat
        bill,
        proforma_bill,
        receipt_bill,
        receipt_fiscal_bill,
        income_bill
    }

    /**
     * Schemat księgowy. W przypadku faktur dotyczących sprzedaży po 1 stycznia 2014 należy stosować schematy
     */
    public enum InvoiceSchema {

        normal, vat_invoice_date, assessor
    }

    /**
     * Warehouse type
     */
    public enum WarehouseType {

        extended, simple
    }

    /**
     * Price type
     */
    public enum PriceType {

        netto, brutto
    }

}
