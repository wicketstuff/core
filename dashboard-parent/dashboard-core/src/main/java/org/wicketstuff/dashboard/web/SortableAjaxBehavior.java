/*
 * Copyright 2012 Decebal Suiu
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.wicketstuff.dashboard.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.wicketstuff.dashboard.WidgetLocation;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;

/**
 * @author Decebal Suiu
 */
public abstract class SortableAjaxBehavior extends AbstractDefaultAjaxBehavior {
	private static final long serialVersionUID = 1L;

	/** Sorted identifiant into the request */
	private static final String JSON_DATA = "data";

	public abstract void onSort(AjaxRequestTarget target, Map<String, WidgetLocation> widgetLocations);

	@Override
	protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
		super.updateAjaxAttributes(attributes);

		attributes.getDynamicExtraParameters()
			.add("var data = serializeWidgetLocations();" +
				"return {'" + JSON_DATA + "': data};");
	}

	@Override
	protected void respond(AjaxRequestTarget target) {
		Map<String, WidgetLocation> locations = new HashMap<String, WidgetLocation>();
		String jsonData = getComponent().getRequest().getRequestParameters().getParameterValue(JSON_DATA).toString();
		JSONArray arr = new JSONArray(jsonData);
		for (int i = 0; i < arr.length(); ++i) {
			JSONObject obj = arr.getJSONObject(i);
			locations.put(obj.getString("widget"),
					new WidgetLocation(obj.getInt("column"), obj.getInt("sortIndex")));
		}
		onSort(target, locations);
	}
}
