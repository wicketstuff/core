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
package org.wicketstuff.openlayers.api.control;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.wicketstuff.openlayers.IOpenLayersMap;
import org.wicketstuff.openlayers.api.IJavascriptComponent;
import org.wicketstuff.openlayers.api.layer.Layer;

/**
 * @author Michael O'Cleirigh
 * 
 *         Provides a base class for the IJavascriptControl interface that provides a default
 *         implementation for the javascript initialization and removal methods.
 * 
 */
public class AbstractControl implements IJavascriptComponent
{


	private static final long serialVersionUID = -9139375207645853318L;

	private final boolean externalizable;

	private final String name;

	private List<Layer> layerList = null;

	private LinkedList<IModel<String>> eventJavascript = new LinkedList<IModel<String>>();


	protected AbstractControl(String name, boolean externalizable)
	{
		this.name = name;
		this.externalizable = externalizable;

	}

	/**
	 * A convenience wrapper to {@link #getJSadd(IOpenLayersMap, String)}
	 * 
	 * that builds the parameter string from the parameter map and then invokes the method.
	 * 
	 * @param map
	 * @param parameters
	 * @return the initialization javascript that adds this control to the map using the parameters
	 *         if given.
	 */
	protected String getJSadd(IOpenLayersMap map, Map<String, String> parameters)
	{

		return this.getJSadd(map, "OpenLayers.Control." + name, parameters);
	}

	protected String getJSadd(IOpenLayersMap map, String javascriptClassName,
		Map<String, String> parameters)
	{


		if (map.isExternalControls() && externalizable)
		{

			parameters.put("div", "OpenLayers.Util.getElement('wicketOpenlayer" + name + "')");
		}

		StringBuffer buf = new StringBuffer();

		List<String> keyList = new LinkedList<String>();

		keyList.addAll(parameters.keySet());

		if (keyList.size() > 0)
		{
			int last = keyList.size() - 1;

			for (int i = 0; i < last; i++)
			{

				String key = keyList.get(i);

				String value = parameters.get(key);

				buf.append(key);
				buf.append(": ");
				buf.append(value);

				buf.append(", ");
			}

			String key = keyList.get(last);
			String value = parameters.get(key);

			buf.append(key);
			buf.append(": ");
			buf.append(value);
		}

		return getJSadd(map, javascriptClassName, buf.toString());

	}

	/**
	 * Calls map.addControl() for the generated initialization javascript using the parameterString
	 * if present.
	 * 
	 * @param map
	 * @param parameterString
	 *            contains comma separated list of parameters
	 * @return
	 */
	protected String getJSadd(IOpenLayersMap map, String parameterString)
	{

		return this.getJSadd(map, "OpenLayers.Control." + name, parameterString);
	}

	/**
	 * 
	 * @param map
	 *            the openlayers map java binding.
	 * @param javascriptClassName
	 *            the name of the javascript class being invoked.
	 * @param parameterString
	 *            the constructor parameters if any.
	 * @return the javascript to invoke the map.addControl() method containing the initialized
	 *         constructor for the control type.
	 * 
	 */
	protected String getJSadd(IOpenLayersMap map, String javascriptClassName, String parameterString)
	{

		String invocation = null;

		String id = getId();

		StringBuffer layers = new StringBuffer();
		if (layerList != null && !layerList.isEmpty())
		{
			layers.append("[");
			for (Layer item : layerList)
			{
				if (layers.length() > 1)
					layers.append(", ");
				layers.append("layer" + item.getId());
			}
			layers.append("]");
		}

		if (parameterString.length() == 0)
		{
			invocation = map.getJSinvoke("addControl('" + id + "', new " + javascriptClassName +
				"(" + layers.toString() + "))");
		}
		else
		{
			invocation = map.getJSinvoke("addControl('" + id + "', new " + javascriptClassName +
				"(" + (layers.length() > 0 ? layers.toString() + ", " : "") + "{" +
				parameterString + "}))");
		}

		if (eventJavascript.size() > 0)
		{
			// prepend the creational logic to the event registration javascript.

			StringBuffer renderedJavascript = new StringBuffer(invocation);

			for (IModel<String> model : eventJavascript)
			{

				renderedJavascript.append(model.getObject());

			}
			return renderedJavascript.toString();
		}
		else
			return invocation;
	}

	public String getJSadd(IOpenLayersMap map)
	{

		return getJSadd(map, "");
	}

	public String getJSremove(IOpenLayersMap map)
	{
		return map.getJSinvoke("removeControl('" + name + "')");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wicketstuff.openlayers.api.IJavascriptComponent#getId()
	 */
	public String getId()
	{
		return name + String.valueOf(System.identityHashCode(this));
	}

	/**
	 * 
	 * Allows javascript to be wired into events on this control.
	 * 
	 * The name of the event object in scope for the model value is 'evt'.
	 * <p>
	 * <blockquote> control.events.register("featureselected", this, function(evt) { modelValue <--
	 * this is the content from the model. }); </blockquote>
	 * <p>
	 * 
	 * Note: This is not the method to use if you want to use ajax and have wicket to the event
	 * processing.
	 * 
	 * @param map
	 *            the map object that the control is bound to (will be bound to).
	 * @param event
	 *            the name of the OpenLayersEvent
	 * @param model
	 *            an IModel<String> that provides the javascript text to be registered on the event.
	 * 
	 */
	public void registerJavascriptEvent(final IOpenLayersMap map, final String event,
		final IModel<String> eventHandlingJavascriptModel)
	{

		// added through a model so that we can wait until the render phase before calculating the
// callback urls if the event handling javascript contains
		// wicket behaviours.
		eventJavascript.add(new AbstractReadOnlyModel<String>()
		{

			private static final long serialVersionUID = 1L;

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.apache.wicket.model.AbstractReadOnlyModel#getObject()
			 */
			@Override
			public String getObject()
			{

				StringBuffer buf = new StringBuffer();

				buf.append(map.getJSinvokeNoLineEnd("controls['" + getId() +
					"'].events.register(\""));
				buf.append(event);
				buf.append("\", this, function (evt) {");
				buf.append(eventHandlingJavascriptModel.getObject());
				buf.append("});\n");


				return buf.toString();
			}

		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wicketstuff.openlayers.api.IJavascriptComponent#getJSResourceReference()
	 */
	public JavaScriptResourceReference[] getJSResourceReferences()
	{
		// default is to not have any custom javascript for the control.
		return null;
	}

	/**
	 * A helper similiar to OpenLayersMap.getJSInvoke except instead of just getting the map context
	 * we get this control context from the root.
	 * 
	 * @param map
	 * @param invocation
	 * @return
	 */
	protected String getJSinvoke(IOpenLayersMap map, String invocation)
	{
		return map.getJSinvoke("controls['" + getId() + "']." + invocation);
	}

	public void setLayerList(List<Layer> layerList)
	{
		this.layerList = layerList;
	}

	public List<Layer> getLayerList()
	{
		return layerList;
	}
}
