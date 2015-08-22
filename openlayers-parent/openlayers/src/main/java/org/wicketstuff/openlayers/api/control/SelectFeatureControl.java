package org.wicketstuff.openlayers.api.control;

import java.util.Map;

import org.wicketstuff.openlayers.IOpenLayersMap;

public class SelectFeatureControl extends AbstractControl
{
	private static final long serialVersionUID = -3832954618666235621L;
	private Map<String, String> parameters = null;

	public SelectFeatureControl()
	{
		this(null);
	}

	public SelectFeatureControl(Map<String, String> parameters)
	{
		super("SelectFeature", false);
		this.parameters = parameters;
	}

	@Override
	public String getJSadd(IOpenLayersMap map)
	{
		if (parameters == null)
			return super.getJSadd(map);
		return super.getJSadd(map, parameters) + super.getJSinvoke(map, "activate()");
	}

	public void setParameters(Map<String, String> parameters)
	{
		this.parameters = parameters;
	}

	public Map<String, String> getParameters()
	{
		return parameters;
	}
}
