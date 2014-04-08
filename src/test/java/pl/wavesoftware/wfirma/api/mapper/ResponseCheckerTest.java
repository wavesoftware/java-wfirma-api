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

package pl.wavesoftware.wfirma.api.mapper;

import java.util.Arrays;
import java.util.Collection;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import pl.wavesoftware.wfirma.api.model.WFirmaException;
import pl.wavesoftware.wfirma.api.model.WFirmaSercurityException;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@gmail.com>
 */
@RunWith(Parameterized.class)
public class ResponseCheckerTest {

    private final String code;

    private final Throwable thowable;

    private final String couse;

    public ResponseCheckerTest(String label, String code, Throwable thowable, String couse) {
        this.code = code;
        this.thowable = thowable;
        this.couse = couse;
    }

    @Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
            {"OK", "OK", null, null},
            {"AUTH", "AUTH", new WFirmaSercurityException("Auth failed for user: `test-user`"), null},
            {"ACTION NOT FOUND", "ACTION NOT FOUND", new WFirmaException("ACTION NOT FOUND"), null},
            {"NOT FOUND", "NOT FOUND", new WFirmaException("NOT FOUND"), null},
            {"FATAL", "FATAL", new WFirmaException("FATAL"), null},
            {"INPUT ERROR", "INPUT ERROR", new WFirmaException("INPUT ERROR"), null},
            {"ERROR", "ERROR", new WFirmaException("ERROR: no error tags?!"),
                null},
            {"ERROR WITH CAUSE", "ERROR", new WFirmaException("ERROR: [a couse]"), "a couse"},
            {"OUT OF SERVICE", "OUT OF SERVICE", new WFirmaException("OUT OF SERVICE"), null},
            {"DENIED SCOPE REQUESTED", "DENIED SCOPE REQUESTED", new WFirmaException("DENIED SCOPE REQUESTED"), null},
            {"UNKNOWN ERROR", "UNKNOWN ERROR", new WFirmaException("Unknown status code: UNKNOWN ERROR"), null}};
        return Arrays.asList(data);
    }

    /**
     * Test of checkedForStatus method, of class ResponseChecker.
     */
    @Test
    public void testCheckedForStatus() throws WFirmaException {
        String login = "test-user";
        String template = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<api>\n"
                + "    <status>\n"
                + "        <code>%s</code>\n"
                + "    </status>\n%s"
                + "</api>";
        String cause = couse == null ? ""
                : String.format(
                        "    <errors>\n"
                        + "        <error>\n"
                        + "            <message>%s</message>\n"
                        + "        </error>\n"
                        + "    </errors>\n", couse);
        String content = String.format(template, code, cause);
        ResponseChecker instance = new ResponseChecker();
        if (thowable == null) {
            String result = instance.checkedForStatus(login, content);
            assertThat(result).isEqualTo(content);
        } else {
            try {
                instance.checkedForStatus(login, content);
                fail("Expected exception, but didn't thrown! => " + thowable);
            } catch (WFirmaException wfe) {
                assertThat(wfe.getClass()).isEqualTo(thowable.getClass());
                assertThat(wfe.getLocalizedMessage()).isEqualTo(thowable.getLocalizedMessage());
            }
        }
    }

}
