package org.wicketstuff.openlayers.api.layer;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.wicketstuff.openlayers.js.JSUtils;

/**
 * 
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 * 
 */
public class WMS extends Layer implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String url;
	/**
	 * can be any of : layers: 'topp:AL_03C5E', styles: '', height: '750', width: '800', srs:
	 * 'EPSG:2400', format: 'image/png', tiled: 'true', tilesOrigin :
	 * "1319519.4432429108,6224522.644438478"
	 * 
	 * 
	 */
	private HashMap<String, String> options = new HashMap<String, String>();
	private final HashMap<String, String> extraOptions;

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public WMS(String name, String url, HashMap<String, String> options)
	{
		this(name, url, options, null);
	}

	/**
	 * 
	 * @param name
	 * @param url
	 * @param options
	 *            layer options
	 * @param extraOptions
	 *            things like baseLayer: need to be placed in extraOptions.
	 */
	public WMS(String name, String url, HashMap<String, String> options,
		HashMap<String, String> extraOptions)
	{
		super();
		this.extraOptions = extraOptions;
		setName(name);
		this.url = url;
		this.options = options;

	}

	/*
	 * name, url, options
	 */

	@Override
	public String getJSconstructor()
	{

		String options = super.getJSOptionsMap(this.options);
		String extraOptions = super.getJSOptionsMap(this.extraOptions);

		if (extraOptions == null)
			return getJSconstructor(
				"OpenLayers.Layer.WMS",
				Arrays.asList(new String[] { JSUtils.getQuotedString(getName()),
						JSUtils.getQuotedString(getUrl()), options }));
		else
			return getJSconstructor(
				"OpenLayers.Layer.WMS",
				Arrays.asList(new String[] { JSUtils.getQuotedString(getName()),
						JSUtils.getQuotedString(getUrl()), options, extraOptions }));

	}

	@Override
	protected void bindHeaderContributors(IHeaderResponse response)
	{
		// mocleiri: implementation is intentionally empty.

	}


}
