/*
 * The MIT License
 *
 * Copyright 2015 Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>.
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
package pl.wavesoftware.wfirma.api.unittests;

import com.google.common.base.Preconditions;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.assertj.core.api.AbstractCharSequenceAssert;
import org.custommonkey.xmlunit.XMLConstants;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class XsdValidatorAssert extends AbstractCharSequenceAssert<XsdValidatorAssert, CharSequence> {

    public XsdValidatorAssert(CharSequence actual) {
        super(actual, XsdValidatorAssert.class);
    }

    public static XsdValidatorAssert assertThat(CharSequence actual) {
        return new XsdValidatorAssert(actual);
    }

    public XsdValidatorAssert isWellFormedXml() {
        isNotNull();

        Exception ex = validateXml();
        if (ex != null) {
            failWithMessage("Expecting to be well formed XML, but validator "
                + "returned <%s> for document <%s>.", ex.getLocalizedMessage(), actual);
        }
        return this;
    }

    public XsdValidatorAssert isValidByXsd(InputStream inputStream) {
        isNotNull();
        isWellFormedXml();

        Exception ex = validateXsd(inputStream);
        if (ex != null) {
            failWithMessage("Expecting to be valid by XSD Schema, but validator "
                + "returned <%s> for document <%s>.", ex.getLocalizedMessage(), actual);
        }
        return this;
    }

    private Exception validateXml() {
        try (InputStream xml = xmlInputStream()) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setNamespaceAware(true);

            DocumentBuilder builder = factory.newDocumentBuilder();

            builder.setErrorHandler(new SimpleErrorHandler());
            // the "parse" method also validates XML, will throw an exception if misformatted
            Document document = builder.parse(new InputSource(xml));
            Preconditions.checkNotNull(document);
            return null;
        } catch (NullPointerException | IOException | SAXException | ParserConfigurationException ex) {
            return ex;
        }
    }

    private class SimpleErrorHandler implements ErrorHandler {

        public void warning(SAXParseException e) throws SAXException {
            throw e;
        }

        public void error(SAXParseException e) throws SAXException {
            throw e;
        }

        public void fatalError(SAXParseException e) throws SAXException {
            throw e;
        }
    }

    private Exception validateXsd(InputStream inputStream) {
        try (InputStream xml = xmlInputStream(); InputStream xsd = inputStream) {
            SchemaFactory factory
                = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsd));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
            return null;
        } catch (SAXException | IOException ex) {
            return ex;
        }
    }

    private InputStream xmlInputStream() {
        byte[] bytes = actual.toString().getBytes(StandardCharsets.UTF_8);
        return new ByteArrayInputStream(bytes);
    }
}
