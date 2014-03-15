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

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import static org.junit.Assert.*;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static pl.wavesoftware.util.RegexMatcher.matches;
import pl.wavesoftware.wfirma.api.SimpleCredentials;
import pl.wavesoftware.wfirma.api.model.AbstractFindRequest;
import pl.wavesoftware.wfirma.api.model.WFirmaException;
import pl.wavesoftware.wfirma.api.model.WFirmaSercurityException;
import pl.wavesoftware.wfirma.api.model.contractors.ContractorsFindRequest;
import pl.wavesoftware.wfirma.api.model.logic.And;
import pl.wavesoftware.wfirma.api.model.logic.Condition;
import pl.wavesoftware.wfirma.api.model.logic.Conditions;
import pl.wavesoftware.wfirma.api.model.logic.LogicalOperator;
import pl.wavesoftware.wfirma.api.model.logic.Parameters;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class SimpleGatewayIT {

    private String path;

    private String expResultAuth;

    private String expResultRe;

    private String correctLogin;

    private String correctPassword;

    private String expResultPost;

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

        path = "/companies/get";
        expResultAuth = "<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>\\s*"
                + "<api>\\s*"
                + "<status>\\s*"
                + "<code>AUTH</code>\\s*"
                + "</status>\\s*"
                + "</api>\\s*";
        expResultRe = "<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>\\s*"
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
        expResultPost = "<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>\\s*"
                + "<api>\\s*"
                + "<contractors>\\s*"
                + "<contractor>\\s*"
                + "<id>\\d+</id>\\s*"
                + "<tax_id_type>nip</tax_id_type>\\s*"
                + "<name>Wave Software Krzysztof Suszyński</name>\\s*"
                + "<altname>Wave Software Krzysztof Suszyński</altname>\\s*"
                + "<nip>5272516453</nip>\\s*"
                + "<regon></regon>\\s*"
                + "<street>ul. Willowa 6</street>\\s*"
                + "<zip>05-083</zip>\\s*"
                + "<city>Zaborów</city>\\s*"
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
                + "<buyer>1</buyer>\\s*"
                + "<seller>1</seller>\\s*"
                + "<discount_percent>\\d+.00</discount_percent>\\s*"
                + "<payment_days>7</payment_days>\\s*"
                + "<payment_method></payment_method>\\s*"
                + "<account_number></account_number>\\s*"
                + "<remind>1</remind>\\s*"
                + "<hash>[0-9a-f]+</hash>\\s*"
                + "<tags></tags>\\s*"
                + "<notes>0</notes>\\s*"
                + "<documents>0</documents>\\s*"
                + "<created>[0-9-]+ [0-9:]+</created>\\s*"
                + "<modified>[0-9-]+ [0-9:]+</modified>\\s*"
                + "<reference_company>\\s*"
                + "<id>0</id>\\s*"
                + "</reference_company>\\s*"
                + "<translation_language>\\s*"
                + "<id>0</id>\\s*"
                + "</translation_language>\\s*"
                + "<company_account>\\s*"
                + "<id>0</id>\\s*"
                + "</company_account>\\s*"
                + "<good_price_group>\\s*"
                + "<id>0</id>\\s*"
                + "</good_price_group>\\s*"
                + "<invoice_description>\\s*"
                + "<id>0</id>\\s*"
                + "</invoice_description>\\s*"
                + "<shop_buyer>\\s*"
                + "<id>0</id>\\s*"
                + "</shop_buyer>\\s*"
                + "</contractor>\\s*"
                + "<parameters>\\s*"
                + "<limit>0</limit>\\s*"
                + "<page>1</page>\\s*"
                + "<total>1</total>\\s*"
                + "</parameters>\\s*"
                + "</contractors>\\s*"
                + "<status>\\s*"
                + "<code>OK</code>\\s*"
                + "</status>\\s*"
                + "</api>\\s*";
    }

    @Test
    public void testFetch() throws WFirmaException {
        SimpleCredentials creds = new SimpleCredentials(correctLogin, correctPassword);
        SimpleGateway instance = new SimpleGateway(creds);

        String result = instance.get(RequestPath.fromString(path));
        assertNotNull(result);
        assertThat(result, matches(expResultRe));
    }

    @Test
    public void testPost() throws Exception {
        Parameters params = new Parameters();
        Conditions conds = params.getConditions();
        And and = new And();
        Condition cond = new Condition();
        cond.setField("nip");
        cond.setOperator(LogicalOperator.EQ);
        cond.setValue("5272516453");
        and.getCondition().add(cond);
        conds.getAnd().add(and);
        AbstractFindRequest findRequest = new ContractorsFindRequest(ContractorsFindRequest.Action.FIND, params);
        SimpleCredentials creds = new SimpleCredentials(correctLogin, correctPassword);
        SimpleGateway instance = new SimpleGateway(creds);
        String result = instance.post(findRequest);
        assertThat(result, matches(expResultPost));
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
        SimpleCredentials creds = new SimpleCredentials("non-existing-login-2@example.org", "invalid-password");
        SimpleGateway instance = new SimpleGateway(creds);
        final StringBuilder responseBuilder = new StringBuilder();
        ResponseListener listener = new ResponseListener() {

            @Override
            public void responseRecived(String recived) {
                responseBuilder.append(recived);
            }
        };
        instance.addListener(listener);
        try {
            instance.get(RequestPath.fromString(path));
            fail("Expected to throw a WFirmaSercurityException for invalid auth");
        } catch (WFirmaSercurityException ex) {
            assertEquals("Auth failed for user: `non-existing-login-2@example.org`", ex.getLocalizedMessage());
        } finally {
            instance.removeListener(listener);
        }
        assertThat(responseBuilder.toString(), matches(expResultAuth));
        prefs.putLong(key, current);
        prefs.flush();
    }

}
