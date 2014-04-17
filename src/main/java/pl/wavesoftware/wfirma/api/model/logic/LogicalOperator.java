/*
 * The MIT License
 *
 * Copyright 2014 Krzysztof Suszyński <krzysztof.suszynski@gmail.com>.
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
package pl.wavesoftware.wfirma.api.model.logic;

import static java.lang.String.format;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for logicalOperator.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <
 * pre>
 * &lt;simpleType name="logicalOperator"> &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"> &lt;enumeration value="eq"/>
 * &lt;enumeration value="ne"/> &lt;enumeration value="lt"/> &lt;enumeration value="le"/> &lt;enumeration value="gt"/> &lt;enumeration
 * value="ge"/> &lt;enumeration value="like"/> &lt;enumeration value="not like"/> &lt;/restriction> &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "logicalOperator")
@XmlEnum
public enum LogicalOperator {

    @XmlEnumValue("eq")
    EQ("eq"),
    @XmlEnumValue("ne")
    NE("ne"),
    @XmlEnumValue("lt")
    LT("lt"),
    @XmlEnumValue("le")
    LE("le"),
    @XmlEnumValue("gt")
    GT("gt"),
    @XmlEnumValue("ge")
    GE("ge"),
    @XmlEnumValue("like")
    LIKE("like"),
    @XmlEnumValue("not like")
    NOT_LIKE("not like");

    private final String value;

    LogicalOperator(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static LogicalOperator fromValue(String value) {
        for (LogicalOperator c : LogicalOperator.values()) {
            if (c.value.equals(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException(format("There is no enum value for `%s` string representaion", value));
    }

}
