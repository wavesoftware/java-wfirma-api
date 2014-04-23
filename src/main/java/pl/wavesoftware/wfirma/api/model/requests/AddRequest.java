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
import pl.wavesoftware.wfirma.api.model.PostRequest;

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
        AddRequest<T> obj = new AddRequest<>(entity);
        return obj;
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

    @Override
    public String getScope() {
        return ApiModule.getScope(ApiModule.ScopeMode.WRITE, module.getClass());
    }

}
