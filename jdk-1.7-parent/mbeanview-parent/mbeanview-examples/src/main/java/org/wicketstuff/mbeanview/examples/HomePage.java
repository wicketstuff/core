package org.wicketstuff.mbeanview.examples;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.mbeanview.MBeansPanel;

/**
 * Homepage
 */
public class HomePage extends WebPage
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public HomePage(final PageParameters parameters)
	{
		IModel<MBeanServer> reachMbeanServer = new LoadableDetachableModel<MBeanServer>() {
			@Override
			protected MBeanServer load() {
				return ManagementFactory.getPlatformMBeanServer();
			}
		};

		add(new MBeansPanel("example", reachMbeanServer));
	}
}
