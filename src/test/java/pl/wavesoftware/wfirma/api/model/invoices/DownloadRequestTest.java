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
package pl.wavesoftware.wfirma.api.model.invoices;

import java.util.List;
import mockit.Deencapsulation;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import pl.wavesoftware.wfirma.api.model.logic.Parameter;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class DownloadRequestTest {

    @Test
    public void testCreate_Long() {
        Long wFirmaId = 78L;
        DownloadRequest result = DownloadRequest.create(wFirmaId);
        assertThat(result).isNotNull();
        assertThat(Deencapsulation.getField(result, "wFirmaId")).isEqualTo(wFirmaId);
        List<Parameter> list = result.getEntity().getParameters().getParameter();
        assertThat(list).hasSize(4);
        assertThat(list).extracting("name").containsExactly("page", "address", "leaflet", "duplicate");
        assertThat(list).extracting("value").containsExactly("invoice", "0", "0", "0");
    }

    @Test
    public void testCreate_5args() {
        Long wFirmaId = 113L;
        DownloadRequest.InvoicePage page = DownloadRequest.InvoicePage.copy;
        boolean printAddress = true;
        boolean printLeaflet = false;
        boolean printAsDuplicate = true;
        DownloadRequest result = DownloadRequest.create(wFirmaId, page, printAddress, printLeaflet, printAsDuplicate);
        assertThat(result).isNotNull();
        assertThat(Deencapsulation.getField(result, "wFirmaId")).isEqualTo(wFirmaId);
        List<Parameter> list = result.getEntity().getParameters().getParameter();
        assertThat(list).hasSize(4);
        assertThat(list).extracting("name").containsExactly("page", "address", "leaflet", "duplicate");
        assertThat(list).extracting("value").containsExactly("copy", "1", "0", "1");
    }

    @Test
    public void testGetEntity() {
        DownloadRequest instance = DownloadRequest.create(45L);
        Invoices result = instance.getEntity();
        assertThat(result).isNotNull();
        List<Parameter> list = result.getParameters().getParameter();
        assertThat(list).hasSize(4);
        assertThat(list).extracting("name").containsExactly("page", "address", "leaflet", "duplicate");
        assertThat(list).extracting("value").containsExactly("invoice", "0", "0", "0");
    }

    @Test
    public void testGetBody() {
        DownloadRequest instance = DownloadRequest.create(67L);
        String expResult = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<api>\n"
                + "    <invoices>\n"
                + "        <parameters>\n"
                + "            <parameter>\n"
                + "                <name>page</name>\n"
                + "                <value>invoice</value>\n"
                + "            </parameter>\n"
                + "            <parameter>\n"
                + "                <name>address</name>\n"
                + "                <value>0</value>\n"
                + "            </parameter>\n"
                + "            <parameter>\n"
                + "                <name>leaflet</name>\n"
                + "                <value>0</value>\n"
                + "            </parameter>\n"
                + "            <parameter>\n"
                + "                <name>duplicate</name>\n"
                + "                <value>0</value>\n"
                + "            </parameter>\n"
                + "            <page>0</page>\n"
                + "            <limit>20</limit>\n"
                + "        </parameters>\n"
                + "    </invoices>\n"
                + "</api>\n";
        String result = instance.getBody();
        assertThat(result).isEqualTo(expResult);
    }

    @Test
    public void testGetAddress() {
        DownloadRequest instance = DownloadRequest.create(89L);
        String result = instance.getAddress().getCorrectedPath();
        assertThat(result).isEqualTo("/invoices/download/89");
    }

    @Test
    public void testGetEntityClass() {
        DownloadRequest instance = DownloadRequest.create(99L);
        Class<Invoices> result = instance.getEntityClass();
        assertThat(result).isEqualTo(Invoices.class);
    }

}
