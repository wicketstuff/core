package org.wicketstuff.yui.helper;

import junit.framework.TestCase;

public class CSSInlineStyleTest extends TestCase
{

	public CSSInlineStyleTest(String arg0)
	{
		super(arg0);
	}
	
    public void testInlineStyle() throws Exception
    {
    	CSSInlineStyle jso = new CSSInlineStyle()
    	.add("color", "#FFFFFF")
    	.add("padding", "2px");
    	
    	assertEquals("color:#FFFFFF;padding:2px;", jso.toString());

    }
    
}
