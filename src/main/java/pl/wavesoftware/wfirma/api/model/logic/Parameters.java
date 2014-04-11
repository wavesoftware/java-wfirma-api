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
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parametersType", propOrder = {
    "conditions",
    "order",
    "page",
    "limit"
})
@XmlRootElement(name = "parameters")
public class Parameters {

    @XmlElement(required = true)
    protected Conditions conditions = new Conditions();

    @XmlElement(required = true)
    protected Order order;

    @XmlElement(required = false)
    protected Integer page = 0;

    @XmlElement(required = false)
    protected Integer limit = 20;

    /**
     * Gets the value of the conditions property.
     *
     * @return possible object is {@link Conditions }
     *
     */
    public Conditions getConditions() {
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

}
