package org.wicketstuff.openlayers.api.layer;

import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.wicketstuff.openlayers.IOpenLayersMap;
import org.wicketstuff.openlayers.js.Constructor;
import org.wicketstuff.openlayers.js.ObjectLiteral;

/**
 * 
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 * 
 */
public abstract class Layer
{
	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getId()
	{
		return String.valueOf(System.identityHashCode(this));
	}

	public final String getJSAddLayer(IOpenLayersMap map)
	{
		return "var layer" + getId() + " = " + getJSconstructor() + ";\n" +
			map.getJSinvoke("addLayer(layer" + getId() + ", " + getId() + ")");
	}

	public final void bindHeaderContributors(Component c)
	{
		c.add(new Behavior()
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void renderHead(Component c, IHeaderResponse response)
			{
				bindHeaderContributors(response);
			}


		});
	}

	protected abstract void bindHeaderContributors(IHeaderResponse response);

	public abstract String getJSconstructor();

	/**
	 * A helper to build the { ... } options list from a map.
	 * 
	 * @param options
	 * @return
	 */
	protected String getJSOptionsMap(Map<String, String> options)
	{

		if (options == null || options.size() == 0)
			return null;

		ObjectLiteral builder = new ObjectLiteral();

		for (Map.Entry<String, String> entry : options.entrySet())
		{

			builder.set(entry.getKey(), entry.getValue());

		}

		return builder.toJS();
	}


	/**
	 * A convience method for the common initialization case.
	 * 
	 * @param javascriptTypeName
	 * @param options
	 * @return the contextualized contstructor for the layer.
	 */


	protected String getJSconstructor(String javascriptTypeName, List<String> parameterList)
	{

		Constructor c = new Constructor(javascriptTypeName);

		for (String parameter : parameterList)
		{

			c.add(parameter);

		}

		return c.toJS();
	}
}
