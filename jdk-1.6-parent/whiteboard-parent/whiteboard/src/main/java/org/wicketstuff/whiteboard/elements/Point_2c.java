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

public class Point_2c extends Element{
	protected int obj1;
	protected int obj2;
	protected int num;

	public Point_2c(int id, String label, String color, Boolean hidden, String type, Boolean trace, int obj1, int obj2, int num){
		this.id=id;
		this.label=label;
		this.color=color;
		this.hidden=hidden;
		this.type=type;
		this.trace=trace;
		this.obj1=obj1;
		this.obj2=obj2;
		this.num=num;
	}

	public Point_2c(JSONObject object) throws JSONException{
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
		this.obj1=(Integer)object.get("a");
		this.obj2=(Integer)object.get("b");
		this.num=(Integer)object.get("c");
	}

	public JSONObject getJSON(){
		JSONObject jsonObject=new JSONObject();
		try{
			jsonObject.put("id",id);
			jsonObject.put("type",type);
			jsonObject.put("obj1",obj1);
			jsonObject.put("obj2",obj2);
			jsonObject.put("num",num);
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

	public int getObj2(){
		return obj2;
	}

	public void setObj2(int obj2){
		this.obj2=obj2;
	}

	public int getObj1(){
		return obj1;
	}

	public void setObj1(int obj1){
		this.obj1=obj1;
	}

	public int getNum(){
		return num;
	}

	public void setNum(int num){
		this.num=num;
	}

}
