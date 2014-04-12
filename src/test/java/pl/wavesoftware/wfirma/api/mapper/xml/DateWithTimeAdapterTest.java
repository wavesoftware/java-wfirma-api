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
public class DateWithTimeAdapterTest {

    /**
     * Test of marshal method, of class DateAdapter.
     */
    @Test
    public void testMarshal() {
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(2014, 4, 11, 12, 13, 14);
        Date date = cal.getTime();
        DateWithTimeAdapter instance = new DateWithTimeAdapter();
        String result = instance.marshal(date);
        assertThat(result).isEqualTo("2014-05-11 12:13:14");
    }

    /**
     * Test of unmarshal method, of class DateAdapter.
     *
     * @throws java.text.ParseException
     */
    @Test
    public void testUnmarshal() throws ParseException {
        String input = "2014-02-09 16:05:15";
        DateWithTimeAdapter instance = new DateWithTimeAdapter();
        Date result = instance.unmarshal(input);
        Calendar cal = new GregorianCalendar();
        cal.setTime(result);
        assertThat(cal.get(Calendar.DAY_OF_MONTH)).isEqualTo(9);
        assertThat(cal.get(Calendar.MONTH)).isEqualTo(1);
        assertThat(cal.get(Calendar.YEAR)).isEqualTo(2014);
        assertThat(cal.get(Calendar.HOUR_OF_DAY)).isEqualTo(16);
        assertThat(cal.get(Calendar.MINUTE)).isEqualTo(5);
        assertThat(cal.get(Calendar.SECOND)).isEqualTo(15);
    }

}
