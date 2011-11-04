package org.wicketstuff.facebook.plugins;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

/**
 * 
 * @author Till Freier
 * 
 */
public abstract class AbstractFacebookPlugin extends WebMarkupContainer
{
	protected class EnumModel extends AbstractReadOnlyModel<String>
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final IModel<? extends Enum<?>> model;

		/**
		 * @param model
		 */
		public EnumModel(final IModel<? extends Enum<?>> model)
		{
			super();
			this.model = model;
		}

		@Override
		public void detach()
		{
			model.detach();
		}

		@Override
		public String getObject()
		{
			if (model == null || model.getObject() == null)
				return null;

			return model.getObject().name().toLowerCase();
		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ColorScheme colorScheme;


	/**
	 * @param id
	 */
	public AbstractFacebookPlugin(final String id, final String facebookPluginClass)
	{
		super(id);

		add(new AttributeModifier("class", facebookPluginClass));

		add(new AttributeModifier("data-colorscheme", new EnumModel(new PropertyModel<ColorScheme>(
			this, "colorScheme"))));
	}


	public ColorScheme getColorScheme()
	{
		return colorScheme;
	}

	@Override
	protected void onRender()
	{
		if (AjaxRequestTarget.get() != null && findPage() != null)
		{
			setOutputMarkupId(true);

			final StringBuilder js = new StringBuilder();
			js.append("FB.XFBML.parse(document.getElementById('");
			js.append(getMarkupId());
			js.append("').parentNode);");

			AjaxRequestTarget.get().appendJavaScript(js.toString());
		}

		super.onRender();
	}


	public void setColorScheme(final ColorScheme colorScheme)
	{
		this.colorScheme = colorScheme;
	}


}
