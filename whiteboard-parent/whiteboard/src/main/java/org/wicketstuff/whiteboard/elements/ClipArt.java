/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.whiteboard.elements;

import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;

/**
 * This class represent a clipArt picture element on Whiteboard
 * 
 * @author andunslg
 */
public class ClipArt extends Element {
	private static final long serialVersionUID = 1L;
	protected int r; // id of the rectangle object which holds the clipArt

	public ClipArt(int id, String label, String color, Boolean hidden, Type type, Boolean trace, int r) {
		super(id, label, color, hidden, type, trace);
		this.r = r;
	}

	public ClipArt(JSONObject object) throws JSONException {
		super(object);
		this.type = Type.ClipArt;
		this.r = object.getInt("r");
	}

	/**
	 * Return a JSON object which represent the Element
	 * 
	 * @return JSON object with field values added
	 * @throws JSONException
	 */
	public JSONObject getJSON() throws JSONException {
		JSONObject jsonObject = super.getJSON(new JSONObject());
		jsonObject.put("r", r);

		return jsonObject;
	}

	public double getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}
}
