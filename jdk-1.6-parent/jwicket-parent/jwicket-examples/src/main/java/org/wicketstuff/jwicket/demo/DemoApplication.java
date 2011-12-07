package org.wicketstuff.jwicket.demo;


import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;


public class DemoApplication extends WebApplication {

	public static final String globalEncoding     = "UTF-8";

	public boolean debug = false;

	public DemoApplication() {
		super();
	}


	public static DemoApplication get() {
		return (DemoApplication)org.apache.wicket.Application.get();
	}


	@Override
	public Class<? extends Page> getHomePage() {
		return TestPage.class;
	}






	@Override
	public void init() {
		getApplicationSettings().setPageExpiredErrorPage(getHomePage());


		if (debug) {
			getDebugSettings().setOutputMarkupContainerClassName(true);
		    getDebugSettings().setAjaxDebugModeEnabled(true);
		    getDebugSettings().setComponentUseCheck(true);

		    getMarkupSettings().setStripWicketTags(false);
		    getMarkupSettings().setStripComments(false);
		    getMarkupSettings().setCompressWhitespace(false);
			getResourceSettings().setThrowExceptionOnMissingResource(false);
		}
		else {
			getDebugSettings().setOutputMarkupContainerClassName(false);
		    getDebugSettings().setAjaxDebugModeEnabled(false);
		    getDebugSettings().setComponentUseCheck(false);
		    getDebugSettings().setLinePreciseReportingOnAddComponentEnabled(false);
		    getDebugSettings().setLinePreciseReportingOnNewComponentEnabled(false);

		    getMarkupSettings().setStripWicketTags(true);
		    getMarkupSettings().setStripComments(true);
		    getMarkupSettings().setCompressWhitespace(true);

		    getResourceSettings().setResourcePollFrequency(null);
		}
	    getMarkupSettings().setStripXmlDeclarationFromOutput(false);

	    getMarkupSettings().setDefaultMarkupEncoding(DemoApplication.globalEncoding);
	    getRequestCycleSettings().setResponseRequestEncoding(DemoApplication.globalEncoding);
	}

}
