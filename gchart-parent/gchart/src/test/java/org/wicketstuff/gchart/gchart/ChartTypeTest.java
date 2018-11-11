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

import org.wicketstuff.gchart.ChartType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Dieter Tremel
 */
public class ChartTypeTest {
    

    /**
     * Test of toJavaScript method, of class ChartType.
     */
    @Test
    public void testToJavaScript() {
        ChartType instance = ChartType.BUBBLE;
        String expResult = "google.visualization." + "BubbleChart";
        String result = instance.toJavaScript();
        assertEquals(expResult, result);
    }
    
}
