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

import java.util.List;
import java.util.Locale;

import org.apache.wicket.Application;
import org.apache.wicket.IClusterable;
import org.apache.wicket.ResourceReference;
import org.wicketstuff.dojo11.AbstractDefaultDojoBehavior.DojoModule;
import org.wicketstuff.dojo11.skin.manager.DojoSkinManager;

/**
 * @author Stefan Fussenegger
 */
public interface IDojoSettings extends IClusterable
{
	/**
	 * @return dojo resource reference
	 */
	List<ResourceReference> getDojoResourceReferences();
	
	/**
	 * @return dojo modules
	 */
	List<DojoModule> getDojoModules(); 
	
	/**
	 * register a new dojo module which will always be available
	 * 
	 * @param namespace 
	 * @param scope 
	 */
	public void addDojoModule(String namespace, Class<?> scope);
	
	/**
	 * @param dojoBaseUrl
	 */
	void setDojoBaseUrl(String dojoBaseUrl);
	

	/**
	 * @return dojoBaseUrl
	 */
	String getDojoBaseUrl();

	/**
	 * @return the dojo skin manager
	 */
	DojoSkinManager getDojoSkinManager();
	
	/**
	 * @param name
	 * @return dojo layer with given name
	 */
	DojoLayer getLayer(String name);
	
	/**
	 * 
	 * @param dependentLayer 
	 * @param layerDependencies 
	 */
	public void addDojoLayer(String dependentLayer, String ... layerDependencies);
	
	/**
	 * @return wicket application
	 */
	Application getApplication();

	/**
	 * @return dojo release version
	 */
	String getDojoRelease();

	/**
	 * @return path from AbstractDefaultDojoBehavior to dojo root without trailing or leading slash
	 */
	String getDojoPath();

	/**
	 * @return the default locale to use
	 */
	Locale getDefaultLocale();
}
