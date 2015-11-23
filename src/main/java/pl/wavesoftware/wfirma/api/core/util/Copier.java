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
package pl.wavesoftware.wfirma.api.core.util;

import pl.wavesoftware.eid.exceptions.EidIllegalStateException;

import javax.annotation.Nullable;
import java.lang.reflect.Method;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 * @param <T> a type of cloneable object
 */
public class Copier<T extends Cloneable> {

    private final T input;

    /**
     * Constructor
     *
     * @param input a object to be copied
     */
    protected Copier(@Nullable T input) {
        this.input = input;
    }

    /**
     * A static factory method
     * @param input a object to be copied
     * @param <T> a type of cloneable object
     * @return a copier ready to be copied
     */
    public static <T extends Cloneable> Copier<T> from(@Nullable T input) {
        return new Copier<>(input);
    }

    /**
     * Gets a copy of passed object
     *
     * @return a copy of input object
     */
    @Nullable
    public T copy() {
        T ret;
        if (input == null) {
            ret = null;
        } else {
            try {
                Method method = input.getClass().getDeclaredMethod("clone");
                Object clone = method.invoke(input);
                @SuppressWarnings("unchecked")
                T casted = (T) clone;
                ret = casted;
            } catch (ReflectiveOperationException | SecurityException | IllegalArgumentException ex) {
                throw new EidIllegalStateException("20150925:224232", ex);
            }
        }
        return ret;
    }

}
