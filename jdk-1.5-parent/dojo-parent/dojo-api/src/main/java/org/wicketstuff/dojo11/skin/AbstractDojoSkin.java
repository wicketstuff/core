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
package org.wicketstuff.dojo11.skin;

import java.net.URL;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.wicketstuff.dojo11.AbstractRequireDojoBehavior;
import org.wicketstuff.dojo11.skin.manager.DojoSkinManager;

/**
 * <p>
 * Abstract class to define a new Skin for dojo widget.
 * </p>
 * <p>
 * A dojo skin is associated with a set of html templates and css. Templates and
 * css should be in the same package as the skin class. If a widget css/template
 * is not defined, the default skin will be used.
 * </p>
 * <p>
 * Template and css should have the same name as the widget class.
 * </p>
 * 
 * <p>
 * For example, if you want to skin DojoFloatingPane you should write a new
 * class extending AbstractDojoSkin and provide two files:
 * <code>DojoFloatingPane.css</code> and <code>DojoFloatingPane.htm</code>.
 * </p>
 * 
 * <p>
 * Widgets without a custom skin defined in the package will be using the
 * default Dojo skin.
 * </p>
 * 
 * @see DojoSkinManager to know how to use a skin.
 * 
 * @author Vincent Demay
 */
public abstract class AbstractDojoSkin {

	protected abstract Class<?> getResourceClass();

	/**
	 * Check if the file exists
	 * 
	 * @param file
	 *            file path
	 * @return true if the file exist and false otherwise
	 */
	private final boolean exists(String file) {
		URL res = getResourceClass().getClassLoader().getResource(
				getResourceClass().getPackage().getName().replace('.', '/') + '/' + file);
		return res != null;
	}

	/**
	 * Get the css to used for the widget is this file exist otherwise return
	 * null
	 * 
	 * @param component
	 *            Component to skin
	 * @param behavior
	 *            Dojo component behavior
	 * @return the css to used for the widget is this file exist otherwise
	 *         return null
	 */
	public final String getTemplateCssPath(Component component, AbstractRequireDojoBehavior behavior) {
		String cssTemplate = getClassName(behavior.getClass().getName()).replaceAll("Handler", "") + ".css";
		if (exists(cssTemplate)) {
			return (String) component.urlFor(new ResourceReference(getResourceClass(), cssTemplate));
		} else {
			return null;
		}
	}

	/**
	 * Get the htm to used for the widget is this file exist otherwise return
	 * null
	 * 
	 * @param component
	 *            Component to skin
	 * @param behavior
	 *            Dojo component behavior
	 * @return the htm to used for the widget is this file exist otherwise
	 *         return null
	 */
	public final String getTemplateHtmlPath(Component component, AbstractRequireDojoBehavior behavior) {
		String htmlTemplate = getClassName(behavior.getClass().getName().replaceAll("Handler", "")) + ".htm";
		if (exists(htmlTemplate)) {
			return (String) component.urlFor(new ResourceReference(getResourceClass(), htmlTemplate));
		} else {
			return null;
		}
	}

	/**
	 * Return the name that should be used to find templates
	 * 
	 * @param clazz
	 * @return
	 */
	private final String getClassName(String clazz) {
		return clazz.substring(clazz.lastIndexOf(".") + 1, clazz.length());
	}
}
