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
package pl.wavesoftware.wfirma.api.mapper;

import java.lang.reflect.Method;
import javax.annotation.Nullable;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public final class Copier {

    protected Copier() {
    }

    /**
     * Gets a copy of passed object
     *
     * @param <T> a type of object
     * @param input a object to be copied
     * @return a copy of input object
     */
    @Nullable
    public static <T extends Cloneable> T copy(@Nullable T input) {
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
                throw new IllegalStateException(ex);
            }
        }
        return ret;
    }

}
