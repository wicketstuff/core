/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License") +  you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wicketstuff.select2;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;

import de.agilecoders.wicket.webjars.request.resource.WebjarsCssResourceReference;

/**
 * Gives Select2 the look and feel of Bootstrap 5 form controls, using the
 * <a href="https://github.com/apalfrey/select2-bootstrap-5-theme">select2-bootstrap-5-theme</a>.
 * <p>
 * The Bootstrap stylesheet itself is only rendered when requested, from the
 * {@code org.webjars.npm:bootstrap} webjar, which the application has to provide.
 *
 * @see Select2BootstrapTheme for Bootstrap 4
 */
public class Select2Bootstrap5Theme implements ISelect2Theme
{
	private static final long serialVersionUID = 1L;

	private final boolean useBootstrapWebJar;

	/**
	 * Creates the theme without rendering the Bootstrap stylesheet.
	 */
	public Select2Bootstrap5Theme()
	{
		this(false);
	}

	/**
	 * @param useBootstrapWebJar
	 *            whether to also render the Bootstrap stylesheet from the Bootstrap webjar
	 */
	public Select2Bootstrap5Theme(boolean useBootstrapWebJar)
	{
		this.useBootstrapWebJar = useBootstrapWebJar;
	}

	@Override
	public void renderHead(final Component component, final IHeaderResponse response)
	{
		if (useBootstrapWebJar)
		{
			response.render(CssHeaderItem.forReference(
				new WebjarsCssResourceReference("/bootstrap/current/dist/css/bootstrap.css")));
		}
		response.render(CssHeaderItem.forReference(new WebjarsCssResourceReference(
			"/select2-bootstrap-5-theme/current/dist/select2-bootstrap-5-theme.min.css")));
	}

	@Override
	public String name()
	{
		return "bootstrap-5";
	}
}
