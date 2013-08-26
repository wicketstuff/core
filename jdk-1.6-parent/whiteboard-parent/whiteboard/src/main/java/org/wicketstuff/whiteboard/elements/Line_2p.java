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

public class Line_2p extends Element{
	protected int p1;
	protected int p2;

	public Line_2p(int id, String label, String color, Boolean hidden, String type, Boolean trace, int p1, int p2){
		this.id=id;
		this.label=label;
		this.color=color;
		this.hidden=hidden;
		this.type=type;
		this.trace=trace;
		this.p1=p1;
		this.p2=p2;
	}

	public Line_2p(JSONObject object) throws JSONException{
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
		this.p1=(Integer)object.get("p1");
		this.p2=(Integer)object.get("p2");

	}

	public JSONObject getJSON(){
		JSONObject jsonObject=new JSONObject();
		try{
			jsonObject.put("id",id);
			jsonObject.put("type",type);
			jsonObject.put("p1",p1);
			jsonObject.put("p2",p2);
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

	public int getP2(){
		return p2;
	}

	public void setP2(int p2){
		this.p2=p2;
	}

	public int getP1(){
		return p1;
	}

	public void setP1(int p1){
		this.p1=p1;
	}

}
