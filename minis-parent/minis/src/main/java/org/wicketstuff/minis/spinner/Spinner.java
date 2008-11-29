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
package org.wicketstuff.minis.spinner;

import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.Response;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.apache.wicket.util.string.JavascriptUtils;
import org.apache.wicket.util.string.Strings;

/**
 * This behavior is used to augment a {@link TextField} with spinning abilities.<br/>
 * For customization of the {@link Spinner} take a look at the
 * {@link #configure(Properties)} method.
 * 
 * @author Gerolf Seitz
 * 
 */
public class Spinner extends AbstractBehavior
{
	private static final long serialVersionUID = 1L;

	private static final ResourceReference JS = new JavascriptResourceReference(Spinner.class,
			"spinner.js");
	private static final ResourceReference CSS = new ResourceReference(Spinner.class, "spinner.css");

	private Component component;

	/**
	 * @see org.apache.wicket.behavior.AbstractBehavior#bind(org.apache.wicket.Component)
	 */
	@Override
	public void bind(Component component)
	{
		if (this.component != null)
		{
			throw new IllegalStateException("Spinner can not be added to more than one component.");
		}
		else if (!(component instanceof TextField))
		{
			throw new IllegalArgumentException("Spinner can only be added to a TextField.");
		}
		this.component = component.setOutputMarkupId(true);
		;
	}

	/**
	 * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		response.renderJavascriptReference(JS);
		response.renderCSSReference(CSS);
		Properties p = new Properties();
		configure(p);
		String downId = getSpinDownComponent() == null
				? getMarkupId() + "Down"
				: getSpinDownComponent().getMarkupId();
		String upId = getSpinUpComponent() == null ? getMarkupId() + "Up" : getSpinUpComponent()
				.getMarkupId();
		response.renderOnDomReadyJavascript("new Wicket.Spinner('" + component.getMarkupId()
				+ "', '" + upId + "', '" + downId + "', {" + propertiesToJavascriptArray(p) + "})");
	}

	/**
	 * Transforms a {@link Properties} instance into an associative javascript
	 * array. <br/> TODO: this is copied from wicket-datetime/DatePicker and
	 * should be moved to {@link JavascriptUtils}
	 * 
	 * @param p
	 *            the {@link Properties} to process.
	 * @return the {@link String} representing the given {@link Properties}
	 *         instance as an associative javascript array.
	 */
	private String propertiesToJavascriptArray(Properties p)
	{
		StringBuffer sb = new StringBuffer();
		for (Iterator i = p.entrySet().iterator(); i.hasNext();)
		{
			Entry entry = (Entry)i.next();
			sb.append(entry.getKey());
			Object value = entry.getValue();
			if (value instanceof CharSequence)
			{
				sb.append(":\"");
				sb.append(Strings.toEscapedUnicode(value.toString()));
				sb.append("\"");
			}
			else if (value instanceof CharSequence[])
			{
				sb.append(":[");
				CharSequence[] valueArray = (CharSequence[])value;
				for (int j = 0; j < valueArray.length; j++)
				{
					CharSequence tmpValue = valueArray[j];
					if (j > 0)
					{
						sb.append(",");
					}
					if (tmpValue != null)
					{
						sb.append("\"");
						sb.append(Strings.toEscapedUnicode(tmpValue.toString()));
						sb.append("\"");
					}
				}
				sb.append("]");
			}
			else
			{
				sb.append(":");
				sb.append(Strings.toEscapedUnicode(String.valueOf(value)));
			}
			if (i.hasNext())
			{
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * @see org.apache.wicket.behavior.AbstractBehavior#beforeRender(org.apache.wicket.Component)
	 */
	@Override
	public void beforeRender(Component component)
	{
		if (getSpinDownComponent() == null)
		{
			Response response = component.getResponse();
			response.write("<span class='spinnerContainer'>");
			response.write("<span id='" + getMarkupId() + "Down'>-</span>");
		}
	}

	/**
	 * @see org.apache.wicket.behavior.AbstractBehavior#onRendered(org.apache.wicket.Component)
	 */
	@Override
	public void onRendered(Component component)
	{
		if (getSpinUpComponent() == null)
		{
			Response response = component.getResponse();
			response.write("<span id='" + getMarkupId() + "Up'>+</span></span>");
		}
	}

	/**
	 * @return
	 */
	private String getMarkupId()
	{
		return component.getMarkupId() + "-Spinner";
	}

	/**
	 * @return the component the is used to spin down.
	 */
	protected Component getSpinDownComponent()
	{
		return null;
	}

	/**
	 * @return the component that is used to spin up.
	 */
	protected Component getSpinUpComponent()
	{
		return null;
	}

	/**
	 * This method can be overridden to customize the Spinner.<br/> The
	 * following options are used by the Spinner:
	 * <ul>
	 * <li>interval The amount to increment (default=1)
	 * <li>round The number of decimal points to which to round (default=0)
	 * <li>min The lowest allowed value, false for no min (default=false)
	 * <li>max The highest allowed value, false for no max (default=false)
	 * <li>prefix String to prepend when updating (default='')
	 * <li>suffix String to append when updating (default='')
	 * <li>data An array giving a list of items through which to iterate
	 * <li>onIncrement Function to call after incrementing
	 * <li>onDecrement Function to call after decrementing
	 * <li>afterUpdate Function to call after update of the value
	 * <li>onStop Function to call on click or mouseup (default=false)
	 * </ul>
	 * 
	 * @param p
	 */
	protected void configure(Properties p)
	{

	}
}
