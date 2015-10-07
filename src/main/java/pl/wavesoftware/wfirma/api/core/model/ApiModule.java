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
package pl.wavesoftware.wfirma.api.core.model;

import com.google.common.base.CaseFormat;
import pl.wavesoftware.eid.exceptions.EidIllegalArgumentException;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public final class ApiModule {

    protected ApiModule() {
    }

    /**
     * Makes a collection of classes from input
     *
     * @param classes a input classes
     * @return a collection of classes
     */
    public static Collection<Class<? extends Request>> collectRequests(Class<?>... classes) {
        List<Class<? extends Request>> out = new ArrayList<>();
        for (Class<?> class1 : classes) {
            if (Request.class.isAssignableFrom(class1)) {
                @SuppressWarnings("unchecked")
                Class<? extends Request> casted = (Class<? extends Request>) class1;
                out.add(casted);
            } else {
                throw new EidIllegalArgumentException("20150820:004549", "Class `" + class1 + "` is not instance of Request");
            }
        }
        return out;
    }

    /**
     * Creates a sample API object
     *
     * @param entityClass a entity class
     * @return a sample API
     */
    @Nonnull
    public static Api createSampleApi(@Nonnull Class<? extends ApiEntityElement> entityClass) {
        ApiEntityElement entity = instantinate(entityClass);
        return entity.getApi();
    }

    /**
     * Gets a request module path
     *
     * @param apiClass a domain class
     * @return a string for request
     */
    @Nonnull
    public static String getRequestModulePath(@Nonnull Class<? extends Api> apiClass) {
        checkNotNull(apiClass);
        String name = apiClass.getSimpleName().replaceAll("Api$", "");
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
    }

    /**
     * Gets a entity class for enum
     *
     * @param apiClass a class of API
     * @return a class of entity
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static Class<? extends ApiEntityElement> getEntityClass(@Nonnull Class<? extends Api> apiClass) {
        return instantinate(apiClass).getEntityClass();
    }

    /**
     * Gets a module for entity object
     *
     * @param entity a entity object
     * @return a enum module
     */
    @Nonnull
    public static Class<? extends Api> getModuleFor(@Nonnull ApiEntityElement entity) {
        return getModuleFor(checkNotNull(entity).getClass());
    }

    /**
     * Gets a module for entity object
     *
     * @param <T> type of API antity
     * @param cls a entity class
     * @return a enum module
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static <T extends ApiEntityElement> Class<? extends Api<T>> getModuleFor(@Nonnull Class<T> cls) {
        T inst = instantinate(cls);
        Api api = inst.getApi();
        return (Class<? extends Api<T>>) api.getClass();
    }

    @Nonnull
    private static <T> T instantinate(@Nonnull Class<T> cls) {
        try {
            return checkNotNull(cls).newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new EidIllegalArgumentException("20150716:113042", ex);
        }
    }

}
