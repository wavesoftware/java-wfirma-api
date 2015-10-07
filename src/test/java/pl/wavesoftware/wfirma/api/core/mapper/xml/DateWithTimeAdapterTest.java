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
