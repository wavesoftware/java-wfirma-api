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
package pl.wavesoftware.wfirma.api.mapper.xml;

import pl.wavesoftware.wfirma.api.mapper.xml.UsesXmlCustomFormatter.Param;

/**
 * Custom XML formatter
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public interface XmlCustomFormatter {

    /**
     * Configures a formatter
     *
     * @param configuration a configuration
     */
    void configure(Param[] configuration);

    /**
     * Adds a custom formatting to XML source
     *
     * @param input a input unformatted XML source
     * @return a formatted XML output
     */
    String format(String input);

    /**
     * Removes a custom formatting from XML source
     *
     * @param input a input formatted XML source
     * @return an unformatted XML output
     */
    String unformat(String input);
}
