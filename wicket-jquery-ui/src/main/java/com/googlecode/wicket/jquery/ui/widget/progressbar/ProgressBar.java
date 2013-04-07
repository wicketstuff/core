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
package com.googlecode.wicket.jquery.ui.widget.progressbar;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryContainer;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.event.IValueChangedListener;
import com.googlecode.wicket.jquery.core.event.JQueryAjaxChangeBehavior.ChangeEvent;

/**
 * Provides a jQuery progress-bar based on a {@link JQueryContainer}
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.0
 */
public class ProgressBar extends JQueryContainer implements IJQueryAjaxAware, IValueChangedListener
{
	private static final long serialVersionUID = 1L;

	private static final int MIN = 0;
	private static final int MAX = 100;

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


	// Properties //
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
	 * This method is needed to be called after the model object changed.<br/>
	 * But It is not required to be called when calling forward or backward methods.
	 * @param target the {@link AjaxRequestTarget}
	 */
	public final void respond(AjaxRequestTarget target)
	{
		target.appendJavaScript(this.widgetBehavior.toString()); //change the value ui-side so the change-event will be fired
	}

	/* Events */
	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof ChangeEvent)
		{
			this.onValueChanged(target);

			if(this.getModelObject() == ProgressBar.MAX)
			{
				this.onComplete(target);
			}
		}
	}

	/**
	 * Triggered when the value changed
	 * @param target the {@link AjaxRequestTarget}
	 */
	@Override
	public void onValueChanged(AjaxRequestTarget target)
	{
	}

	/**
	 * Triggered when the value reach {@link #MAX}
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected void onComplete(AjaxRequestTarget target)
	{
	}

	@Override
	protected void onModelChanged()
	{
		this.widgetBehavior.setOption("value", this.getModelObject()); //cannot be null?
	}


	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new ProgressBarBehavior(selector) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onAjax(AjaxRequestTarget target, JQueryEvent event)
			{
				ProgressBar.this.onAjax(target, event);
			}
		};
	}
}
