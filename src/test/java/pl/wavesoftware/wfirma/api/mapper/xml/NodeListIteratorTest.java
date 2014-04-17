/*
 * The MIT License
 *
 * Copyright 2014 Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>.
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

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import static org.assertj.core.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class NodeListIteratorTest {

    private NodeList nodeList;

    private List<Node> nodes;

    @Before
    public void before() {
        nodes = Arrays.asList(
                mock(Node.class),
                mock(Node.class),
                mock(Node.class),
                mock(Node.class),
                mock(Node.class)
        );
        int idx = 1;
        for (Node node : nodes) {
            when(node.getNodeName()).thenReturn("node-" + idx);
            idx++;
        }
        nodeList = new NodeList() {

            @Override
            public Node item(int index) {
                return nodes.get(index);
            }

            @Override
            public int getLength() {
                return nodes.size();
            }

        };
    }

    @Test
    public void testHasNext() {
        NodeListIterator instance = new NodeListIterator(nodeList);
        assertThat(instance.hasNext()).isTrue();
        instance.next();
        assertThat(instance.hasNext()).isTrue();
        instance.next();
        assertThat(instance.hasNext()).isTrue();
        instance.next();
        assertThat(instance.hasNext()).isTrue();
        instance.next();
        assertThat(instance.hasNext()).isTrue();
        instance.next();
        assertThat(instance.hasNext()).isFalse();
    }

    @Test
    public void testNext() {
        NodeListIterator instance = new NodeListIterator(nodeList);
        Node result = instance.next();
        assertThat(result).isNotNull();
        assertThat(result.getNodeName()).isEqualTo("node-1");
        int idx = 2;
        for (; instance.hasNext(); idx++) {
            Node node = instance.next();
            assertThat(node).isNotNull();
            assertThat(node.getNodeName()).isEqualTo("node-" + idx);
        }
        assertThat(idx - 1).isEqualTo(nodes.size());
    }

    @Test
    public void testRemove() {
        try {
            NodeListIterator instance = new NodeListIterator(nodeList);
            instance.remove();
            failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
        } catch (UnsupportedOperationException ex) {
            assertThat(ex.getLocalizedMessage()).isNull();
            assertThat(ex).hasNoCause();
        }
    }

    @Test
    public void testEmpty() {
        try {
            NodeListIterator instance = new NodeListIterator(nodeList);
            instance.next();
            instance.empty();
            failBecauseExceptionWasNotThrown(NoSuchElementException.class);
        } catch (NoSuchElementException nsee) {
            assertThat(nsee).hasNoCause();
            assertThat(nsee).hasMessage("NodeList element `1` doesn't exists");
        }
    }

}
