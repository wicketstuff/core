package com.googlecode.wicket.kendo.ui.widget.menu.item;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

/**
 * <p>A specialization of {@link Form} that could be used for menu items with form components.</p>
 * Suppresses the JavaScript click event for the whole menu item but the &lt;button&gt;s inside it.
 */
public class MenuItemForm<T> extends Form<T>
{
	/**
	 * Constructor
	 *
	 * @param id The component id
	 */
	public MenuItemForm(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 *
	 * @param id The component id
	 * @param model The component model
	 */
	public MenuItemForm(String id, IModel<T> model)
	{
		super(id, model);
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);

		tag.put("onclick", "var $evt=jQuery.event.fix(event);if($evt.target.nodeName !== 'BUTTON'){$evt.stopPropagation()}");
	}
}
