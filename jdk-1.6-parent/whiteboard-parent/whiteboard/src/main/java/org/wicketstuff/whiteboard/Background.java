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
package org.wicketstuff.whiteboard;

import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.wicketstuff.whiteboard.elements.Element;

public class Background{
	private String type;
	private String url;
	private Double width;
	private Double height;
	private Double left;
	private Double top;

	public Background(String type, String url, Double width, Double height, Double left, Double top){
		this.type=type;
		this.url=url;
		this.width=width;
		this.height=height;
		this.left=left;
		this.top=top;
	}

	public Background(JSONObject object) throws JSONException{
		this.type="Background";
		this.url=object.getString("url");
		if(object.isNull("width")) {
			this.width=object.getDouble("width");
		}
		else {
			width=null;
		}
		if(object.isNull("height")) {
			this.height=object.getDouble("height");
		}
		else {
			height=null;
		}
		if(object.isNull("left")) {
			this.left=object.getDouble("left");
		}
		else {
			left=null;
		}
		if(object.isNull("top")) {
			this.top=object.getDouble("top");
		}
		else {
			top=null;
		}

	}

	public JSONObject getJSON(JSONObject obj) throws JSONException{
		obj.put("url",url);
		obj.put("type",type);
		if(width!=null){
			obj.put("width",width);
		}
		if(height!=null){
			obj.put("height",height);
		}
		if(left!=null){
			obj.put("left",left);
		}
		if(top!=null){
			obj.put("top",top);
		}
		return obj;
	}

	public String getType(){
		return type;
	}

	public void setType(String type){
		this.type=type;
	}

	public String getUrl(){
		return url;
	}

	public void setUrl(String url){
		this.url=url;
	}

	public Double getWidth(){
		return width;
	}

	public void setWidth(Double width){
		this.width=width;
	}

	public Double getHeight(){
		return height;
	}

	public void setHeight(Double height){
		this.height=height;
	}

	public Double getLeft(){
		return left;
	}

	public void setLeft(Double left){
		this.left=left;
	}

	public Double getTop(){
		return top;
	}

	public void setTop(Double top){
		this.top=top;
	}
}
