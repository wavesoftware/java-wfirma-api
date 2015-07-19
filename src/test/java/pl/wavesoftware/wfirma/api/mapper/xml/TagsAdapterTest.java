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
