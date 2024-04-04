package com.googlecode.wicket.jquery.ui.form.button;

import java.util.List;

import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.widget.menu.IMenuItem;

/**
 * Provides a jQuery split-button
 * 
 * @author Patrick Davids - Patrick1701
 * @author Sebastien Briquet - sebfz1
 * 
 */
public class SplitButton extends AbstractSplitButton
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param items the list of {@link IMenuItem}
	 */
	public SplitButton(String id, List<IMenuItem> items)
	{
		super(id, items);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param items the list model of {@link IMenuItem}
	 */
	public SplitButton(String id, IModel<List<IMenuItem>> items)
	{
		super(id, items);
	}

	// Events //

	/**
	 * Triggered when the form is submitted, but the validation failed
	 */
	protected void onError()
	{
		// noop
	}

	/**
	 * Triggered when the form is submitted, and the validation succeed
	 *
	 * @param item the selected {@link IMenuItem}
	 */
	protected void onSubmit(IMenuItem item)
	{
		// noop
	}

	// Factories //

	@Override
	protected AbstractLink newLink(String id)
	{
		return new SubmitLink(id) {

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
				return SplitButton.this.getDefaultFormProcessing();
			}

			// events //

			@Override
			protected void onInitialize()
			{
				super.onInitialize();

				this.setOutputMarkupId(true);
			}

			@Override
			protected void onConfigure()
			{
				super.onConfigure();

				if (this.getDefaultModelObject() == null)
				{
					List<IMenuItem> items = SplitButton.this.getModelObject();

					if (!items.isEmpty())
					{
						this.setDefaultModelObject(items.get(0));
					}
				}
			}

			@Override
			public void onError()
			{
				super.onError();

				SplitButton.this.onError();
			}

			@Override
			public void onSubmit()
			{
				super.onSubmit();

				if (this.getDefaultModelObject() != null)
				{
					SplitButton.this.onSubmit((IMenuItem) this.getDefaultModelObject());
				}
			}
		};
	}
}
