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
