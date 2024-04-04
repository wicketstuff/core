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

import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryGenericContainer;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

/**
 * Provides a Kendo UI progress-bar based on a {@link JQueryGenericContainer}
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.17.0
 */
public class ProgressBar extends JQueryGenericContainer<Integer> implements IProgressBarListener
{
	private static final long serialVersionUID = 1L;

	private static final int MIN = 0;
	private static final int MAX = 100;

	/** Flag that indicates the value (model object) has changed */
	private boolean valueChanged = false;

	protected final Options options;

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

		this.options = Args.notNull(options, "options");
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

		this.options = Args.notNull(options, "options");
	}

	// Properties //

	/**
	 * Gets the min value
	 *
	 * @return the 'min' value specified in the options or {@link #MIN} if unspecified
	 */
	public int getMin()
	{
		Integer min = this.options.get("min");

		return min != null ? min : MIN;
	}

	/**
	 * Gets the max value
	 *
	 * @return the 'max' value specified in the options or {@link #MAX} if unspecified
	 */
	public int getMax()
	{
		Integer max = this.options.get("max");

		return max != null ? max : MAX;
	}

	/**
	 * Sets the progress-bar value
	 *
	 * @param value value which should be greater than or equals to {@link #MIN} and less than or equals to {@link #MAX}
	 */
	@Override
	public JQueryGenericContainer<Integer> setModelObject(Integer value)
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

		return super.setModelObject(v);
	}

	// Methods //

	/**
	 * Gets the Kendo UI widget
	 *
	 * @return the jQuery object
	 */
	public String widget()
	{
		return KendoUIBehavior.widget(this, ProgressBarBehavior.METHOD);
	}

	/**
	 * Increments the progress-bar value by 1
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void forward(IPartialPageRequestHandler handler)
	{
		this.forward(handler, 1);
	}

	/**
	 * Increments the progress-bar value by the specified step value
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param step the value
	 */
	public final void forward(IPartialPageRequestHandler handler, int step)
	{
		this.setModelObject(this.getModelObject() + step);
		this.refresh(handler);
	}

	/**
	 * Decrements the progress-bar value by 1
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public final void backward(IPartialPageRequestHandler handler)
	{
		this.backward(handler, 1);
	}

	/**
	 * Decrements the progress-bar value by the specified step value
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param step the value
	 */
	public final void backward(IPartialPageRequestHandler handler, int step)
	{
		this.setModelObject(this.getModelObject() - step);
		this.refresh(handler);
	}

	/**
	 * Refreshes the ProgressBar.<br>
	 * This method needs to be called after the model object changes.<br>
	 * But is not required to be called when calling forward or backward methods.
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public final void refresh(IPartialPageRequestHandler handler)
	{
		handler.appendJavaScript(String.format("%s.value(%d);", this.widget(), this.getModelObject()));

		if (this.valueChanged)
		{
			this.valueChanged = false;
			this.onValueChanged(handler);

			if (this.getModelObject() >= this.getMax())
			{
				this.onComplete(handler);
			}
		}
	}

	// Events //

	@Override
	protected void onModelChanged()
	{
		this.valueChanged = true;
	}

	/**
	 * Triggered when the value changed
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	@Override
	public void onValueChanged(IPartialPageRequestHandler handler)
	{
		// noop
	}

	/**
	 * Triggered when the value reach {@link #MAX}
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	@Override
	public void onComplete(IPartialPageRequestHandler handler)
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
