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

package pl.wavesoftware.wfirma.api.core.mapper;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import pl.wavesoftware.eid.exceptions.Eid;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;
import pl.wavesoftware.wfirma.api.core.model.WFirmaException;
import pl.wavesoftware.wfirma.api.core.model.WFirmaSecurityException;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@gmail.com>
 */
@RunWith(Parameterized.class)
public class ResponseCheckerTest {

    private static final String INVALID_INPUT_PHP = "<html><h1>PHP Error: Internal Server Error</h1></html>";
    private static Eid.UniqIdGenerator generator;

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
        generator = Eid.setUniqIdGenerator(new Eid.UniqIdGenerator() {
            @Override
            public String generateUniqId() {
                return "static";
            }
        });
        Object[][] data = new Object[][]{
            {"OK", in("OK"), null},
            {"AUTH", in("AUTH"), new WFirmaSecurityException("Auth failed for user: `test-user`")},
            {"ACTION NOT FOUND", in("ACTION NOT FOUND"), new WFirmaException("ACTION NOT FOUND")},
            {"NOT FOUND", in("NOT FOUND"), new WFirmaException("NOT FOUND")},
            {"FATAL", in("FATAL"), new WFirmaException("FATAL")},
            {"INPUT ERROR", in("INPUT ERROR"), new WFirmaException("INPUT ERROR")},
            {"ERROR", in("ERROR"), new WFirmaException("ERROR: no error tags?!")},
            {"ERROR WITH CAUSE", in("ERROR", "a couse"), new WFirmaException("ERROR: [a couse]")},
            {"OUT OF SERVICE", in("OUT OF SERVICE"), new WFirmaException("OUT OF SERVICE")},
            {"DENIED SCOPE REQUESTED", in("DENIED SCOPE REQUESTED"), new WFirmaException("DENIED SCOPE REQUESTED")},
            {"UNKNOWN ERROR", in("UNKNOWN ERROR"), new WFirmaException("Unknown status code: UNKNOWN ERROR")},
            {"Invalid input PHP", INVALID_INPUT_PHP, new EidIllegalStateException(new Eid("20150925:224454"),
                    "Unexpected WForma output: <html><h1>PHP Error: Internal Server Error</h1></html>")},
            {"Invalid input - xml error", "<api>\n←", new EidIllegalStateException(new Eid("20150925:225028"),
                    "Invalid WFirma output: XML document structures must start and end within the same entity.")}
        };
        return Arrays.asList(data);
    }

    @AfterClass
    public static void after() {
        if (generator != null) {
            Eid.setUniqIdGenerator(generator);
        }
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
            } catch (WFirmaException | EidIllegalStateException ex) {
                assertThat(ex.getClass()).isEqualTo(thowable.getClass());
                assertThat(ex.getLocalizedMessage()).isEqualTo(thowable.getLocalizedMessage());
            }
        }
    }

}
