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
 * This class represent a underline element on Whiteboard which is represented by starting point of the underline ,
 * ending point of the underline nad the thickness of the underline.
 * 
 * @author andunslg
 */
public class PencilUnderline extends Element {
	private static final long serialVersionUID = 1L;
	protected int p1;
	protected int p2;
	protected int thick;

	public PencilUnderline(int id, String label, String color, Boolean hidden, Type type, Boolean trace, int p1,
			int p2, int thick) {
		super(id, label, color, hidden, type, trace);
		this.p1 = p1;
		this.p2 = p2;
		this.thick = thick;
	}

	public PencilUnderline(JSONObject object) throws JSONException {
		super(object);
		this.type = Type.PencilUnderline;
		this.p1 = object.getInt("p1");
		this.p2 = object.getInt("p2");
		this.thick = object.getInt("thick");
	}

	/**
	 * Return a JSON object which represent the Element
	 * 
	 * @return JSON object with field values added
	 * @throws JSONException
	 */
	public JSONObject getJSON() throws JSONException {
		JSONObject jsonObject = super.getJSON(new JSONObject());
		jsonObject.put("p1", p1);
		jsonObject.put("p2", p2);
		jsonObject.put("thick", thick);

		return jsonObject;
	}

	public int getP2() {
		return p2;
	}

	public void setP2(int p2) {
		this.p2 = p2;
	}

	public int getP1() {
		return p1;
	}

	public void setP1(int p1) {
		this.p1 = p1;
	}

	public int getThick() {
		return thick;
	}

	public void setThick(int thick) {
		this.thick = thick;
	}
}
