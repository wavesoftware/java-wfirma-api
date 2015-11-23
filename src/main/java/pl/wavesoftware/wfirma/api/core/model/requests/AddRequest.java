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
package pl.wavesoftware.wfirma.api.core.model.requests;

import pl.wavesoftware.wfirma.api.core.model.Api;
import pl.wavesoftware.wfirma.api.core.model.ApiModule;
import pl.wavesoftware.wfirma.api.core.mapper.RequestPath;
import pl.wavesoftware.wfirma.api.core.mapper.xml.JaxbMarshaller;
import pl.wavesoftware.wfirma.api.core.model.ApiEntityElement;
import pl.wavesoftware.wfirma.api.core.model.PostRequest;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 * @param <T> a type of this request
 */
public class AddRequest<T extends ApiEntityElement> implements PostRequest<T> {

    private final Api module;

    private final T entity;

    protected AddRequest(T entity) {
        this.module = ApiModule.createSampleApi(entity.getClass());
        this.entity = entity;
    }

    /**
     * Creates a AddRequest for an entity
     *
     * @param <T> a type of entity
     * @param entity a entity
     * @return a request
     */
    public static <T extends ApiEntityElement> AddRequest<T> create(T entity) {
        return new AddRequest<>(entity);
    }

    @Override
    public RequestPath getAddress() {
        return RequestPath.fromString(ApiModule.getRequestModulePath(module.getClass()), "add");
    }

    @Override
    public String getBody() {
        return JaxbMarshaller.createFor(entity.getApi()).marshal(entity.getApi());
    }

    @Override
    public T getEntity() {
        return entity;
    }

    @Override
    public Class<T> getEntityClass() {
        @SuppressWarnings("unchecked")
        Class<T> cls = (Class<T>) entity.getClass();
        return cls;
    }

}
