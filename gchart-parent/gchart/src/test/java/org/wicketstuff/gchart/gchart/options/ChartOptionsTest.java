/*
 * Copyright 2017 Dieter Tremel.
 * http://www.tremel-computer.de
 * All rights, if not explicitly granted, reserved.
 */
package org.wicketstuff.gchart.gchart.options;

import org.apache.wicket.ajax.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
        System.out.println("getName");
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
        System.out.println("toJavaScript");
        ChartOptions opts = new ChartOptions("options");
        opts.put("title", "How Much Pizza I Ate Last Night");
        opts.put("width", 400);
        opts.put("height", 300);
        String expResult = "var options = {\"width\":400,\"title\":\"How Much Pizza I Ate Last Night\",\"height\":300};\n";
        String result = opts.toJavaScript();
        assertEquals(expResult, result);
    }

    /**
     * Test of fromJson method, of class ChartOptions.
     */
    @Test
    public void testFromJson_String() {
        System.out.println("fromJson");
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
        System.out.println("fromJson");
        JSONObject json = new JSONObject("{'title':'Header',hAxis: { ticks: [5,10,15,20] }}");
        ChartOptions result = ChartOptions.fromJson(json);
        assertEquals("[5,10,15,20]", ((ChartOptions) result.get("hAxis")).get("ticks").toString());
    }

    /**
     * Test of setName method, of class ChartOptions.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
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
        System.out.println("toJSON");
        ChartOptions instance = new ChartOptions();
        instance.put("title", "Header");
        JSONObject expResult = new JSONObject("{'title':'Header'}");
        JSONObject result = instance.toJSON();
        assertEquals(expResult.toString(), result.toString());
    }
    
}
