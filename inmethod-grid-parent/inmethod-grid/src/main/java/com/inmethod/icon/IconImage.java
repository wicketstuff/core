package com.inmethod.icon;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Simple component that displays an icon. If the component is attached to an &lt;img&gt; tag, the
 * icon URL is passed as the <code>src</code> attribute, otherwise the icon is set by a CSS
 * background image property.
 * 
 * @author Matej Knopp
 * 
 */
public class IconImage extends WebMarkupContainer
{

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new icon instance.
	 * 
	 * @param id
	 *            component id
	 */
	public IconImage(String id)
	{
		super(id);
	}

	/**
	 * Creates a new icon instance.
	 * 
	 * @param id
	 *            component id
	 * @param icon
	 */
	public IconImage(String id, Icon icon)
	{
		this(id, new Model<Icon>(icon));
	}

	/**
	 * Creates a a new icon instance.
	 * 
	 * @param id
	 *            component id
	 * @param model
	 *            model used to obtain the {@link Icon} instance
	 */
	public IconImage(String id, IModel<Icon> model)
	{
		super(id, model);
	}

	/**
	 * Returns the icon instance associated with this component.
	 * 
	 * @return icon instance
	 */
	public Icon getIcon()
	{
		return (Icon)getDefaultModelObject();
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);

		Icon icon = getIcon();

		if (tag.getName().toLowerCase().equals("img"))
		{
			int width = icon.getWidth();
			int height = icon.getHeight();

			tag.put("src", icon.getUrl());
			if (width != -1 && height != -1)
			{
				tag.put("width", width);
				tag.put("height", height);
			}

		}
		else
		{
			CharSequence style = tag.getAttribute("style");
			if (style == null)
				style = "";
			else if (style.length() > 0)
				style = style + ";";
			style = style + "background-image: url('" + icon.getUrl() +
				"'); background-repeat: no-repeat;";
			tag.put("style", style);
		}
	}

}
