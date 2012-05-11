package com.googlecode.wicket.jquery.ui.event;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.FormComponent;

import com.googlecode.wicket.jquery.ui.JQueryEvent;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxPostBehavior;
import com.googlecode.wicket.jquery.ui.form.slider.AbstractSlider;

/**
 * Provides a new {@link JQueryAjaxPostBehavior} that will (should) be called on 'change' jQuery method
 */
public class JQueryAjaxChangeBehavior extends JQueryAjaxPostBehavior
{
	private static final long serialVersionUID = 1L;

	public JQueryAjaxChangeBehavior(FormComponent<?> component)
	{
		super(component);
	}

	public JQueryAjaxChangeBehavior(AbstractSlider<?> slider, FormComponent<?>... components)
	{
		super(slider, components);
	}

	@Override
	protected JQueryEvent newEvent(AjaxRequestTarget target)
	{
		return new ChangeEvent(target);
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxChangeBehavior}
	 */
	public static class ChangeEvent extends JQueryEvent
	{
		public ChangeEvent(AjaxRequestTarget target)
		{
			super(target);
		}
	}}