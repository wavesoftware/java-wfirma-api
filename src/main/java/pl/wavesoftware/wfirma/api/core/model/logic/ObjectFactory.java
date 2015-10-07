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

import javax.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * pl.wavesoftware.wfirma.domain.model.logic package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content. The Java
 * representation of XML content can consist of schema derived interfaces and classes representing the binding of schema type definitions,
 * element declarations and model groups. Factory methods for each of these are provided in this class.
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
@XmlRegistry
public class ObjectFactory {

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
     * pl.wavesoftware.wfirma.domain.model.logic
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
