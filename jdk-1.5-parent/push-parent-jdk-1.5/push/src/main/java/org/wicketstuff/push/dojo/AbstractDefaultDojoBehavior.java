/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.wicketstuff.push.dojo;

import org.apache.wicket.Application;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;

/**
 * Handles event requests using Dojo.
 * <p>
 * This class is mainly here to automatically add the javascript files you need.
 * As header contributions are done once per class, you can have multiple
 * instances/ subclasses without having duplicate header contributions.
 * </p>
 * <p>
 * this class use {@link AjaxRequestTarget} to respond to XMLHttpRequest
 * </p>
 * <p>
 * this behavior can work with a {@link DojoIndicatorBehavior} to set up an
 * Indicator when a request has been sent and waiting for the response. This
 * Behavior auto manage Indicator.
 * </p>
 * <p>
 * By default this behavior will use the package dojo distributiuon included in
 * this jar. If you want to use an other Dojo Distribution (A custom one to fit
 * to your need), You should write the following code in your
 * {@link Application} to use a custom {@link CompressedResourceReference}
 * 
 * <pre>
 *  CompressedResourceReference myCustomDojo = new CompressedResourceReference([...]);
 *  setMetaData(AbstractDefaultDojoBehavior.USE_CUSTOM_DOJO_DIST, myCustomDojo);
 * </pre>
 * 
 * <b>WARNING</b> : the package dojo distribution contains some patches on dojo.
 * If you use your own distribution it can break some component behaviors.
 * </p>
 * 
 * @see <a href="http://dojotoolkit.org/">Dojo< /a>
 * @author Eelco Hillenius
 */
public abstract class AbstractDefaultDojoBehavior extends
		AbstractDefaultAjaxBehavior {
	
	private static final long serialVersionUID = 1L;

	/**
	 * a Unique key to know if a CompressedResourceReference is set by the user
	 * in order to use a custom dojo distribution
	 */
	public static final MetaDataKey<CompressedResourceReference> USE_CUSTOM_DOJO_DIST = new MetaDataKey<CompressedResourceReference>() {
		private static final long serialVersionUID = 1L;
	};

	/** reference to the dojo support javascript file. */
	public static final ResourceReference DOJO = new CompressedResourceReference(
			AbstractDefaultDojoBehavior.class, "dojo/dojo.js");

	/** A unique ID for the JavaScript Dojo config script */
	private static final String COMETD_DOJO_CONFIG_ID = AbstractDefaultDojoBehavior.class
			.getName() + "::debug";

	/**
	 * @see wicket.ajax.AbstractDefaultAjaxBehavior#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);

		// Dojo configuration
		final StringBuffer dojoConfig = new StringBuffer();
		dojoConfig.append("var djConfig = {};\n");

		// enable dojo debug if our configuration type is DEVELOPMENT
		final String configurationType = Application.get()
				.getConfigurationType();
		if (configurationType.equalsIgnoreCase(Application.DEVELOPMENT)) {
			dojoConfig.append("djConfig.isDebug = true;\n");
			dojoConfig.append("djConfig.parseOnLoad = true;\n");
		} else {
			dojoConfig.append("djConfig.isDebug = false;\n");
		}

		// render dojo config
		response.renderJavascript(dojoConfig.toString(), COMETD_DOJO_CONFIG_ID);

		response.renderJavascriptReference(getDojoResourceReference());
	}

	/**
	 * Get the reference to the Dojo scripts.
	 * 
	 * @return
	 */
	public ResourceReference getDojoResourceReference() {
		if (Application.get().getMetaData(USE_CUSTOM_DOJO_DIST) == null
				|| !(Application.get().getMetaData(USE_CUSTOM_DOJO_DIST) instanceof CompressedResourceReference)) {
			return DOJO;
		} else {
			return Application.get().getMetaData(USE_CUSTOM_DOJO_DIST);
		}
	}

}
