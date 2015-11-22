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

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.wavesoftware.eid.exceptions.EidRuntimeException;
import pl.wavesoftware.wfirma.api.core.model.GatewayFactory;
import pl.wavesoftware.wfirma.api.oauth.model.OAuthCredentials;
import pl.wavesoftware.wfirma.api.simple.model.SimpleCredentials;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class ApiContextTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreate() {
        // given
        OAuthCredentials credentials = new OAuthCredentials("key", "secret");

        // then
        thrown.expect(EidRuntimeException.class);
        thrown.expectMessage(containsString("20151007:235712"));
        thrown.expectMessage(containsString("OAuth method is not yet implemented"));
        thrown.expectCause(CoreMatchers.<Throwable>instanceOf(UnsupportedOperationException.class));

        // when
        new ApiContext(credentials);
    }

    @Test
    public void testGatewayFactory() {
        SimpleCredentials input = new SimpleCredentials("login2", "password2");
        ApiContext instance = new ApiContext(input);
        GatewayFactory result = instance.getGatewayFactory();
        assertThat(result).isNotNull();
    }

}
