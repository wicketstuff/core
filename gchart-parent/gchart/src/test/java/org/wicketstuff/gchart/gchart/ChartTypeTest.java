/*
 * Copyright 2017 Dieter Tremel.
 * http://www.tremel-computer.de
 * All rights, if not explicitly granted, reserved.
 */
package org.wicketstuff.gchart.gchart;

import org.wicketstuff.gchart.ChartType;
import org.junit.Test;
import static org.junit.Assert.*;

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
        System.out.println("toJavaScript");
        ChartType instance = ChartType.BUBBLE;
        String expResult = "google.visualization." + "BubbleChart";
        String result = instance.toJavaScript();
        assertEquals(expResult, result);
    }
    
}
