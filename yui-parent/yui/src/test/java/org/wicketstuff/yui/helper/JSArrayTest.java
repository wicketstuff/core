package org.wicketstuff.yui.helper;


import junit.framework.TestCase;

public class JSArrayTest extends TestCase
{

	public JSArrayTest(String arg0)
	{
		super(arg0);
	}
	
	
    public void testJSArray() throws Exception
    {
    	Attributes yuiProperty = new Attributes()
    	.add("from", "0")
    	.add("to", "1");
       	
    	JSArray jso = new JSArray()
    	.add("'value1'")
    	.add("value2")
    	.add(yuiProperty);
    	
    	assertEquals("['value1',value2,{to:1,from:0}]", jso.toString());
    }
    
}
