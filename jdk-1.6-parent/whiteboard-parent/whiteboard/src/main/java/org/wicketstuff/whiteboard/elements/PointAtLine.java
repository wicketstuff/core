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

public class PointAtLine extends Element{
	protected int obj;
	protected double t;

	public PointAtLine(int id, String label, String color, Boolean hidden, String type, Boolean trace, double t, int obj){
		this.id=id;
		this.label=label;
		this.color=color;
		this.hidden=hidden;
		this.type=type;
		this.trace=trace;
		this.t=t;
		this.obj=obj;
	}

	public PointAtLine(JSONObject object) throws JSONException{
		this.id=(Integer)object.get("id");

		try{
			this.label=(String)object.get("label");
		}catch(JSONException e){
			//Add Error Handling
		}

		try{
			this.color=(String)object.get("color");
		}catch(JSONException e){
			//Add Error Handling
		}

		try{
			this.trace=(Boolean)object.get("trace");
		}catch(JSONException e){
			//Add Error Handling
		}

		try{
			this.hidden=(Boolean)object.get("hidden");
		}catch(JSONException e){
			//Add Error Handling
		}

		this.type=(String)object.get("type");
		this.obj=(Integer)object.get("obj");
		this.t=(Double)object.get("t");
	}

	public JSONObject getJSON(){
		JSONObject jsonObject=new JSONObject();
		try{
			jsonObject.put("id",id);
			jsonObject.put("type",type);
			jsonObject.put("obj",obj);
			jsonObject.put("t",t);
			if(label!=null){
				jsonObject.put("label",label);
			}
			if(color!=null){
				jsonObject.put("color",color);
			}
			if(hidden!=null){
				jsonObject.put("hidden",hidden);
			}
			if(trace!=null){
				jsonObject.put("trace",trace);
			}

		}catch(JSONException e){
			e.printStackTrace();
		}

		return jsonObject;
	}


	public int getObj(){
		return obj;
	}

	public void setObj(int obj){
		this.obj=obj;
	}

	public double getT(){
		return t;
	}

	public void setT(double t){
		this.t=t;
	}

}
