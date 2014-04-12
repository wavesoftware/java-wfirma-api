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
import java.util.Collection;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class TagsAdapterTest {

    @Test
    public void testUnmarshal() {
        String input = "(some),(fantastic)";
        TagsAdapter instance = new TagsAdapter();
        Collection<String> result = instance.unmarshal(input);
        assertThat(result).containsExactly("some", "fantastic");
    }

    @Test
    public void testUnmarshalEmpty() {
        String input = "";
        TagsAdapter instance = new TagsAdapter();
        Collection<String> result = instance.unmarshal(input);
        assertThat(result).containsExactly();
        assertThat(result).hasSize(0);
    }

    @Test
    public void testUnmarshalNull() {
        String input = null;
        TagsAdapter instance = new TagsAdapter();
        Collection<String> result = instance.unmarshal(input);
        assertThat(result).containsExactly();
        assertThat(result).hasSize(0);
    }

    @Test
    public void testMarshal() {
        Collection<String> tags = Arrays.asList("tag1", "new", "better");
        TagsAdapter instance = new TagsAdapter();
        String expResult = "(tag1),(new),(better)";
        String result = instance.marshal(tags);
        assertThat(result).isEqualTo(expResult);
    }

    @Test
    public void testMarshalEmpty() {
        Collection<String> tags = Arrays.asList();
        TagsAdapter instance = new TagsAdapter();
        String result = instance.marshal(tags);
        assertThat(result).isEqualTo("");
    }

    @Test
    public void testMarshalNull() {
        Collection<String> tags = null;
        TagsAdapter instance = new TagsAdapter();
        String result = instance.marshal(tags);
        assertThat(result).isEqualTo("");
    }

}
