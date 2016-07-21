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
 * This class represent a point element on Whiteboard which is on a line element
 * 
 * @author andunslg
 */
public class PointAtLine extends Element {
	private static final long serialVersionUID = 1L;
	protected int obj;
	protected double t;

	public PointAtLine(int id, String label, String color, Boolean hidden, Type type, Boolean trace, double t, int obj) {
		super(id, label, color, hidden, type, trace);
		this.t = t;
		this.obj = obj;
	}

	public PointAtLine(JSONObject object) throws JSONException {
		super(object);
		this.type = Type.PointAtLine;
		this.obj = object.getInt("obj");
		this.t = object.getDouble("t");
	}

	/**
	 * Return a JSON object which represent the Element
	 * 
	 * @return JSON object with field values added
	 * @throws JSONException
	 */
	public JSONObject getJSON() throws JSONException {
		JSONObject jsonObject = super.getJSON(new JSONObject());
		jsonObject.put("obj", obj);
		jsonObject.put("t", t);

		return jsonObject;
	}

	public int getObj() {
		return obj;
	}

	public void setObj(int obj) {
		this.obj = obj;
	}

	public double getT() {
		return t;
	}

	public void setT(double t) {
		this.t = t;
	}
}
