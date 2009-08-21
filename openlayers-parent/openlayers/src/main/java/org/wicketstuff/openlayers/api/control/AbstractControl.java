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

import org.apache.wicket.model.IModel;
import org.wicketstuff.openlayers.IOpenLayersMap;
import org.wicketstuff.openlayers.api.IJavascriptComponent;

/**
 * @author Michael O'Cleirigh
 * 
 *         Provides a base class for the IJavascriptControl interface that
 *         provides a default implementation for the javascript initialization
 *         and removal methods.
 * 
 */
public class AbstractControl implements IJavascriptComponent {

	
	private static final long serialVersionUID = -9139375207645853318L;

	private final boolean externalizable;

	private final String name;
	
	private StringBuffer eventJavascript = new StringBuffer();
	

	protected AbstractControl(String name, boolean externalizable) {
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
	 * @return the initialization javascript that adds this control to the map using the parameters if given.
	 */
	protected String getJSadd(IOpenLayersMap map, Map<String, String> parameters) {

		if (map.isExternalControls() && externalizable) {

			parameters
					.put("div", "OpenLayers.Util.getElement('wicketOpenlayer"
							+ this.name + "')");
		}

		StringBuffer buf = new StringBuffer();

		List<String>keyList = new LinkedList<String>();
		
		keyList.addAll(parameters.keySet());
		
		if (keyList.size() > 0) {
			int last = keyList.size() - 1;

			for (int i = 0; i < last; i++) {

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

		return getJSadd(map, buf.toString());

	}

	/**
	 * Calls map.addControl() for the generated initialization javascript using the parameterString if present.
	 * 
	 * @param map 
	 * @param parameterString contains comma separated list of parameters
	 * @return
	 */
	protected String getJSadd(IOpenLayersMap map, String parameterString) {

		String invocation = null;
		
		String id = getId();
		
		if (parameterString.length() == 0) {
			invocation =  map.getJSinvoke("addControl('" + id
					+ "', new OpenLayers.Control." + this.name + "())");
		} else {
			invocation =  map.getJSinvoke("addControl('" + id
					+ "', new OpenLayers.Control." + this.name + "({"
					+ parameterString + "}))");
		}
		
		if (eventJavascript.length() > 0) {
			// prepend the creational logic to the event registration javascript.
			return eventJavascript.insert(0, invocation).toString();
		}
		else
			return invocation;
	}

	public String getJSadd(IOpenLayersMap map) {

		return getJSadd(map, "");
	}

	public String getJSremove(IOpenLayersMap map) {
		return map.getJSinvoke("removeControl('" + this.name + "')");
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.openlayers.api.IJavascriptComponent#getId()
	 */
	public String getId() {
		return this.name + String.valueOf(System.identityHashCode(this));
	}
	
	/**
	 * 
	 * Allows javascript to be wired into events on this control.
	 * 
	 * The name of the event object in scope for the model value is 'evt'. 
	 * <p>
	 * <blockquote>
	 * 		 control.events.register("featureselected", this, function(evt) {
     *       modelValue <-- this is the content from the model.
     *        });
	 * </blockquote>
	 * <p>
	 * 
	 * Note: This is not the method to use if you want to use ajax and have wicket to the event processing.
	 * @param map the map object that the control is bound to (will be bound to).
	 * @param event the name of the OpenLayersEvent
	 * @param model an IModel<String> that provides the javascript text to be registered on the event.
	 * 
	 */
	public void registerJavascriptEvent(IOpenLayersMap map, String event, IModel<String> eventHandlingJavascriptModel) {
		
		eventJavascript.append(map.getJSinvokeNoLineEnd("controls['"+getId()+"'].events.register(\""));
		eventJavascript.append(event);
		eventJavascript.append("\", this, function (evt) {");
		eventJavascript.append(eventHandlingJavascriptModel.getObject());
		eventJavascript.append("});\n");

		
	}

}
