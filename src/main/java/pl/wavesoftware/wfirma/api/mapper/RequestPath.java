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

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class RequestPath {

    private final String path;

    /**
     * A constructor
     * @param path a input path
     */
    public RequestPath(String path) {
        this.path = path;
    }

    /**
     * Gets a corrected path for request
     * @return a corrected path
     */
    public String getCorrectedPath() {
        String corrected;
        if ("/".equals(this.path.substring(0, 1))) {
            corrected = this.path;
        } else {
            corrected = "/" + this.path;
        }
        return corrected;
    }

    /**
     * Builder form list of strings as parts of the address path
     * @param path a list of string as parts
     * @return a request path
     */
    public static RequestPath fromString(String... path) {
        StringBuilder builder = new StringBuilder();
        for (String onePath : path) {
            builder.append("/");
            builder.append(onePath);
        }
        return new RequestPath(builder.toString());
    }
}
