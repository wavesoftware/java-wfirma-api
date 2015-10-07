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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for parametersType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="parametersType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="conditions" type="{}conditionsType"/>
 *         &lt;element name="order" type="{}orderType"/>
 *         &lt;element name="page" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="limit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parametersType")
@XmlRootElement(name = "parameters")
public class Parameters {

    @XmlElement
    private Conditions conditions;

    @XmlElement
    private List<Parameter> parameter;

    @XmlElement(required = true)
    private Order order;

    @XmlElement
    private Integer page = 0;

    @XmlElement
    private Integer limit = 20;

    /**
     * Gets the value of the conditions property.
     *
     * @return possible object is {@link Conditions }
     *
     */
    public Conditions getConditions() {
        if (conditions == null) {
            conditions = new Conditions();
        }
        return conditions;
    }

    /**
     * Sets the value of the conditions property.
     *
     * @param value allowed object is {@link Conditions }
     *
     */
    public void setConditions(Conditions value) {
        this.conditions = value;
    }

    /**
     * Gets the value of the order property.
     *
     * @return possible object is {@link Order }
     *
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Sets the value of the order property.
     *
     * @param value allowed object is {@link Order }
     *
     */
    public void setOrder(Order value) {
        this.order = value;
    }

    /**
     * Gets the value of the page property.
     *
     * @return a page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * Sets the value of the page property.
     *
     * @param page a page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * Gets the value of the limit property.
     *
     * @return a page limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * Sets the value of the limit property.
     *
     * @param limit a limit
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * Gets the value of the parameter property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list
     * will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the and property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameter().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Parameter}
     *
     * @return Parameter(s)
     */
    public List<Parameter> getParameter() {
        if (parameter == null) {
            parameter = new ArrayList<>();
        }
        return parameter;
    }

    protected void setParameter(List<Parameter> parameter) {
        this.parameter = parameter;
    }

}
