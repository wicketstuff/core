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
 * This class represent a line element on Whiteboard which is represented by three coordinates
 * 
 * @author andunslg
 */
public class CircleGeneral extends Element {
	private static final long serialVersionUID = 1L;
	protected double a;
	protected double b;
	protected double c;

	public CircleGeneral(int id, String label, String color, Boolean hidden, Type type, Boolean trace, double a,
			double b, double c) {
		super(id, label, color, hidden, type, trace);
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public CircleGeneral(JSONObject object) throws JSONException {
		super(object);
		this.type = Type.CircleGeneral;
		this.a = object.getInt("a");
		this.b = object.getInt("b");
		this.c = object.getInt("c");

	}

	/**
	 * Return a JSON object which represent the Element
	 * 
	 * @return JSON object with field values added
	 * @throws JSONException
	 */
	public JSONObject getJSON() throws JSONException {
		JSONObject jsonObject = super.getJSON(new JSONObject());
		jsonObject.put("a", a);
		jsonObject.put("b", b);
		jsonObject.put("c", c);
		return jsonObject;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}
}
