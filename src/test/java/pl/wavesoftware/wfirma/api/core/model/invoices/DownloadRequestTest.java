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
package pl.wavesoftware.wfirma.api.core.model.invoices;

import mockit.Deencapsulation;
import org.junit.Test;
import pl.wavesoftware.wfirma.api.core.model.logic.Parameter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
