/*
 * $Id: Calendar.java 5044 2006-03-20 16:46:35 -0800 (Mon, 20 Mar 2006)
 * jonathanlocke $ $Revision: 5159 $ $Date: 2006-03-20 16:46:35 -0800 (Mon, 20
 * Mar 2006) $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.yui.markup.html.calendar;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.util.template.PackagedTextTemplate;
import org.wicketstuff.yui.markup.html.contributor.YuiHeaderContributor;

/**
 * Calendar component based on the Calendar of Yahoo UI Library.
 * 
 * @author Eelco Hillenius
 */
public class Calendar extends Panel implements IHeaderContributor
{
	/**
	 * The container/ receiver of the javascript component.
	 */
	private final class CalendarElement extends FormComponent
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * 
		 * @param id
		 */
		public CalendarElement(String id)
		{
			super(id);
			add(new AttributeModifier("id", true, new AbstractReadOnlyModel()
			{
				private static final long serialVersionUID = 1L;

				@Override
				public Object getObject()
				{
					return elementId;
				}
			}));
		}

		/**
		 * @see wicket.markup.html.form.FormComponent#updateModel()
		 */
		@Override
		public void updateModel()
		{
			Calendar.this.updateModel();
		}
	}

	private static final long serialVersionUID = 1L;

	/** the receiving component. */
	private CalendarElement calendarElement;

	/**
	 * The DOM id of the element that hosts the javascript component.
	 */
	private String elementId;

	/**
	 * The JavaScript variable name of the calendar component.
	 */
	private String javaScriptId;

	/**
	 * Construct.
	 * 
	 * @param id
	 *            the component id
	 */
	public Calendar(String id)
	{
		super(id);
		add(YuiHeaderContributor.forModule("calendar"));
		add(HeaderContributor.forJavaScript(Calendar.class, "calendar.js"));
		add(HeaderContributor.forCss(Calendar.class, "calendar.css"));

		Label initialization = new Label("initialization", new AbstractReadOnlyModel()
		{
			private static final long serialVersionUID = 1L;

			/**
			 * @see wicket.model.IModel#getObject(wicket.Component)
			 */
			@Override
			public Object getObject()
			{
				return getJavaScriptComponentInitializationScript();
			}
		});
		initialization.setEscapeModelStrings(false);
		add(initialization);
		add(calendarElement = new CalendarElement("calendarContainer"));
	}

	/**
	 * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		response.renderOnLoadJavascript("init" + javaScriptId + "();");
	}

	/**
	 * TODO implement
	 */
	public void updateModel()
	{
	}

	/**
	 * Gets the initilization script for the javascript component.
	 * 
	 * @return the initilization script
	 */
	protected String getJavaScriptComponentInitializationScript()
	{
		CharSequence leftImage = RequestCycle.get().urlFor(new ResourceReference(Calendar.class, "callt.gif")).toString();
		CharSequence rightImage = RequestCycle.get().urlFor(new ResourceReference(Calendar.class, "calrt.gif")).toString();

		Map<String, Object> variables = new HashMap<String, Object>(4);
		variables.put("javaScriptId", javaScriptId);
		variables.put("elementId", elementId);
		variables.put("navigationArrowLeft", leftImage);
		variables.put("navigationArrowRight", rightImage);

		PackagedTextTemplate template = new PackagedTextTemplate(Calendar.class, "init.js");
		template.interpolate(variables);

		return template.getString();
	}

	/**
	 * @see wicket.Component#onAttach()
	 */
	@Override
	protected void onBeforeRender()
	{
		super.onBeforeRender();

		// initialize lazily
		if (elementId == null) {
			// assign the markup id
			String id = getMarkupId();
			elementId = id + "Element";
			javaScriptId = elementId + "JS";
		}
	}
}
