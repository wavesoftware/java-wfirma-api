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
import pl.wavesoftware.wfirma.api.model.ApiEntityElement;
import pl.wavesoftware.wfirma.api.model.Request;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 * @param <T> type of entity
 */
public class DeleteRequest<T extends ApiEntityElement> implements Request<T> {

    private final Long wfirmaId;

    private final Class<T> entityClass;

    /**
     * Constructor
     */
    protected DeleteRequest(Class<T> entityClass, Long wfirmaId) {
        this.entityClass = entityClass;
        this.wfirmaId = wfirmaId;
    }

    /**
     * Creates a AddRequest for an entity
     *
     * @param entityClass a entity class of api module
     * @param wfirmaId a ID of wfirma
     * @param <T> a type of entity
     * @return a request
     */
    public static <T extends ApiEntityElement> DeleteRequest<T> create(Class<T> entityClass, Long wfirmaId) {
        DeleteRequest<T> obj = new DeleteRequest<>(entityClass, wfirmaId);
        return obj;
    }

    @Override
    public RequestPath getAddress() {
        Class<? extends Api> module = ApiModule.getModuleFor(entityClass);
        return RequestPath.fromString(ApiModule.getRequestModulePath(module), "delete", wfirmaId.toString());
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }

}
