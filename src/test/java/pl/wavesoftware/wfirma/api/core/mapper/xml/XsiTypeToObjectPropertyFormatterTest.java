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
package pl.wavesoftware.wfirma.api.core.mapper.xml;

import mockit.Deencapsulation;
import org.assertj.core.data.MapEntry;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.internal.stubbing.answers.ThrowsExceptionClass;
import org.w3c.dom.Document;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;
import pl.wavesoftware.wfirma.api.core.mapper.xml.UsesXmlCustomFormatter.Param;

import java.io.IOException;
import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class XsiTypeToObjectPropertyFormatterTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static final String TYPE = "type";

    private static final String UN_FORMATTED = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
        + "<api>\n"
        + "    <invoices>\n"
        + "        <invoice xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"normal\">\n"
        + "            <id>13</id>\n"
        + "            <paymentmethod>payment_card</paymentmethod>\n"
        + "        </invoice>\n"
        + "        <invoice xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"proforma\">\n"
        + "            <id>13</id>\n"
        + "            <paymentmethod>cash</paymentmethod>\n"
        + "        </invoice>\n"
        + "    </invoices>\n"
        + "</api>\n";

    private static final String QUASI_FORMATTED = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
        + "<api>\n"
        + "    <invoices>\n"
        + "        <invoice xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"normal\">\n"
        + "            <id>13</id>\n"
        + "            <paymentmethod>payment_card</paymentmethod>\n"
        + "            <type>normal</type>\n"
        + "        </invoice>\n"
        + "        <invoice xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"proforma\">\n"
        + "            <id>13</id>\n"
        + "            <paymentmethod>cash</paymentmethod>\n"
        + "        </invoice>\n"
        + "    </invoices>\n"
        + "</api>\n";

    private static final String FORMATTED = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
        + "<api>\n"
        + "    <invoices>\n"
        + "        <invoice>\n"
        + "            <id>13</id>\n"
        + "            <paymentmethod>payment_card</paymentmethod>\n"
        + "            <type>normal</type>\n"
        + "        </invoice>\n"
        + "        <invoice>\n"
        + "            <id>13</id>\n"
        + "            <paymentmethod>cash</paymentmethod>\n"
        + "            <type>proforma</type>\n"
        + "        </invoice>\n"
        + "    </invoices>\n"
        + "</api>\n";

    private static final String FORMATTED_CDATA = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
        + "<api>\n"
        + "    <invoices>\n"
        + "        <invoice>\n"
        + "            <id>13</id>\n"
        + "            <paymentmethod>payment_card</paymentmethod>\n"
        + "            <type><![CDATA[normal]]></type>\n"
        + "        </invoice>\n"
        + "        <invoice>\n"
        + "            <id>13</id>\n"
        + "            <paymentmethod>cash</paymentmethod>\n"
        + "            <type>proforma</type>\n"
        + "        </invoice>\n"
        + "    </invoices>\n"
        + "</api>\n";

    @Test
    public void testFormat() {
        XsiTypeToObjectPropertyFormatter instance = new XsiTypeToObjectPropertyFormatter();
        Param[] configuration = new Param[]{
            newParam(XsiTypeToObjectPropertyFormatter.FIELD, TYPE)
        };
        instance.configure(configuration);
        String result = instance.format(UN_FORMATTED);
        assertThat(result).isEqualTo(FORMATTED);
    }

    @Test
    public void testFormatUnconfigured() {
        // given
        XsiTypeToObjectPropertyFormatter instance = new XsiTypeToObjectPropertyFormatter();
        // then
        thrown.expect(EidIllegalStateException.class);
        thrown.expectMessage(containsString("20151122:172000"));
        thrown.expectMessage(containsString("Value for `field` param is required, but not set!"));
        // when
        instance.format(UN_FORMATTED);
    }

    @Test
    public void testUnFormatCdata() {
        XsiTypeToObjectPropertyFormatter instance = new XsiTypeToObjectPropertyFormatter();
        Param[] configuration = new Param[]{
            newParam(XsiTypeToObjectPropertyFormatter.FIELD, TYPE)
        };
        instance.configure(configuration);
        String result = instance.unformat(FORMATTED_CDATA);
        assertThat(result).isEqualTo(UN_FORMATTED);
    }

    @Test
    public void testFormatQuasi() {
        XsiTypeToObjectPropertyFormatter instance = new XsiTypeToObjectPropertyFormatter();
        Param[] configuration = new Param[]{
            newParam(XsiTypeToObjectPropertyFormatter.FIELD, TYPE)
        };
        instance.configure(configuration);
        String result = instance.format(QUASI_FORMATTED);
        assertThat(result).isEqualTo(FORMATTED);
    }

    @Test
    public void testUnformat() {
        XsiTypeToObjectPropertyFormatter instance = new XsiTypeToObjectPropertyFormatter();
        Param[] configuration = new Param[]{
            newParam(XsiTypeToObjectPropertyFormatter.FIELD, TYPE)
        };
        instance.configure(configuration);
        String result = instance.unformat(FORMATTED);
        assertThat(result).isEqualTo(UN_FORMATTED);
    }

    @Test
    public void testFormatUnformat() {
        XsiTypeToObjectPropertyFormatter instance = new XsiTypeToObjectPropertyFormatter();
        Param[] configuration = new Param[]{
            newParam(XsiTypeToObjectPropertyFormatter.FIELD, TYPE)
        };
        instance.configure(configuration);
        String result = instance.format(instance.unformat(FORMATTED));
        assertThat(result).isEqualTo(FORMATTED);
        result = instance.unformat(instance.format(UN_FORMATTED));
        assertThat(result).isEqualTo(UN_FORMATTED);
    }

    @Test
    public void testConfigure() {
        Param[] configuration = new Param[]{
            newParam(XsiTypeToObjectPropertyFormatter.FIELD, TYPE)
        };
        XsiTypeToObjectPropertyFormatter instance = new XsiTypeToObjectPropertyFormatter();
        instance.configure(configuration);
        assertThat(instance).isNotNull();
        Map<String, String> map = Deencapsulation.getField(instance, "configuration");
        assertThat(map).containsOnly(
                MapEntry.entry(XsiTypeToObjectPropertyFormatter.FIELD, TYPE)
        );
    }

    private Param newParam(final String key, final String value) {
        return new Param() {

            @Override
            public String key() {
                return key;
            }

            @Override
            public String value() {
                return value;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Param.class;
            }
        };
    }

    @Test
    public void testGetDoc() throws IOException {
        // when
        XsiTypeToObjectPropertyFormatter instance = new XsiTypeToObjectPropertyFormatter();
        String sample = "sample";
        StringReader reader = new StringReader(sample);
        assertThat(reader.read()).inHexadecimal().isEqualTo(0x73);
        reader.close();

        // then
        thrown.expect(EidIllegalStateException.class);
        thrown.expectMessage(containsString("20150716:113147"));
        thrown.expectMessage(containsString("Stream closed"));
        thrown.expectCause(any(IOException.class));

        // when
        instance.getDoc(reader);
    }

    @Test
    public void testDumpXml_Invalid() {
        // given
        XsiTypeToObjectPropertyFormatter instance = new XsiTypeToObjectPropertyFormatter();
        Document doc = mock(Document.class);
        when(doc.getNodeType()).thenAnswer(new ThrowsExceptionClass(InstantiationException.class));
        // then
        thrown.expect(EidIllegalStateException.class);
        thrown.expectMessage(containsString("20150716:113135"));
        thrown.expectCause(isA(InstantiationException.class));
        // when
        instance.dumpXml(doc);
    }

}
