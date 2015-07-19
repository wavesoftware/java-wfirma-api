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
package pl.wavesoftware.wfirma.api.mapper.xml.invalidjaxbformatter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import pl.wavesoftware.wfirma.api.mapper.xml.UsesXmlCustomFormatter;
import pl.wavesoftware.wfirma.api.mapper.xml.XmlCustomFormatter;
import pl.wavesoftware.wfirma.api.mapper.xml.invalidjaxbformatter.JaxbEntityWithInvalidFormatter.InvalidJaxbFormatter;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@UsesXmlCustomFormatter(value = InvalidJaxbFormatter.class, parameters = {
    @UsesXmlCustomFormatter.Param(key = "field", value = "type")
})
public class JaxbEntityWithInvalidFormatter {

    @XmlElement
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class InvalidJaxbFormatter implements XmlCustomFormatter {

        private InvalidJaxbFormatter() {
        }

        @Override
        public void configure(UsesXmlCustomFormatter.Param[] configuration) {
            // empty, it should be
        }

        @Override
        public String format(String input) {
            throw new UnsupportedOperationException("Not supported, on purpose!");
        }

        @Override
        public String unformat(String input) {
            throw new UnsupportedOperationException("Not supported, on purpose!");
        }

    }

}
