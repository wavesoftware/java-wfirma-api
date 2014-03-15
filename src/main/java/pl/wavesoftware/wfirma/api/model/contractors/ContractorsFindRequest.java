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

package pl.wavesoftware.wfirma.api.model.contractors;

import pl.wavesoftware.wfirma.api.mapper.RequestPath;
import pl.wavesoftware.wfirma.api.mapper.xml.JaxbMarshaller;
import pl.wavesoftware.wfirma.api.model.AbstractFindRequest;
import pl.wavesoftware.wfirma.api.model.logic.Parameters;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class ContractorsFindRequest extends AbstractFindRequest {

    private final RequestPath address;

    private Api api;

    private ContractorsFindRequest(Action action, Api api) {
        super(api.getContractors());
        this.api = api;
        String path = String.format("/contractors/%s", action.name().toLowerCase());
        address = new RequestPath(path);
    }

    public ContractorsFindRequest(Action action, Parameters parameters) {
        this(action, new Api());
        api.getContractors().setParameters(parameters);
    }

    public ContractorsFindRequest(Action action) {
        this(action, new Api());
    }

    public ContractorsFindRequest() {
        this(Action.FIND);
    }

    @Override
    public RequestPath getAddress() {
        return address;
    }

    @Override
    public String buildRequest() {
        return JaxbMarshaller.create(Api.class).marshal(api);
    }

    public enum Action {

        FIND, GET
    }

}
