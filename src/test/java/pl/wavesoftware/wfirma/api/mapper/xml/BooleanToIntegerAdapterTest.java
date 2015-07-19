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

package pl.wavesoftware.wfirma.api.mapper.xml;

import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class BooleanToIntegerAdapterTest {

    @Test
    public void testUnmarshal() throws Exception {
        BooleanToIntegerAdapter instance = new BooleanToIntegerAdapter();
        Boolean result = instance.unmarshal(1);
        assertThat(result).isTrue();
        result = instance.unmarshal(0);
        assertThat(result).isFalse();
        Random rand = new Random();
        for (int i = 1; i <= 10; i++) {
            int test = rand.nextInt(1000) + 1;
            result = instance.unmarshal(test);
            assertThat(result).isTrue();
        }
    }

    @Test
    public void testMarshal() throws Exception {
        BooleanToIntegerAdapter instance = new BooleanToIntegerAdapter();
        Integer result = instance.marshal(Boolean.TRUE);
        assertThat(result).isEqualTo(1);
        result = instance.marshal(Boolean.FALSE);
        assertThat(result).isEqualTo(0);
    }

}
