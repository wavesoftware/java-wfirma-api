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
import pl.wavesoftware.wfirma.api.model.WFirmaSecurityException;

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
                    throw new WFirmaSecurityException("Auth failed for user: `%s`", login);
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
