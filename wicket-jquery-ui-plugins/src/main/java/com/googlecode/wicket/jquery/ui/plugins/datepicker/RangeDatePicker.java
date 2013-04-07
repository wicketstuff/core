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
package com.googlecode.wicket.jquery.ui.plugins.datepicker;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryContainer;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a jQuery integration of foxrunsoftware's (range) DatePicker<br/>
 * https://github.com/foxrunsoftware/DatePicker/
 *
 * @author Sebastien Briquet - sebfz1
 */
public class RangeDatePicker extends JQueryContainer implements IRangeDatePickerListener
{
	private static final long serialVersionUID = 1L;

	private Options options;

	/**
	 * Constructor
	 * @param id the markup id
	 * @param options {@link Options}
	 */
	public RangeDatePicker(String id, Options options)
	{
		super(id);

		this.options = options;
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options {@link Options}
	 */
	public RangeDatePicker(String id, IModel<DateRange> model, Options options)
	{
		super(id, model);

		this.options = options;
	}

	// Properties //
	/**
	 * Gets the model
	 * @return {@link IModel}
	 */
	@SuppressWarnings("unchecked")
	public final IModel<DateRange> getModel()
	{
		return (IModel<DateRange>) this.getDefaultModel();
	}

	/**
	 * Gets the model object
	 * @return the model object
	 */
	public final DateRange getModelObject()
	{
		return (DateRange) this.getDefaultModelObject();
	}

	/**
	 * Sets the model object
	 * @param object the model object
	 */
	public void setModelObject(DateRange object)
	{
		this.setDefaultModelObject(object);
	}


	// Events //
	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering
	 * cycle has begun, the behavior can modify the configuration of the component (i.e. {@link Options})
	 *
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(JQueryBehavior behavior)
	{
	}

	//XXX: report as changed - onValueChanged(AjaxRequestTarget target) > onValueChanged(AjaxRequestTarget target, DateRange range)
	@Override
	public void onValueChanged(AjaxRequestTarget target, DateRange range)
	{
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new RangeDatePickerBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				super.onConfigure(component);

				RangeDatePicker.this.onConfigure(this);

				// build date array
				StringBuilder builder = new StringBuilder("[");
				DateRange dateRange = RangeDatePicker.this.getModelObject();

				if (dateRange != null)
				{
					if (dateRange.getStart() != null)
					{
						builder.append("new Date(").append(dateRange.getStart().getTime()).append(")");
					}

					builder.append(",");

					if (dateRange.getEnd() != null)
					{
						builder.append("new Date(").append(dateRange.getEnd().getTime()).append(")");
					}
				}

				builder.append("]");

				// set options
				this.setOption("date", builder.toString());
				this.setOption("mode", Options.asString("range")); //immutable
			}

			@Override
			public void onValueChanged(AjaxRequestTarget target, DateRange range)
			{
				RangeDatePicker.this.setModelObject(range);
				RangeDatePicker.this.onValueChanged(target, range);
			}
		};
	}
}
