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

package pl.wavesoftware.wfirma.api.core.model.requests;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import pl.wavesoftware.wfirma.api.core.model.contractors.Contractor;
import pl.wavesoftware.wfirma.api.core.model.contractors.Contractors;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class EditRequestTest {

    @Test
    public void testGetEntity() {
        Contractors contractors = new Contractors();
        contractors.getContractor().add(new Contractor());
        EditRequest<Contractors> instance = EditRequest.create(contractors, 45L);
        Object result = instance.getEntity();
        assertThat(result).isEqualTo(contractors);
    }

    @Test
    public void testGetBody() {
        Contractor contractor = new Contractor();
        contractor.setName("It will be 456L");
        Contractors contractors = new Contractors();
        contractors.getContractor().add(contractor);
        EditRequest<Contractors> instance = EditRequest.create(contractors, 456L);
        String result = instance.getBody();
        assertThat(result).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<api>\n"
                + "    <contractors>\n"
                + "        <contractor>\n"
                + "            <name>It will be 456L</name>\n"
                + "        </contractor>\n"
                + "    </contractors>\n"
                + "</api>\n");
    }

    @Test
    public void testGetAddress() {
        Contractor contractor = new Contractor();
        contractor.setAccess("tak");
        Contractors contractors = new Contractors();
        contractors.getContractor().add(contractor);
        EditRequest<Contractors> instance = EditRequest.create(contractors, 17L);
        String result = instance.getAddress().getCorrectedPath();
        assertThat(result).isEqualTo("/contractors/edit/17");
    }

}
