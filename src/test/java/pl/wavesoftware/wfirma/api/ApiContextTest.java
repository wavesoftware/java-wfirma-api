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
package pl.wavesoftware.wfirma.api;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import pl.wavesoftware.wfirma.api.model.Credentials;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class ApiContextTest {

    @Test
    public void testCreate() {
        ApiContext context = new ApiContext(new OAuthCredentials("key", "secret"));
        assertThat(context).isNotNull();
        context = new ApiContext(new SimpleCredentials("login", "password"));
        assertThat(context).isNotNull();
    }

    @Test
    public void testGetCredentials() {
        SimpleCredentials input = new SimpleCredentials("login2", "password2");
        ApiContext instance = new ApiContext(input);
        Credentials result = instance.getCredentials();
        assertThat(result).isSameAs(input);
    }

}
