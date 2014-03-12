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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static pl.wavesoftware.util.RegexMatcher.matches;
import pl.wavesoftware.wfirma.api.SimpleCredentials;
import pl.wavesoftware.wfirma.api.model.WFirmaException;
import pl.wavesoftware.wfirma.api.model.WFirmaSercurityException;

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
        expResultAuth = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<api>\n"
                + "    <status>\n"
                + "        <code>AUTH</code>\n"
                + "    </status>\n"
                + "</api>\n"
                + " \n";
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

        try {
            instance.get(RequestPath.fromString(path));
            fail("Expected to throw a WFirmaSercurityException for invalid auth");
        } catch (WFirmaSercurityException ex) {
            assertEquals("Auth failed for user: `non-existing-login-2@example.org`", ex.getLocalizedMessage());
        }
        prefs.putLong(key, current);
        prefs.flush();
    }

}
