/*
 * Copyright (c) 2015 Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
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

package pl.wavesoftware.wfirma.api.oauth.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 * @since 2015-11-22
 */
public class OAuthCredentialsTest {

    @Test
    public void testGetConsumerKey() throws Exception {
        // given
        OAuthCredentials credentials = new OAuthCredentials("key1", "secret1");

        // when
        String key = credentials.getConsumerKey();

        // then
        assertThat(key).isEqualTo("key1");
    }

    @Test
    public void testGetConsumerSecret() throws Exception {
        // given
        OAuthCredentials credentials = new OAuthCredentials("key2", "secret2");

        // when
        String secret = credentials.getConsumerSecret();

        // then
        assertThat(secret).isEqualTo("secret2");
    }
}