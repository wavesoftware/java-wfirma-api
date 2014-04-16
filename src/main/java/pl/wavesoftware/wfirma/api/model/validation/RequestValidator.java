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
package pl.wavesoftware.wfirma.api.model.validation;

import com.google.common.base.Joiner;
import static com.google.common.base.Preconditions.checkArgument;
import com.google.common.primitives.Primitives;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import pl.wavesoftware.wfirma.api.mapper.Api;
import pl.wavesoftware.wfirma.api.mapper.ApiModule;
import pl.wavesoftware.wfirma.api.model.Request;
import pl.wavesoftware.wfirma.api.model.WFirmaException;
import pl.wavesoftware.wfirma.api.model.requests.EditRequest;
import pl.wavesoftware.wfirma.api.model.utils.ReadOnly;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class RequestValidator {

    private final Request<?> request;

    private Collection<String> errors;

    public RequestValidator(Request<?> request) {
        this.request = request;
    }

    public boolean isValid() {
        return getErrors().isEmpty();
    }

    public Collection<String> getErrors() {
        if (errors == null) {
            errors = validateAndGetErrors();
        }
        return errors;
    }

    private Collection<String> validateAndGetErrors() {
        Collection<String> errs = new ArrayList<>();
        validateIsSupported(errs);
        validateNotReadOnly(errs);
        return errs;
    }

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
        try {
            errs.addAll(validateNotReadOnly(entity, entity.getClass()));
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new RuntimeException("Should not happend! Bug! " + ex.getLocalizedMessage(), ex);
        }
    }

    private static Collection<String> validateNotReadOnly(Object entity, Class<?> cls) throws IllegalArgumentException, IllegalAccessException {
        checkArgument(cls.isAssignableFrom(entity.getClass()), "entity `%s` must be a instance of cls `%s`", entity, cls);
        Collection<String> out = new ArrayList<>();
        if (cls.getSuperclass() != null) {
            out.addAll(validateNotReadOnly(entity, cls.getSuperclass()));
        }
        for (Field field : cls.getDeclaredFields()) {
            if (isNonEmptyCollection(field, entity)) {
                boolean access = field.isAccessible();
                field.setAccessible(true);
                Collection<?> col = Collection.class.cast(field.get(entity));
                field.setAccessible(access);
                for (Object object : col) {
                    out.addAll(validateNotReadOnly(object, object.getClass()));
                }
            }
            ReadOnly annot = field.getAnnotation(ReadOnly.class);
            if (annot == null) {
                continue;
            }
            boolean access = field.isAccessible();
            field.setAccessible(true);
            Object value = field.get(entity);
            field.setAccessible(access);
            if (value != null) {
                String format = "The `%s` property of `%s` is read only";
                out.add(String.format(format, field.getName(), entity.getClass().getSimpleName()));
            }
        }
        return out;
    }

    private static boolean isNonEmptyCollection(Field field, Object entity) throws IllegalArgumentException, IllegalAccessException {
        boolean ret = Collection.class.isAssignableFrom(field.getType());
        if (!ret) {
            return false;
        }
        boolean access = field.isAccessible();
        field.setAccessible(true);
        Collection<?> col = Collection.class.cast(field.get(entity));
        field.setAccessible(access);
        if (col == null || col.isEmpty()) {
            return false;
        }
        Object element = col.iterator().next();
        return !Primitives.isWrapperType(element.getClass());
    }
}
