/*
 * Copyright (c) 2015 Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
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

package pl.wavesoftware.wfirma.api.core.mapper.xml;

import pl.wavesoftware.wfirma.api.core.model.Api;
import pl.wavesoftware.wfirma.api.core.model.ApiEntityElement;
import pl.wavesoftware.wfirma.api.core.model.ApiModule;
import pl.wavesoftware.wfirma.api.core.model.Response;

/**
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 * @since 2015-09-27
 */
public class JaxbResponse<T extends ApiEntityElement> implements Response<T> {

    private final Class<T> entityClass;

    private final String response;

    private T entity;

    private boolean fetched = false;

    public JaxbResponse(Class<T> entityClass, String response) {
        this.entityClass = entityClass;
        this.response = response;
    }

    @Override
    public T getEntity() {
        if (!fetched) {
            Class<? extends Api<T>> module = ApiModule.getModuleFor(entityClass);
            JaxbMarshaller<? extends Api> marshaller = JaxbMarshaller.create(module);
            Api api = marshaller.unmarshal(response);
            @SuppressWarnings("unchecked")
            T en = (T) api.getEntityElement();
            entity = en;
            fetched = true;
        }
        return entity;
    }

}
