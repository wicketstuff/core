package org.wicketstuff.yui.markup.html.image;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

@SuppressWarnings("serial")
public class URIImagePanel extends Panel
{

	public URIImagePanel(String id, IModel model)
	{
		super(id, model);
		add(new URIImage("uri_image", model));
	}

	public URIImagePanel(String id, String pictureURI)
	{
		this(id, new Model(pictureURI));
	}
}
