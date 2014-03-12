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

package pl.wavesoftware.util;

import java.util.regex.Pattern;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class RegexMatcherTest {

	@Test
	public void testMatchesRegular() {
		RegexMatcher matcher = RegexMatcher.matches("[a-c]{2}fg[0-9]+");
		assertTrue(matcher.matches((Object) "bcfg111"));
		assertTrue(matcher.matches((Object) "ccfg23411"));
		assertTrue(matcher.matches((Object) "aafg0"));
		assertFalse(matcher.matches((Object) "affg0"));
		assertFalse(matcher.matches((Object) "AAfg0"));
	}

	@Test
	public void testMatchesWithFlags() {
		RegexMatcher matcher = RegexMatcher.matches("[a-c]{2}fg[0-9]+", Pattern.CASE_INSENSITIVE);
		assertTrue(matcher.matches((Object) "bcfg111"));
		assertTrue(matcher.matches((Object) "ccfg23411"));
		assertTrue(matcher.matches((Object) "aAfG0"));
		assertFalse(matcher.matches((Object) "AFFG0"));
	}

	@Test
	public void testMatchesPattern() {
		RegexMatcher matcher = RegexMatcher.matches(Pattern.compile("[a-c]{2}fg[0-9]+"));
		assertTrue(matcher.matches((Object) "bcfg111"));
		assertTrue(matcher.matches((Object) "ccfg23411"));
		assertTrue(matcher.matches((Object) "aafg0"));
		assertFalse(matcher.matches((Object) "affg0"));
		assertFalse(matcher.matches((Object) "AAfg0"));
	}
}
