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

    private static final String INVALID_INPUT_PHP = "<html><h1>PHP Error: Internal Server Error</h1></html>";

    private final Throwable thowable;

    private final String input;

    public ResponseCheckerTest(String label, String input, Throwable thowable) {
        this.input = input;
        this.thowable = thowable;
    }

    private static String in(String code) {
        return in(code, null);
    }

    private static String in(String code, String couse) {
        String template = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<api>\n"
                + "    <status>\n"
                + "        <code>%s</code>\n"
                + "    </status>\n%s"
                + "</api>";
        String couseIn = couse == null ? ""
                : String.format(
                        "    <errors>\n"
                        + "        <error>\n"
                        + "            <message>%s</message>\n"
                        + "        </error>\n"
                        + "    </errors>\n", couse);

        return String.format(template, code, couseIn);
    }

    @Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
            {"OK", in("OK"), null},
            {"AUTH", in("AUTH"), new WFirmaSercurityException("Auth failed for user: `test-user`")},
            {"ACTION NOT FOUND", in("ACTION NOT FOUND"), new WFirmaException("ACTION NOT FOUND")},
            {"NOT FOUND", in("NOT FOUND"), new WFirmaException("NOT FOUND")},
            {"FATAL", in("FATAL"), new WFirmaException("FATAL")},
            {"INPUT ERROR", in("INPUT ERROR"), new WFirmaException("INPUT ERROR")},
            {"ERROR", in("ERROR"), new WFirmaException("ERROR: no error tags?!")},
            {"ERROR WITH CAUSE", in("ERROR", "a couse"), new WFirmaException("ERROR: [a couse]")},
            {"OUT OF SERVICE", in("OUT OF SERVICE"), new WFirmaException("OUT OF SERVICE")},
            {"DENIED SCOPE REQUESTED", in("DENIED SCOPE REQUESTED"), new WFirmaException("DENIED SCOPE REQUESTED")},
            {"UNKNOWN ERROR", in("UNKNOWN ERROR"), new WFirmaException("Unknown status code: UNKNOWN ERROR")},
            {"Invalid input PHP", INVALID_INPUT_PHP, new IllegalStateException("Invalid WFirma output: "
                + "<html><h1>PHP Error: Internal Server Error</h1></html>")},
            {"Invalid input - xml error", "<api>\n←", new IllegalStateException("Invalid WFirma output: "
                + "XML document structures must start and end within the same entity.")}
        };
        return Arrays.asList(data);
    }

    /**
     * Test of checkedForStatus method, of class ResponseChecker.
     */
    @Test
    public void testCheckedForStatus() throws WFirmaException {
        String login = "test-user";
        ResponseChecker instance = new ResponseChecker();
        if (thowable == null) {
            String result = instance.checkedForStatus(login, input);
            assertThat(result).isEqualTo(input);
        } else {
            try {
                instance.checkedForStatus(login, input);
                fail("Expected exception, but didn't thrown! => " + thowable);
            } catch (WFirmaException | IllegalStateException | UnsupportedOperationException ex) {
                assertThat(ex.getClass()).isEqualTo(thowable.getClass());
                assertThat(ex.getLocalizedMessage()).isEqualTo(thowable.getLocalizedMessage());
            }
        }
    }

}
