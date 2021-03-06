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
package pl.wavesoftware.wfirma.api.core.mapper.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import pl.wavesoftware.eid.exceptions.Eid;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;
import pl.wavesoftware.wfirma.api.core.mapper.xml.UsesXmlCustomFormatter.Param;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkState;


/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class XsiTypeToObjectPropertyFormatter implements XmlCustomFormatter {

    public static final String FIELD = "field";

    private static final String XSI_URL = "http://www.w3.org/2001/XMLSchema-instance";

    private static final String XSI_SCHEMA = "xsi";

    private static final String XSI_TYPE = "type";

    private final Map<String, String> configuration = new HashMap<>();

    @Override
    public String format(String input) {
        Document document = getDoc(input);
        for (Node node : iterateOnAllNodes(document)) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element el = Element.class.cast(node);
                formatElement(document, el);
            }
        }
        return dumpXml(document);
    }

    protected String dumpXml(Document document) {
        try {
            final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            final DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
            final LSSerializer writer = impl.createLSSerializer();
            LSOutput lsOutput = impl.createLSOutput();
            Writer stringWriter = new StringWriter();
            lsOutput.setCharacterStream(stringWriter);
            lsOutput.setEncoding("UTF-8");

            writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
            writer.getDomConfig().setParameter("xml-declaration", true);
            writer.write(document, lsOutput);
            return stringWriter.toString().replace("\"?>", "\" standalone=\"yes\"?>");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException ex) {
            throw new EidIllegalStateException("20150716:113135", ex);
        }
    }

    private Iterable<Node> iterate(final NodeList nodeList) {
        return new Iterable<Node>() {

            @Override
            public Iterator<Node> iterator() {
                return new NodeListIterator(nodeList);
            }
        };
    }

    private Iterable<Node> iterateOnAllNodes(Document document) {
        final NodeList nodeList = document.getElementsByTagName("*");
        return iterate(nodeList);
    }

    protected Document getDoc(StringReader reader) {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new InputSource(reader));
            doc.setXmlStandalone(true);
            return doc;
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            throw new EidIllegalStateException("20150716:113147", ex);
        }
    }

    private Document getDoc(String xml) {
        try (StringReader reader = new StringReader(xml)) {
            return getDoc(reader);
        }
    }

    @Override
    public String unformat(String input) {
        Document document = getDoc(input);
        for (Node node : iterateOnAllNodes(document)) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element el = Element.class.cast(node);
                unFormatElement(el);
            }
        }
        return dumpXml(document);
    }

    @Override
    public void configure(Param[] configuration) {
        for (Param param : configuration) {
            this.configuration.put(param.key(), param.value());
        }
    }

    private String getConfig(String key) {
        checkState(configuration.containsKey(key), new Eid("20151122:172000"),
                "Value for `%s` param is required, but not set!", key);
        return configuration.get(key);
    }

    private void formatElement(Document document, Element el) {
        String attrType = XSI_SCHEMA + ":" + XSI_TYPE;
        if (el.hasAttribute(attrType)) {
            Element type = formatElementChildNodes(el);
            if (type == null) {
                type = document.createElement(getConfig(FIELD));
                el.appendChild(type);
            }
            type.setTextContent(el.getAttribute(attrType));
            el.removeAttribute(attrType);
            el.removeAttribute("xmlns:xsi");
        }
    }

    private Element formatElementChildNodes(Element el) {
        Element type = null;
        for (Node node : iterate(el.getChildNodes())) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element child = Element.class.cast(node);
                if (child.getTagName().equals(getConfig(FIELD))) {
                    type = child;
                    break;
                }
            }
        }
        return type;
    }

    private boolean isTextNode(Node node) {
        return node.getNodeType() == Node.TEXT_NODE || node.getNodeType() == Node.CDATA_SECTION_NODE;
    }

    private void unFormatElement(Element el) {
        if (el.getTagName().equals(getConfig(FIELD))) {
            NodeList childs = el.getChildNodes();
            if (childs.getLength() == 1 && isTextNode(childs.item(0))) {
                String value = childs.item(0).getTextContent();
                Element parent = Element.class.cast(el.getParentNode());
                parent.removeChild(el);
                parent.setAttribute(XSI_SCHEMA + ":" + XSI_TYPE, value);
                parent.setAttribute("xmlns:xsi", XSI_URL);
            }
        }
    }

}
