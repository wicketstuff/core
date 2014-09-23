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
 * This class represent a pointer element on Whiteboard which is represented by a coordinate and a blinking time
 * 
 * @author andunslg
 */
public class PencilPointer extends Element {
	private static final long serialVersionUID = 1L;
	protected int p;
	protected int age;

	public PencilPointer(int id, String label, String color, Boolean hidden, Type type, Boolean trace, int p, int age) {
		super(id, label, color, hidden, type, trace);
		this.p = p;
		this.age = age;
	}

	public PencilPointer(JSONObject object) throws JSONException {
		super(object);
		this.type = Type.PencilPointer;
		this.p = object.getInt("p");
		this.age = "undefined".equals(object.get("age")) ? 1000 : object.getInt("age");
	}

	/**
	 * Return a JSON object which represent the Element
	 * 
	 * @return JSON object with field values added
	 * @throws JSONException
	 */
	public JSONObject getJSON() throws JSONException {
		JSONObject jsonObject = super.getJSON(new JSONObject());
		jsonObject.put("p", p);
		jsonObject.put("age", age);

		return jsonObject;
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
