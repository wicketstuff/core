/*
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.openlayers.api.layer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.util.io.IClusterable;
import org.wicketstuff.openlayers.js.JSUtils;

/**
 * @author mocleiri
 * 
 *         Maps the OpenLayers.Layer.WFS object.
 * 
 *         See http://dev.openlayers.org/docs/files/OpenLayers/Layer/WFS-js.html
 * 
 */
public class WFS extends Layer implements IClusterable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7019052241702567568L;
	private String url;
	private Map<String, String> params;
	private Map<String, String> options;

	/**
	 * name {String} url {String} params {Object} options {Object} Hashtable of extra options to tag
	 * onto the layer
	 */
	public WFS(String name, String url, Map<String, String> params, Map<String, String> options)
	{
		super();
		this.url = url;
		this.params = params;
		this.options = options;
		setName(name);

	}


	/**
	 * @return the url
	 */
	public String getUrl()
	{
		return url;
	}


	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}


	/**
	 * @return the params
	 */
	public Map<String, String> getParams()
	{
		return params;
	}


	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(Map<String, String> params)
	{
		this.params = params;
	}


	/**
	 * @return the options
	 */
	public Map<String, String> getOptions()
	{
		return options;
	}


	/**
	 * @param options
	 *            the options to set
	 */
	public void setOptions(Map<String, String> options)
	{
		this.options = options;
	}

	@Override
	protected void bindHeaderContributors(IHeaderResponse response)
	{
		// mocleiri: implementation is intentionally empty

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wicketstuff.openlayers.api.layer.Layer#getJSconstructor()
	 */
	@Override
	public String getJSconstructor()
	{

		// For example:
		// layer = new OpenLayers.Layer.WFS( "Owl Survey",
		// "http://www.bsc-eoc.org/cgi-bin/bsc_ows.asp?",
		// {typename: "OWLS", maxfeatures: 10},
		// { featureClass: OpenLayers.Feature.WFS});

		String paramString = getJSOptionsMap(params);
		String optionsString = getJSOptionsMap(options);

		List<String> parameterList = new LinkedList<String>();

		parameterList.add(JSUtils.getQuotedString(getName()));
		parameterList.add(JSUtils.getQuotedString(getUrl()));

		if (params.size() > 0)
			parameterList.add(paramString);

		if (options.size() > 0)
			parameterList.add(optionsString);

		return getJSconstructor("OpenLayers.Layer.WFS", parameterList);
	}

}
