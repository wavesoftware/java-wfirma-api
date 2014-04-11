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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.joda.money.Money;
import pl.wavesoftware.wfirma.api.mapper.xml.BooleanToIntegerAdapter;
import pl.wavesoftware.wfirma.api.mapper.xml.DateAdapter;
import pl.wavesoftware.wfirma.api.mapper.xml.MoneyAdapter;
import pl.wavesoftware.wfirma.api.model.utils.ReadOnly;

/**
 * <p>
 * Java class for invoiceType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="invoiceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
@XmlTransient
public abstract class AbstractInvoice {

    @ReadOnly
    @XmlElement(required = false)
    private Long id;

    @XmlElement(required = true)
    protected TypeOfInvoice type;

    @XmlElement(required = false, name = "paymentmethod")
    private PaymentMethod paymentMethod;

    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlElement(required = false, name = "paymentdate")
    private Date paymentDate;

    @ReadOnly
    @XmlElement(required = false, name = "paymentstate")
    private PaymentState paymentState;

    @XmlElement(required = false, name = "disposaldate_form")
    private DisposalDateForm disposalDateForm;

    @XmlJavaTypeAdapter(BooleanToIntegerAdapter.class)
    @XmlElement(required = false, name = "disposaldate_empty")
    private Boolean disposalDateEmpty;

    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlElement(required = false, name = "disposaldate")
    private Date disposalDate;

    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlElement(required = false)
    private Date date;

    @XmlElement(required = false)
    private Integer period;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(required = false)
    private Money total;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(required = false, name = "total_composed")
    private Money totalComposed;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(required = false, name = "alreadypaid")
    private Money alreadyPaid;

    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(required = false, name = "alreadypaid_initial")
    private Money alreadyPaidInitial;

    @ReadOnly
    @XmlJavaTypeAdapter(MoneyAdapter.class)
    @XmlElement(required = false)
    private Money remaining;

    @XmlElement(required = false)
    private Integer number;

    @XmlElement(required = false)
    private Integer day;

    @XmlElement(required = false)
    private Integer month;

    @XmlElement(required = false)
    private Integer year;

    @XmlElement(required = false, name = "semitemplatenumber")
    private String semiTemplateNumber;

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

    public Date getPaymentDate() {
        return Date.class.cast(paymentDate.clone());
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = Date.class.cast(paymentDate.clone());
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
        return Date.class.cast(disposalDate.clone());
    }

    public void setDisposalDate(Date disposalDate) {
        this.disposalDate = Date.class.cast(disposalDate.clone());
    }

    public Date getDate() {
        return Date.class.cast(date.clone());
    }

    public void setDate(Date date) {
        this.date = Date.class.cast(date.clone());
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

}
