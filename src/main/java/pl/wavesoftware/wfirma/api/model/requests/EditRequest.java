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
import pl.wavesoftware.wfirma.api.model.PostRequest;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 * @param <T> a type of entity
 */
public class EditRequest<T extends ApiEntityElement> implements PostRequest<T> {

    private final T entity;

    private final Class<? extends Api> module;

    private final Long wFirmaId;

    protected EditRequest(T entity, Long wFirmaId) {
        this.entity = entity;
        this.wFirmaId = wFirmaId;
        this.module = ApiModule.getModuleFor(entity);
    }

    /**
     * Creates a EditRequest for an entity
     *
     * @param <T> a type of entity
     * @param entity a entity
     * @param wFirmaId a WFirma ID
     * @return a request
     */
    public static <T extends ApiEntityElement> EditRequest<T> create(T entity, Long wFirmaId) {
        EditRequest<T> obj = new EditRequest<>(entity, wFirmaId);
        return obj;
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
    public RequestPath getAddress() {
        return RequestPath.fromString(ApiModule.getRequestModulePath(module), "edit", wFirmaId.toString());
    }

    @Override
    public Class<T> getEntityClass() {
        @SuppressWarnings("unchecked")
        Class<T> cls = (Class<T>) entity.getClass();
        return cls;
    }

}
