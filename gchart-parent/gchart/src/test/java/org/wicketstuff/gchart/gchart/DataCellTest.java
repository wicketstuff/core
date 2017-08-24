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

import org.wicketstuff.gchart.TimeOfDay;
import org.wicketstuff.gchart.DataCell;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dieter Tremel
 */
public class DataCellTest {

    public DataCellTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of toJSON method, of class DataCell.
     */
    @Test
    public void testToJSON() {
        DataCell instance = new DataCell(2.6, "2.60");
        instance.getProperties().put("style", "color: blue;");
        // {"v":2.6,"f":"2.60","p":{"style":"color: blue;"}}
        String expResult = "{\"v\":2.6,\"f\":\"2.60\",\"p\":{\"style\":\"color: blue;\"}}";
        String result = instance.toJSON().toString();
//        System.out.println(result);
        assertEquals(expResult, result);

        GregorianCalendar cal = new GregorianCalendar(2017, 6, 20, 10, 31, 20);
        instance = new DataCell(cal);
        // expResult = "{\"v\":new Date(2017, 6, 20, 10, 31, 20)}"; // {"v":new Date(2017, 6, 20, 10, 31, 20, 0)}
        expResult = "{\"v\":new Date(1500539480000)}";
        result = instance.toJSON().toString();
//        System.out.println(result);
        assertEquals(expResult, result);
        
        instance = new DataCell(cal.getTime());
//        expResult = "{\"v\":new Date(2017, 6, 20, 10, 31, 20)}"; // {"v":new Date(2017, 6, 20, 10, 31, 20, 0)}
        expResult = "{\"v\":new Date(1500539480000)}";
        result = instance.toJSON().toString();
//        System.out.println(result);
        assertEquals(expResult, result);
        
        instance = new DataCell(true);
        expResult = "{\"v\":\"true\"}";
        result = instance.toJSON().toString();
//        System.out.println(result);
        assertEquals(expResult, result);
        
        instance = new DataCell(new TimeOfDay(20, 15, 14));
        expResult = "{\"v\":[20,15,14]}";
        result = instance.toJSON().toString();
//        System.out.println(result);
        assertEquals(expResult, result);
    }

}
