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
package pl.wavesoftware.wfirma.api.core.mapper.xml;

import com.google.common.reflect.ClassPath;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;
import pl.wavesoftware.wfirma.api.core.model.Api;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@gmail.com>
 * @param <Type> a type of object to marshal
 */
public class JaxbMarshaller<Type> {

    private final Class<Type> type;

    /**
     * Create a intance of marchaller
     *
     * @param <T> a type
     * @param cls a class of type
     * @return marshaller for type
     */
    public static <T> JaxbMarshaller<T> create(Class<T> cls) {
        return new JaxbMarshaller<>(cls);
    }

    /**
     * Create a intance of marchaller
     *
     * @param <T> a type
     * @param entity a entity object
     * @return marshaller for type
     */
    public static <T extends Api> JaxbMarshaller<T> createFor(T entity) {
        @SuppressWarnings("unchecked")
        JaxbMarshaller<T> marshaller = (JaxbMarshaller<T>) JaxbMarshaller.create(entity.getClass());
        return marshaller;
    }

    protected JaxbMarshaller(Class<Type> cls) {
        this.type = cls;
    }

    protected JAXBContext getContext() {
        try {
            final Package pack = type.getPackage();
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();

            Set<Class<?>> classes = new HashSet<>();
            for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
                if (info.getName().startsWith(pack.getName()) && !info.getName().endsWith("Test")) {
                    classes.add(info.load());
                }
            }
            Class<?>[] arr = new Class<?>[classes.size()];
            classes.toArray(arr);
            return JAXBContext.newInstance(arr);
        } catch (JAXBException | IOException ex) {
            throw new EidIllegalStateException("20150716:113108", ex);
        }
    }

    /**
     * Marshal a object to string
     *
     * @param object a object to marshal
     * @return a string
     */
    public String marshal(Type object) {
        try {
            JAXBContext jaxbContext = getContext();
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(object, sw);
            return format(sw.toString());
        } catch (JAXBException ex) {
            throw new EidIllegalStateException("20150719:181250", ex);
        }
    }

    /**
     * Unmarshal a string to object
     *
     * @param input a input string
     * @return a object
     */
    public Type unmarshal(String input) {
        try {
            JAXBContext jaxbContext = getContext();

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            try (StringReader sr = new StringReader(unformat(input))) {
                Object element = jaxbUnmarshaller.unmarshal(sr);
                if (element instanceof JAXBElement) {
                    @SuppressWarnings("unchecked")
                    JAXBElement<Type> jaxbe = (JAXBElement<Type>) element;
                    return jaxbe.getValue();
                }
                return type.cast(element);
            }
        } catch (JAXBException ex) {
            throw new EidIllegalStateException("20150716:115101", ex);
        }
    }

    private XmlCustomFormatter getFormatter() {
        try {
            UsesXmlCustomFormatter annot = type.getAnnotation(UsesXmlCustomFormatter.class);
            XmlCustomFormatter formatter = null;
            if (annot != null) {
                formatter = annot.value().newInstance();
                formatter.configure(annot.parameters());
            }
            return formatter;
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new EidIllegalStateException("20150716:113119", ex);
        }
    }

    protected String format(String input) {
        XmlCustomFormatter formatter = getFormatter();
        return (formatter != null) ? formatter.format(input) : input;
    }

    protected String unformat(String input) {
        XmlCustomFormatter formatter = getFormatter();
        return (formatter != null) ? formatter.unformat(input) : input;
    }

}
