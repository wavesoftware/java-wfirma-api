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

package pl.wavesoftware.wfirma.api.mapper.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;
import static org.unitils.reflectionassert.ReflectionAssert.*;
import org.xml.sax.SAXException;
import pl.wavesoftware.wfirma.api.model.logic.And;
import pl.wavesoftware.wfirma.api.model.logic.LogicalOperator;
import pl.wavesoftware.wfirma.api.model.logic.ObjectFactory;
import pl.wavesoftware.wfirma.api.model.logic.Or;
import pl.wavesoftware.wfirma.api.model.logic.Parameters;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@gmail.com>
 */
public class JaxbMarshallerTest {

    private String expectedXml;

    @Before
    public void setUp() throws IOException {
        try (InputStream stream = this.getClass().getResourceAsStream("sample-parameters.xml")) {
            expectedXml = read(stream);
        }
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true);
    }

    private String read(InputStream stream) {
        Scanner s = new Scanner(stream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    private Parameters sampleParameters() {
        ObjectFactory factory = new ObjectFactory();
        Parameters params = factory.createParametersType();
        params.setPage(2);
        params.setLimit(10);
        params.setOrder(factory.createOrderType());
        params.setConditions(factory.createConditionsType());
        params.getOrder().getAsc().add("name");
        params.getOrder().getAsc().add("surname");
        params.getOrder().getDesc().add("count");
        params.getOrder().getDesc().add("modfied");
        Or or = factory.createOrType();
        And and = factory.createAndType();
        params.getConditions().getOr().add(or);
        params.getConditions().getAnd().add(and);
        or.getCondition().add(factory.createConditionType("name", LogicalOperator.LIKE, "test"));
        or.getCondition().add(factory.createConditionType("id", LogicalOperator.LT, 10));
        and.getCondition().add(factory.createConditionType("surname", LogicalOperator.EQ, "Kowalski"));
        and.getCondition().add(factory.createConditionType("money", LogicalOperator.GE, 5600L));
        return params;
    }

    /**
     * Test of marshal method, of class JaxbMarshaller.
     */
    @Test
    public void testMarshal() throws SAXException, IOException {
        JaxbMarshaller<Parameters> instance = new JaxbMarshaller<>(Parameters.class);
        Parameters params = sampleParameters();
        String result = instance.marshal(params);
        assertXMLEqual("comparing test xml to control xml", expectedXml, result);
    }

    /**
     * Test of unmarshal method, of class JaxbMarshaller.
     */
    @Test
    public void testUnmarshal() {
        JaxbMarshaller<Parameters> instance = new JaxbMarshaller<>(Parameters.class);
        Parameters expResult = sampleParameters();
        Parameters result = instance.unmarshal(expectedXml);
        assertReflectionEquals(expResult, result);
    }

    @Test
    public void test2Way() {
        JaxbMarshaller<Parameters> instance = new JaxbMarshaller<>(Parameters.class);
        Parameters input = sampleParameters();
        String xml = instance.marshal(input);
        Parameters output = instance.unmarshal(xml);
        assertReflectionEquals(input, output);
    }

}
