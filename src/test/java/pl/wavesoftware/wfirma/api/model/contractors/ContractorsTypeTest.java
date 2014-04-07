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

package pl.wavesoftware.wfirma.api.model.contractors;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import pl.wavesoftware.wfirma.api.mapper.Api;
import pl.wavesoftware.wfirma.api.model.ApiEntityElement;
import pl.wavesoftware.wfirma.api.model.logic.Parameters;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class ContractorsTypeTest {

    private final Api api;

    public ContractorsTypeTest() {
        api = new Api() {

            @Override
            public Class<? extends ApiEntityElement> getEntityClass() {
                return Contractors.class;
            }
        };
    }

    @Test
    public void testGetContractor() {
        Contractors instance = new Contractors(api);
        List<Contractor> result = instance.getContractor();
        assertThat(result).isEmpty();
    }

    @Test
    public void testGetParameters() {
        Contractors instance = new Contractors(api);
        Parameters expResult = null;
        Parameters result = instance.getParameters();
        assertThat(result).isEqualTo(expResult);
    }

    @Test
    public void testSetParameters() {
        Parameters value = new Parameters();
        Contractors instance = new Contractors(api);
        instance.setParameters(value);
        assertThat(instance.getParameters()).isEqualTo(value);
    }

    @Test
    public void testGetApi() {
        Contractors instance = new Contractors(api);
        assertThat(instance.getApi()).isEqualTo(api);
    }

}
