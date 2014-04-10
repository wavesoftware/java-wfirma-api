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

import pl.wavesoftware.wfirma.api.mapper.RequestPath;
import pl.wavesoftware.wfirma.api.mapper.xml.JaxbMarshaller;
import pl.wavesoftware.wfirma.api.model.ApiEntityElement;
import pl.wavesoftware.wfirma.api.model.ApiModule;
import pl.wavesoftware.wfirma.api.model.PostRequest;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 * @param <T> a type of entity
 */
public class EditRequest<T extends ApiEntityElement> implements PostRequest<T> {

    private final T entity;

    private final ApiModule module;

    private final Long wFirmaId;

    private EditRequest(T entity, Long wFirmaId) {
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
        return RequestPath.fromString(module.name().toLowerCase(), "edit", wFirmaId.toString());
    }

    @Override
    public Class<? extends ApiEntityElement> getEntityClass() {
        return entity.getClass();
    }

}
