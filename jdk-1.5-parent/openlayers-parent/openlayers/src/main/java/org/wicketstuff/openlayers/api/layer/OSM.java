package org.wicketstuff.openlayers.api.layer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.wicketstuff.openlayers.js.Constructor;
import org.wicketstuff.openlayers.js.JSUtils;

public class OSM extends Layer implements Serializable {

	public static enum OSMLayer {
		Mapnik, TilesAtHome, CycleMap
	}

	private OSMLayer layer;

	public OSM(String name, OSMLayer layer) {
		setName(name);
		this.layer = layer;
	}

	@Override
	public List<HeaderContributor> getHeaderContributors() {
		List<HeaderContributor> contributors = new ArrayList<HeaderContributor>();

		contributors.add(new HeaderContributor(new IHeaderContributor() {
			public void renderHead(IHeaderResponse response) {
				response
						.renderJavascriptReference("http://www.openstreetmap.org/openlayers/OpenStreetMap.js");
			}
		}));

		return contributors;
	}

	public String getJSconstructor() {
		
		String quotedName = JSUtils.getQuotedString(getName());
		
		switch (layer) {
		case Mapnik:
			return getJSconstructor("OpenLayers.Layer.OSM.Mapnik", Arrays.asList(quotedName));
			
		case TilesAtHome:
			return getJSconstructor("OpenLayers.Layer.OSM.Osmarender", Arrays.asList(quotedName));
		case CycleMap:
			return getJSconstructor("OpenLayers.Layer.OSM.CycleMap", Arrays.asList(quotedName));
		default:
			return getJSconstructor("OpenLayers.Layer.OSM.Mapnik", Arrays.asList(quotedName));
		}
	}
}
