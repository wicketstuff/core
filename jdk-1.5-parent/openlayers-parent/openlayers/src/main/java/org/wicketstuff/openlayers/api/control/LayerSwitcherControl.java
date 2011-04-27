package org.wicketstuff.openlayers.api.control;

import java.util.List;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.wicketstuff.openlayers.IOpenLayersMap;
import org.wicketstuff.openlayers.api.layer.Layer;

/**
 * 
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 * 
 */
public class LayerSwitcherControl extends Panel
{

	private static final long serialVersionUID = 1L;

	public LayerSwitcherControl(String id, IOpenLayersMap map)
	{
		super(id);
		add(new Toggler("layers", map.getLayers(), map));


		// Manually add marker control for now!
		String layerId = "1";
		WebMarkupContainer link = new WebMarkupContainer("link");
// link.add(new AttributeAppender("onClick",new
// Model(map.getJSinvokeNoLineEnd("toggleLayer("+layerId+")")),";"));
		link.add(new AttributeAppender("href", Model.of("javascript:" +
			map.getJSinvokeNoLineEnd("toggleLayer(" + layerId + ")")), ";"));

		link.add(new Label("layerName", "Markers"));

		add(link);


	}

	private static class Toggler extends ListView<Layer>
	{
		private static final long serialVersionUID = 1L;
		private final IOpenLayersMap omap;

		public Toggler(String id, List<Layer> list, final IOpenLayersMap omap)
		{
			super(id, list);
			this.omap = omap;
		}

		@Override
		protected void populateItem(ListItem<Layer> item)
		{
			Layer overlay = item.getModelObject();

			WebMarkupContainer link = new WebMarkupContainer("link");
// link.add(new AttributeAppender("onClick",new
// Model(omap.getJSinvokeNoLineEnd("toggleLayer("+id+")")),";"));
			link.add(new AttributeAppender("href", Model.of("javascript:" +
				omap.getJSinvokeNoLineEnd("toggleLayer(" + overlay.getId() + ")")), ";"));

			link.add(new Label("layerName", overlay.getName()));

			item.add(link);

		}
	}
}
