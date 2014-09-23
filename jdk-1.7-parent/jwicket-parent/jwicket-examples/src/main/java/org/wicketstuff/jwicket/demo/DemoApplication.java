package org.wicketstuff.jwicket.demo;

import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.jwicket.demo.tooltip.JQueryUiTooltipPage;

public class DemoApplication extends WebApplication
{

	public static final String globalEncoding = "UTF-8";

	private boolean development;

	public DemoApplication()
	{
		super();
		this.setDevelopment(true);
	}

	@Override
	public Class<? extends Page> getHomePage()
	{
		return TestPage.class;
	}

	@Override
	public void init()
	{
		getApplicationSettings().setPageExpiredErrorPage(getHomePage());

		getMarkupSettings().setDefaultMarkupEncoding(DemoApplication.globalEncoding);
		getRequestCycleSettings().setResponseRequestEncoding(DemoApplication.globalEncoding);

		mountPage("jqueryuitooltip", JQueryUiTooltipPage.class);
	}

    @Override
	public RuntimeConfigurationType getConfigurationType()
	{
		return isDevelopment() ? RuntimeConfigurationType.DEVELOPMENT
			: RuntimeConfigurationType.DEPLOYMENT;
	}

	private boolean isDevelopment()
	{
		return this.development;
	}

	private void setDevelopment(boolean development)
	{
		this.development = development;
	}
}
