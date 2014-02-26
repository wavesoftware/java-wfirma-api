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

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.notMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;
import pl.wavesoftware.wfirma.api.SimpleCredentials;
import pl.wavesoftware.wfirma.api.model.WFirmaException;
import pl.wavesoftware.wfirma.api.model.WFirmaSercurityException;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class SimpleGatewayTest {

    private static final int PORT;

    static {
        try {
            ServerSocket socket = new ServerSocket(0);
            PORT = socket.getLocalPort();
            socket.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(PORT);

    private URI mockAddress = URI.create("http://localhost:" + PORT);

    private static final String CONTENT_TYPE_TEXT_XML = "text/xml";

    private String path;

    private String expResultAuth;

    private String expResult;

    @Test
    public void testFetch() throws WFirmaException {
        stubMock();
        SimpleCredentials creds = new SimpleCredentials("login@example.org", "a-user-password");
        SimpleGateway instance = new SimpleGateway(creds, mockAddress);
        
        String result = instance.get(RequestPath.fromString(path));
        assertNotNull(result);
        assertEquals(expResult, result);

        creds = new SimpleCredentials("login2@example.org", "invalid-password");
        instance = new SimpleGateway(creds, mockAddress);

        try {
            instance.get(RequestPath.fromString(path));
            fail("Expected to throw a WFirmaSercurityException for invalid auth");
        } catch (WFirmaSercurityException ex) {
            assertEquals("Auth failed for user: `login2@example.org`", ex.getLocalizedMessage());
        }
    }

    @Test
    public void testFetchWithAuthFail() throws WFirmaException {
        stubMock();
        SimpleCredentials creds = new SimpleCredentials("login@example.org", "a-user-password");
        SimpleGateway instance = new SimpleGateway(creds, mockAddress);

        String result = instance.get(RequestPath.fromString(path));
        assertNotNull(result);
        assertEquals(expResult, result);

        creds = new SimpleCredentials("login2@example.org", "invalid-password");
        instance = new SimpleGateway(creds, mockAddress);

        try {
            instance.get(RequestPath.fromString(path));
            fail("Expected to throw a WFirmaSercurityException for invalid auth");
        } catch (WFirmaSercurityException ex) {
            assertEquals("Auth failed for user: `login2@example.org`", ex.getLocalizedMessage());
        }
    }

    private void stubMock() {
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
                + " \n";
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
    }

}
