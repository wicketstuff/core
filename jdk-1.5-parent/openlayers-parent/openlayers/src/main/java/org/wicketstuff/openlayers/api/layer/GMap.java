package org.wicketstuff.openlayers.api.layer;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.wicketstuff.openlayers.js.JSUtils;

public class GMap extends Layer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String apiVersion;
	private String apiKey;
	private HashMap<String, String> options;

	public GMap(String name, String apiKey, String apiVersion,
			HashMap<String, String> options) {
		super();
		this.apiKey = apiKey;
		this.apiVersion = apiVersion;
		this.options = options;
		setName(name);
	}

	
	@Override
	protected void bindHeaderContributors(IHeaderResponse response) {
		
				response
						.renderJavascriptReference("http://maps.google.com/maps?file=api&amp;v="
								+ apiVersion + "&amp;key=" + apiKey);

	}

	/*
	 * 
	 */

	public String getJSconstructor() {
		
		String options = super.getJSOptionsMap(this.options);
		
		return getJSconstructor("OpenLayers.Layer.Google", Arrays.asList (JSUtils.getQuotedString(getName()), options));
	}

}
