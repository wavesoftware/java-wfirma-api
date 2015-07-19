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
package pl.wavesoftware.wfirma.api.mapper.xml;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
class NodeListIterator implements Iterator<Node> {

    private final NodeList nodeList;

    private int index = 0;

    public NodeListIterator(NodeList nodeList) {
        this.nodeList = nodeList;
    }

    @Override
    public boolean hasNext() {
        return index < nodeList.getLength();
    }

    @Override
    public Node next() throws NoSuchElementException {
        return hasNext() ? nodeList.item(forward()) : empty();
    }

    protected Node empty() throws NoSuchElementException {
        throw new NoSuchElementException("NodeList element `" + index + "` doesn't exists");
    }

    private int forward() {
        int actual = index;
        index++;
        return actual;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
