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

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class TagsAdapter extends XmlAdapter<String, Collection<String>> {

    @Override
    @Nonnull
    public Collection<String> unmarshal(@Nullable String input) {
        List<String> out = new ArrayList<>();
        if (input != null) {
            String txt = input;
            if (!txt.isEmpty() && txt.startsWith("(") && txt.endsWith(")")) {
                txt = txt.subSequence(1, txt.length() - 1).toString();
            }
            for (String tag : Splitter.on("),(").split(txt)) {
                out.add(tag);
            }
            if (out.size() == 1 && "".equals(out.iterator().next())) {
                out = new ArrayList<>();
            }
        }
        return out;
    }

    @Override
    @Nonnull
    public String marshal(@Nullable Collection<String> tags) {
        StringBuilder builder = new StringBuilder();
        if (tags != null && !tags.isEmpty()) {
            builder.append("(")
                .append(Joiner.on("),(").join(tags))
                .append(")");
        }
        return builder.toString();
    }

}
