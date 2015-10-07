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
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import pl.wavesoftware.wfirma.api.core.mapper.xml.*;
import pl.wavesoftware.wfirma.api.core.model.companies.Company;
import pl.wavesoftware.wfirma.api.core.model.contractors.Contractor;
import pl.wavesoftware.wfirma.api.core.model.utils.ReadOnly;
import pl.wavesoftware.wfirma.api.core.model.utils.Reference;
import pl.wavesoftware.wfirma.api.core.util.Copier;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Collection;
import java.util.Date;

/**
 * <p/>
 * A abstract base Java class for invoiceType complex type.
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
@Setter
@Getter
@XmlTransient
public abstract class AbstractInvoice {

    @ReadOnly
    @XmlElement
    private Long id;

    @XmlTransient
    protected TypeOfInvoice type;

    @XmlElement(name = "contractor_detail")
    private Contractor contractorDetail;

    @ReadOnly
    @XmlElement(name = "company_detail")
    private Company companyDetail;

    @XmlElement(name = "paymentmethod")
    private PaymentMethod paymentMethod;

    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlElement(name = "paymentdate")
    private Date paymentDate;

    @ReadOnly
    @XmlElement(name = "paymentstate")
    private PaymentState paymentState;

    @XmlElement(name = "disposaldate_form")
    private DisposalDateForm disposalDateForm;

    @XmlJavaTypeAdapter(BooleanToIntegerAdapter.class)
    @XmlElement(name = "disposaldate_empty")
    private Boolean disposalDateEmpty;

    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlElement(name = "disposaldate")
    private Date disposalDate;

    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlElement
    private Date date;

    @XmlElement
    private Integer period;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money total;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(name = "total_composed")
    private Money totalComposed;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(name = "alreadypaid")
    private Money alreadyPaid;

    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(name = "alreadypaid_initial")
    private Money alreadyPaidInitial;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money remaining;

    @XmlElement
    private Integer number;

    @XmlElement
    private Integer day;

    @XmlElement
    private Integer month;

    @XmlElement
    private Integer year;

    @XmlElement(name = "semitemplatenumber")
    private String semiTemplateNumber;

    @ReadOnly
    @XmlElement(name = "correction_type")
    private String correctionType;

    @ReadOnly
    @XmlElement
    private Integer corrections;

    @XmlJavaTypeAdapter(CurrencyAdapter.class)
    @XmlElement
    private CurrencyUnit currency;

    @ReadOnly
    @XmlElement(name = "currency_exchange")
    private Double currencyExchange;

    @ReadOnly
    @XmlElement(name = "currency_label")
    private String currencyLabel;

    @ReadOnly
    @XmlElement(name = "currency_date")
    private Date currencyDate;

    @ReadOnly
    @XmlElement(name = "price_currency_exchange")
    private Double priceCurrencyExchange;

    @ReadOnly
    @XmlElement(name = "good_price_group_currency_exchange")
    private Double goodPriceGroupCurrencyExchange;

    @XmlElement
    private Integer template;

    @XmlJavaTypeAdapter(BooleanToIntegerAdapter.class)
    @XmlElement(name = "auto_send")
    private Boolean autoSend;

    @XmlElement
    private String description;

    @ReadOnly
    @XmlElement
    private String header;

    @ReadOnly
    @XmlElement
    private String footer;

    @ReadOnly
    @XmlElement(name = "user_name")
    private String userName;

    @XmlElement
    private InvoiceSchema schema;

    @XmlJavaTypeAdapter(BooleanToIntegerAdapter.class)
    @XmlElement(name = "schema_bill")
    private Boolean schemaBill;

    @XmlJavaTypeAdapter(BooleanToIntegerAdapter.class)
    @XmlElement(name = "schema_canceled")
    private Boolean schemaCanceled;

    @XmlElement(name = "register_description")
    private String registerDescription;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money netto22;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money netto7;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money netto3;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money netto0;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(name = "nettozw")
    private Money nettoZw;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(name = "nettowdt")
    private Money nettoWdt;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(name = "nettoexp")
    private Money nettoExp;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(name = "nettonp")
    private Money nettoNp;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(name = "nettonpue")
    private Money nettoNpUe;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(name = "nettovat_buyer")
    private Money nettoVatBuyer;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money netto23;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money netto8;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money netto5;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money netto;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money tax22;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money tax7;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money tax3;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money tax23;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money tax8;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money tax5;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement
    private Money tax;

    @ReadOnly
    @XmlJavaTypeAdapter(BooleanToIntegerAdapter.class)
    @XmlElement
    private Boolean signed;

    @ReadOnly
    @XmlElement
    private String hash;

    @XmlElement
    private String idExternal;

    @ReadOnly
    @XmlElement(name = "warehouse_type")
    private WarehouseType warehouseType;

    @ReadOnly
    @XmlElement
    private Integer notes;

    @ReadOnly
    @XmlElement
    private Integer documents;

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

    @XmlElement(name = "price_type")
    private PriceType priceType;

    @XmlElement(name = "invoicecontents")
    private InvoiceContents invoiceContents;

    @ReadOnly
    @XmlElement
    private Reference series;

    @ReadOnly
    @XmlElement
    private Reference company;

    @ReadOnly
    @XmlElement
    private Reference contractor;

    @ReadOnly
    @XmlElement
    private Reference parent;

    @ReadOnly
    @XmlElement
    private Reference order;

    @ReadOnly
    @XmlElement
    private Reference cronemail;

    @ReadOnly
    @XmlElement
    private Reference cronemail2;

    @ReadOnly
    @XmlElement
    private Reference expenses;

    @ReadOnly
    @XmlElement(name = "company_accounts")
    private Reference companyAccounts;

    @ReadOnly
    @XmlElement(name = "payment_cashboxes")
    private Reference paymentCashboxes;

    @ReadOnly
    @XmlElement(name = "translation_languages")
    private Reference translationLanguages;

    @ReadOnly
    @XmlElement(name = "postivo_shipments")
    private Reference postivoShipments;

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

    private static Date copy(Date date) {
        return Copier.from(date).copy();
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
