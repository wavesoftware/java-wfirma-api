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

package pl.wavesoftware.wfirma.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import pl.wavesoftware.wfirma.api.OAuthCredentials;
import pl.wavesoftware.wfirma.api.SimpleCredentials;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class CredentialsBuilderTest {

    public CredentialsBuilderTest() {
    }

    @Test
    public void testGetLogin() {
        CredentialsBuilder instance = CredentialsBuilder.from(new SimpleCredentials("a", "b"));
        String result = instance.getKey();
        assertThat(result).isEqualTo("a");
    }

    @Test
    public void testGetPassword() {
        CredentialsBuilder instance = CredentialsBuilder.from(new OAuthCredentials("a", "b"));
        String result = instance.getSecret();
        assertThat(result).isEqualTo("b");
    }

}
