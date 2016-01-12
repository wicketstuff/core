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
package org.wicketstuff.minis.behavior.spinner;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.core.util.string.JavaScriptUtils;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.Strings;

/**
 * This behavior is used to augment a {@link TextField} with spinning abilities.<br/>
 * For customization of the {@link Spinner} take a look at the {@link #configure(Properties)}
 * method.
 * 
 * @author Gerolf Seitz
 * 
 */
public class Spinner extends Behavior
{
	private static final long serialVersionUID = 1L;

	private static final ResourceReference JS = new JavaScriptResourceReference(Spinner.class,
		"spinner.js");
	private static final ResourceReference CSS = new CssResourceReference(Spinner.class,
		"spinner.css");

	private Component component;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeRender(final Component component)
	{
		if (getSpinDownComponent() == null)
		{
			final Response response = component.getResponse();
			response.write("<span class='spinnerContainer'>");
			response.write("<span id='" + getMarkupId() + "Down'>-</span>");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind(final Component component)
	{
		if (this.component != null)
			throw new IllegalStateException("Spinner can not be added to more than one component.");
		else if (!(component instanceof TextField))
			throw new IllegalArgumentException("Spinner can only be added to a TextField.");
		this.component = component.setOutputMarkupId(true);
		;
	}

	/**
	 * This method can be overridden to customize the Spinner.<br/>
	 * The following options are used by the Spinner:
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
	protected void configure(final Properties p)
	{

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
	 * {@inheritDoc}
	 */
	@Override
	public void afterRender(final Component component)
	{
		if (getSpinUpComponent() == null)
		{
			final Response response = component.getResponse();
			response.write("<span id='" + getMarkupId() + "Up'>+</span></span>");
		}
	}

	/**
	 * Transforms a {@link Properties} instance into an associative javascript array. <br/>
	 * TODO: this is copied from wicket-datetime/DatePicker and should be moved to
	 * {@link JavaScriptUtils}
	 * 
	 * @param p
	 *            the {@link Properties} to process.
	 * @return the {@link String} representing the given {@link Properties} instance as an
	 *         associative javascript array.
	 */
	private String propertiesToJavascriptArray(final Properties p)
	{
		final StringBuilder sb = new StringBuilder();
		for (final Iterator<Map.Entry<Object, Object>> it = p.entrySet().iterator(); it.hasNext();)
		{
			final Entry<Object, Object> entry = it.next();
			sb.append(entry.getKey());
			final Object value = entry.getValue();
			if (value instanceof CharSequence)
			{
				sb.append(":\"");
				sb.append(Strings.toEscapedUnicode(value.toString()));
				sb.append("\"");
			}
			else if (value instanceof CharSequence[])
			{
				sb.append(":[");
				final CharSequence[] valueArray = (CharSequence[])value;
				for (int j = 0; j < valueArray.length; j++)
				{
					final CharSequence tmpValue = valueArray[j];
					if (j > 0)
						sb.append(',');
					if (tmpValue != null)
					{
						sb.append("\"");
						sb.append(Strings.toEscapedUnicode(tmpValue.toString()));
						sb.append("\"");
					}
				}
				sb.append(']');
			}
			else
			{
				sb.append(':');
				sb.append(String.valueOf(value)); // no Strings.toEscapedUnicode for properties like afterUpdate, ... that need to evaluate as JS functions, not strings
			}
			if (it.hasNext())
				sb.append(',');
		}
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(Component c, final IHeaderResponse response)
	{
		response.render(JavaScriptHeaderItem.forReference(JS));
		response.render(CssHeaderItem.forReference(CSS));
		final Properties p = new Properties();
		configure(p);
		final String downId = getSpinDownComponent() == null ? getMarkupId() + "Down"
			: getSpinDownComponent().getMarkupId();
		final String upId = getSpinUpComponent() == null ? getMarkupId() + "Up"
			: getSpinUpComponent().getMarkupId();
		response.render(OnDomReadyHeaderItem.forScript("new Wicket.Spinner('" +
			component.getMarkupId() + "', '" + upId + "', '" + downId + "', {" +
			propertiesToJavascriptArray(p) + "})"));
	}
}
