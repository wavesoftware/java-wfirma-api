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

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class WFirmaException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception with the specified detail message. The cause is not initialized, and may subsequently
     * be initialized by a call to {@link #initCause}.
     *
     * @param format the format of the detail message. The detail message is saved for later retrieval by the
     * {@link #getMessage()}
     * @param params a params to be embeded into a format message     * method.
     */
    public WFirmaException(String format, Object... params) {
        super(String.format(format, params));
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * <p>
     * Note that the detail message associated with {@code cause} is <i>not</i> automatically incorporated in this
     * exception's detail message.
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
     * value is permitted, and indicates that the cause is nonexistent or unknown.)
     * @param format the format of the detail message. The detail message is saved for later retrieval by the
     * {@link #getMessage()}
     * @param params a params to be embeded into a format message method.
     */
    public WFirmaException(Throwable cause, String format, Object... params) {
        super(String.format(format, params), cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message of <tt>(cause==null ? null :
     * cause.toString())</tt> (which typically contains the class and detail message of <tt>cause</tt>). This
     * constructor is useful for exceptions that are little more than wrappers for other throwables (for example, {@link
     * java.security.PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
     * value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public WFirmaException(Throwable cause) {
        super(cause);
    }

}
