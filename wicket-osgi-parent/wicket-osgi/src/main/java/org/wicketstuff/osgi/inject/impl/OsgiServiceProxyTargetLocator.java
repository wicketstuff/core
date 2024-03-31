package org.wicketstuff.osgi.inject.impl;

import jakarta.servlet.ServletContext;

import org.apache.wicket.Application;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.proxy.IProxyTargetLocator;
import org.osgi.framework.BundleContext;
import org.wicketstuff.osgi.util.OsgiServiceLookup;

/**
 * 
 * @author Harald Wellmann
 * 
 */
public class OsgiServiceProxyTargetLocator implements IProxyTargetLocator
{

	private static final long serialVersionUID = 1L;
	private String className;

	public OsgiServiceProxyTargetLocator(String className)
	{
		this.className = className;
	}

	public Object locateProxyTarget()
	{
		WebApplication application = (WebApplication)Application.get();
		ServletContext servletContext = application.getServletContext();
		BundleContext context = (BundleContext)servletContext.getAttribute("osgi-bundlecontext");
		return OsgiServiceLookup.getOsgiService(context, className);
	}
}
