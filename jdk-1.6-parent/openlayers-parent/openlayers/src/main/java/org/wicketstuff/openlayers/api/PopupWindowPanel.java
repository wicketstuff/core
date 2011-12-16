package org.wicketstuff.openlayers.api;

import org.apache.wicket.markup.html.panel.Panel;

/**
 * A popup for your marker, please extend this if you want poups..
 * 
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 * 
 */
public class PopupWindowPanel extends Panel
{

	private static final long serialVersionUID = 1L;
	private final static String markupId = "content";

	public PopupWindowPanel()
	{
		super(markupId);
		setOutputMarkupId(true);
	}


}
