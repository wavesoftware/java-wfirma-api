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

package pl.wavesoftware.wfirma.api.model;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class WFirmaSecurityException extends WFirmaException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param format a format of exception
     * @param params a objects to be embedded into exception
     */
    public WFirmaSecurityException(String format, Object... params) {
        super(format, params);
    }

    /**
     * Constructor
     *
     * @param cause a cause of this exception
     * @param format a format of exception
     * @param params a objects to be embedded into exception
     */
    public WFirmaSecurityException(Throwable cause, String format, Object... params) {
        super(cause, format, params);
    }

    /**
     * Constructor
     *
     * @param cause a cause of this exception
     */
    public WFirmaSecurityException(Throwable cause) {
        super(cause);
    }

}
