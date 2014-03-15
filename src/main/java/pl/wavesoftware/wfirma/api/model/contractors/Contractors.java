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
package pl.wavesoftware.wfirma.api.model.contractors;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import pl.wavesoftware.wfirma.api.model.Parametrizable;
import pl.wavesoftware.wfirma.api.model.logic.Parameters;

/**
 * <p>
 * Java class for contractorsType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="contractorsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contractor" type="{}contractorType" maxOccurs="unbounded"/>
 *         &lt;element name="parameters" type="{}parametersType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contractorsType", propOrder = {
    "contractor",
    "parameters"
})
public class Contractors implements Parametrizable {

    @XmlElement(required = true)
    protected List<Contractor> contractor;

    @XmlElement(required = false)
    protected Parameters parameters;

    /**
     * Gets the value of the contractor property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the contractor property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContractor().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Contractor }
     *
     *
     * @return a list of contractors
     */
    public List<Contractor> getContractor() {
        if (contractor == null) {
            contractor = new ArrayList<>();
        }
        return this.contractor;
    }

    /**
     * Gets the value of the parameters property.
     *
     * @return possible object is {@link Parameters }
     *
     */
    @Override
    public Parameters getParameters() {
        return parameters;
    }

    /**
     * Sets the value of the parameters property.
     *
     * @param value allowed object is {@link Parameters }
     *
     */
    @Override
    public void setParameters(Parameters value) {
        this.parameters = value;
    }

}
