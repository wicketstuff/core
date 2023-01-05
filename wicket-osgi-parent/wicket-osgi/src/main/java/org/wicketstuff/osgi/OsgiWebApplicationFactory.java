package org.wicketstuff.osgi;

import jakarta.servlet.ServletContext;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.osgi.framework.BundleContext;
import org.wicketstuff.osgi.util.OsgiServiceLookup;

/**
 * An {@link IWebApplicationFactory} which looks up a {@link WebApplication} from the OSGi service
 * registry.
 * <p>
 * In an OSGi context, classes from client bundles cannot be loaded by name in general. This factory
 * loads a {@code WebApplication} service from the OSGi service registry, using a property with key
 * {@link #APPLICATION_NAME_KEY} for disambiguation.
 * <p>
 * To bootstrap your Wicket Application when running under OSGi, configure the {@link WicketFilter}
 * in your {@code web.xml} deployment descriptor as follows:
 *
 * <pre>
 *   &lt;filter&gt;
 *     &lt;filter-name&gt;Wicket&lt;/filter-name&gt;
 *     &lt;filter-class&gt;org.apache.wicket.protocol.http.WicketFilter&lt;/filter-class&gt;
 *     &lt;init-param&gt;
 *       &lt;param-name&gt;applicationFactoryClassName&lt;/param-name&gt;
 *       &lt;param-value&gt;org.wicketstuff.osgi.OsgiWebApplicationFactory&lt;/param-value&gt;
 *     &lt;/init-param&gt;
 *     &lt;init-param&gt;
 *       &lt;param-name&gt;wicket.osgi.application.name&lt;/param-name&gt;
 *       &lt;param-value&gt;someUniqueApplicationName&lt;/param-value&gt;
 *     &lt;/init-param&gt;
 *   &lt;/filter&gt;
 * </pre>
 *
 * The implementation uses a ServiceTracker waiting for a given period for the service to become
 * available.
 * <p>
 * You need to register your {@code WebApplication} class in the OSGi service registry by any method
 * of your choice, e.g. programmatically in a {@code BundleActivator}, or using Declarative Services
 * or Blueprint.
 *
 * @author Harald Wellmann
 *
 */
public class OsgiWebApplicationFactory implements IWebApplicationFactory
{

	public static final String APPLICATION_NAME_KEY = "wicket.osgi.application.name";

	@Override
	public WebApplication createApplication(WicketFilter filter)
	{
		ServletContext servletContext = filter.getFilterConfig().getServletContext();
		BundleContext bundleContext = (BundleContext)servletContext.getAttribute("osgi-bundlecontext");

		String appName = filter.getFilterConfig().getInitParameter(APPLICATION_NAME_KEY);
		Map<String, String> props = new LinkedHashMap<String, String>(1);
		props.put(APPLICATION_NAME_KEY, appName);

		WebApplication webApplication = OsgiServiceLookup.getOsgiService(bundleContext,
			WebApplication.class, props);
		return webApplication;
	}

	@Override
	public void destroy(WicketFilter filter)
	{
		// service reference is released automatically when the WAB is stopped
	}
}
