package org.wicketstuff.openlayers.api.layer;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.wicketstuff.openlayers.js.JSUtils;

public class OSM extends Layer implements Serializable
{

	private static final long serialVersionUID = 1L;

	public static enum OSMLayer
	{
		Mapnik, TilesAtHome, CycleMap
	}

	private OSMLayer layer;

	public OSM(String name, OSMLayer layer)
	{
		setName(name);
		this.layer = layer;
	}


	@Override
	protected void bindHeaderContributors(IHeaderResponse response)
	{

		response.render(JavaScriptHeaderItem.forUrl("http://www.openstreetmap.org/openlayers/OpenStreetMap.js"));
	}

	@Override
	public String getJSconstructor()
	{

		String quotedName = JSUtils.getQuotedString(getName());

		switch (layer)
		{
			case Mapnik :
				return getJSconstructor("OpenLayers.Layer.OSM.Mapnik", Arrays.asList(quotedName));

			case TilesAtHome :
				return getJSconstructor("OpenLayers.Layer.OSM.Osmarender",
					Arrays.asList(quotedName));
			case CycleMap :
				return getJSconstructor("OpenLayers.Layer.OSM.CycleMap", Arrays.asList(quotedName));
			default :
				return getJSconstructor("OpenLayers.Layer.OSM.Mapnik", Arrays.asList(quotedName));
		}
	}
}
