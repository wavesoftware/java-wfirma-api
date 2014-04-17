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

import java.util.Collection;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeOfInvoice getType() {
        return type;
    }

    public void setType(TypeOfInvoice type) {
        this.type = type;
    }

    public Contractor getContractorDetail() {
        return contractorDetail;
    }

    public void setContractorDetail(Contractor contractor) {
        this.contractorDetail = contractor;
    }

    public Company getCompanyDetail() {
        return companyDetail;
    }

    public void setCompanyDetail(Company company) {
        this.companyDetail = company;
    }

    public Date getPaymentDate() {
        return copy(paymentDate);
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = copy(paymentDate);
    }

    public PaymentState getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(PaymentState paymentState) {
        this.paymentState = paymentState;
    }

    public DisposalDateForm getDisposalDateForm() {
        return disposalDateForm;
    }

    public void setDisposalDateForm(DisposalDateForm disposalDateForm) {
        this.disposalDateForm = disposalDateForm;
    }

    public Boolean isDisposalDateEmpty() {
        return disposalDateEmpty;
    }

    public void setDisposalDateEmpty(Boolean disposalDateEmpty) {
        this.disposalDateEmpty = disposalDateEmpty;
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

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Money getTotal() {
        return total;
    }

    public void setTotal(Money total) {
        this.total = total;
    }

    public Money getTotalComposed() {
        return totalComposed;
    }

    public void setTotalComposed(Money totalComposed) {
        this.totalComposed = totalComposed;
    }

    public Money getAlreadyPaid() {
        return alreadyPaid;
    }

    public void setAlreadyPaid(Money alreadyPaid) {
        this.alreadyPaid = alreadyPaid;
    }

    public Money getAlreadyPaidInitial() {
        return alreadyPaidInitial;
    }

    public void setAlreadyPaidInitial(Money alreadyPaidInitial) {
        this.alreadyPaidInitial = alreadyPaidInitial;
    }

    public Money getRemaining() {
        return remaining;
    }

    public void setRemaining(Money remaining) {
        this.remaining = remaining;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getSemiTemplateNumber() {
        return semiTemplateNumber;
    }

    public void setSemiTemplateNumber(String semiTemplateNumber) {
        this.semiTemplateNumber = semiTemplateNumber;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCorrectionType() {
        return correctionType;
    }

    public void setCorrectionType(String correctionType) {
        this.correctionType = correctionType;
    }

    public Integer getCorrections() {
        return corrections;
    }

    public void setCorrections(Integer corrections) {
        this.corrections = corrections;
    }

    public CurrencyUnit getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyUnit currency) {
        this.currency = currency;
    }

    public Double getCurrencyExchange() {
        return currencyExchange;
    }

    public void setCurrencyExchange(Double currencyExchange) {
        this.currencyExchange = currencyExchange;
    }

    public String getCurrencyLabel() {
        return currencyLabel;
    }

    public void setCurrencyLabel(String currencyLabel) {
        this.currencyLabel = currencyLabel;
    }

    public Date getCurrencyDate() {
        return copy(currencyDate);
    }

    public void setCurrencyDate(Date currencyDate) {
        this.currencyDate = copy(currencyDate);
    }

    public Double getPriceCurrencyExchange() {
        return priceCurrencyExchange;
    }

    public void setPriceCurrencyExchange(Double priceCurrencyExchange) {
        this.priceCurrencyExchange = priceCurrencyExchange;
    }

    public Double getGoodPriceGroupCurrencyExchange() {
        return goodPriceGroupCurrencyExchange;
    }

    public void setGoodPriceGroupCurrencyExchange(Double goodPriceGroupCurrencyExchange) {
        this.goodPriceGroupCurrencyExchange = goodPriceGroupCurrencyExchange;
    }

    public Integer getTemplate() {
        return template;
    }

    public void setTemplate(Integer template) {
        this.template = template;
    }

    public Boolean isAutoSend() {
        return autoSend;
    }

    public void setAutoSend(Boolean autoSend) {
        this.autoSend = autoSend;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public InvoiceSchema getSchema() {
        return schema;
    }

    public void setSchema(InvoiceSchema schema) {
        this.schema = schema;
    }

    public Boolean isSchemaBill() {
        return schemaBill;
    }

    public void setSchemaBill(Boolean schemaBill) {
        this.schemaBill = schemaBill;
    }

    public Boolean isSchemaCanceled() {
        return schemaCanceled;
    }

    public void setSchemaCanceled(Boolean schemaCanceled) {
        this.schemaCanceled = schemaCanceled;
    }

    public String getRegisterDescription() {
        return registerDescription;
    }

    public void setRegisterDescription(String registerDescription) {
        this.registerDescription = registerDescription;
    }

    public Money getNetto22() {
        return netto22;
    }

    public void setNetto22(Money netto22) {
        this.netto22 = netto22;
    }

    public Money getNetto7() {
        return netto7;
    }

    public void setNetto7(Money netto7) {
        this.netto7 = netto7;
    }

    public Money getNetto3() {
        return netto3;
    }

    public void setNetto3(Money netto3) {
        this.netto3 = netto3;
    }

    public Money getNetto0() {
        return netto0;
    }

    public void setNetto0(Money netto0) {
        this.netto0 = netto0;
    }

    public Money getNettoZw() {
        return nettoZw;
    }

    public void setNettoZw(Money nettoZw) {
        this.nettoZw = nettoZw;
    }

    public Money getNettoWdt() {
        return nettoWdt;
    }

    public void setNettoWdt(Money nettoWdt) {
        this.nettoWdt = nettoWdt;
    }

    public Money getNettoExp() {
        return nettoExp;
    }

    public void setNettoExp(Money nettoExp) {
        this.nettoExp = nettoExp;
    }

    public Money getNettoNp() {
        return nettoNp;
    }

    public void setNettoNp(Money nettoNp) {
        this.nettoNp = nettoNp;
    }

    public Money getNettoNpUe() {
        return nettoNpUe;
    }

    public void setNettoNpUe(Money nettoNpUe) {
        this.nettoNpUe = nettoNpUe;
    }

    public Money getNettoVatBuyer() {
        return nettoVatBuyer;
    }

    public void setNettoVatBuyer(Money nettoVatBuyer) {
        this.nettoVatBuyer = nettoVatBuyer;
    }

    public Money getNetto23() {
        return netto23;
    }

    public void setNetto23(Money netto23) {
        this.netto23 = netto23;
    }

    public Money getNetto8() {
        return netto8;
    }

    public void setNetto8(Money netto8) {
        this.netto8 = netto8;
    }

    public Money getNetto5() {
        return netto5;
    }

    public void setNetto5(Money netto5) {
        this.netto5 = netto5;
    }

    public Money getNetto() {
        return netto;
    }

    public void setNetto(Money netto) {
        this.netto = netto;
    }

    public Money getTax22() {
        return tax22;
    }

    public void setTax22(Money tax22) {
        this.tax22 = tax22;
    }

    public Money getTax7() {
        return tax7;
    }

    public void setTax7(Money tax7) {
        this.tax7 = tax7;
    }

    public Money getTax3() {
        return tax3;
    }

    public void setTax3(Money tax3) {
        this.tax3 = tax3;
    }

    public Money getTax23() {
        return tax23;
    }

    public void setTax23(Money tax23) {
        this.tax23 = tax23;
    }

    public Money getTax8() {
        return tax8;
    }

    public void setTax8(Money tax8) {
        this.tax8 = tax8;
    }

    public Money getTax5() {
        return tax5;
    }

    public void setTax5(Money tax5) {
        this.tax5 = tax5;
    }

    public Money getTax() {
        return tax;
    }

    public void setTax(Money tax) {
        this.tax = tax;
    }

    public Boolean isSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getIdExternal() {
        return idExternal;
    }

    public void setIdExternal(String idExternal) {
        this.idExternal = idExternal;
    }

    public WarehouseType getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(WarehouseType warehouseType) {
        this.warehouseType = warehouseType;
    }

    public Integer getNotes() {
        return notes;
    }

    public void setNotes(Integer notes) {
        this.notes = notes;
    }

    public Integer getDocuments() {
        return documents;
    }

    public void setDocuments(Integer documents) {
        this.documents = documents;
    }

    public Collection<String> getTags() {
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
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

    public PriceType getPriceType() {
        return priceType;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }

    public InvoiceContents getInvoiceContents() {
        return invoiceContents;
    }

    public void setInvoiceContents(InvoiceContents invoiceContents) {
        this.invoiceContents = invoiceContents;
    }

    public Reference getSeries() {
        return series;
    }

    public void setSeries(Reference series) {
        this.series = series;
    }

    public Reference getCompany() {
        return company;
    }

    public void setCompany(Reference company) {
        this.company = company;
    }

    public Reference getContractor() {
        return contractor;
    }

    public void setContractor(Reference contractor) {
        this.contractor = contractor;
    }

    public Reference getParent() {
        return parent;
    }

    public void setParent(Reference parent) {
        this.parent = parent;
    }

    public Reference getOrder() {
        return order;
    }

    public void setOrder(Reference order) {
        this.order = order;
    }

    public Reference getCronemail() {
        return cronemail;
    }

    public void setCronemail(Reference cronemail) {
        this.cronemail = cronemail;
    }

    public Reference getCronemail2() {
        return cronemail2;
    }

    public void setCronemail2(Reference cronemail2) {
        this.cronemail2 = cronemail2;
    }

    public Reference getExpenses() {
        return expenses;
    }

    public void setExpenses(Reference expenses) {
        this.expenses = expenses;
    }

    public Reference getCompanyAccounts() {
        return companyAccounts;
    }

    public void setCompanyAccounts(Reference companyAccounts) {
        this.companyAccounts = companyAccounts;
    }

    public Reference getPaymentCashboxes() {
        return paymentCashboxes;
    }

    public void setPaymentCashboxes(Reference paymentCashboxes) {
        this.paymentCashboxes = paymentCashboxes;
    }

    public Reference getTranslationLanguages() {
        return translationLanguages;
    }

    public void setTranslationLanguages(Reference translationLanguages) {
        this.translationLanguages = translationLanguages;
    }

    public Reference getPostivoShipments() {
        return postivoShipments;
    }

    public void setPostivoShipments(Reference postivoShipments) {
        this.postivoShipments = postivoShipments;
    }

    public Reference getPostivoShipmentContents() {
        return postivoShipmentContents;
    }

    public void setPostivoShipmentContents(Reference postivoShipmentContents) {
        this.postivoShipmentContents = postivoShipmentContents;
    }

    /**
     * Metoda płatności
     */
    public static enum PaymentMethod {

        cash, transfer, compensation, cod, payment_card
    }

    /**
     * Stan płatności
     */
    public static enum PaymentState {

        paid, unpaid, udefined
    }

    /**
     * Format daty sprzedaży na wydruku faktury
     */
    public static enum DisposalDateForm {

        month, day
    }

    /**
     * Type of invoice
     */
    public static enum TypeOfInvoice {

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
    public static enum InvoiceSchema {

        normal, vat_invoice_date, assessor
    }

    /**
     * Warehouse type
     */
    public static enum WarehouseType {

        extended, simple
    }

    /**
     * Price type
     */
    public static enum PriceType {

        netto, brutto
    }

}
