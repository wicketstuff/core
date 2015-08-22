package org.wicketstuff.yui.markup.html.animselect;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.yui.markup.html.contributor.YuiHeaderContributor;

/**
 * Represent the option's value
 * 
 * @author cptan
 */
public class AnimSelectOptionLabel extends Panel
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param text
	 */
	public AnimSelectOptionLabel(String id, String text)
	{
		super(id);
		add(YuiHeaderContributor.forModule("animation"));
		add(new Label("label", text));
	}
}
