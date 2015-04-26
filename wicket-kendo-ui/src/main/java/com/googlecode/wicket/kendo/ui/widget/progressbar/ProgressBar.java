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
package com.googlecode.wicket.kendo.ui.widget.progressbar;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryContainer;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a Kendo UI progress-bar based on a {@link JQueryContainer}
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.17.0
 */
public class ProgressBar extends JQueryContainer implements IProgressBarListener
{
	private static final long serialVersionUID = 1L;

	private static final int MIN = 0;
	private static final int MAX = 100;

	private final Options options;

	/** Flag that indicates the value (model object) has changed */
	private boolean valueChanged = false;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public ProgressBar(String id)
	{
		this(id, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public ProgressBar(String id, Options options)
	{
		super(id);

		this.options = options;
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public ProgressBar(String id, IModel<Integer> model)
	{
		this(id, model, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options the {@link Options}
	 */
	public ProgressBar(String id, IModel<Integer> model, Options options)
	{
		super(id, model);

		this.options = options;
	}

	// Properties //

	/**
	 * Gets the min value
	 *
	 * @return the 'min' value specified in the options or {@value #MIN} if unspecified
	 */
	public int getMin()
	{
		Integer min = this.options.get("min");

		return min != null ? min : MIN;
	}

	/**
	 * Gets the max value
	 *
	 * @return the 'max' value specified in the options or {@value #MAX} if unspecified
	 */
	public int getMax()
	{
		Integer max = this.options.get("max");

		return max != null ? max : MAX;
	}

	/**
	 * Gets the model (wrapping the value)
	 *
	 * @return {@link IModel}
	 */
	@SuppressWarnings("unchecked")
	public IModel<Integer> getModel()
	{
		return (IModel<Integer>) this.getDefaultModel();
	}

	/**
	 * Gets the model object
	 *
	 * @return the progress-bar value
	 */
	public Integer getModelObject()
	{
		return (Integer) this.getDefaultModelObject();
	}

	/**
	 * Sets the progress-bar value
	 *
	 * @param value value which should be greater than or equals to {@link #MIN} and less than or equals to {@link #MAX}
	 */
	public void setModelObject(Integer value)
	{
		Integer v = Args.notNull(value, "value");

		if (v < this.getMin())
		{
			v = this.getMin();
		}
		else if (v > this.getMax())
		{
			v = this.getMax();
		}

		this.setDefaultModelObject(v);
	}

	/* Methods */

	/**
	 * Gets the Kendo (jQuery) object
	 *
	 * @return the jQuery object
	 */
	protected String widget()
	{
		return String.format("jQuery('%s').data('%s')", JQueryWidget.getSelector(this), ProgressBarBehavior.METHOD);
	}

	/**
	 * Increments the progress-bar value by 1
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void forward(AjaxRequestTarget target)
	{
		this.forward(target, 1);
	}

	/**
	 * Increments the progress-bar value by the specified step value
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param step the value
	 */
	public final void forward(AjaxRequestTarget target, int step)
	{
		this.setModelObject(this.getModelObject() + step);
		this.refresh(target);
	}

	/**
	 * Decrements the progress-bar value by 1
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	public final void backward(AjaxRequestTarget target)
	{
		this.backward(target, 1);
	}

	/**
	 * Decrements the progress-bar value by the specified step value
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param step the value
	 */
	public final void backward(AjaxRequestTarget target, int step)
	{
		this.setModelObject(this.getModelObject() - step);
		this.refresh(target);
	}

	/**
	 * Refreshes the ProgressBar.<br/>
	 * This method needs to be called after the model object changes.<br/>
	 * But is not required to be called when calling forward or backward methods.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	public final void refresh(AjaxRequestTarget target)
	{
		target.appendJavaScript(String.format("%s.value(%d);", this.widget(), this.getModelObject()));

		if (this.valueChanged)
		{
			this.valueChanged = false;
			this.onValueChanged(target);

			if (this.getModelObject() >= this.getMax())
			{
				this.onComplete(target);
			}
		}
	}

	/* Events */

	@Override
	protected void onModelChanged()
	{
		this.valueChanged = true;
	}

	/**
	 * Triggered when the value changed
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	@Override
	public void onValueChanged(AjaxRequestTarget target)
	{
		// noop
	}

	/**
	 * Triggered when the value reach {@link #MAX}
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	@Override
	public void onComplete(AjaxRequestTarget target)
	{
		// noop
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new ProgressBarBehavior(selector, this.options);
	}
}
