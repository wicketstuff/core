package org.wicketstuff.openlayers.api;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.Component;

/**
 * A popup for your marker, please extend this if you want poups..
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 *
 */
public class PopupWindowPanel extends Panel {

	private final static String markupId="content";
	
	public PopupWindowPanel() {
		super(markupId);
		setOutputMarkupId(true);
	}
/**
 * NOOP!
 * NEED STATIC ID!
 */
	@Override
	public Component setMarkupId(String markupId) {
		// TODO Auto-generated method stub
//		super.setMarkupId(markupId);
    return this;
	}
	

}
