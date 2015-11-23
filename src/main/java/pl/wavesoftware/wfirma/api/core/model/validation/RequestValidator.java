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
package pl.wavesoftware.wfirma.api.core.model.validation;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Primitives;
import junit.extensions.PA;
import pl.wavesoftware.wfirma.api.core.model.Api;
import pl.wavesoftware.wfirma.api.core.model.ApiModule;
import pl.wavesoftware.wfirma.api.core.model.Request;
import pl.wavesoftware.wfirma.api.core.model.exceptions.WFirmaException;
import pl.wavesoftware.wfirma.api.core.model.requests.EditRequest;
import pl.wavesoftware.wfirma.api.core.model.utils.ReadOnly;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class RequestValidator {

    private final Request<?> request;

    private Collection<String> errors;

    /**
     * Constructor
     *
     * @param request a request
     */
    public RequestValidator(Request<?> request) {
        this.request = request;
    }

    public boolean isValid() {
        return getErrors().isEmpty();
    }

    /**
     * Gets all errors collected by this validator in validation process
     *
     * @return all errors
     */
    public Collection<String> getErrors() {
        if (errors == null) {
            errors = validateAndGetErrors();
        }
        return ImmutableList.copyOf(errors);
    }

    private Collection<String> validateAndGetErrors() {
        Collection<String> errs = new ArrayList<>();
        validateIsSupported(errs);
        validateNotReadOnly(errs);
        return errs;
    }

    /**
     * Validate a request befre it being submitted to WFirma API
     *
     * @throws WFirmaException throws if something is wrong
     */
    public void validate() throws WFirmaException {
        if (!getErrors().isEmpty()) {
            throw new WFirmaException("Validation errors: [%s]", Joiner.on("][").join(errors));
        }
    }

    private void validateIsSupported(Collection<String> errs) {
        Class<? extends Api> module = ApiModule.getModuleFor(request.getEntityClass());
        Api api = ApiModule.createSampleApi(request.getEntityClass());
        @SuppressWarnings("unchecked")
        Collection<Class<? extends Request>> supported = api.getSupportedRequests();
        boolean found = false;
        for (Class<? extends Request> cls : supported) {
            if (cls.isAssignableFrom(request.getClass())) {
                found = true;
                break;
            }
        }
        if (!found) {
            String template = "`%s` is not supported by "
                    + "`%s` module. Only supported request are: `%s`";
            List<String> simple = new ArrayList<>();
            for (Class<? extends Request> cls : supported) {
                simple.add(cls.getSimpleName());
            }
            String valid = Joiner.on("`, `").join(simple);
            errs.add(String.format(template, request.getClass().getSimpleName(), module.getSimpleName(), valid));
        }
    }

    private void validateNotReadOnly(Collection<String> errs) {
        if (!(request instanceof EditRequest)) {
            return; // check only on EditRequests
        }
        EditRequest<?> edit = (EditRequest<?>) request;
        Object entity = edit.getEntity();
        errs.addAll(validateNotReadOnly(entity, entity.getClass()));
    }

    private static Collection<String> validateNotReadOnly(Object entity, Class<?> cls) {
        checkArgument(cls.isAssignableFrom(entity.getClass()), "entity `%s` must be a instance of cls `%s`", entity, cls);
        Collection<String> out = new ArrayList<>();
        if (cls.getSuperclass() != null) {
            out.addAll(validateNotReadOnly(entity, cls.getSuperclass()));
        }
        for (Field field : cls.getDeclaredFields()) {
            if (isNonEmptyCollection(field, entity)) {
                Object value = PA.getValue(entity, field.getName());
                Collection<?> col = Collection.class.cast(value);
                for (Object object : col) {
                    out.addAll(validateNotReadOnly(object, object.getClass()));
                }
            }
            ReadOnly annot = field.getAnnotation(ReadOnly.class);
            if (annot == null) {
                continue;
            }
            Object value = PA.getValue(entity, field.getName());
            if (value != null) {
                String format = "The `%s` property of `%s` is read only";
                out.add(String.format(format, field.getName(), entity.getClass().getSimpleName()));
            }
        }
        return out;
    }

    private static boolean isNonEmptyCollection(Field field, Object entity) {
        boolean ret = Collection.class.isAssignableFrom(field.getType());
        if (!ret) {
            return false;
        }
        Object value = PA.getValue(entity, field.getName());
        Collection<?> col = Collection.class.cast(value);
        if (col == null || col.isEmpty()) {
            return false;
        }
        Object element = col.iterator().next();
        return !Primitives.isWrapperType(element.getClass());
    }
}
