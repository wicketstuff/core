package com.googlecode.wicket.jquery.ui.form.button;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.widget.menu.IMenuItem;

/**
 * Provides a jQuery split ajax-button
 * 
 * @author Patrick Davids - Patrick1701
 * @author Sebastien Briquet - sebfz1
 * 
 */
public class AjaxSplitButton extends SplitButton
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param items the list of {@link IMenuItem}
	 */
	public AjaxSplitButton(String id, List<IMenuItem> items)
	{
		super(id, items);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param items the list model of {@link IMenuItem}
	 */
	public AjaxSplitButton(String id, IModel<List<IMenuItem>> items)
	{
		super(id, items);
	}

	// Events //

	/**
	 * Triggered when the form is submitted, but the validation failed
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param form the {@link Form}
	 */
	protected void onError(AjaxRequestTarget target, Form<?> form)
	{
		// noop
	}

	/**
	 * Triggered when the form is submitted, and the validation succeed
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param item the selected {@link IMenuItem}
	 */
	protected void onSubmit(AjaxRequestTarget target, IMenuItem item)
	{
		// noop
	}

	// Factories //

	@Override
	protected AbstractLink newLink(String id)
	{
		return new AjaxSubmitLink(id) {

			private static final long serialVersionUID = 1L;

			// methods //

			@Override
			protected IModel<?> initModel()
			{
				return new Model<IMenuItem>();
			}

			// properties //

			@Override
			public IModel<?> getBody()
			{
				if (this.getDefaultModelObject() != null)
				{
					return ((IMenuItem) this.getDefaultModelObject()).getTitle();
				}

				return Model.of("");
			}

			@Override
			public boolean getDefaultFormProcessing()
			{
				return AjaxSplitButton.this.getDefaultFormProcessing();
			}

			// events //

			@Override
			protected void onConfigure()
			{
				super.onConfigure();

				if (this.getDefaultModelObject() == null)
				{
					List<IMenuItem> items = AjaxSplitButton.this.getModelObject();

					if (!items.isEmpty())
					{
						this.setDefaultModelObject(items.get(0));
					}
				}
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				super.onSubmit(target, form);

				if (this.getDefaultModelObject() != null)
				{
					AjaxSplitButton.this.onSubmit(target, (IMenuItem) this.getDefaultModelObject());
				}
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				super.onError(target, form);

				AjaxSplitButton.this.onError(target, form);
			}
		};
	}
}
