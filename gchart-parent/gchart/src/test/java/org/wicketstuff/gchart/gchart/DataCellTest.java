/*
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
package org.wicketstuff.gchart.gchart;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wicketstuff.gchart.DataCell;
import org.wicketstuff.gchart.TimeOfDay;

/**
 *
 * @author Dieter Tremel
 */
public class DataCellTest {

    public DataCellTest() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of toJSON method, of class DataCell.
     */
    @Test
    public void testToJSON() {
        DataCell instance = new DataCell(2.6, "2.60");
        instance.getProperties().put("style", "color: blue;");
        String expResult = "{\"v\":2.6,\"f\":\"2.60\",\"p\":{\"style\":\"color: blue;\"}}";
        String result = instance.toJSON().toString();
        assertEquals(expResult, result);

        GregorianCalendar cal = new GregorianCalendar(2017, 6, 20, 10, 31, 20);
        cal.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        instance = new DataCell(cal);
        expResult = "{\"v\":new Date(1500539480000)}";
        result = instance.toJSON().toString();
        assertEquals(expResult, result);

        instance = new DataCell(cal.getTime());
        expResult = "{\"v\":new Date(1500539480000)}";
        result = instance.toJSON().toString();
        assertEquals(expResult, result);

        instance = new DataCell(true);
        expResult = "{\"v\":\"true\"}";
        result = instance.toJSON().toString();
        assertEquals(expResult, result);

        instance = new DataCell(new TimeOfDay(20, 15, 14));
        expResult = "{\"v\":[20,15,14]}";
        result = instance.toJSON().toString();
        assertEquals(expResult, result);
    }

}
