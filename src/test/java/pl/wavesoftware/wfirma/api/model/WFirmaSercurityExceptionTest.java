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

package pl.wavesoftware.wfirma.api.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class WFirmaSercurityExceptionTest {

    @Test
    public void testCreate1() {
        WFirmaSecurityException exception = new WFirmaSecurityException("int `%d` and bool: `%s`", 5, true);
        assertThat(exception.getLocalizedMessage()).isEqualTo("int `5` and bool: `true`");
    }

    @Test
    public void testCreate2() {
        IllegalArgumentException argumentException = new IllegalArgumentException("Original");
        WFirmaSecurityException exception = new WFirmaSecurityException(argumentException);
        assertThat(exception.getLocalizedMessage()).isEqualTo("java.lang.IllegalArgumentException: Original");
    }

    @Test
    public void testCreate3() {
        IllegalArgumentException argumentException = new IllegalArgumentException("Original");
        WFirmaSecurityException exception = new WFirmaSecurityException(argumentException,
                "Embed here int `%d` and bool: `%s`", 5, true);
        assertThat(exception.getLocalizedMessage()).isEqualTo("Embed here int `5` and bool: `true`");
    }

}
