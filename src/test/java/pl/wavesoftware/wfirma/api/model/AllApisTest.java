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
package pl.wavesoftware.wfirma.api.model;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Collection;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.reflections.Reflections;
import pl.wavesoftware.wfirma.api.mapper.Api;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
@RunWith(Parameterized.class)
public class AllApisTest {

    private final String label;

    private final Class<? extends Api> cls;

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        Reflections reflections = new Reflections("pl.wavesoftware");
        Predicate<Class<? extends Api>> predi = new Predicate<Class<? extends Api>>() {

            @Override
            public boolean apply(Class<? extends Api> input) {
                return !(input.getSimpleName().contains("Test")
                        || input.getSimpleName().contains("IT")
                        || input.isSynthetic()
                        || input.isLocalClass()
                        || input.isInterface()
                        || input.isMemberClass());
            }
        };
        Iterable<Class<? extends Api>> all = reflections.getSubTypesOf(Api.class);
        Iterable<Class<? extends Api>> classes = Iterables.filter(all, predi);
        assertThat(classes).hasSize(3);
        Collection<Object[]> output = new ArrayList<>();
        for (Class<? extends Api> cls : classes) {
            output.add(new Object[]{cls.getSimpleName(), cls});
        }
        return output;
    }

    private static ArrayList<Class<? extends ApiEntityElement>> collectedEntityClasses;

    private static ArrayList<ApiEntityElement> collectedEntities;

    public AllApisTest(String label, Class<? extends Api> cls) {
        this.label = label;
        this.cls = cls;
    }

    @BeforeClass
    public static void beforeClass() {
        collectedEntityClasses = new ArrayList<>();
        collectedEntities = new ArrayList<>();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreate() throws InstantiationException, IllegalAccessException {
        assertThat(label).isNotEmpty();
        @SuppressWarnings("unchecked")
        Api<? extends ApiEntityElement> inst = cls.newInstance();
        assertThat(inst).isNotNull();
        Class<? extends ApiEntityElement> entityClass = inst.getEntityClass();
        assertThat(entityClass).isNotNull();
        assertThat(collectedEntityClasses).doesNotContain(entityClass);
        collectedEntityClasses.add(entityClass);
        ApiEntityElement elem = inst.getEntityElement();
        assertThat(elem).isNotNull();
        assertThat(collectedEntities).doesNotContain(elem);
        collectedEntities.add(elem);
        assertThat(elem.getApi()).isSameAs(inst);
    }
}
