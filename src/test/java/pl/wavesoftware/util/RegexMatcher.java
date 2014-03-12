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
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class RegexMatcher extends BaseMatcher<CharSequence> {

	private final Pattern regex;

	public RegexMatcher(Pattern regex) {
		this.regex = regex;
	}

	@Override
	public void describeTo(Description description) {
        description.appendText("matches regex=" + regex);
	}

	@Override
	public boolean matches(Object o) {
		if (o == null) {
			return false;
		}
		String str = o.toString();
		return regex.matcher(str).matches();

	}

	public static RegexMatcher matches(String regex, int flags) {
		Pattern compiled = Pattern.compile(regex, flags);
		return new RegexMatcher(compiled);
	}

	public static RegexMatcher matches(String regex) {
		Pattern compiled = Pattern.compile(regex);
		return new RegexMatcher(compiled);
	}

	public static RegexMatcher matches(Pattern regex) {
		return new RegexMatcher(regex);
	}
}
