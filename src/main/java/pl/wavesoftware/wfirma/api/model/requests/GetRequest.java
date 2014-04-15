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
import pl.wavesoftware.wfirma.api.mapper.RequestPath;
import pl.wavesoftware.wfirma.api.model.ApiEntityElement;
import pl.wavesoftware.wfirma.api.mapper.ApiModule;
import pl.wavesoftware.wfirma.api.model.Request;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class GetRequest implements Request {

    private final Class<? extends Api> module;

    private final Long wfirmaId;

    /**
     * Constructor
     *
     * @param module a module of api
     * @param wfirmaId a ID of wfirma
     */
    public GetRequest(Class<? extends Api> module, Long wfirmaId) {
        this.module = module;
        this.wfirmaId = wfirmaId;
    }

    @Override
    public RequestPath getAddress() {
        return RequestPath.fromString(ApiModule.getRequestModulePath(module), "get", wfirmaId.toString());
    }

    @Override
    public Class<? extends ApiEntityElement> getEntityClass() {
        return ApiModule.getEntityClass(module);
    }

}
