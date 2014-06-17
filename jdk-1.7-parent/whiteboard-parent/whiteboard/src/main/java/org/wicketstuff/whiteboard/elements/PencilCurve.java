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

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a curve element on Whiteboard which is represented by list of coordinate points
 * 
 * @author andunslg
 */
public class PencilCurve extends Element {
	private static final long serialVersionUID = 1L;
	protected int p0;
	protected List<Double[][]> points;

	public PencilCurve(int id, String label, String color, Boolean hidden, Type type, Boolean trace, int p0,
			List<Double[][]> points) {
		super(id, label, color, hidden, type, trace);
		this.p0 = p0;
		this.points = points;
	}

	public PencilCurve(JSONObject object) throws JSONException {
		super(object);
		this.type = Type.PencilCurve;
		this.p0 = object.getInt("p0");

		int pointCount = 0;

		while (true) {
			try {
				object.get("x" + pointCount);
				pointCount++;
			} catch (JSONException e) {
				break;
			}
		}

		this.points = new ArrayList<Double[][]>();

		for (int i = 0; i < pointCount; i++) {
			if (object.get("x" + i) instanceof Double) {
				Double[][] point = { { object.getDouble("x" + i), object.getDouble("y" + i) } };
				points.add(point);
			} else {
				double x = object.getInt("x" + i);
				double y = object.getInt("y" + i);
				Double[][] point = { { x, y } };
				points.add(point);
			}
		}
	}

	/**
	 * Return a JSON object which represent the Element
	 * 
	 * @return JSON object with field values added
	 * @throws JSONException
	 */
	public JSONObject getJSON() throws JSONException {
		JSONObject jsonObject = super.getJSON(new JSONObject());
		jsonObject.put("p0", p0);
		for (int i = 0; i < points.size(); i++) {
			jsonObject.put("x" + i, points.get(i)[0][0]);
			jsonObject.put("y" + i, points.get(i)[0][1]);
		}

		return jsonObject;
	}

	public List<Double[][]> getPoints() {
		return points;
	}

	public void setPoints(List<Double[][]> points) {
		this.points = points;
	}

	public int getP0() {
		return p0;
	}

	public void setP0(int p0) {
		this.p0 = p0;
	}
}
