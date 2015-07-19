/*
 * Copyright (c) 2015 Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
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
