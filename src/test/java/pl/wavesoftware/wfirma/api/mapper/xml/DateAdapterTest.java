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
package pl.wavesoftware.wfirma.api.mapper.xml;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class DateAdapterTest {

    /**
     * Test of marshal method, of class DateAdapter.
     */
    @Test
    public void testMarshal() {
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(2014, 4, 11);
        Date date = cal.getTime();
        DateAdapter instance = new DateAdapter();
        String result = instance.marshal(date);
        assertThat(result).isEqualTo("2014-05-11");
    }

    /**
     * Test of unmarshal method, of class DateAdapter.
     *
     * @throws java.text.ParseException
     */
    @Test
    public void testUnmarshal() throws ParseException {
        String input = "2014-02-09";
        DateAdapter instance = new DateAdapter();
        Date result = instance.unmarshal(input);
        assertThat(result).isEqualTo("2014-02-09");
        Calendar cal = new GregorianCalendar();
        cal.setTime(result);
        assertThat(cal.get(Calendar.DAY_OF_MONTH)).isEqualTo(9);
        assertThat(cal.get(Calendar.MONTH)).isEqualTo(1);
        assertThat(cal.get(Calendar.YEAR)).isEqualTo(2014);
        assertThat(cal.get(Calendar.HOUR)).isEqualTo(0);
        assertThat(cal.get(Calendar.MINUTE)).isEqualTo(0);
        assertThat(cal.get(Calendar.SECOND)).isEqualTo(0);
    }

}