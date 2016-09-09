package org.apache.wicket.portlet;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.xml.namespace.QName;

/**
 * PortletConfig implementation which enables action-scoped request attributes support (see JSR286 specification PLT.10.4.4).
 * It fixes the issue "WicketPortlet attempts to modify immutable map #540"
 * 
 * @author Konstantinos Karavitis
 *
 */
public class WicketPortletConfig implements PortletConfig {
	
	private PortletConfig delegate;
	private Map<String, String[]> containerRuntimeOptions;
	
	public WicketPortletConfig(PortletConfig config) {
		delegate = config;
		
		Map<String, String[]> map = new HashMap<String, String[]>();
		//config.getContainerRuntimeOptions() returns an immutable map (see JSR286 specification PLT.6.8 ).
		for(Map.Entry<String, String[]> entry : config.getContainerRuntimeOptions().entrySet()) {
			map.put(entry.getKey(), entry.getValue());
		}
		//enable action-scoped request attributes support (see JSR286 specification PLT.10.4.4)
		map.put("javax.portlet.actionScopedRequestAttributes",
				new String[] { "true", "numberOfCachedScopes", "10" });
		
		//make the containerRuntimeOptions map an immutable map as the specification requires (see JSR286 specification PLT.6.8 ).
		containerRuntimeOptions = Collections.unmodifiableMap(map);
	}

	@Override
	public String getPortletName() {
		return delegate.getPortletName();
	}

	@Override
	public PortletContext getPortletContext() {
		return delegate.getPortletContext();
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return delegate.getResourceBundle(locale);
	}

	@Override
	public String getInitParameter(String name) {
		return delegate.getInitParameter(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return delegate.getInitParameterNames();
	}

	@Override
	public Enumeration<String> getPublicRenderParameterNames() {
		return delegate.getPublicRenderParameterNames();
	}

	@Override
	public String getDefaultNamespace() {
		return delegate.getDefaultNamespace();
	}

	@Override
	public Enumeration<QName> getPublishingEventQNames() {
		return delegate.getPublishingEventQNames();
	}

	@Override
	public Enumeration<QName> getProcessingEventQNames() {
		return delegate.getProcessingEventQNames();
	}

	@Override
	public Enumeration<Locale> getSupportedLocales() {
		return delegate.getSupportedLocales();
	}

	@Override
	public Map<String, String[]> getContainerRuntimeOptions() {
		return containerRuntimeOptions;
	}

}
