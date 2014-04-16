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
