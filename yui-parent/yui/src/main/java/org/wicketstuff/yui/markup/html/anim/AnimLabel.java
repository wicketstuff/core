package org.wicketstuff.yui.markup.html.anim;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.yui.markup.html.contributor.YuiHeaderContributor;

/**
 * An AnimLabel contains the option's value
 * 
 * @author cptan
 */
public class AnimLabel extends Panel {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates an AnimLabel
	 * 
	 * @param id -
	 *            wicket id
	 * @param text -
	 *            value of the option
	 */
	public AnimLabel(String id, String text) {
		super(id);
		add(YuiHeaderContributor.forModule("animation"));
		add(new Label("label", text));
	}
}
