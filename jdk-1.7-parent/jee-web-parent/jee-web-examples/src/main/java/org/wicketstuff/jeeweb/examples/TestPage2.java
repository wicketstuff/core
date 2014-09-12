package org.wicketstuff.jeeweb.examples;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.cycle.RequestCycle;

public class TestPage2 extends WebPage{

    private static final long serialVersionUID = -4216634299208537562L;
    
    public TestPage2() {
	String hiddenvalue = RequestCycle.get().getRequest()
		.getPostParameters().getParameterValue("hiddenparam")
		.toString();
	if (hiddenvalue != null) {
	    System.err.println(hiddenvalue);
	}
    }

}
