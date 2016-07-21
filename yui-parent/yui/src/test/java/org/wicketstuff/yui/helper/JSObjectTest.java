package org.wicketstuff.yui.helper;

import junit.framework.TestCase;


public class JSObjectTest extends TestCase
{

	public JSObjectTest(String arg0)
	{
		super(arg0);
	}
	
    public void testJavascriptObject() throws Exception
    {
    	Attributes jso = new Attributes()
    	.add("to", "20")
    	.add("from", "0");
    	assertEquals("{to:20,from:0}", jso.toString());
    	
    	Attributes jso1 = new Attributes();
    	assertEquals("{}", jso1.toString());
    	
    	Attributes jso2 = new Attributes()
    	.add("name", "\"John Denver\"")
    	.add("age", "20");
    	
    	// test Yui Attributes and Properties
    	Attributes yuiProperty = new Attributes()
    	.add("from", "0")
    	.add("to", "1");
    	
    	Attributes ypHeight = new Attributes();
    	ypHeight.add("from", "0");
    	ypHeight.add("to", "1");
    	
    	Attributes yuiAttribute = new Attributes();
    	yuiAttribute.add("width", yuiProperty);
    	yuiAttribute.add("height", ypHeight);
    	
    	System.out.println(yuiAttribute.toString());
//    	assertEquals("{width:{to:1,from:0},height:{to:1,from:0}}",yuiAttribute.toString());
    }
}
