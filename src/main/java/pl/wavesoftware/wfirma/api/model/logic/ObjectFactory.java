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

import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * pl.wavesoftware.wfirma.api.model.logic package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content. The Java
 * representation of XML content can consist of schema derived interfaces and classes representing the binding of schema type definitions,
 * element declarations and model groups. Factory methods for each of these are provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PARAMETERS_QNAME = new QName("", "parameters");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
     * pl.wavesoftware.wfirma.api.model.logic
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Parameters }
     *
     * @return parameters
     */
    public Parameters createParametersType() {
        return new Parameters();
    }

    /**
     * Create an instance of {@link Order }
     *
     * @return order
     */
    public Order createOrderType() {
        return new Order();
    }

    /**
     * Create an instance of {@link And }
     *
     * @return and
     */
    public And createAndType() {
        return new And();
    }

    /**
     * Create an instance of {@link Condition }
     *
     * @return condition
     */
    public Condition createConditionType() {
        return new Condition();
    }

    /**
     * Create an instance of {@link Condition }
     *
     * @param field a field
     * @param operator a operator
     * @param value a value
     * @return condition
     */
    public Condition createConditionType(String field, LogicalOperator operator, Object value) {
        Condition cond = createConditionType();
        cond.setField(field);
        cond.setOperator(operator);
        cond.setValue(value.toString());
        return cond;
    }

    /**
     * Create an instance of {@link Or }
     *
     * @return or
     */
    public Or createOrType() {
        return new Or();
    }

    /**
     * Create an instance of {@link Conditions }
     *
     * @return conditions
     */
    public Conditions createConditionsType() {
        return new Conditions();
    }

}
