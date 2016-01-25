package com.googlecode.wicket.jquery.ui.form.dropdown;

import java.util.List;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;

/**
 * Provides a JQuery UI Selectmenu widget (DropDownChoice). It extends built-in {@link org.apache.wicket.markup.html.form.DropDownChoice}<br/>
 *
 * @author Patrick Davids - Patrick1701
 *
 * @param <T> the model object type
 */
public class DropDownChoice<T> extends org.apache.wicket.markup.html.form.DropDownChoice<T> implements IJQueryWidget {

	private static final long serialVersionUID = 1L;

	public static final String METHOD = "selectmenu";

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 */
	public DropDownChoice(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param choices the list of choices
	 */
	public DropDownChoice(String id, List<? extends T> choices)
	{
		super(id, choices);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param choices the list of choices
	 * @param renderer the rendering engine
	 */
	public DropDownChoice(String id, List<? extends T> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, choices, renderer);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the list of choices
	 */
	public DropDownChoice(String id, IModel<T> model, List<? extends T> choices)
	{
		super(id, model, choices);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the list of choices
	 * @param renderer the rendering engine
	 */
	public DropDownChoice(String id, IModel<T> model, List<? extends T> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, model, choices, renderer);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param choices the list of choices
	 */
	public DropDownChoice(String id, IModel<? extends List<? extends T>> choices)
	{
		super(id, choices);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the list of choices
	 */
	public DropDownChoice(String id, IModel<T> model, IModel<? extends List<? extends T>> choices)
	{
		super(id, model, choices);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param choices the list of choices
	 * @param renderer the rendering engine
	 */
	public DropDownChoice(String id, IModel<? extends List<? extends T>> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, choices, renderer);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the list of choices
	 * @param renderer the rendering engine
	 */
	public DropDownChoice(String id, IModel<T> model, IModel<? extends List<? extends T>> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, model, choices, renderer);
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this));
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		// noop
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new JQueryUIBehavior(selector, DropDownChoice.METHOD);
	}

}