package org.wicketstuff.openlayers.api.layer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.wicketstuff.openlayers.js.Constructor;

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
	public List<HeaderContributor> getHeaderContributors() {
		List contributors = new ArrayList<IHeaderContributor>();
		contributors.add(new HeaderContributor(new IHeaderContributor() {

			public void renderHead(IHeaderResponse response) {
				response
						.renderJavascriptReference("http://maps.google.com/maps?file=api&amp;v="
								+ apiVersion + "&amp;key=" + apiKey);
			}

		}));
		return contributors;
	}

	/*
	 * 
	 */

	public String getJSconstructor() {
		String optionlist = "";

		boolean first = true;

		for (String key : options.keySet()) {
			if (first) {
				first = false;
			} else {
				optionlist += ",\n";
			}
			optionlist += key + ": " + options.get(key);

		}

		return new Constructor("OpenLayers.Layer.Google").add(
				"'" + getName() + "'").add("{" + optionlist + "}").toJS();
	}

}
