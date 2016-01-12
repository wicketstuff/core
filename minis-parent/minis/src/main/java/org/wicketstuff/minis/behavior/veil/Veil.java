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
package org.wicketstuff.minis.behavior.veil;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.util.string.Strings;

/**
 * Behavior that shows a veil over a component
 * 
 * @author Igor Vaynberg (ivaynberg)
 */
public class Veil extends VeilResources
{
	private static final long serialVersionUID = 1L;

	private Component component;
	private String cssClassName;

	/**
	 * Constructor
	 */
	public Veil()
	{
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param cssClassName
	 *            name of css class that will be used for the veil
	 */
	public Veil(final String cssClassName)
	{
		if (!Strings.isEmpty(cssClassName))
			this.cssClassName = cssClassName;

	}

	/**
	 * @see org.wicketstuff.minis.behavior.veil.VeilResources#bind(org.apache.wicket.Component)
	 */
	@Override
	public void bind(final Component component)
	{
		super.bind(component);
		if (this.component != null)
			throw new IllegalStateException(
				"This behavior is already bound to component. An instance of this behavior cannot be reused between components. Bound component: " +
					this.component.toString());
		this.component = component;
	}

	private String getCssClassName()
	{
		return cssClassName != null ? cssClassName : VeilResources.DEFAULT_CSS_CLASS_NAME;
	}


	/**
	 * @see org.wicketstuff.minis.behavior.veil.VeilResources#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(Component c, final IHeaderResponse response)
	{
		super.renderHead(c, response);
		response.render(OnDomReadyHeaderItem.forScript(VeilResources.Javascript.show(component)));
	}
}
