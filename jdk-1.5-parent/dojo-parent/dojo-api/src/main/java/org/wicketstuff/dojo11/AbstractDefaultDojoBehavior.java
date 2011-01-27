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
package org.wicketstuff.dojo11;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.util.collections.MiniMap;
import org.wicketstuff.dojo11.application.DojoSettings;
import org.wicketstuff.dojo11.application.IDojoApplication;
import org.wicketstuff.dojo11.application.IDojoSettings;
import org.wicketstuff.dojo11.templates.DojoPackagedTextTemplate;

/**
 * Handles event requests using Dojo.
 * <p>
 * This class is mainly here to automatically add the javascript files you need.
 * As header contributions are done once per class, you can have multiple
 * instances/ subclasses without having duplicate header contributions.
 * </p>
 * <p> this class use {@link AjaxRequestTarget} to respond to XMLHttpRequest </p>
 * 
 * <p>
 * By default this behavior will use the package dojo distributiuon included in this jar. If you want to use an other 
 * Dojo Distribution (A custom one to fit to your need), You should write the following code in your {@link Application} to
 * use a custom {@link CompressedResourceReference}
 * <pre>
 *  CompressedResourceReference myCustomDojo = new CompressedResourceReference([...]);
 * 	setMetaData(AbstractDefaultDojoBehavior.USE_CUSTOM_DOJO_DIST, myCustomDojo);
 * </pre>
 * <b>WARNING</b> : the package dojo distribution contains some patches on dojo. If you use your own
 * distribution it can break some component behaviors.
 * </p>
 * 
 * @see <a href="http://dojotoolkit.org/">Dojo</a>
 * @author Eelco Hillenius
 */
public abstract class AbstractDefaultDojoBehavior extends AbstractDefaultAjaxBehavior /*implements IAjaxIndicatorAware*/
{
	private static final long serialVersionUID = 1L;

	/** A unique ID for a piece of JavaScript that registers the wicketstuff dojo namespace */
	private static final String DOJO_MODULES_ID = AbstractDefaultDojoBehavior.class.getName() + "::modules::";
	
	/** The wicketstuff dojo module */
//	private static final DojoModule WICKETSTUFF_MODULE = new DojoModuleImpl("wicketstuff", AbstractDefaultDojoBehavior.class);
	
	/**
	 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		
		IDojoSettings dojoSettings = ((IDojoApplication)Application.get()).getDojoSettings();
		List<ResourceReference> dojoResourceReferences = dojoSettings.getDojoResourceReferences();
		
		renderConfig(response);
		
		for (ResourceReference r : dojoResourceReferences)
		{
			response.renderJavascriptReference(r);
		}

		registerDojoModulePathes(response, getDojoModules());
	}

	protected void renderConfig(IHeaderResponse response)
	{	
		DojoPackagedTextTemplate template = new DojoPackagedTextTemplate(AbstractDefaultDojoBehavior.class, AbstractDefaultDojoBehavior.class.getSimpleName()+".js");
		MiniMap map = new MiniMap(3);
		map.put("debug", Application.DEVELOPMENT.equals(Application.get().getConfigurationType()));
		map.put("baseUrl", DojoSettings.get().getDojoBaseUrl());
		map.put("locale", ((IDojoApplication)Application.get()).getDojoSettings().getDefaultLocale().toString().replace('_', '-'));
		response.renderJavascript(template.asString(map), template.getStaticKey());
	}
	
	/**
	 * Register a specific dojo module.
	 * @param response
	 * @param modules
	 */
	protected void registerDojoModulePathes(IHeaderResponse response, Collection<DojoModule> modules) {
		if (modules != null && modules.size() > 0) {
			StringBuilder buf = new StringBuilder();
			for (DojoModule module : modules) {
				registerDojoModulePath(buf, module);
			}
			response.renderJavascript(buf.toString(), DOJO_MODULES_ID + "module");
		}
	}
	
	private void registerDojoModulePath(StringBuilder javascript, DojoModule module) {
		ResourceReference dojoReference = getDojoResourceReference();
		String dojoUrl = RequestCycle.get().urlFor(dojoReference).toString();
		
		//FIXME : investigate on url resolving : http://81.17.46.170:8090/jira/browse/DOJO-67
		
		// count the depth to determine the relative path
		String url = "";
		
		int slash = 0;  // count /
		int last = 0;
		while (last > -1) {
			last = dojoUrl.indexOf("/", last + 1);
			if (last > -1) {
				slash ++;
			}
		}
		
		//count ../
		//each "../" should remove 2 "/" :
		//     - One because it is contained in "../"
		//     - An other because of the meaning of "../"
		last = 0;
		while (last > -1) {
			last = dojoUrl.indexOf("../", last);
			if (last > -1) {
				dojoUrl = dojoUrl.substring(last + "../".length());
				slash = slash - 2;
			}
		}
		
		for (int i=0; i < slash; i++){
			url += "../";
		}
		
		ResourceReference moduleReference = new ResourceReference(module.getScope(), module.getPath());
		String moduleUrl = RequestCycle.get().urlFor(moduleReference).toString();
		url = url + moduleUrl;
		//remove / at the end if exists
		if (url.charAt(url.length()-1) == '/'){
			url = url.substring(0, url.length() - 1);
		}
				
		javascript.append("dojo.registerModulePath(\"" + module.getNamespace() + "\", \"" + url + "\");\n");
	}
	
	/**
	 * XXX untested, using first ref might not work as expected
	 * @return reference to the Dojo scripts.
	 */
	public ResourceReference getDojoResourceReference() {
		return ((IDojoApplication)Application.get()).getDojoSettings().getDojoResourceReferences().get(0);
	}
	
//	/**
//	 * return the indicator Id to show it if it is in the page
//	 * @return the indicator Id to show it if it is in the page
//	 */
//	public String getAjaxIndicatorMarkupId()
//	{
//		return new WicketAjaxIndicatorAppender().getMarkupId();
//	}
//
//	/**
//	 * return the ajax call decorator to do more than hide or show an image
//	 * @return the ajax call decorator to do more than hide or show an image
//	 */
//	protected IAjaxCallDecorator getAjaxCallDecorator()
//	{
//		return new DojoIndicatorHandlerHelper(getComponent()).getAjaxCallDecorator();
//	}
	
	/**
	 * @return collection of modules that should be registered.
	 */
	protected final Collection<DojoModule> getDojoModules() {
		Collection<DojoModule> modules = new ArrayList<DojoModule>();
		modules.addAll(DojoSettings.get().getDojoModules());
		setDojoModules(modules);
		return modules;
	}
	
	/**
	 * Allow classes to override this and add their own modules.
	 * @param modules
	 */
	protected void setDojoModules(Collection<DojoModule> modules) {
//		modules.add(DOJO_MODULE);
//		modules.add(DIJIT_MODULE);
//		modules.add(DOJOX_MODULE);
		//modules.add(WICKETSTUFF_MODULE);
	}
	
	/**
	 * Provides information about a dojo module.
	 * 
	 * @author B. Molenkamp
	 */
	public static interface DojoModule {
		
		/**
		 * Returns the scope. This scope will be used to calculate the relative
		 * path to this module.
		 * 
		 * @return the scope
		 */
		public Class<?> getScope();
		
		/**
		 * Returns the namespace of this module.
		 * 
		 * @return the module's namespace
		 */
		public String getNamespace();
		
		/**
		 * Returns the path of this module starting from scope or null.
		 * 
		 * @return the module's path starting from scope
		 */
		public String getPath();
		
	}
	
	/**
	 * Abstract implementation of a dojo module. If the scope is not passed to
	 * the constructor, or if it's null, it will use it's own class as a scope.
	 * 
	 * @author B. Molenkamp
	 */
	public static class DojoModuleImpl implements DojoModule {

		private String moduleNamespace;
		private Class<?> scope;
		
		/**
		 * Creates a module with the scope of the implementing class.
		 * @param namespace
		 */
		public DojoModuleImpl(String namespace) {
			this(namespace, null);
		}
		
		/**
		 * Creates a module with the given namespace at the given scope. It will
		 * use the scope to resolve anything from the namespace.
		 * 
		 * @param namespace
		 * @param scope
		 */
		public DojoModuleImpl(String namespace, Class<?> scope) {
			this.moduleNamespace = namespace;
			this.scope = scope == null? AbstractDefaultDojoBehavior.class : scope;
		}
		
		/**
		 * @see org.wicketstuff.dojo11.AbstractDefaultDojoBehavior.DojoModule#getNamespace()
		 */
		public String getNamespace() {
			return this.moduleNamespace;
		}

		/**
		 * @see org.wicketstuff.dojo11.AbstractDefaultDojoBehavior.DojoModule#getScope()
		 */
		public Class< ? > getScope() {
			return this.scope;
		}

		/**
		 * @see org.wicketstuff.dojo11.AbstractDefaultDojoBehavior.DojoModule#getPath()
		 */
		public String getPath()
		{
			return "";
		}
		
		
		
	}
}
