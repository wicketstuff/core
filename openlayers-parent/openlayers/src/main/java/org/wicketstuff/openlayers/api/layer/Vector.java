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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.behavior.HeaderContributor;
import org.wicketstuff.openlayers.js.Constructor;

public class Vector extends Layer implements Serializable {

	private HashMap<String, String> options = new HashMap<String, String>();

	public Vector(String name) {
		this(name, null);
	}

	public Vector(String name, HashMap<String, String> options) {
		super();
		setName(name);
		this.options = options;
	}

	@Override
	public List<HeaderContributor> getHeaderContributors() {
		return new ArrayList<HeaderContributor>();
	}

	@Override
	public String getJSconstructor() {
		String optionlist = "";
		if (options != null) {
			boolean first = true;
			for (String key : options.keySet()) {
				if (first) {
					first = false;
				} else {
					optionlist += ",\n";
				}
				optionlist += key + ": " + options.get(key);
			}
		}
		return new Constructor("OpenLayers.Layer.Vector").add(
				"'" + getName() + "'").add("{" + optionlist + "}").toJS();
	}
}
