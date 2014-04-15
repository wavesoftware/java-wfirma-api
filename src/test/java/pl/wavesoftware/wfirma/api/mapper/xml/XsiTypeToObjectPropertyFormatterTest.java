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
package pl.wavesoftware.wfirma.api.mapper.xml;

import java.lang.annotation.Annotation;
import java.util.Map;
import mockit.Deencapsulation;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.data.MapEntry;
import org.junit.Test;
import pl.wavesoftware.wfirma.api.mapper.xml.UsesXmlCustomFormatter.Param;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class XsiTypeToObjectPropertyFormatterTest {

    private static final String TYPE = "type";

    private final String unFormatted = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
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

    private final String quasiFormatted = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
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

    private final String formatted = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
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

    private final String formattedCdata = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
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
        String result = instance.format(unFormatted);
        assertThat(result).isEqualTo(formatted);
    }

    @Test
    public void testFormatUnconfigured() {
        try {
            XsiTypeToObjectPropertyFormatter instance = new XsiTypeToObjectPropertyFormatter();
            instance.format(unFormatted);
            Assertions.failBecauseExceptionWasNotThrown(IllegalStateException.class);
        } catch (IllegalStateException ex) {
            assertThat(ex).hasNoCause();
            assertThat(ex).hasMessage("Value for `field` param is required, but not set!");
        }
    }

    @Test
    public void testUnFormatCdata() {
        XsiTypeToObjectPropertyFormatter instance = new XsiTypeToObjectPropertyFormatter();
        Param[] configuration = new Param[]{
            newParam(XsiTypeToObjectPropertyFormatter.FIELD, TYPE)
        };
        instance.configure(configuration);
        String result = instance.unformat(formattedCdata);
        assertThat(result).isEqualTo(unFormatted);
    }

    @Test
    public void testFormatQuasi() {
        XsiTypeToObjectPropertyFormatter instance = new XsiTypeToObjectPropertyFormatter();
        Param[] configuration = new Param[]{
            newParam(XsiTypeToObjectPropertyFormatter.FIELD, TYPE)
        };
        instance.configure(configuration);
        String result = instance.format(quasiFormatted);
        assertThat(result).isEqualTo(formatted);
    }

    @Test
    public void testUnformat() {
        XsiTypeToObjectPropertyFormatter instance = new XsiTypeToObjectPropertyFormatter();
        Param[] configuration = new Param[]{
            newParam(XsiTypeToObjectPropertyFormatter.FIELD, TYPE)
        };
        instance.configure(configuration);
        String result = instance.unformat(formatted);
        assertThat(result).isEqualTo(unFormatted);
    }

    @Test
    public void testFormatUnformat() {
        XsiTypeToObjectPropertyFormatter instance = new XsiTypeToObjectPropertyFormatter();
        Param[] configuration = new Param[]{
            newParam(XsiTypeToObjectPropertyFormatter.FIELD, TYPE)
        };
        instance.configure(configuration);
        String result = instance.format(instance.unformat(formatted));
        assertThat(result).isEqualTo(formatted);
        result = instance.unformat(instance.format(unFormatted));
        assertThat(result).isEqualTo(unFormatted);
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

}
