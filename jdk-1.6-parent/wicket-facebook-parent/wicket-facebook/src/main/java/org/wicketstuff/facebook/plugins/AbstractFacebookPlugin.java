package org.wicketstuff.facebook.plugins;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.iterator.ComponentHierarchyIterator;
import org.wicketstuff.facebook.FacebookRootProvider;
import org.wicketstuff.facebook.MissingFacebookRootException;

/**
 * https://developers.facebook.com/docs/plugins/
 * 
 * This is an abstract class for facebook social plugins.
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
	private Font font;


	/**
	 * @param id
	 *            wicket-id
	 * @param facebookPluginClass
	 *            the plugin class like <code>class="fb-recommendations"</code>
	 */
	protected AbstractFacebookPlugin(final String id, final String facebookPluginClass)
	{
		super(id);

		add(new AttributeModifier("class", facebookPluginClass));

		add(new AttributeModifier("data-colorscheme", new EnumModel(new PropertyModel<ColorScheme>(
			this, "colorScheme"))));
		add(new AttributeModifier("data-font", new EnumModel(new PropertyModel<Font>(this, "font"))));
	}


	/**
	 * @see #setColorScheme(ColorScheme)
	 * @return
	 */
	public ColorScheme getColorScheme()
	{
		return colorScheme;
	}


	/**
	 * @see #setFont(Font)
	 * @return the font
	 */
	public Font getFont()
	{
		return font;
	}

	/**
	 * specifies the colorscheme for the plugin
	 * 
	 * @param colorScheme
	 *            the color scheme for the plugin. Options: {@link ColorScheme}
	 */
	public void setColorScheme(final ColorScheme colorScheme)
	{
		this.colorScheme = colorScheme;
	}

	/**
	 * @param font
	 *            the font to display in the plugin. Options: {@link Font}
	 */
	public void setFont(final Font font)
	{
		this.font = font;
	}

	@Override
	protected void onRender()
	{
		final ComponentHierarchyIterator visitChildren = getPage().visitChildren(
			FacebookRootProvider.class);
		if (!visitChildren.hasNext())
			throw new MissingFacebookRootException();

		if (findPage() != null)
		{
			AjaxRequestTarget target = getRequestCycle().find(AjaxRequestTarget.class);
			if (target != null)
			{
			setOutputMarkupId(true);

			final StringBuilder js = new StringBuilder();
			js.append("FB.XFBML.parse(document.getElementById('");
			js.append(getMarkupId());
			js.append("').parentNode);");

			target.appendJavaScript(js.toString());
			}
		}

		super.onRender();
	}


}
