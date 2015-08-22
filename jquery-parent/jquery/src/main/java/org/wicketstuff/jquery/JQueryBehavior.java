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
package org.wicketstuff.jquery;

import java.util.regex.Pattern;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.StringHeaderItem;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.JQueryResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author David Bernard (dwayne)
 * @created 2007-08-08
 */
// http://jquery.com/plugins for a list a jquery plugins
public class JQueryBehavior extends AbstractDefaultAjaxBehavior
{
	private static final long serialVersionUID = 1L;

	// create a reference to the base javascript file.
	// we use ResourceReference so that the included file will have
	// its comments stripped and gzipped.
	/**
	 * ResourceReference for <a href="http://jquery.com">jquery-1.2.6</a> (include by default when
	 * you add the current Behavior).
	 */
	// public static final ResourceReference JQUERY_JS = new
	// PackageResourceReference(
	// JQueryBehavior.class, "jquery.js");

	/**
	 * ResourceReference for <a href="http://jquery.glyphix.com/">jquery.debug.js</a> (include by
	 * default if wicket's configuration is "development")
	 */
	public static final ResourceReference JQUERY_DEBUG_JS = new PackageResourceReference(
		JQueryBehavior.class, "jquery.debug.js");

	/**
	 * ResourceReference for <a href="http://interface.eyecon.ro">interface-1.2.js</a> (not include
	 * in reponse header)
	 */
	public static final ResourceReference INTERFACE_JS = new PackageResourceReference(
		JQueryBehavior.class, "interface-1.2.js");

	/**
	 * ResourceReference for jquery.ui containing core, resizable, draggable, droppable, selectable
	 * <a href="http://ui.jquery.com">jquery-ui-personalized.js</a>
	 */
	public static final ResourceReference JQUERY_UI_JS = new PackageResourceReference(
		JQueryBehavior.class, "jquery-ui-personalized.js");

	/**
	 * ResourceReference for <a href="http://jquery.com/plugins/project/bgiframe"
	 * >jquery.bgiframe-2.1.1.js</a> (not include in reponse header)
	 */
	public static final ResourceReference JQUERY_BGIFRAME_JS = new PackageResourceReference(
		JQueryBehavior.class, "jquery.bgiframe-2.1.1.js");

	public static final Pattern JQUERY_REGEXP = Pattern.compile(
		".*\\<.*script.*src=\".*jquery.*\\.js\"\\>.*", Pattern.DOTALL);

	private transient Logger logger_;

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		try
		{
			super.renderHead(component, response);
			if (getIncludeJQueryJS(response))
			{
				response.render(JavaScriptHeaderItem.forReference(JQueryResourceReference.get()));
				if (Application.get().usesDevelopmentConfig())
				{
					response.render(JavaScriptHeaderItem.forReference(JQUERY_DEBUG_JS));
				}
			}
			CharSequence script = getOnReadyScript();
			if (script != null && script.length() > 0)
			{
				StringBuilder builder = new StringBuilder();
				builder.append("<script type=\"text/javascript\">\n$(document).ready(function(){\n");
				builder.append(script);
				builder.append("\n});</script>");
				response.render(StringHeaderItem.forString(builder));
			}
		}
		catch (RuntimeException exc)
		{
			throw exc;
		}
		catch (Exception exc)
		{
			throw new RuntimeException("wrap: " + exc.getMessage(), exc);
		}
	}

	/**
	 * to be overridden by subclass if you don't want to autodetect wether a jquery.js has already
	 * been added, and thus skip adding JQUERY_JS as a JavascriptReference.
	 * 
	 * You can either simply return false, or implement your own check to see if a jquery.js
	 * reference has been added either as a HeaderContributor or in a <wicket:head> tag of some page
	 * in the hierarchy.
	 * 
	 * @param response
	 *            The IHeaderResponse, containing the rendered headers until now
	 * @return true if you want renderHead to include the JQUERY_JS in the head.
	 */
	public boolean getIncludeJQueryJS(IHeaderResponse response)
	{
		return !JQUERY_REGEXP.matcher(response.getResponse().toString()).matches();
	}

	/**
	 * to be override by subclass if need to run script when dom is ready. The returned script is
	 * wrapped by caller into &lt;script&gt; tag and the "$(document).ready(function(){...}"
	 * 
	 * @return the script to execute when the dom is ready, or null (default)
	 */
	protected CharSequence getOnReadyScript()
	{
		return null;
	}

	@Override
	protected void respond(AjaxRequestTarget target)
	{
		throw new UnsupportedOperationException("nothing to do");
	}

	protected Logger logger()
	{
		if (logger_ == null)
		{
			logger_ = LoggerFactory.getLogger(this.getClass());
		}
		return logger_;
	}
}
