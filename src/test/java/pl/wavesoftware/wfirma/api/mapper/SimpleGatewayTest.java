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
package pl.wavesoftware.wfirma.api.mapper;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;
import mockit.Deencapsulation;
import org.apache.http.HttpHost;
import static org.assertj.core.api.Assertions.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.wavesoftware.wfirma.api.SimpleCredentials;
import pl.wavesoftware.wfirma.api.model.WFirmaException;
import pl.wavesoftware.wfirma.api.model.WFirmaSecurityException;
import pl.wavesoftware.wfirma.api.model.companies.CompaniesGetRequest;
import pl.wavesoftware.wfirma.api.model.contractors.Contractors;
import pl.wavesoftware.wfirma.api.model.logic.And;
import pl.wavesoftware.wfirma.api.model.logic.Condition;
import pl.wavesoftware.wfirma.api.model.logic.Conditions;
import pl.wavesoftware.wfirma.api.model.logic.LogicalOperator;
import pl.wavesoftware.wfirma.api.model.logic.Parameters;
import pl.wavesoftware.wfirma.api.model.requests.FindRequest;
import pl.wavesoftware.wfirma.api.model.requests.GetRequest;
import pl.wavesoftware.wfirma.api.runtime.FatalSdkException;

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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final URI mockAddress = URI.create("http://localhost:" + PORT);

    private static final String CONTENT_TYPE_TEXT_XML = "text/xml";

    private String expResultAuth;

    private String expResult;

    private String expPostResult;

    private String contractorsFindBody;

    private SimpleGateway instance(String login, String password) {
        return instance(login, password, mockAddress);
    }

    private SimpleGateway instance(String login, String password, URI address) {
        SimpleCredentials creds = new SimpleCredentials(login, password);
        CredentialsBuilder builder = CredentialsBuilder.from(creds);
        return new SimpleGateway(builder, address);
    }

    @Test
    public void testFetch() throws WFirmaException {
        SimpleGateway instance = instance("login@example.org", "a-user-password");

        CompaniesGetRequest get = CompaniesGetRequest.create();
        String result = instance.get(get);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expResult);

        instance = instance("login2@example.org", "invalid-password");

        try {
            instance.get(get);
            fail("Expected to throw a WFirmaSercurityException for invalid auth");
        } catch (WFirmaSecurityException ex) {
            assertThat(ex.getLocalizedMessage()).isEqualTo("Auth failed for user: `login2@example.org`");
        }
    }

    @Test
    public void testFetchWithAuthFail() throws WFirmaException {
        SimpleGateway instance = instance("login@example.org", "a-user-password");

        CompaniesGetRequest get = CompaniesGetRequest.create();
        String result = instance.get(get);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expResult);

        instance = instance("login2@example.org", "invalid-password");

        try {
            instance.get(get);
            fail("Expected to throw a WFirmaSercurityException for invalid auth");
        } catch (WFirmaSecurityException ex) {
            assertThat(ex.getLocalizedMessage()).isEqualTo("Auth failed for user: `login2@example.org`");
        }
    }

    @Test
    public void testFetchWithAuthFail2() throws WFirmaException {
        GetRequest<Contractors> get = GetRequest.create(Contractors.class, 1L);
        SimpleGateway instance = instance("login2@example.org", "invalid-password");

        try {
            instance.get(get);
            fail("Expected to throw a WFirmaSercurityException for invalid auth");
        } catch (WFirmaSecurityException ex) {
            assertThat(ex.getLocalizedMessage()).isEqualTo("Auth failed for user: `login2@example.org`");
        }
    }

    @Test
    public void testFetchWithConnectionFail() throws WFirmaException {
        FindRequest<Contractors> get = FindRequest.create(Contractors.class);
        URI uri = URI.create("http://localhost:" + (PORT - 1));
        SimpleGateway instance = instance("login@example.org", "a-user-password", uri);

        try {
            instance.get(get);
            fail("Expected to throw a WFirmaException for connection fail");
        } catch (WFirmaException ex) {
            assertThat(ex.getLocalizedMessage()).contains("Connect to localhost:", "failed:");
        }
    }

    @Test
    public void testFetchWithFatalFail() throws WFirmaException {
        FindRequest<Contractors> get = FindRequest.create(Contractors.class);
        SimpleGateway instance = instance("login@example.org", "fatal-error");

        try {
            instance.get(get);
            fail("Expected to throw a WFirmaException for connection fail");
        } catch (WFirmaException ex) {
            assertThat(ex.getLocalizedMessage()).isEqualTo("Connection error: 500 - Internal Server Error");
        }
    }

    @Test
    public void testGetTargetHost() {
        SimpleGateway instance = instance("login@example.org", "a-user-password", URI.create("http://localhost"));
        HttpHost host = (HttpHost) Deencapsulation.invoke(instance, "getTargetHost");
        assertThat(host.getPort()).isEqualTo(80);

        instance = instance("login@example.org", "a-user-password", URI.create("https://localhost"));
        host = (HttpHost) Deencapsulation.invoke(instance, "getTargetHost");
        assertThat(host.getPort()).isEqualTo(443);

        instance = instance("login@example.org", "a-user-password", URI.create("ajp://localhost"));
        thrown.expect(FatalSdkException.class);
        thrown.expectMessage("[20150716:113056]: Unsupported URI scheme: ajp, supporting only: `http` and `https`");
        Deencapsulation.invoke(instance, "getTargetHost");
    }

    @Before
    public void before() {
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
            + "            <limit>20</limit>\n"
            + "        </parameters>\n"
            + "    </contractors>\n"
            + "</api>\n"
            + "";

        stubFor(get(urlEqualTo("/companies/get"))
            .withHeader("Accept", equalTo(CONTENT_TYPE_TEXT_XML))
            .withHeader("Authorization", equalTo("Basic bG9naW5AZXhhbXBsZS5vcmc6YS11c2VyLXBhc3N3b3Jk"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", CONTENT_TYPE_TEXT_XML)
                .withBody(expResult)));
        stubFor(get(urlEqualTo("/companies/get"))
            .withHeader("Accept", equalTo(CONTENT_TYPE_TEXT_XML))
            .withHeader("Authorization", notMatching("Basic bG9naW5AZXhhbXBsZS5vcmc6YS11c2VyLXBhc3N3b3Jk"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", CONTENT_TYPE_TEXT_XML)
                .withBody(expResultAuth)));
        stubFor(get(urlEqualTo("/contractors/get/1"))
            .withHeader("Accept", equalTo(CONTENT_TYPE_TEXT_XML))
            .withHeader("Authorization", notMatching("Basic bG9naW5AZXhhbXBsZS5vcmc6YS11c2VyLXBhc3N3b3Jk"))
            .willReturn(aResponse()
                .withStatus(403)
                .withHeader("Content-Type", CONTENT_TYPE_TEXT_XML)
                .withBody(expResultAuth)));
        stubFor(get(urlEqualTo("/contractors/find"))
            .withHeader("Accept", equalTo(CONTENT_TYPE_TEXT_XML))
            .withHeader("Authorization", notMatching("Basic bG9naW5AZXhhbXBsZS5vcmc6YS11c2VyLXBhc3N3b3Jk"))
            .willReturn(aResponse()
                .withStatus(500)
                .withHeader("Content-Type", CONTENT_TYPE_TEXT_XML)
                .withBody("")));
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
        SimpleGateway instance = instance("login@example.org", "a-user-password");
        instance.addListener(listener);
        CompaniesGetRequest get = CompaniesGetRequest.create();
        instance.get(get);
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
        SimpleGateway instance = instance("login@example.org", "a-user-password");
        instance.addListener(listener);
        instance.removeListener(listener);
        CompaniesGetRequest get = CompaniesGetRequest.create();
        instance.get(get);
        assertThat(sb.toString()).isEqualTo("");
    }

    @Test
    public void testGet() throws Exception {
        SimpleGateway instance = instance("login@example.org", "a-user-password");
        CompaniesGetRequest get = CompaniesGetRequest.create();
        String result = instance.get(get);
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
        FindRequest<Contractors> findRequest = FindRequest.create(Contractors.class, params);
        SimpleGateway instance = instance("login@example.org", "a-user-password");
        String result = instance.post(findRequest);
        assertThat(result).isEqualTo(expPostResult);
    }

}
