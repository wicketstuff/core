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
package org.wicketstuff.jquery.ui.form.autocomplete;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;
import org.danekja.java.util.function.serializable.SerializableSupplier;
import org.wicketstuff.jquery.core.JQueryEvent;
import org.wicketstuff.jquery.core.Options;
import org.wicketstuff.jquery.core.ajax.IJQueryAjaxAware;
import org.wicketstuff.jquery.core.ajax.JQueryAjaxBehavior;
import org.wicketstuff.jquery.core.utils.RequestCycleUtils;
import org.wicketstuff.jquery.ui.JQueryUIBehavior;

/**
 * Provides a jQuery auto-complete behavior
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class AutoCompleteBehavior<T> extends JQueryUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "autocomplete";

	/** event listener */
	private final IAutoCompleteListener<T> listener;

    /** the model producing values */

    private final SerializableSupplier<List<T>> supplier;

	private JQueryAjaxBehavior onSelectAjaxBehavior = null;

	/**
	 * Constructor
	 *
	 * @param selector the HTML selector (ie: "#myId")
	 * @param listener the {@link IAutoCompleteListener}
	 */
	public AutoCompleteBehavior(String selector, IAutoCompleteListener<T> listener, SerializableSupplier<List<T>> supplier)
	{
		this(selector, new Options(), listener, supplier);
	}

	/**
	 * Constructor
	 *
	 * @param selector the HTML selector (ie: "#myId")
	 * @param options the {@link Options}
	 * @param listener the {@link IAutoCompleteListener}
	 */
	public AutoCompleteBehavior(String selector, Options options, IAutoCompleteListener<T> listener, SerializableSupplier<List<T>> supplier)
	{
		super(selector, METHOD, options);

		this.listener = Args.notNull(listener, "listener");
        this.supplier = supplier;
    }

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		this.onSelectAjaxBehavior = this.newOnSelectAjaxBehavior(this);
		component.add(this.onSelectAjaxBehavior);
	}

	// Properties //

	@Override
	public boolean isEnabled(Component component)
	{
		return component.isEnabledInHierarchy();
	}

	protected abstract CharSequence getChoiceCallbackUrl();

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		this.setOption("select", this.onSelectAjaxBehavior.getCallbackFunction());

		if (this.isEnabled(component))
		{
			this.setOption("source", Options.asString(this.getChoiceCallbackUrl()));
		}

		super.onConfigure(component);
	}

	// IJQueryAjaxAware //

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof SelectEvent selectEvent)
		{
			this.listener.onSelect(target, listener.getElementSelectionStrategy().findChoice(supplier.get(), selectEvent.getIdentifier()), selectEvent.getIdentifier());
		}
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'select' eventta
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnSelectAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnSelectAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnSelectAjaxBehavior(source);
	}

	// Ajax classes //,

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'select' event
	 */
	protected static class OnSelectAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnSelectAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("event"), // lf
					CallbackParameter.context("ui"), // lf
					CallbackParameter.resolved("index", "ui.item.id") };
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new SelectEvent();
		}
	}

	// Event objects //

	/**
	 * Provides an event object that will be broadcasted by the {@link OnSelectAjaxBehavior} callback
	 */
	protected static class SelectEvent extends JQueryEvent
	{
		private final String identifier;

		public SelectEvent()
		{
			this.identifier = RequestCycleUtils.getQueryParameterValue("index").toString();
		}

		/**
		 * Gets the selected item identifier (JSON {@code id}). For the default index strategy this is the list index.
		 *
		 * @return the identifier
		 */
		public String getIdentifier()
		{
			return this.identifier;
		}
	}

	/**
	 * Gets the {@link JQueryAjaxBehavior} wired to the 'select' event
	 *
	 * @return the {@code OnSelectAjaxBehavior}, or {@code null} if the behavior has not been bound yet
	 */
	public final JQueryAjaxBehavior getOnSelectAjaxBehavior()
	{
		return this.onSelectAjaxBehavior;
	}
}
