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
package org.wicketstuff.dojo11.skin.manager;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.wicketstuff.dojo11.AbstractRequireDojoBehavior;
import org.wicketstuff.dojo11.skin.AbstractDojoSkin;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebPage;

/**
 * <p>
 * SkinManager was a singleton class allowing user to set up a skin for all Dojo
 * component. When you setup a skin it will be used while you set up an other
 * skin.
 * </p>
 * <p>
 * To use a skin in a page you can for exemple call the following code in the
 * {@link WebPage} constructor
 * </p>
 * 
 * <pre>
 * SkinManager.getInstance().setupSkin(new WindowsDojoSkin());
 * </pre>
 * 
 * <p>
 * If the skin should be the same for all the application, use the same code in
 * the constructor of the {@link Application}
 * </p>
 * <p>
 * You also can remove skin using the following :
 * 
 * <pre>
 * SkinManager.getInstance().setupSkin(null);
 * </pre>. In this case default skin will be used
 * </p>
 * 
 * @author Vincent Demay
 */
public class DojoSkinManager {

	private AbstractDojoSkin skin;

	/**
	 *  constructor of the skinManager
	 */
	public DojoSkinManager() {

	}

	/**
	 * Set a new skin for the skin Manager
	 * 
	 * @param skin
	 *            skin to be used for all widgets
	 */
	public void setupSkin(AbstractDojoSkin skin) {
		this.skin = skin;
	}

	/**
	 * Used on {@link AbstractRequireDojoBehavior} to set the skin on a widget
	 * 
	 * @param component
	 *            component to skin
	 * @param behavior
	 *            {@link AbstractRequireDojoBehavior} used in thed component
	 * @param tag
	 *            {@link ComponentTag} of the Component
	 */
	public final void setSkinOnComponent(Component component, AbstractRequireDojoBehavior behavior, ComponentTag tag) {
		if (skin != null) {
			String templateCssPath = skin.getTemplateCssPath(component, behavior);
			String templateHtmlPath = skin.getTemplateHtmlPath(component, behavior);
			if (templateCssPath != null) {
				tag.put("templateCssPath", templateCssPath);
			}
			if (templateHtmlPath != null) {
				tag.put("templatePath", templateHtmlPath);
			}
		}
	}
	
	/**
	 * return the current used skin
	 * @return the current used skin
	 */
	public AbstractDojoSkin getSkin(){
		return skin;
	}
}
