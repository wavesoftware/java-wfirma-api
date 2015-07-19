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

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class RequestPath {

    private final String path;

    /**
     * A constructor
     *
     * @param path a input path
     */
    public RequestPath(String path) {
        this.path = path;
    }

    /**
     * Gets a corrected path for request
     *
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
     *
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

    @Override
    public String toString() {
        return getCorrectedPath();
    }

}
