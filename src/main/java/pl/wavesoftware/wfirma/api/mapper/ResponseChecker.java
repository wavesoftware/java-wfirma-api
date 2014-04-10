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
package pl.wavesoftware.wfirma.api.mapper;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import static java.util.Locale.US;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import pl.wavesoftware.wfirma.api.model.WFirmaException;
import pl.wavesoftware.wfirma.api.model.WFirmaSercurityException;

/**
 * @author Krzysztof Suszyński <krzysztof.suszynski@gmail.com>
 */
public class ResponseChecker {

    /**
     * Checks for response status, and throws appropriate exception if other then `OK`
     *
     * @param login a user identifier
     * @param content a content of response
     * @return a content of response
     * @throws WFirmaException if status was other then `OK`
     */
    public String checkedForStatus(String login, String content) throws WFirmaException {
        try {
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expr = xpath.compile("/api/status/code");
            InputSource source = new InputSource();
            source.setCharacterStream(new StringReader(content));
            NodeList nodes = (NodeList) expr.evaluate(source, XPathConstants.NODESET);
            if (nodes.getLength() != 1) {
                throw new IllegalStateException("Invalid WFirma output: " + content);
            }
            Node node = nodes.item(0);
            String code = node.getTextContent().toUpperCase(US);
            switch (code) {
                case "OK":
                    return content;
                case "AUTH":
                    throw new WFirmaSercurityException("Auth failed for user: `%s`", login);
                case "ACTION NOT FOUND":
                case "FATAL":
                case "INPUT ERROR":
                case "OUT OF SERVICE":
                case "DENIED SCOPE REQUESTED":
                case "NOT FOUND":
                    throw new WFirmaException(code);
                case "ERROR":
                    XPathExpression errors = xpath.compile("//error/message");
                    source = new InputSource();
                    source.setCharacterStream(new StringReader(content));
                    NodeList errorNodes = (NodeList) errors.evaluate(source, XPathConstants.NODESET);
                    List<String> errorsStr = new ArrayList<>();
                    for (int i = 0; i < errorNodes.getLength(); i++) {
                        errorsStr.add(errorNodes.item(i).getTextContent());
                    }
                    if (errorsStr.isEmpty()) {
                        throw new WFirmaException("%s: %s", code, "no error tags?!");
                    }
                    throw new WFirmaException("%s: %s", code, errorsStr);
                default:
                    throw new WFirmaException("Unknown status code: " + code);
            }
        } catch (XPathExpressionException ex) {
            throw new IllegalStateException("Invalid WFirma output: " + ex.getCause().getLocalizedMessage(), ex);
        }
    }

}
