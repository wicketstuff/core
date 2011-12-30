package org.wicketstuff.openlayers.api.layer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.behavior.HeaderContributor;
import org.wicketstuff.openlayers.js.JSUtils;

/**
 * 
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 * 
 */
public class WMS extends Layer implements Serializable {

	private String url;
	/**
	 * can be any of : layers: 'topp:AL_03C5E', styles: '', height: '750',
	 * width: '800', srs: 'EPSG:2400', format: 'image/png', tiled: 'true',
	 * tilesOrigin : "1319519.4432429108,6224522.644438478"
	 * 
	 * 
	 */
	private HashMap<String, String> options = new HashMap<String, String>();
	private final HashMap<String, String> extraOptions;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public WMS(String name, String url, HashMap<String, String>options) {
		this (name, url, options, null);
	}

	/**
	 * 
	 * @param name
	 * @param url
	 * @param options layer options
	 * @param extraOptions things like baseLayer: need to be placed in extraOptions.
	 */
	public WMS(String name, String url, HashMap<String, String> options, HashMap<String, String>extraOptions) {
		super("wms");
		this.extraOptions = extraOptions;
		setName(name);
		this.url = url;
		this.options = options;

	}

	/*
	 * name, url, options
	 */

	public String getJSconstructor() {
		
		String options = super.getJSOptionsMap(this.options);
		String extraOptions = super.getJSOptionsMap(this.extraOptions);
		
		if (extraOptions == null)
			return getJSconstructor("OpenLayers.Layer.WMS", Arrays.asList(new String[] {JSUtils.getQuotedString(getName()), JSUtils.getQuotedString(getUrl()), options}));
		else 			
			return getJSconstructor("OpenLayers.Layer.WMS", Arrays.asList(new String[] {JSUtils.getQuotedString(getName()), JSUtils.getQuotedString(getUrl()), options, extraOptions}));
		
	}

	@Override
	public List<HeaderContributor> getHeaderContributors() {
		return new ArrayList<HeaderContributor>();
	}

}
