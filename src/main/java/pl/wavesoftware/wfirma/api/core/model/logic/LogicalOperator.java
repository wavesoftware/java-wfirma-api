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
package pl.wavesoftware.wfirma.api.core.model.logic;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p/>
 * Java class for logicalOperator.
 * <p/>
 * <p/>
 * The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="logicalOperator">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="eq"/>
 *     &lt;enumeration value="ne"/>
 *     &lt;enumeration value="lt"/>
 *     &lt;enumeration value="le"/>
 *     &lt;enumeration value="gt"/>
 *     &lt;enumeration value="ge"/>
 *     &lt;enumeration value="like"/>
 *     &lt;enumeration value="not like"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
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

    /**
     * Gets a value of enum
     *
     * @return a value of enum
     */
    public String value() {
        return value;
    }

    /**
     * Creates a enum from string input as XML string
     * <p/>
     * <p/>
     * Example
     * <p/>
     * <pre>
     * LogicalOperator.fromValue("not like");
     * </pre>
     *
     * @param value input as XML string
     * @return an created enum
     */
    public static LogicalOperator fromValue(String value) {
        for (LogicalOperator c : LogicalOperator.values()) {
            if (c.value().equals(value)) {
                return c;
            }
        }
        String msg = String.format("There is no enum value for `%s` string representation", value);
        throw new IllegalArgumentException(msg);
    }

}
