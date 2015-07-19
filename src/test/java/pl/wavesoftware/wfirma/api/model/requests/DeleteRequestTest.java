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
import pl.wavesoftware.wfirma.api.model.companies.Companies;
import pl.wavesoftware.wfirma.api.model.contractors.Contractors;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class DeleteRequestTest {

    @Test
    public void testGetAddress() {
        DeleteRequest<Companies> instance = DeleteRequest.create(Companies.class, 5L);
        String result = instance.getAddress().getCorrectedPath();
        assertThat(result).isEqualTo("/companies/delete/5");
    }

    @Test
    public void testCreate() {
        DeleteRequest<Contractors> result = DeleteRequest.create(Contractors.class, 56L);
        assertThat(result).isNotNull();
    }

    @Test
    public void testGetEntityClass() {
        DeleteRequest<Contractors> instance = DeleteRequest.create(Contractors.class, 56L);
        assertThat(instance).isNotNull();
        Class<Contractors> result = instance.getEntityClass();
        assertThat(result).isEqualTo(Contractors.class);
    }

}
