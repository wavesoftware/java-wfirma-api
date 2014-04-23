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
package pl.wavesoftware.wfirma.api.mapper;

import com.google.common.base.CaseFormat;
import com.google.common.base.Preconditions;
import static java.lang.String.format;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
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
            throw new RuntimeException(ex);
        }
    }

    /**
     * Geta a scope for a api module and scope
     *
     * @param mode a mode
     * @param apiClass a api module class
     * @return a wfirma scope
     */
    @Nonnull
    public static String getScope(@Nonnull ScopeMode mode, @Nonnull Class<? extends Api> apiClass) {
        return format("%s-%s", getRequestModulePath(apiClass), mode.name().toLowerCase());
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
            throw new RuntimeException(ex);
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
            throw new RuntimeException(ex);
        }
    }

    public static enum ScopeMode {

        READ, WRITE
    }

}
