package org.wicketstuff.openlayers.api.layer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.behavior.HeaderContributor;
import org.wicketstuff.openlayers.IOpenLayersMap;
import org.wicketstuff.openlayers.js.Constructor;
import org.wicketstuff.openlayers.js.JSUtils;
import org.wicketstuff.openlayers.js.ObjectLiteral;

/**
 * 
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 * 
 */
public abstract class Layer {
	private String name;
	private final String baseVariableName;
	private Map<String, String> onEventHandlers;

	/**
	 * 
	 */
	protected Layer(String baseVariableName) {
		super();
		this.baseVariableName = baseVariableName;
		this.onEventHandlers = new LinkedHashMap<String, String>();

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public final String getId() {
		return String.valueOf(System.identityHashCode(this));
	}

	public final String getJSAddLayer(IOpenLayersMap map) {
		StringBuffer buf = new StringBuffer();

		buf.append("var "
				+ getVariableName()
				+ " = "
				+ getJSconstructor()
				+ ";\n"
				+ map.getJSinvoke("addLayer(" + getVariableName() + ", "
						+ getId() + ")"));

		if (onEventHandlers.size() > 0) {
			// add event handler registration

			buf.append("\n");

			// 'featureselected': function(feature) {
			// $('counter').innerHTML = this.selectedFeatures.length;
			// }

			buf.append(getVariableName() + ".events.on (");
			
			List<String>eventHandlerList = new ArrayList<String>();

			for (String event : onEventHandlers.keySet()) {

				StringBuffer eventHandlerBuffer = new StringBuffer();

				eventHandlerBuffer.append("{\n");

				eventHandlerBuffer.append(JSUtils.getQuotedString(event));
				
				eventHandlerBuffer.append(" : ");
				
				eventHandlerBuffer.append("function (e) {");
				
				eventHandlerBuffer.append(onEventHandlers.get(event));
				
				eventHandlerBuffer.append("}\n");
				
				eventHandlerBuffer.append("}\n");
				
				eventHandlerList.add(eventHandlerBuffer.toString());

			}
			
			for (int i = 0; i < eventHandlerList.size(); i++) {
				
				if (i != 0)
					buf.append(", ");
				
				buf.append(eventHandlerList.get(i));
			}
			
			buf.append(");\n");
		}

		return buf.toString();
	}

	public abstract List<HeaderContributor> getHeaderContributors();

	public abstract String getJSconstructor();

	/**
	 * A helper to build the { ... } options list from a map.
	 * 
	 * @param options
	 * @return
	 */
	protected String getJSOptionsMap(Map<String, String> options) {

		if (options == null || options.size() == 0)
			return null;

		ObjectLiteral builder = new ObjectLiteral();

		for (String key : options.keySet()) {

			builder.set(key, options.get(key));

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

	protected String getJSconstructor(String javascriptTypeName,
			List<String> parameterList) {

		Constructor c = new Constructor(javascriptTypeName);

		for (String parameter : parameterList) {

			c.add(parameter);

		}

		return c.toJS();
	}

	/**
	 * @return variable name to use for this id.
	 */
	public String getVariableName() {

		return baseVariableName + getId();
	}

	
	/**
	 * @param event
	 * @param eventHandlingJavascript
	 
	 This =
	  <code>
	  vectors.events.on({
        'featureselected': function(feature) {
            (code goes here)
        },
        'featureunselected': function(feature) {
            (code goes here)
        }
    });
    </code>
	 * 
	 */
	public void registerOnEventJavascript(String event,
			String eventHandlingJavascript) {

		onEventHandlers.put(event, eventHandlingJavascript);
	}
}
