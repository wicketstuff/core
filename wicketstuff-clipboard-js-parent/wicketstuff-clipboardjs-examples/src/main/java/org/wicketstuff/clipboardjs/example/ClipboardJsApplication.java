package org.wicketstuff.clipboardjs.example;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class ClipboardJsApplication extends WebApplication {
	@Override
	public Class<? extends Page> getHomePage()
	{
		return ClipboardJsDemoPage.class;
	}
}
