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
package pl.wavesoftware.wfirma.api.runtime;

/**
 * This exception is thrown if there happened something unpredicted, it's clearly a bug. Report back it to issue tracker, please.
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class FatalSdkException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     *
     * @param errorId an application unhandled error, a bug
     * @param cause what causes this bug
     */
    public FatalSdkException(String errorId, Throwable cause) {
        super(makeMessage(errorId, cause), cause);
    }

    /**
     * Default constructor
     *
     * @param errorId an application unhandled error, a bug
     * @param message what causes this bug
     */
    public FatalSdkException(String errorId, String message) {
        super(makeMessage(errorId, message));
    }

    private static String makeMessage(String errorId, Throwable cause) {
        String message = cause.toString();
        return makeMessage(errorId, message);
    }

    private static String makeMessage(String errorId, String message) {
        return String.format("[%s]: %s", errorId, message);
    }

}
