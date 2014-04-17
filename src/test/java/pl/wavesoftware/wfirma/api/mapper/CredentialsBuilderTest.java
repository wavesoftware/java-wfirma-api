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
