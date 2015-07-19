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
