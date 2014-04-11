/*
 * The MIT License
 *
 * Copyright 2014 Krzysztof Suszyński <krzysztof.suszynski@gmail.com>.
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
package pl.wavesoftware.wfirma.api.mapper.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import org.custommonkey.xmlunit.XMLUnit;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import org.xml.sax.SAXException;
import pl.wavesoftware.wfirma.api.model.companies.Companies;
import pl.wavesoftware.wfirma.api.model.companies.CompaniesApi;
import pl.wavesoftware.wfirma.api.model.companies.Company;
import pl.wavesoftware.wfirma.api.model.invoices.AbstractInvoice;
import pl.wavesoftware.wfirma.api.model.invoices.Invoices;
import pl.wavesoftware.wfirma.api.model.invoices.InvoicesApi;
import pl.wavesoftware.wfirma.api.model.invoices.NormalInvoice;
import pl.wavesoftware.wfirma.api.model.logic.And;
import pl.wavesoftware.wfirma.api.model.logic.LogicalOperator;
import pl.wavesoftware.wfirma.api.model.logic.ObjectFactory;
import pl.wavesoftware.wfirma.api.model.logic.Or;
import pl.wavesoftware.wfirma.api.model.logic.Parameters;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@gmail.com>
 */
public class JaxbMarshallerTest {

    private String expectedXml;

    @Before
    public void setUp() throws IOException {
        try (InputStream stream = this.getClass().getResourceAsStream("sample-parameters.xml")) {
            expectedXml = read(stream);
        }
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true);
    }

    private String read(InputStream stream) {
        try (Scanner scanner = new Scanner(stream)) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }

    private Parameters sampleParameters() {
        ObjectFactory factory = new ObjectFactory();
        Parameters params = factory.createParametersType();
        params.setPage(2);
        params.setLimit(10);
        params.setOrder(factory.createOrderType());
        params.setConditions(factory.createConditionsType());
        params.getOrder().getAsc().add("name");
        params.getOrder().getAsc().add("surname");
        params.getOrder().getDesc().add("count");
        params.getOrder().getDesc().add("modfied");
        Or or = factory.createOrType();
        And and = factory.createAndType();
        params.getConditions().getOr().add(or);
        params.getConditions().getAnd().add(and);
        or.getCondition().add(factory.createConditionType("name", LogicalOperator.LIKE, "test"));
        or.getCondition().add(factory.createConditionType("id", LogicalOperator.LT, 10));
        and.getCondition().add(factory.createConditionType("surname", LogicalOperator.EQ, "Kowalski"));
        and.getCondition().add(factory.createConditionType("money", LogicalOperator.GE, 5600L));
        return params;
    }

    /**
     * Test of marshal method, of class JaxbMarshaller.
     */
    @Test
    public void testMarshal() throws SAXException, IOException {
        JaxbMarshaller<Parameters> instance = JaxbMarshaller.create(Parameters.class);
        Parameters params = sampleParameters();
        String result = instance.marshal(params);
        assertXMLEqual("comparing test xml to control xml", expectedXml, result);
    }

    /**
     * Test of unmarshal method, of class JaxbMarshaller.
     */
    @Test
    public void testUnmarshal() {
        JaxbMarshaller<Parameters> instance = JaxbMarshaller.create(Parameters.class);
        Parameters expResult = sampleParameters();
        Parameters result = instance.unmarshal(expectedXml);
        assertReflectionEquals(expResult, result);
    }

    @Test
    public void test2Way() {
        JaxbMarshaller<Parameters> instance = JaxbMarshaller.create(Parameters.class);
        Parameters input = sampleParameters();
        String xml = instance.marshal(input);
        Parameters output = instance.unmarshal(xml);
        assertReflectionEquals(input, output);
    }

    @Test
    public void testOnBooleanToIntegerAdapter() {
        JaxbMarshaller<CompaniesApi> instance = JaxbMarshaller.create(CompaniesApi.class);
        Companies companies = new Companies();
        CompaniesApi api = CompaniesApi.class.cast(companies.getApi());
        Company company = new Company();
        company.setRegistered(Boolean.TRUE);
        company.setVatPayer(false);
        company.setName("Coca Cola");
        company.setNip("123-45-67-890");
        companies.getCompany().add(company);
        String output = instance.marshal(api);
        String expOutputFull = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<api>\n"
                + "    <companies>\n"
                + "        <company>\n"
                + "            <name>Coca Cola</name>\n"
                + "            <nip>123-45-67-890</nip>\n"
                + "            <vat_payer>0</vat_payer>\n"
                + "            <is_registered>1</is_registered>\n"
                + "        </company>\n"
                + "    </companies>\n"
                + "</api>\n";
        assertThat(output).isEqualTo(expOutputFull);
        CompaniesApi resultApi = instance.unmarshal(expOutputFull);
        assertReflectionEquals(api, resultApi);
        company = new Company();
        company.setRegistered(true);
        companies.getCompany().clear();
        companies.getCompany().add(company);
        output = instance.marshal(api);
        assertThat(output).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<api>\n"
                + "    <companies>\n"
                + "        <company>\n"
                + "            <is_registered>1</is_registered>\n"
                + "        </company>\n"
                + "    </companies>\n"
                + "</api>\n");
    }

    @Test(expected = IllegalStateException.class)
    public void testOnInvalidInputString() {
        JaxbMarshaller<CompaniesApi> instance = JaxbMarshaller.create(CompaniesApi.class);
        String expOutputFull = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<apis>\n"
                + "    <companies>\n"
                + "        <company />\n"
                + "    </companies>\n"
                + "</apis>\n";
        assertThat(instance.unmarshal(expOutputFull)).isNull();
    }

    @Test
    public void testMarshalUnMarshalInvoice() {
        JaxbMarshaller<InvoicesApi> instance = JaxbMarshaller.create(InvoicesApi.class);
        InvoicesApi api = new InvoicesApi();
        Invoices invoices = new Invoices(api);
        NormalInvoice invoice = new NormalInvoice();
        GregorianCalendar cal = new GregorianCalendar(2014, 3, 11);
        invoice.setId(13L);
        invoice.setAlreadyPaid(Money.of(CurrencyUnit.USD, 932.00d));
        invoice.setAlreadyPaidInitial(Money.of(CurrencyUnit.USD, 932.00d));
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
        String result = instance.marshal(api);
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<api>\n"
                + "    <invoices>\n"
                + "        <normalInvoiceType>\n"
                + "            <id>13</id>\n"
                + "            <type>normal</type>\n"
                + "            <disposaldate_form>month</disposaldate_form>\n"
                + "            <disposaldate_empty>1</disposaldate_empty>\n"
                + "            <disposaldate>2014-04-11</disposaldate>\n"
                + "            <date>2014-04-11</date>\n"
                + "            <total>932.00</total>\n"
                + "            <alreadypaid>932.00</alreadypaid>\n"
                + "            <alreadypaid_initial>932.00</alreadypaid_initial>\n"
                + "            <remaining>0.00</remaining>\n"
                + "            <number>3</number>\n"
                + "            <day>11</day>\n"
                + "            <month>4</month>\n"
                + "            <year>2014</year>\n"
                + "            <semitemplatenumber>FV [number]/[year]</semitemplatenumber>\n"
                + "            <fullnumber>FV 3/2014</fullnumber>\n"
                + "        </normalInvoiceType>\n"
                + "    </invoices>\n"
                + "</api>\n";
        assertThat(result).isEqualTo(expected);
        InvoicesApi resultApi = instance.unmarshal(expected);
        assertThat(resultApi).isNotNull();
        List<AbstractInvoice> resultList = resultApi.getInvoices().getInvoice();
        assertThat(resultList).isNotNull();
        assertThat(resultList).hasSize(1);
        AbstractInvoice resultInvoice = resultList.iterator().next();
        assertThat(resultInvoice).isExactlyInstanceOf(NormalInvoice.class);
        assertThat(resultInvoice).isEqualToComparingFieldByField(invoice);
    }

}
