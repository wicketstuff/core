package org.wicketstuff.openlayers.api.layer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.wicketstuff.openlayers.js.Constructor;

public class OSM extends Layer implements Serializable {
	
	public static enum OSMLayer {Mapnik, TilesAtHome, CycleMap}
	
	private OSMLayer layer; 
	
	public OSM(String name, OSMLayer layer ){
		setName(name);
		this.layer = layer;
	}
	
	@Override
	public List<HeaderContributor> getHeaderContributors() {
		List contributors = new ArrayList<IHeaderContributor>();
		
		contributors.add(new HeaderContributor(new IHeaderContributor(){
			public void renderHead(IHeaderResponse response) {
				response.renderJavascriptReference("http://www.openstreetmap.org/openlayers/OpenStreetMap.js");
			}
		}));
		
		return contributors;
	}
	
	public String getJSconstructor() {
		switch (layer.ordinal()){
			case 0: return new Constructor("OpenLayers.Layer.OSM.Mapnik").add("'" + getName() + "'").toJS();
			case 1: return new Constructor("OpenLayers.Layer.OSM.Osmarender").add("'" + getName() + "'").toJS(); 
			case 2: return new Constructor("OpenLayers.Layer.OSM.CycleMap").add("'" + getName() + "'").toJS(); 			
			default: return new Constructor("OpenLayers.Layer.OSM.Mapnik").add("'" + getName() + "'").toJS();
		}			
	}
}
