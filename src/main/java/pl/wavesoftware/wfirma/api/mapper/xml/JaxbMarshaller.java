/*
 * The MIT License
 *
 * Copyright 2014 Krzysztof Suszyński <krzysztof.suszynski@gmail.com>.
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
package pl.wavesoftware.wfirma.api.mapper.xml;

import com.google.common.reflect.ClassPath;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import pl.wavesoftware.wfirma.api.mapper.Api;

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
        JaxbMarshaller<T> marshaller = new JaxbMarshaller<>(cls);
        return marshaller;
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

    private JaxbMarshaller(Class<Type> cls) {
        this.type = cls;
    }

    private JAXBContext getContext() {
        try {
            final Package pack = type.getPackage();
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();

            Set<Class<? extends Object>> classes = new HashSet<>();
            for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
                if (info.getName().startsWith(pack.getName()) && !info.getName().endsWith("Test")) {
                    classes.add(info.load());
                }
            }
            Class<?>[] arr = new Class<?>[classes.size()];
            classes.toArray(arr);
            return JAXBContext.newInstance(arr);
        } catch (JAXBException | IOException ex) {
            throw new RuntimeException(ex);
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
            throw new IllegalStateException(ex);
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
            throw new IllegalStateException(ex);
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
            throw new RuntimeException(ex);
        }
    }

    private String format(String input) {
        XmlCustomFormatter formatter = getFormatter();
        return (formatter != null) ? formatter.format(input) : input;
    }

    private String unformat(String input) {
        XmlCustomFormatter formatter = getFormatter();
        return (formatter != null) ? formatter.unformat(input) : input;
    }

}
