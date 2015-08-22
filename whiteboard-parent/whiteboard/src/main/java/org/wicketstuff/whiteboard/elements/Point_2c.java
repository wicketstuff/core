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
public class Point_2c extends Element {
	private static final long serialVersionUID = 1L;
	protected int obj1;
	protected int obj2;
	protected int num;

	public Point_2c(int id, String label, String color, Boolean hidden, Type type, Boolean trace, int obj1, int obj2,
			int num) {
		super(id, label, color, hidden, type, trace);
		this.obj1 = obj1;
		this.obj2 = obj2;
		this.num = num;
	}

	public Point_2c(JSONObject object) throws JSONException {
		super(object);
		this.type = Type.Point_2c;
		this.obj1 = object.getInt("a");
		this.obj2 = object.getInt("b");
		this.num = object.getInt("c");
	}

	/**
	 * Return a JSON object which represent the Element
	 * 
	 * @return JSON object with field values added
	 * @throws JSONException
	 */
	public JSONObject getJSON() throws JSONException {
		JSONObject jsonObject = super.getJSON(new JSONObject());
		jsonObject.put("obj1", obj1);
		jsonObject.put("obj2", obj2);
		jsonObject.put("num", num);

		return jsonObject;
	}

	public int getObj2() {
		return obj2;
	}

	public void setObj2(int obj2) {
		this.obj2 = obj2;
	}

	public int getObj1() {
		return obj1;
	}

	public void setObj1(int obj1) {
		this.obj1 = obj1;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
