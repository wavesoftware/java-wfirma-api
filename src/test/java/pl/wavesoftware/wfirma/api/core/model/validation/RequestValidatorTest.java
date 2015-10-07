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
package pl.wavesoftware.wfirma.api.core.model.validation;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import static org.assertj.core.api.Assertions.*;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;
import pl.wavesoftware.wfirma.api.core.model.WFirmaException;
import pl.wavesoftware.wfirma.api.core.model.companies.Companies;
import pl.wavesoftware.wfirma.api.core.model.companies.CompaniesGetRequest;
import pl.wavesoftware.wfirma.api.core.model.companies.Company;
import pl.wavesoftware.wfirma.api.core.model.contractors.Contractor;
import pl.wavesoftware.wfirma.api.core.model.contractors.Contractors;
import pl.wavesoftware.wfirma.api.core.model.invoices.AbstractInvoice;
import pl.wavesoftware.wfirma.api.core.model.invoices.Invoices;
import pl.wavesoftware.wfirma.api.core.model.invoices.InvoicesApi;
import pl.wavesoftware.wfirma.api.core.model.invoices.NormalInvoice;
import pl.wavesoftware.wfirma.api.core.model.requests.EditRequest;

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

        CompaniesGetRequest get = CompaniesGetRequest.create();
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
                + "`CompaniesApi` module. Only supported request are: `CompaniesGetRequest`");
    }

    @Test
    public void testValidate() throws Exception {
        CompaniesGetRequest get = CompaniesGetRequest.create();
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
                    + "`CompaniesApi` module. Only supported request are: `CompaniesGetRequest`]");
        }
    }

    @Test
    public void testGetErrorsOnReadOnly() {
        Contractors contractors = new Contractors();
        Contractor contractor = new Contractor();
        contractor.setName("New name");
        contractor.setModified(new Date()); // Should fail on read only
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
