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
package pl.wavesoftware.wfirma.api.core.mapper;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import pl.wavesoftware.eid.exceptions.Eid;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;
import pl.wavesoftware.wfirma.api.core.model.WFirmaException;
import pl.wavesoftware.wfirma.api.core.model.WFirmaSecurityException;

import javax.xml.xpath.*;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static java.util.Locale.US;
import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;
import static pl.wavesoftware.eid.utils.EidPreconditions.checkState;

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
            return execute(login, content);
        } catch (XPathExpressionException ex) {
            String message = "Invalid WFirma output: " + ex.getCause().getLocalizedMessage();
            throw new EidIllegalStateException("20150925:225028", message, ex);
        }
    }

    private String execute(String login, String content) throws XPathExpressionException, WFirmaException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr = xpath.compile("/api/status/code");
        InputSource source = new InputSource();
        source.setCharacterStream(new StringReader(content));
        NodeList nodes = (NodeList) expr.evaluate(source, XPathConstants.NODESET);
        checkState(nodes.getLength() != 1, new Eid("20150925:224454", errorMessage(content)));
        Node node = checkNotNull(nodes.item(0), new Eid("20151005:221513", errorMessage(content)));
        String code = node.getTextContent().toUpperCase(US);
        return switchAtCode(xpath, content, code, login);
    }

    private String errorMessage(String content) {
        return "Unexpected WForma output: " + content;
    }

    private String switchAtCode(XPath xpath, String content, String code, String login)
            throws WFirmaException, XPathExpressionException {
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
                handleError(xpath, content, code);
            default:
                throw new WFirmaException("Unknown status code: %s", code);
        }
    }

    private void handleError(XPath xpath, String content, String code) throws
            XPathExpressionException, WFirmaException {
        XPathExpression errors = xpath.compile("//error/message");
        InputSource source = new InputSource();
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
    }

}
