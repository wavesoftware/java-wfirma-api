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
package pl.wavesoftware.wfirma.api.model.validation;

import java.util.Collection;
import java.util.GregorianCalendar;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;
import pl.wavesoftware.wfirma.api.model.ApiModule;
import pl.wavesoftware.wfirma.api.model.WFirmaException;
import pl.wavesoftware.wfirma.api.model.companies.Companies;
import pl.wavesoftware.wfirma.api.model.companies.Company;
import pl.wavesoftware.wfirma.api.model.contractors.Contractor;
import pl.wavesoftware.wfirma.api.model.contractors.Contractors;
import pl.wavesoftware.wfirma.api.model.invoices.AbstractInvoice;
import pl.wavesoftware.wfirma.api.model.invoices.Invoices;
import pl.wavesoftware.wfirma.api.model.invoices.InvoicesApi;
import pl.wavesoftware.wfirma.api.model.invoices.NormalInvoice;
import pl.wavesoftware.wfirma.api.model.requests.EditRequest;
import pl.wavesoftware.wfirma.api.model.requests.GetRequest;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class RequestValidatorTest {

    @Test
    public void testIsValid() {
        Companies companies = new Companies();
        Company company = new Company();
        company.setName("New value");
        companies.getCompany().add(company);
        EditRequest<Companies> edit = EditRequest.create(companies, 6L);
        RequestValidator instance = new RequestValidator(edit);
        assertThat(instance.isValid()).isFalse();

        GetRequest get = new GetRequest(ApiModule.COMPANIES, 6L);
        instance = new RequestValidator(get);
        assertThat(instance.isValid()).isTrue();
    }

    @Test
    public void testGetErrors() {
        Companies companies = new Companies();
        Company company = new Company();
        companies.getCompany().add(company);
        EditRequest<Companies> edit = EditRequest.create(companies, 67L);
        RequestValidator instance = new RequestValidator(edit);
        Collection<String> result = instance.getErrors();
        assertThat(result).containsExactly("`EditRequest` is not supported by "
                + "`COMPANIES` API module. Only supported request are: `GetRequest`");
    }

    @Test
    public void testValidate() throws Exception {
        GetRequest get = new GetRequest(ApiModule.COMPANIES, 6l);
        RequestValidator instance = new RequestValidator(get);
        instance.validate();
        assertThat(instance).isNotNull();
    }

    @Test
    public void testValidateFailOnType() {
        try {
            Companies companies = new Companies();
            Company company = new Company();
            companies.getCompany().add(company);
            EditRequest<Companies> edit = EditRequest.create(companies, 98L);
            RequestValidator instance = new RequestValidator(edit);
            instance.validate();
            failBecauseExceptionWasNotThrown(WFirmaException.class);
        } catch (WFirmaException wfse) {
            assertThat(wfse).hasMessage("Validation errors: [`EditRequest` is not supported by "
                    + "`COMPANIES` API module. Only supported request are: `GetRequest`]");
        }
    }

    @Test
    public void testGetErrorsOnReadOnly() {
        Contractors contractors = new Contractors();
        Contractor contractor = new Contractor();
        contractor.setName("New name");
        contractor.setModified("set as now"); // Should fail on read only
        contractors.getContractor().add(contractor);
        EditRequest<Contractors> edit = EditRequest.create(contractors, 234L);
        RequestValidator instance = new RequestValidator(edit);
        Collection<String> errors = instance.getErrors();
        assertThat(errors).containsExactly("The `modified` property of `Contractor` is read only");
    }

    @Test
    public void testGetErrorsOnReadOnly2() {
        InvoicesApi api = new InvoicesApi();
        Invoices invoices = new Invoices(api);
        NormalInvoice invoice = new NormalInvoice();
        GregorianCalendar cal = new GregorianCalendar(2014, 3, 11);
        invoice.setAlreadyPaid(Money.of(CurrencyUnit.USD, 932.00d));
        invoice.setAlreadyPaidInitial(Money.of(CurrencyUnit.USD, 932.00d));
        invoice.setPaymentState(AbstractInvoice.PaymentState.paid);
        invoice.setRemaining(Money.of(CurrencyUnit.USD, 0d));
        invoice.setTotal(Money.of(CurrencyUnit.USD, 932.00d));
        invoice.setDate(cal.getTime());
        invoice.setDay(11);
        invoice.setDisposalDate(cal.getTime());
        invoice.setDisposalDateEmpty(Boolean.TRUE);
        invoice.setDisposalDateForm(AbstractInvoice.DisposalDateForm.month);
        invoice.setMonth(4);
        invoice.setNumber(3);
        invoice.setYear(2014);
        invoice.setSemiTemplateNumber("FV [number]/[year]");
        invoice.setFullNumber("FV 3/2014");
        invoices.getInvoice().add(invoice);
        EditRequest<Invoices> edit = EditRequest.create(invoices, 234L);
        RequestValidator instance = new RequestValidator(edit);
        Collection<String> errors = instance.getErrors();
        assertThat(errors).containsExactly(
                "The `paymentState` property of `NormalInvoice` is read only",
                "The `total` property of `NormalInvoice` is read only",
                "The `alreadyPaid` property of `NormalInvoice` is read only",
                "The `remaining` property of `NormalInvoice` is read only",
                "The `fullNumber` property of `NormalInvoice` is read only"
        );
    }

}
