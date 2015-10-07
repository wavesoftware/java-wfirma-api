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
package pl.wavesoftware.wfirma.api.core.mapper;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class RequestPathTest {

    @Test
    public void testGetCorrectedPath() {
        String input = "/ala/ma/kota";
        String expected = input;
        RequestPath instance = new RequestPath(input);
        String result = instance.getCorrectedPath();
        assertThat(result).isEqualTo(expected);

        input = "ala/ma/kota";
        instance = new RequestPath(input);
        result = instance.getCorrectedPath();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testFromString() {
        RequestPath result = RequestPath.fromString("ala", "ma", "kota");
        String empty = "";
        assertThat(result + empty).isEqualTo("/ala/ma/kota");
    }

}
