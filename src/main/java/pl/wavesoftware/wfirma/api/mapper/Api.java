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

import java.util.Collection;
import pl.wavesoftware.wfirma.api.model.ApiEntityElement;
import pl.wavesoftware.wfirma.api.model.Request;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 * @param <T> a type of API
 */
public interface Api<T extends ApiEntityElement> {

    /**
     * Gets a entity class for API
     *
     * @return a entity class
     */
    Class<T> getEntityClass();

    /**
     * Gets a entity element for API
     *
     * @return a entity element
     */
    T getEntityElement();

    /**
     * Sets a entity element for API
     *
     * @param entityElement an entity element
     */
    void setEntityElement(T entityElement);

    /**
     * Gets a collection of supported requests
     *
     * @return a list of supported request types
     */
    Collection<Class<? extends Request>> getSupportedRequests();

}
