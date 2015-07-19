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
package pl.wavesoftware.wfirma.api.model.requests;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import pl.wavesoftware.wfirma.api.model.contractors.Contractor;
import pl.wavesoftware.wfirma.api.model.contractors.Contractors;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class AddRequestTest {

    @Test
    public void testGetAddress() {
        Contractors contractors = new Contractors();
        contractors.getContractor().add(new Contractor());
        AddRequest<Contractors> instance = AddRequest.create(contractors);
        String result = instance.getAddress().getCorrectedPath();
        assertThat(result).isEqualTo("/contractors/add");
    }

    @Test
    public void testGetBody() {
        Contractors contractors = new Contractors();
        contractors.getContractor().add(new Contractor());
        AddRequest<Contractors> instance = AddRequest.create(contractors);
        String result = instance.getBody();
        assertThat(result).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<api>\n"
                + "    <contractors>\n"
                + "        <contractor/>\n"
                + "    </contractors>\n"
                + "</api>\n");
    }

    @Test
    public void testGetEntity() {
        Contractors contractors = new Contractors();
        contractors.getContractor().add(new Contractor());
        AddRequest<Contractors> instance = AddRequest.create(contractors);
        Object result = instance.getEntity();
        assertThat(result).isEqualTo(contractors);
    }

    @Test
    public void testCreate() {
        Contractors entity = new Contractors();
        AddRequest<Contractors> result = AddRequest.create(entity);
        assertThat(result).isNotNull();
    }

    @Test
    public void testGetEntityClass() {
        Contractors entity = new Contractors();
        AddRequest<Contractors> instance = AddRequest.create(entity);
        Class<Contractors> result = instance.getEntityClass();
        assertThat(result).isEqualTo(Contractors.class);
    }

}
