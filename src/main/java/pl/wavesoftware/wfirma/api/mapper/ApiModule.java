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

import com.google.common.base.CaseFormat;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import pl.wavesoftware.wfirma.api.runtime.FatalSdkException;
import pl.wavesoftware.wfirma.api.model.ApiEntityElement;
import pl.wavesoftware.wfirma.api.model.Request;

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
                throw new IllegalArgumentException("Class `" + class1 + "` is not instance of Request");
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
        Preconditions.checkNotNull(entityClass);
        try {
            ApiEntityElement entity = entityClass.newInstance();
            return entity.getApi();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new FatalSdkException("20150716:113015", ex);
        }
    }

    /**
     * Gets a request module path
     *
     * @param apiClass a api class
     * @return a string for request
     */
    @Nonnull
    public static String getRequestModulePath(@Nonnull Class<? extends Api> apiClass) {
        Preconditions.checkNotNull(apiClass);
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
    public static Class<? extends ApiEntityElement> getEntityClass(@Nonnull Class<? extends Api> apiClass) {
        Preconditions.checkNotNull(apiClass);
        try {
            @SuppressWarnings("unchecked")
            Class<? extends ApiEntityElement> ret = apiClass.newInstance().getEntityClass();
            return ret;
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new FatalSdkException("20150716:113030", ex);
        }
    }

    /**
     * Gets a module for entity object
     *
     * @param entity a entity object
     * @return a enum module
     */
    @Nonnull
    public static Class<? extends Api> getModuleFor(@Nonnull ApiEntityElement entity) {
        Preconditions.checkNotNull(entity);
        return ApiModule.getModuleFor(entity.getClass());
    }

    /**
     * Gets a module for entity object
     *
     * @param <T> type of API antity
     * @param cls a entity class
     * @return a enum module
     */
    @Nonnull
    public static <T extends ApiEntityElement> Class<? extends Api<T>> getModuleFor(@Nonnull Class<T> cls) {
        Preconditions.checkNotNull(cls);
        try {
            T inst = cls.newInstance();
            Api api = inst.getApi();
            @SuppressWarnings("unchecked")
            Class<? extends Api<T>> ret = (Class<? extends Api<T>>) api.getClass();
            return ret;
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new FatalSdkException("20150716:113042", ex);
        }
    }

}
