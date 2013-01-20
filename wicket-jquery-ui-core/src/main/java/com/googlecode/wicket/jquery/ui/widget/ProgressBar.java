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
package com.googlecode.wicket.jquery.ui.widget;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryContainer;
import com.googlecode.wicket.jquery.ui.JQueryEvent;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.event.JQueryAjaxChangeBehavior.ChangeEvent;

/**
 * Provides a jQuery progress-bar based on a {@link JQueryContainer}
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.0
 */
public class ProgressBar extends JQueryContainer
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "progressbar";
	private static final int MIN = 0;
	private static final int MAX = 100;

	private JQueryAjaxBehavior onChangeBehavior = null;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public ProgressBar(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public ProgressBar(String id, IModel<Integer> model)
	{
		super(id, model);
	}

//	public ProgressBar(String id, IModel<Integer> model, Duration duration)
//	{
//		super(id, model);
//		this.init();
//	}


	/* Getters / Setters */
	/**
	 * Gets the model (wrapping the value)
	 * @return {@link IModel}
	 */
	@SuppressWarnings("unchecked")
	public IModel<Integer> getModel()
	{
		return (IModel<Integer>) this.getDefaultModel();
	}

	/**
	 * Gets the model object
	 * @return the progress-bar value
	 */
	public Integer getModelObject()
	{
		return (Integer) this.getDefaultModelObject();
	}

	/**
	 * Sets the progress-bar value
	 * @param value value which should be greater than or equals to {@link #MIN} and less than or equals to {@link #MAX}
	 */
	public void setModelObject(Integer value)
	{
		if (value < MIN)
		{
			value = MIN;
		}
		else if (value > MAX)
		{
			value = MAX;
		}

		this.setDefaultModelObject(value);
	}


	/* Ajax Methods */
	/**
	 * Increments the progress-bar value by 1
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void forward(AjaxRequestTarget target)
	{
		this.forward(target, 1);
	}

	/**
	 * Increments the progress-bar value by the specified step value
	 * @param target the {@link AjaxRequestTarget}
	 * @param step the value
	 */
	public final void forward(AjaxRequestTarget target, int step)
	{
		this.setModelObject(this.getModelObject() + step);
		this.respond(target);
	}

	/**
	 * Decrements the progress-bar value by 1
	 * @param target the {@link AjaxRequestTarget}
	 */
	public final void backward(AjaxRequestTarget target)
	{
		this.backward(target, 1);
	}

	/**
	 * Decrements the progress-bar value by the specified step value
	 * @param target the {@link AjaxRequestTarget}
	 * @param step the value
	 */
	public final void backward(AjaxRequestTarget target, int step)
	{
		this.setModelObject(this.getModelObject() - step);
		this.respond(target);
	}

	/**
	 * Re-attaches the widget behavior to the specified target, causing the progress-bar to refresh.<br/>
	 * This method is needed to be called atfer the model object changed.<br/>
	 * But It is not required to be called when calling forward or backward methods.
	 * @param target the {@link AjaxRequestTarget}
	 */
	public final void respond(AjaxRequestTarget target)
	{
		target.appendJavaScript(this.widgetBehavior.toString()); //change the value ui-side so the change-event will be fired
	}

	/* Events */
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.onChangeBehavior = this.newOnChangeBehavior());
	}

	@Override
	public void onEvent(IEvent<?> event)
	{
		if (event.getPayload() instanceof ChangeEvent)
		{
			JQueryEvent payload = (JQueryEvent) event.getPayload();
			AjaxRequestTarget target = payload.getTarget();

			this.onValueChanged(target);

			if(this.getModelObject() == MAX)
			{
				this.onComplete(target);
			}
		}
	}

	@Override
	protected void onModelChanged()
	{
		this.widgetBehavior.setOption("value", this.getModelObject()); //cannot be null?
	}

	/**
	 * Triggered when the value changed
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected void onValueChanged(AjaxRequestTarget target)
	{
	}

	/**
	 * Triggered when the value reach {@link #MAX}
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected void onComplete(AjaxRequestTarget target)
	{
	}


	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new JQueryBehavior(selector, METHOD) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				this.setOption("value", ProgressBar.this.getModelObject()); //initial value
				this.setOption("change", ProgressBar.this.onChangeBehavior.getCallbackFunction());
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called on 'change' javascript event
	 * @return the {@link JQueryAjaxBehavior}
	 */
	private JQueryAjaxBehavior newOnChangeBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			public String getCallbackFunction()
			{
				return "function(event, ui) { " + this.getCallbackScript() + " }";
			}

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new ChangeEvent(target);
			}
		};
	}
}
