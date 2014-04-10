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

package pl.wavesoftware.wfirma.api.model;

import java.util.EnumMap;
import java.util.Map;
import pl.wavesoftware.wfirma.api.model.companies.Companies;
import pl.wavesoftware.wfirma.api.model.contractors.Contractors;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public enum ApiModule {

    CONTRACTORS, COMPANIES;

    private static final EnumMap<ApiModule, Class<? extends ApiEntityElement>> MAP = new EnumMap<>(ApiModule.class);

    static {
        MAP.put(CONTRACTORS, Contractors.class);
        MAP.put(COMPANIES, Companies.class);
    }

    /**
     * Gets a entity class for enum
     *
     * @param <T> a type of class
     * @return a class of entity
     */
    public <T extends ApiEntityElement> Class<T> getEntityClass() {
        if (MAP.containsKey(this)) {
            @SuppressWarnings("unchecked")
            Class<T> ret = (Class<T>) MAP.get(this);
            return ret;
        } else {
            String msg = String.format("The enum `%s.%s` does not have a implementation class!",
                    this.getClass().getName(), this.name());
            throw new UnsupportedOperationException(msg);
        }
    }

    /**
     * Gets a module for entity object
     *
     * @param entity a entity object
     * @return a enum module
     */
    public static ApiModule getModuleFor(ApiEntityElement entity) {
        for (Map.Entry<ApiModule, Class<? extends ApiEntityElement>> entry : MAP.entrySet()) {
            if (entry.getValue().equals(entity.getClass())) {
                return entry.getKey();
            }
        }
        throw new UnsupportedOperationException(
                String.format("Class `%s` is not supported in enum `ApiModule`", entity.getClass().getName()));
    }
}
