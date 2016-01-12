package org.wicketstuff.jquery.lavalamp;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.panel.Panel;

public class MenuItem extends Panel
{
	private static final long serialVersionUID = 1628561736142317830L;
	public final static String MENU_ITEM_ID = "menuItem";
	public final static String LINK_ID = "link";
	public final static String CAPTION_ID = "caption";

	public MenuItem(AbstractLink link, Label captionLabel)
	{
		super(MENU_ITEM_ID);
		link.add(captionLabel);
		add(link);
	}

}
