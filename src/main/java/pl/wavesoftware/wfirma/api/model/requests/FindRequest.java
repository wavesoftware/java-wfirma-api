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
package pl.wavesoftware.wfirma.api.model.requests;

import pl.wavesoftware.wfirma.api.mapper.Api;
import pl.wavesoftware.wfirma.api.mapper.ApiModule;
import pl.wavesoftware.wfirma.api.mapper.RequestPath;
import pl.wavesoftware.wfirma.api.mapper.xml.JaxbMarshaller;
import pl.wavesoftware.wfirma.api.model.ApiEntityElement;
import pl.wavesoftware.wfirma.api.model.Parametrizable;
import pl.wavesoftware.wfirma.api.model.PostRequest;
import pl.wavesoftware.wfirma.api.model.logic.Parameters;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 * @param <T> a type of this request
 */
public class FindRequest<T extends ApiEntityElement> implements PostRequest<T> {

    private final T entity;

    private final Class<T> entityClass;

    private FindRequest(Class<T> entityClass, Parameters parameters) {
        this.entityClass = entityClass;
        try {
            T en = entityClass.newInstance();
            this.entity = en;
            if (entity instanceof Parametrizable && parameters != null) {
                Parametrizable params = (Parametrizable) entity;
                params.setParameters(parameters);
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private FindRequest(Class<T> entityClass) {
        this(entityClass, null);
    }

    /**
     * Constructor method
     *
     * @param entityClass a entity class of api module
     * @param parameters a parameters to find element
     * @param <T> a type of entity
     * @return a request
     */
    public static <T extends ApiEntityElement> FindRequest<T> create(Class<T> entityClass, Parameters parameters) {
        FindRequest<T> obj = new FindRequest<>(entityClass, parameters);
        return obj;
    }

    /**
     * Constructor method for find all elements
     *
     * @param entityClass a entity class of api module
     * @param <T> a type of entity
     * @return a request
     */
    public static <T extends ApiEntityElement> FindRequest<T> create(Class<T> entityClass) {
        FindRequest<T> obj = new FindRequest<>(entityClass);
        return obj;
    }

    @Override
    public RequestPath getAddress() {
        Class<? extends Api> module = ApiModule.getModuleFor(entityClass);
        return RequestPath.fromString(ApiModule.getRequestModulePath(module), "find");
    }

    @Override
    public T getEntity() {
        return entity;
    }

    @Override
    public String getBody() {
        return JaxbMarshaller.createFor(entity.getApi()).marshal(entity.getApi());
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }

}
