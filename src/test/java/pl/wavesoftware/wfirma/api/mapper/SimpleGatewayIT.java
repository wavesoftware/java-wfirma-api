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
package pl.wavesoftware.wfirma.api.mapper;

import java.net.URI;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.wavesoftware.wfirma.api.SimpleCredentials;
import pl.wavesoftware.wfirma.api.mapper.xml.JaxbMarshaller;
import pl.wavesoftware.wfirma.api.model.Request;
import pl.wavesoftware.wfirma.api.model.WFirmaException;
import pl.wavesoftware.wfirma.api.model.WFirmaSecurityException;
import pl.wavesoftware.wfirma.api.model.companies.Companies;
import pl.wavesoftware.wfirma.api.model.companies.CompaniesGetRequest;
import pl.wavesoftware.wfirma.api.model.contractors.Contractor;
import pl.wavesoftware.wfirma.api.model.contractors.Contractors;
import pl.wavesoftware.wfirma.api.model.contractors.ContractorsApi;
import pl.wavesoftware.wfirma.api.model.logic.And;
import pl.wavesoftware.wfirma.api.model.logic.Condition;
import pl.wavesoftware.wfirma.api.model.logic.Conditions;
import pl.wavesoftware.wfirma.api.model.logic.LogicalOperator;
import pl.wavesoftware.wfirma.api.model.logic.Parameters;
import pl.wavesoftware.wfirma.api.model.requests.AddRequest;
import pl.wavesoftware.wfirma.api.model.requests.DeleteRequest;
import pl.wavesoftware.wfirma.api.model.requests.FindRequest;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class SimpleGatewayIT {

    private String expResultAuth;

    private String expResultRe;

    private String correctLogin;

    private String correctPassword;

    private String expResultPost;

    private static final String EXAMPLE_NIP = "5272516453";

    private static final URI GATEWAY_ADDRESS = URI.create(WFirmaGateway.GATEWAY_ADDRESS);

    @Before
    public void before() {
        correctLogin = System.getenv("WFIRMA_LOGIN");
        correctPassword = System.getenv("WFIRMA_PASSWORD");
        Logger logger = LoggerFactory.getLogger(this.getClass());
        boolean loginCond = (correctLogin == null || "".equals(correctLogin.trim()));
        boolean passCond = (correctPassword == null || "".equals(correctPassword.trim()));
        if (loginCond) {
            logger.warn("Please set environment variable `WFIRMA_LOGIN`"
                    + " before running this intergration test, skipping!");
        }
        Assume.assumeFalse(loginCond);
        if (passCond) {
            logger.warn("Please set environment variable `WFIRMA_PASSWORD`"
                    + " before running this intergration test, skipping!");
        }
        Assume.assumeFalse(passCond);

        expResultAuth = "<\\?xml version=\"1\\.0\" encoding=\"UTF-8\"\\?>\\s*"
                + "<api>\\s*"
                + "<status>\\s*"
                + "<code>AUTH</code>\\s*"
                + "</status>\\s*"
                + "</api>\\s*";
        expResultRe = "<\\?xml version=\"1\\.0\" encoding=\"UTF-8\"\\?>\\s*"
                + "<api>\\s*"
                + "<companies>\\s*"
                + "<company>\\s*"
                + "<id>\\d+</id>\\s*"
                + "<name>.*</name>\\s*"
                + "<altname>.*</altname>\\s*"
                + "<nip>.*</nip>\\s*"
                + "<vat_payer>(?:1|0)</vat_payer>\\s*"
                + "<tax>taxregister</tax>\\s*"
                + "<is_registered>(?:1|0)</is_registered>\\s*"
                + "</company>\\s*"
                + "</companies>\\s*"
                + "<status>\\s*"
                + "<code>OK</code>\\s*"
                + "</status>\\s*"
                + "</api>\\s*";
        expResultPost = "<\\?xml version=\"1\\.0\" encoding=\"UTF-8\"\\?>\\s*"
                + "<api>\\s*"
                + "<contractors>\\s*"
                + "<contractor>\\s*"
                + "<id>\\d+</id>\\s*"
                + "<tax_id_type>.*?</tax_id_type>\\s*"
                + "<name>Wave Software</name>\\s*"
                + "<altname>Wave Software</altname>\\s*"
                + "<nip>" + EXAMPLE_NIP + "</nip>\\s*"
                + "<regon></regon>\\s*"
                + "<street></street>\\s*"
                + "<zip></zip>\\s*"
                + "<city></city>\\s*"
                + "<country>PL</country>\\s*"
                + "<different_contact_address>0</different_contact_address>\\s*"
                + "<contact_name></contact_name>\\s*"
                + "<contact_street></contact_street>\\s*"
                + "<contact_zip></contact_zip>\\s*"
                + "<contact_city></contact_city>\\s*"
                + "<contact_country>PL</contact_country>\\s*"
                + "<contact_person></contact_person>\\s*"
                + "<phone></phone>\\s*"
                + "<skype></skype>\\s*"
                + "<fax></fax>\\s*"
                + "<email></email>\\s*"
                + "<url></url>\\s*"
                + "<description></description>\\s*"
                + "<buyer>\\d</buyer>\\s*"
                + "<seller>\\d</seller>\\s*"
                + "<discount_percent>\\d+.00</discount_percent>\\s*"
                + "<payment_days>\\d+</payment_days>\\s*"
                + "<payment_method></payment_method>\\s*"
                + "<account_number></account_number>\\s*"
                + "<remind>\\d</remind>\\s*"
                + "<hash>[0-9a-f]+</hash>\\s*"
                + "<tags></tags>\\s*"
                + "<notes>0</notes>\\s*"
                + "<documents>0</documents>\\s*"
                + "<created>[0-9-]+ [0-9:]+</created>\\s*"
                + "<modified>[0-9-]+ [0-9:]+</modified>\\s*"
                + "<reference_company>\\s*<id>0</id>\\s*</reference_company>\\s*"
                + "<translation_language>\\s*<id>0</id>\\s*</translation_language>\\s*"
                + "<company_account>\\s*<id>0</id>\\s*</company_account>\\s*"
                + "<good_price_group>\\s*<id>0</id>\\s*</good_price_group>\\s*"
                + "<invoice_description>\\s*<id>0</id>\\s*</invoice_description>\\s*"
                + "<shop_buyer>\\s*<id>0</id>\\s*</shop_buyer>\\s*"
                + "</contractor>\\s*"
                + "<parameters>\\s*"
                + "<limit>\\d+</limit>\\s*"
                + "<page>1</page>\\s*"
                + "<total>1</total>\\s*"
                + "</parameters>\\s*"
                + "</contractors>\\s*"
                + "<status>\\s*"
                + "<code>OK</code>\\s*"
                + "</status>\\s*"
                + "</api>\\s*";
    }

    private SimpleGateway instance(String login, String password) {
        SimpleCredentials creds = new SimpleCredentials(login, password);
        CredentialsBuilder builder = CredentialsBuilder.from(creds);
        return new SimpleGateway(builder, GATEWAY_ADDRESS);
    }

    @Test
    public void testFetch() throws WFirmaException {
        SimpleGateway instance = instance(correctLogin, correctPassword);

        Request<Companies> get = CompaniesGetRequest.create();
        String result = instance.get(get);
        assertThat(result).isNotNull();
        assertThat(result).matches(expResultRe);
    }

    @Test
    public void testPost() throws Exception {
        AddRequest<Contractors> addRequest = createAddRequest();
        FindRequest<Contractors> findRequest = createFindRequest();
        DeleteRequest<Contractors> deleteRequest;

        SimpleGateway instance = instance(correctLogin, correctPassword);

        String result;
        result = instance.post(findRequest);
        ContractorsApi api;
        api = JaxbMarshaller.create(ContractorsApi.class).unmarshal(result);
        if (!api.getContractors().getContractor().isEmpty()) {
            for (Contractor contractor : api.getContractors().getContractor()) {
                deleteRequest = createDeleteRequest(contractor);
                instance.get(deleteRequest);
            }
        }
        result = instance.post(findRequest);
        assertThat(result).doesNotContain(EXAMPLE_NIP);
        result = instance.post(addRequest);
        assertThat(result).isNotEmpty();
        result = instance.post(findRequest);
        assertThat(result).matches(expResultPost);
        assertThat(result).contains(EXAMPLE_NIP);
        api = JaxbMarshaller.create(ContractorsApi.class).unmarshal(result);
        deleteRequest = createDeleteRequest(api.getContractors().getContractor().iterator().next());
        result = instance.get(deleteRequest);
        assertThat(result).isNotEmpty();
        assertThat(result).containsSequence("Kontrahent: ", " został usunięty.");
        result = instance.post(findRequest);
        assertThat(result).doesNotMatch(expResultPost);
        assertThat(result).doesNotContain(EXAMPLE_NIP);
    }

    @Test
    public void testFetchWithAuthFail() throws WFirmaException, BackingStoreException {
        Preferences prefs = Preferences.userRoot();
        String key = this.getClass().getName() + "::testFetchWithAuthFail";
        long lastRun = prefs.getLong(key, 0);
        long current = System.currentTimeMillis();
        boolean cond = current - lastRun > 5 * 60 * 1000;
        if (!cond) {
            LoggerFactory.getLogger(this.getClass()).warn(
                    "Invalid login testing on WFirma API is possible no more often then 5min interval, skipping test!");
        }
        Assume.assumeTrue(cond);
        SimpleGateway instance = instance("non-existing-login-2@example.org", "invalid-password");
        final StringBuilder responseBuilder = new StringBuilder();
        ResponseListener listener = new ResponseListener() {

            @Override
            public void responseRecived(String recived) {
                responseBuilder.append(recived);
            }
        };
        instance.addListener(listener);
        try {
            Request<Companies> get = CompaniesGetRequest.create();
            instance.get(get);
            fail("Expected to throw a WFirmaSercurityException for invalid auth");
        } catch (WFirmaSecurityException ex) {
            assertEquals("Auth failed for user: `non-existing-login-2@example.org`", ex.getLocalizedMessage());
        } finally {
            instance.removeListener(listener);
        }
        assertThat(responseBuilder.toString()).matches(expResultAuth);
        prefs.putLong(key, current);
        prefs.flush();
    }

    private FindRequest<Contractors> createFindRequest() {
        Parameters params = new Parameters();
        Conditions conds = params.getConditions();
        And and = new And();
        Condition cond = new Condition();
        cond.setField("nip");
        cond.setOperator(LogicalOperator.EQ);
        cond.setValue(EXAMPLE_NIP);
        and.getCondition().add(cond);
        conds.getAnd().add(and);
        return FindRequest.create(Contractors.class, params);
    }

    private AddRequest<Contractors> createAddRequest() {
        ContractorsApi api = new ContractorsApi();
        Contractors contractors = new Contractors(api);
        api.setContractors(contractors);
        Contractor contractor = new Contractor();
        contractors.getContractor().add(contractor);
        contractor.setNip(EXAMPLE_NIP);
        contractor.setName("Wave Software");
        return AddRequest.create(contractors);
    }

    private DeleteRequest<Contractors> createDeleteRequest(Contractor contractor) {
        return DeleteRequest.create(Contractors.class, contractor.getId());
    }

}
