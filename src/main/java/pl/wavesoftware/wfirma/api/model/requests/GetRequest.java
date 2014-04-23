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
import pl.wavesoftware.wfirma.api.model.ApiEntityElement;
import pl.wavesoftware.wfirma.api.model.Request;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 * @param <T> a type of request
 */
public class GetRequest<T extends ApiEntityElement> implements Request<T> {

    private final Long wfirmaId;

    private final Class<T> entityClass;

    /**
     * Constructor
     *
     * @param entityClass a entity class of api module
     * @param wfirmaId a ID of wfirma
     */
    protected GetRequest(Class<T> entityClass, Long wfirmaId) {
        this.entityClass = entityClass;
        this.wfirmaId = wfirmaId;
    }

    /**
     * Creates a {@link GetRequest} for an entity
     *
     * @param entityClass a entity class of api module
     * @param wfirmaId a ID of wfirma
     * @param <T> a type of entity
     * @return a request
     */
    public static <T extends ApiEntityElement> GetRequest<T> create(Class<T> entityClass, Long wfirmaId) {
        GetRequest<T> obj = new GetRequest<>(entityClass, wfirmaId);
        return obj;
    }

    @Override
    public RequestPath getAddress() {
        Class<? extends Api> module = ApiModule.getModuleFor(entityClass);
        return RequestPath.fromString(ApiModule.getRequestModulePath(module), "get", wfirmaId.toString());
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public String getScope() {
        Class<? extends Api> module = ApiModule.getModuleFor(entityClass);
        return ApiModule.getScope(ApiModule.ScopeMode.READ, module);
    }

}
