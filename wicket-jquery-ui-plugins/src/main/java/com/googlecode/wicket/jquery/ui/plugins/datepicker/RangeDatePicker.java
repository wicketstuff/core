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

import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryContainer;
import com.googlecode.wicket.jquery.ui.JQueryEvent;
import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.utils.RequestCycleUtils;

/**
 * Provides a jQuery integration of foxrunsoftware's (range) DatePicker<br/>
 * https://github.com/foxrunsoftware/DatePicker/
 *
 * @author Sebastien Briquet - sebfz1
 */
public class RangeDatePicker extends JQueryContainer
{
	private static final long serialVersionUID = 1L;

	private Options options;
	private JQueryAjaxBehavior onChangeBehavior;

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
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.onChangeBehavior = this.newOnChangeBehavior());
	}

	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering
	 * cycle has begun, the behavior can modify the configuration of the component (i.e. {@link Options})
	 *
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(JQueryBehavior behavior)
	{
		behavior.setOptions(this.options);
	}

	@Override
	public void onEvent(IEvent<?> event)
	{
		if (event.getPayload() instanceof ChangeEvent)
		{
			ChangeEvent payload = (ChangeEvent) event.getPayload();

			this.setModelObject(new DateRange(payload.getStart(), payload.getEnd()));
			this.onValueChanged(payload.getTarget());
		}
	}

	/**
	 * Triggered when the date(s) changed
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected void onValueChanged(AjaxRequestTarget target)
	{
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new RangeDatePickerBehavior(selector) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
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
				this.setOption("onChange", RangeDatePicker.this.onChangeBehavior.getCallbackFunction());
			}
		};
	}

	// Factories //
	/**
	 * Gets a new {@link JQueryAjaxBehavior} that acts as the 'change' javascript callback
	 * @return the {@link JQueryAjaxBehavior}
	 */
	private JQueryAjaxBehavior newOnChangeBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			public String getCallbackFunction()
			{
				return "function(dates, el) { " + this.getCallbackScript() + " }";
			}

			@Override
			public CharSequence getCallbackScript()
			{
				return this.generateCallbackScript("wicketAjaxGet('" + this.getCallbackUrl() + "&start=' + dates[0].getTime() + '&end=' + dates[1].getTime()");
			}

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new ChangeEvent(target);
			}
		};
	}

	// Event class //
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} 'change' callback
	 */
	class ChangeEvent extends JQueryEvent
	{
		private final Date start;
		private final Date end;

		public ChangeEvent(AjaxRequestTarget target)
		{
			super(target);

			long start = RequestCycleUtils.getQueryParameterValue("start").toLong();
			this.start = new Date(start);

			long end = RequestCycleUtils.getQueryParameterValue("end").toLong();
			this.end = new Date(end);
		}

		/**
		 * Gets the event's start date
		 * @return the start date
		 */
		public Date getStart()
		{
			return this.start;
		}

		/**
		 * Gets the event's end date
		 * @return the end date
		 */
		public Date getEnd()
		{
			return this.end;
		}
	}
}
