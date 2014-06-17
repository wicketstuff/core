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
 * This class represent a point element on Whiteboard
 * 
 * @author andunslg
 */
public class PointFree extends Element {
	private static final long serialVersionUID = 1L;
	protected double x; // x coordinate of the point
	protected double y; // y coordinate of the point

	public PointFree(int id, String label, String color, Boolean hidden, Type type, Boolean trace, double x, double y) {
		super(id, label, color, hidden, type, trace);
		this.x = x;
		this.y = y;
	}

	public PointFree(JSONObject object) throws JSONException {
		super(object);
		this.type = Type.PointFree;
		this.x = object.get("x") instanceof Double ? object.getDouble("x") : object.getInt("x");
		this.y = object.get("y") instanceof Double ? object.getDouble("y") : object.getInt("y");
	}

	/**
	 * Return a JSON object which represent the Element
	 * 
	 * @return JSON object with field values added
	 * @throws JSONException
	 */
	public JSONObject getJSON() throws JSONException {
		JSONObject jsonObject = super.getJSON(new JSONObject());
		jsonObject.put("x", x);
		jsonObject.put("y", y);

		return jsonObject;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}
