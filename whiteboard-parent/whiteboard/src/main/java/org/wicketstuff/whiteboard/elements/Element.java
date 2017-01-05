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

import java.io.Serializable;

/**
 * This class is the parent of all the geometric and textual elements in whiteboard Hold common fields for each element
 * 
 * @author andunslg
 */
public abstract class Element implements Serializable {
	private static final long serialVersionUID = 1L;
	protected int id; // index of the element in the collection list
	protected String label; // text label for the element
	protected String color; // color of the element
	protected Boolean trace; // flag if the element should left trace when moved
	protected Boolean hidden; // flag if the element hidden (not visible)
	protected Type type; // type (class) of the element

	public enum Type {
		Circle_3p
		, CircleGeneral
		, Line_2p
		, LineGeneral
		, PencilArrow
		, PencilCircle
		, PencilCurve
		, PencilFreeLine
		, PencilPointAtRect
		, PencilPointer
		, PencilRect
		, PencilUnderline
		, Point_2c
		, Point_2l
		, PointAtCircle
		, PointAtLine
		, PointFree
		, Point_lc
		, Segment
		, Text
		, ClipArt
	}

	public Element(int id, String label, String color, Boolean hidden, Type type, Boolean trace) {
		this.id = id;
		this.label = label;
		this.color = color;
		this.hidden = hidden;
		this.type = type;
		this.trace = trace;
	}

	public Element(JSONObject object) throws JSONException {
		this.id = object.optInt("id");
		this.label = object.optString("label");
		this.color = object.optString("color");
		this.trace = object.optBoolean("trace");
		this.hidden = object.optBoolean("hidden");
	}

	public abstract JSONObject getJSON() throws JSONException;

	/**
	 * Return a JSON object which represent the Element
	 * 
	 * @param obj
	 *            JSON object which will will be used to add field values
	 * @return JSON object with field values added
	 * @throws JSONException
	 */
	public JSONObject getJSON(JSONObject obj) throws JSONException {
		obj.put("id", id);
		obj.put("type", type);
		if (label != null) {
			obj.put("label", label);
		}
		if (color != null) {
			obj.put("color", color);
		}
		if (hidden != null) {
			obj.put("hidden", hidden);
		}
		if (trace != null) {
			obj.put("trace", trace);
		}
		return obj;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Boolean isTrace() {
		return trace;
	}

	public void setTrace(Boolean trace) {
		this.trace = trace;
	}

	public Boolean isHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
