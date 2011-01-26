/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.dojo11.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.dojo11.AbstractDefaultDojoBehavior;
import org.wicketstuff.dojo11.AbstractDefaultDojoBehavior.DojoModule;
import org.wicketstuff.dojo11.skin.manager.DojoSkinManager;

/**
 * @author Stefan Fussenegger
 */
public class DojoSettings implements IDojoSettings
{

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(DojoSettings.class);
	
	private Application _application;
	
	private String _dojoRelease;
	
	private DojoSkinManager _dojoSkinManager;
	
	private HashMap<String, DojoLayer> _dojoLayerResourceReferences = new HashMap<String, DojoLayer>();

	private HashMap<DojoLayer, List<ResourceReference>> _dependenciesCache = new HashMap<DojoLayer, List<ResourceReference>>();

	private List<DojoModule> _dojoModules = new LinkedList<DojoModule>();

	private boolean _registerDefaultModules;

	private String _dojoBaseUrl;
	
	/**
	 * @return IDojoSettingis of current IDojoApplication
	 * @see Application#get()
	 */
	public static IDojoSettings get() {
		return ((IDojoApplication) Application.get()).getDojoSettings();
	}
	
	/**
	 * 
	 * Construct.
	 * @param application
	 */
	public DojoSettings(Application application) {
		_application = application;
	}

	/**
	 * configure defaults
	 * @return this
	 */
	public DojoSettings configure() {
		try {
			_application.getSharedResources().putClassAlias(AbstractDefaultDojoBehavior.class, "dojo");
			Properties dojoProperties = new Properties();
			dojoProperties.load(ClassLoader.getSystemResourceAsStream("org/wicketstuff/dojo11/wicketstuff-dojo.properties"));
			_dojoRelease = dojoProperties.getProperty("org.wicketstuff.dojo.release");
			if (Strings.isEmpty(_dojoRelease)) {
				throw new IllegalArgumentException("not a valid dojo release: "+_dojoRelease);
			}
			_dojoSkinManager = newDojoSkinManager();
			_dojoBaseUrl = "/resources/dojo/"+getDojoPath()+"/dojo/";
			return this;
		} catch (Throwable t) {
			throw new WicketRuntimeException(t);
		}
	}
	
	/**
	 * default implementation that uses dojo.js in deployment mode, dojo.js.uncompressed.js in development mode
	 * @param layer layer name, which is a path relative to dojo
	 * 
	 * @return ResourceReference for dojo.js file
	 */
	protected ResourceReference newDojoResourceReference(String layer)
	{
		if (layer == null) {
			throw new NullPointerException("layer");
		}
		
		StringBuilder path = new StringBuilder(getDojoPath()).append("/");
		if (layer.startsWith("../")) {
			layer = layer.substring(3);
		} else {
			path.append("dojo/");
		}
		
		if (layer.startsWith("../")) {
			throw new IllegalArgumentException("layer must only start with a single '../' but was, "+layer );
		}
		
		path.append(layer);
		if (Application.DEVELOPMENT.equals(_application.getConfigurationType())) {
			path.append(".uncompressed.js");
		}
		
		return new CompressedResourceReference(AbstractDefaultDojoBehavior.class, path.toString());
	}

	protected DojoSkinManager newDojoSkinManager() {
		return new DojoSkinManager();
	}
	
	/**
	 * @see org.wicketstuff.dojo11.application.IDojoSettings#getDojoResourceReferences()
	 */
	public List<ResourceReference> getDojoResourceReferences()
	{
		Page page = RequestCycle.get().getResponsePage();
		IDojoApplication app = DojoApplication.getDojoApplication();
		
		String layer = null;
		if (app != null && page instanceof WebPage) {
			layer = app.getLayer((WebPage)page);
		}
		DojoLayer dojoLayer = getLayer(layer);
		
		List<ResourceReference> refsWithDependencies = _dependenciesCache.get(dojoLayer);
		
		if (refsWithDependencies == null) {
			refsWithDependencies = resolveDependencies(dojoLayer);
			_dependenciesCache.put(dojoLayer, refsWithDependencies);
		}
		return refsWithDependencies;
	}

	/**
	 * @see org.wicketstuff.dojo11.application.IDojoSettings#getDojoModules()
	 */
	public List<DojoModule> getDojoModules()
	{
		LinkedList<DojoModule> modules = new LinkedList<DojoModule>();
		modules.addAll(_dojoModules);
		return modules;
	}

	/**
	 * @param namespace 
	 * @param scope 
	 */
	public void addDojoModule(String namespace, Class<?> scope) {
		_dojoModules.add(new AbstractDefaultDojoBehavior.DojoModuleImpl(namespace, scope));
	}
	
	/**
	 * @param layer
	 * @return dojo layer for the given name
	 */
	public DojoLayer getLayer(String layer)
	{
		if (layer == null) layer = IDojoApplication.DEFAULT_LAYER;
		
		DojoLayer dojoLayer = _dojoLayerResourceReferences.get(layer);
		if (dojoLayer == null) {			
			ResourceReference ref = newDojoResourceReference(layer);
			if (layer.equals(IDojoApplication.DEFAULT_LAYER)) {
				log.info("creating default dojo layer: "+layer);
				dojoLayer = new DojoLayer(layer, ref);
			} else {
				log.info("creating new dojo layer with dependency on default layer: "+layer);
				dojoLayer = new DojoLayer(layer, ref, IDojoApplication.DEFAULT_LAYER);
			}
			_dojoLayerResourceReferences.put(layer, dojoLayer);
		}
		return dojoLayer;
	}	
	
	/**
	 * @see org.wicketstuff.dojo11.application.IDojoSettings#addDojoLayer(java.lang.String, java.lang.String...)
	 */
	public void addDojoLayer(String dependentLayer, String ... layerDependencies)
	{
		if (log.isInfoEnabled()) {
			if (layerDependencies.length == 0) {
				log.info("creating base dojo layer: "+dependentLayer);
			} else {
				log.info("creating dojo layer: "+dependentLayer + " depending on "+layerDependencies);
			}
		}
		ResourceReference ref = newDojoResourceReference(dependentLayer);
		DojoLayer dojoLayer = new DojoLayer(dependentLayer, ref, layerDependencies);
		_dojoLayerResourceReferences.put(dependentLayer, dojoLayer);
	}

	/**
	 * @param dojoLayer
	 * @return
	 */
	private List<ResourceReference> resolveDependencies(DojoLayer dojoLayer)
	{
		LinkedList<DojoLayer> unresolved = new LinkedList<DojoLayer>();
		HashSet<DojoLayer> resolved = new HashSet<DojoLayer>();
		unresolved.add(dojoLayer);
		
		do {
			DojoLayer layer = unresolved.remove();
			if (resolved.add(layer)) {
				for (String layerDependency : layer.geLayerDependencies()) {
					unresolved.add(getLayer(layerDependency));
				}
			}
			
		} while (unresolved.size() > 0);
		
		List<ResourceReference> refs = new ArrayList<ResourceReference>(resolved.size());
		int i = 0;
		for (DojoLayer layer : resolved) {
			refs.add(layer.getResourceReference());
		}
		// FIXME resolve dependencies in correct order
		Collections.reverse(refs);
		return refs;
	}

	/**
	 * @see org.wicketstuff.dojo11.application.IDojoSettings#getDojoSkinManager()
	 */
	public DojoSkinManager getDojoSkinManager()
	{
		return _dojoSkinManager;
	}

	/**
	 * @return application
	 */
	public Application getApplication()
	{
		return _application;
	}

	/**
	 * @return dojo release version
	 */
	public String getDojoRelease()
	{
		return _dojoRelease;
	}

	/**
	 * @see org.wicketstuff.dojo11.application.IDojoSettings#getDojoPath()
	 */
	public String getDojoPath()
	{
		return "dojo-"+_dojoRelease;
	}

	/**
	 * @see org.wicketstuff.dojo11.application.IDojoSettings#setDojoBaseUrl(String)
	 */
	public void setDojoBaseUrl(String dojoBaseUrl)
	{
		if (!dojoBaseUrl.endsWith("/")) {
			dojoBaseUrl += "/";
		}
		_dojoBaseUrl = dojoBaseUrl;
	}

	/**
	 * @see org.wicketstuff.dojo11.application.IDojoSettings#getDojoBaseUrl()
	 */
	public String getDojoBaseUrl()
	{
		return _dojoBaseUrl;
	}

	/**
	 * @see org.wicketstuff.dojo11.application.IDojoSettings#getDefaultLocale()
	 */
	public Locale getDefaultLocale()
	{
		return Session.get().getLocale();
	}
	
	
	
}
