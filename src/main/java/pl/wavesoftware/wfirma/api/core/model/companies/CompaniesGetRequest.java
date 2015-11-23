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
package pl.wavesoftware.wfirma.api.core.model.companies;

import pl.wavesoftware.wfirma.api.core.model.ApiModule;
import pl.wavesoftware.wfirma.api.core.mapper.RequestPath;
import pl.wavesoftware.wfirma.api.core.model.requests.GetRequest;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class CompaniesGetRequest extends GetRequest<Companies> {

    protected CompaniesGetRequest() {
        super(Companies.class, 0L);
    }

    /**
     * Creats a {@link CompaniesGetRequest} object
     *
     * @return a created request
     */
    public static CompaniesGetRequest create() {
        return new CompaniesGetRequest();
    }

    @Override
    public RequestPath getAddress() {
        return RequestPath.fromString(ApiModule.getRequestModulePath(CompaniesApi.class), "get");
    }
}
