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
package org.wicketstuff.gchart.gchart.options;

import org.apache.wicket.ajax.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static net.javacrumbs.jsonunit.JsonAssert.*;


/**
 *
 * @author Dieter Tremel
 */
public class ChartOptionsTest {
    
    public ChartOptionsTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class ChartOptions.
     */
    @Test
    public void testGetName() {
        ChartOptions instance = new ChartOptions("fred");
        String expResult = "fred";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of toJavaScript method, of class ChartOptions.
     */
    @Test
    public void testToJavaScript() {
        String varPrefix = "var options = ";
        
    	ChartOptions opts = new ChartOptions("options");
        opts.put("title", "How Much Pizza I Ate Last Night");
        opts.put("width", 400);
        opts.put("height", 300);
        
        String expResult = "var options = {\"width\":400,\"title\":\"How Much Pizza I Ate Last Night\",\"height\":300};\n";
        String result = opts.toJavaScript();
        
        assertTrue(result.startsWith(varPrefix));
        assertJsonEquals(expResult.replace(varPrefix, "").replace(';', '\n'), 
        				 result.replaceAll(varPrefix, "").replace(';', '\n'));
    }

    /**
     * Test of fromJson method, of class ChartOptions.
     */
    @Test
    public void testFromJson_String() {
        String json = "{'title':'Header'}";
        ChartOptions expResult = new ChartOptions();
        expResult.put("title", "Header");
        ChartOptions result = ChartOptions.fromJson(json);
        assertEquals(expResult, result);
    }

    /**
     * Test of fromJson method, of class ChartOptions.
     */
    @Test
    public void testFromJson_JSONObject() {
        JSONObject json = new JSONObject("{'title':'Header',hAxis: { ticks: [5,10,15,20] }}");
        ChartOptions result = ChartOptions.fromJson(json);
        assertEquals("[5,10,15,20]", ((ChartOptions) result.get("hAxis")).get("ticks").toString());
    }

    /**
     * Test of setName method, of class ChartOptions.
     */
    @Test
    public void testSetName() {
        String name = "fred";
        ChartOptions instance = new ChartOptions();
        instance.setName(name);
        assertEquals(name, instance.getName());
    }

    /**
     * Test of toJSON method, of class ChartOptions.
     */
    @Test
    public void testToJSON() {
        ChartOptions instance = new ChartOptions();
        instance.put("title", "Header");
        JSONObject expResult = new JSONObject("{'title':'Header'}");
        JSONObject result = instance.toJSON();
        assertEquals(expResult.toString(), result.toString());
    }
    
}
