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

    protected FindRequest(Class<T> entityClass, Parameters parameters) {
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

    protected FindRequest(Class<T> entityClass) {
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
