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

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;
import static org.assertj.core.api.Assertions.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import pl.wavesoftware.wfirma.api.SimpleCredentials;
import pl.wavesoftware.wfirma.api.model.AbstractParametrizedRequest;
import pl.wavesoftware.wfirma.api.model.WFirmaException;
import pl.wavesoftware.wfirma.api.model.WFirmaSercurityException;
import pl.wavesoftware.wfirma.api.model.contractors.ContractorsRequest;
import pl.wavesoftware.wfirma.api.model.logic.And;
import pl.wavesoftware.wfirma.api.model.logic.Condition;
import pl.wavesoftware.wfirma.api.model.logic.Conditions;
import pl.wavesoftware.wfirma.api.model.logic.LogicalOperator;
import pl.wavesoftware.wfirma.api.model.logic.Parameters;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class SimpleGatewayTest {

    private static final int PORT;

    static {
        try (ServerSocket socket = new ServerSocket(0)) {
            PORT = socket.getLocalPort();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(PORT);

    private final URI mockAddress = URI.create("http://localhost:" + PORT);

    private static final String CONTENT_TYPE_TEXT_XML = "text/xml";

    private String path;

    private String expResultAuth;

    private String expResult;

    private String expPostResult;

    private String contractorsFindBody;

    @Test
    public void testFetch() throws WFirmaException {
        SimpleCredentials creds = new SimpleCredentials("login@example.org", "a-user-password");
        SimpleGateway instance = new SimpleGateway(creds, mockAddress);

        String result = instance.get(RequestPath.fromString(path));
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expResult);

        creds = new SimpleCredentials("login2@example.org", "invalid-password");
        instance = new SimpleGateway(creds, mockAddress);

        try {
            instance.get(RequestPath.fromString(path));
            fail("Expected to throw a WFirmaSercurityException for invalid auth");
        } catch (WFirmaSercurityException ex) {
            assertThat(ex.getLocalizedMessage()).isEqualTo("Auth failed for user: `login2@example.org`");
        }
    }

    @Test
    public void testFetchWithAuthFail() throws WFirmaException {
        SimpleCredentials creds = new SimpleCredentials("login@example.org", "a-user-password");
        SimpleGateway instance = new SimpleGateway(creds, mockAddress);

        String result = instance.get(RequestPath.fromString(path));
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expResult);

        creds = new SimpleCredentials("login2@example.org", "invalid-password");
        instance = new SimpleGateway(creds, mockAddress);

        try {
            instance.get(RequestPath.fromString(path));
            fail("Expected to throw a WFirmaSercurityException for invalid auth");
        } catch (WFirmaSercurityException ex) {
            assertThat(ex.getLocalizedMessage()).isEqualTo("Auth failed for user: `login2@example.org`");
        }
    }

    @Before
    public void before() {
        path = "/companies/get";
        expResultAuth = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<api>\n"
                + "    <status>\n"
                + "        <code>AUTH</code>\n"
                + "    </status>\n"
                + "</api>\n"
                + " \n";
        expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<api>\n"
                + "    <companies>\n"
                + "        <company>\n"
                + "            <id>70572</id>\n"
                + "            <name></name>\n"
                + "            <altname></altname>\n"
                + "            <nip></nip>\n"
                + "            <vat_payer>1</vat_payer>\n"
                + "            <tax>taxregister</tax>\n"
                + "            <is_registered>0</is_registered>\n"
                + "        </company>\n"
                + "    </companies>\n"
                + "    <status>\n"
                + "        <code>OK</code>\n"
                + "    </status>\n"
                + "</api>\n"
                + "\n";
        expPostResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<api>\n"
                + "    <contractors>\n"
                + "        <contractor>\n"
                + "            <id>11111111111</id>\n"
                + "            <tax_id_type>nip</tax_id_type>\n"
                + "            <name>SomeSuper Business Ltd.</name>\n"
                + "            <altname>SomeSuper Business Ltd.</altname>\n"
                + "            <nip>1112233444</nip>\n"
                + "            <regon></regon>\n"
                + "            <street>ul. Przykładowa 13</street>\n"
                + "            <zip>12-345</zip>\n"
                + "            <city>Przykładów</city>\n"
                + "            <country>PL</country>\n"
                + "            <different_contact_address>0</different_contact_address>\n"
                + "            <contact_name></contact_name>\n"
                + "            <contact_street></contact_street>\n"
                + "            <contact_zip></contact_zip>\n"
                + "            <contact_city></contact_city>\n"
                + "            <contact_country>PL</contact_country>\n"
                + "            <contact_person></contact_person>\n"
                + "            <phone></phone>\n"
                + "            <skype></skype>\n"
                + "            <fax></fax>\n"
                + "            <email></email>\n"
                + "            <url></url>\n"
                + "            <description></description>\n"
                + "            <buyer>1</buyer>\n"
                + "            <seller>1</seller>\n"
                + "            <discount_percent>5.00</discount_percent>\n"
                + "            <payment_days>7</payment_days>\n"
                + "            <payment_method></payment_method>\n"
                + "            <account_number></account_number>\n"
                + "            <remind>1</remind>\n"
                + "            <hash>8c3268dbcc1961af03b94d2f938c7902</hash>\n"
                + "            <tags></tags>\n"
                + "            <notes>0</notes>\n"
                + "            <documents>0</documents>\n"
                + "            <created>2014-03-15 19:23:39</created>\n"
                + "            <modified>2014-03-15 19:24:53</modified>\n"
                + "            <reference_company>\n"
                + "                <id>0</id>\n"
                + "            </reference_company>\n"
                + "            <translation_language>\n"
                + "                <id>0</id>\n"
                + "            </translation_language>\n"
                + "            <company_account>\n"
                + "                <id>0</id>\n"
                + "            </company_account>\n"
                + "            <good_price_group>\n"
                + "                <id>0</id>\n"
                + "            </good_price_group>\n"
                + "            <invoice_description>\n"
                + "                <id>0</id>\n"
                + "            </invoice_description>\n"
                + "            <shop_buyer>\n"
                + "                <id>0</id>\n"
                + "            </shop_buyer>\n"
                + "        </contractor>\n"
                + "        <parameters>\n"
                + "            <limit>0</limit>\n"
                + "            <page>1</page>\n"
                + "            <total>1</total>\n"
                + "        </parameters>\n"
                + "    </contractors>\n"
                + "    <status>\n"
                + "        <code>OK</code>\n"
                + "    </status>\n"
                + "</api>\n"
                + " \n";
        contractorsFindBody = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<api>\n"
                + "    <contractors>\n"
                + "        <parameters>\n"
                + "            <conditions>\n"
                + "                <and>\n"
                + "                    <condition>\n"
                + "                        <field>nip</field>\n"
                + "                        <operator>eq</operator>\n"
                + "                        <value>1112233444</value>\n"
                + "                    </condition>\n"
                + "                </and>\n"
                + "            </conditions>\n"
                + "            <page>0</page>\n"
                + "            <limit>0</limit>\n"
                + "        </parameters>\n"
                + "    </contractors>\n"
                + "</api>\n"
                + "";

        stubFor(get(urlEqualTo(path))
                .withHeader("Accept", equalTo(CONTENT_TYPE_TEXT_XML))
                .withHeader("Authorization", equalTo("Basic bG9naW5AZXhhbXBsZS5vcmc6YS11c2VyLXBhc3N3b3Jk"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", CONTENT_TYPE_TEXT_XML)
                        .withBody(expResult)));
        stubFor(get(urlEqualTo(path))
                .withHeader("Accept", equalTo(CONTENT_TYPE_TEXT_XML))
                .withHeader("Authorization", notMatching("Basic bG9naW5AZXhhbXBsZS5vcmc6YS11c2VyLXBhc3N3b3Jk"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", CONTENT_TYPE_TEXT_XML)
                        .withBody(expResultAuth)));
        stubFor(post(urlEqualTo("/contractors/find"))
                .withRequestBody(equalTo(contractorsFindBody))
                .withHeader("Accept", equalTo(CONTENT_TYPE_TEXT_XML))
                .withHeader("Authorization", equalTo("Basic bG9naW5AZXhhbXBsZS5vcmc6YS11c2VyLXBhc3N3b3Jk"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", CONTENT_TYPE_TEXT_XML)
                        .withBody(expPostResult)));
    }

    @Test
    public void testAddListener() throws WFirmaException {
        final StringBuilder sb = new StringBuilder();
        ResponseListener listener = new ResponseListener() {

            @Override
            public void responseRecived(String response) {
                sb.append(response);
            }
        };
        SimpleCredentials creds = new SimpleCredentials("login@example.org", "a-user-password");
        SimpleGateway instance = new SimpleGateway(creds, mockAddress);
        instance.addListener(listener);
        instance.get(RequestPath.fromString(path));
        assertThat(sb.toString()).isEqualTo(expResult);
    }

    @Test
    public void testRemoveListener() throws WFirmaException {
        final StringBuilder sb = new StringBuilder();
        ResponseListener listener = new ResponseListener() {

            @Override
            public void responseRecived(String response) {
                sb.append(response);
            }
        };
        SimpleCredentials creds = new SimpleCredentials("login@example.org", "a-user-password");
        SimpleGateway instance = new SimpleGateway(creds, mockAddress);
        instance.addListener(listener);
        instance.removeListener(listener);
        instance.get(RequestPath.fromString(path));
        assertThat(sb.toString()).isEqualTo("");
    }

    @Test
    public void testGet() throws Exception {
        RequestPath requestPath = RequestPath.fromString(path);
        SimpleCredentials creds = new SimpleCredentials("login@example.org", "a-user-password");
        SimpleGateway instance = new SimpleGateway(creds, mockAddress);
        String result = instance.get(requestPath);
        assertThat(result).isEqualTo(expResult);
    }

    @Test
    public void testPost() throws Exception {
        Parameters params = new Parameters();
        Conditions conds = params.getConditions();
        And and = new And();
        Condition cond = new Condition();
        cond.setField("nip");
        cond.setOperator(LogicalOperator.EQ);
        cond.setValue("1112233444");
        and.getCondition().add(cond);
        conds.getAnd().add(and);
        AbstractParametrizedRequest findRequest = new ContractorsRequest(ContractorsRequest.Action.FIND, params);
        SimpleCredentials creds = new SimpleCredentials("login@example.org", "a-user-password");
        SimpleGateway instance = new SimpleGateway(creds, mockAddress);
        String result = instance.post(findRequest);
        assertThat(result).isEqualTo(expPostResult);
    }

}
