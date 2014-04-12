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

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import java.util.ArrayList;
import java.util.Collection;
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
        ArrayList<String> out = new ArrayList<>();
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
