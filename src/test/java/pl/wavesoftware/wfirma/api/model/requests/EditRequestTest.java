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

package pl.wavesoftware.wfirma.api.model.requests;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import pl.wavesoftware.wfirma.api.model.contractors.Contractor;
import pl.wavesoftware.wfirma.api.model.contractors.Contractors;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class EditRequestTest {

    @Test
    public void testGetEntity() {
        Contractors contractors = new Contractors();
        contractors.getContractor().add(new Contractor());
        EditRequest<Contractors> instance = EditRequest.create(contractors);
        Object result = instance.getEntity();
        assertThat(result).isEqualTo(contractors);
    }

    @Test
    public void testGetBody() {
        Contractor contractor = new Contractor();
        contractor.setId(456L);
        Contractors contractors = new Contractors();
        contractors.getContractor().add(contractor);
        EditRequest<Contractors> instance = EditRequest.create(contractors);
        String result = instance.getBody();
        assertThat(result).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<api>\n"
                + "    <contractors>\n"
                + "        <contractor>\n"
                + "            <id>456</id>\n"
                + "        </contractor>\n"
                + "    </contractors>\n"
                + "</api>\n");
    }

    @Test
    public void testGetAddress() {
        Contractor contractor = new Contractor();
        contractor.setId(17L);
        Contractors contractors = new Contractors();
        contractors.getContractor().add(contractor);
        EditRequest<Contractors> instance = EditRequest.create(contractors);
        String result = instance.getAddress().getCorrectedPath();
        assertThat(result).isEqualTo("/contractors/edit/17");
    }

    @Test(expected = IllegalStateException.class)
    public void testGetAddressWithError1() {
        Contractor contractor = new Contractor();
        Contractors contractors = new Contractors();
        contractors.getContractor().add(contractor);
        EditRequest<Contractors> instance = EditRequest.create(contractors);
        String result = instance.getAddress().getCorrectedPath();
        assertThat(result).isEqualTo("/contractors/edit/17");
    }

    @Test(expected = IllegalStateException.class)
    public void testGetAddressWithError2() {
        Contractors contractors = new Contractors();
        EditRequest<Contractors> instance = EditRequest.create(contractors);
        String result = instance.getAddress().getCorrectedPath();
        assertThat(result).isEqualTo("/contractors/edit/17");
    }

}
