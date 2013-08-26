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
package com.googlecode.wicket.jquery.ui.plugins.whiteboard.elements;

import org.apache.wicket.ajax.json.JSONObject;

public abstract class Element{
	protected int id;			//index of the element in the collection list
	protected String label;		//	text label for the element
	protected String color;		//	color of the element
	protected Boolean trace;	//	flag if the element should left trace when moved
	protected Boolean hidden;	//flag if the element hidden (not visible)
	protected String type;		// type (class) of the element

	public abstract JSONObject getJSON();

	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id=id;
	}

	public String getLabel(){
		return label;
	}

	public void setLabel(String label){
		this.label=label;
	}

	public String getColor(){
		return color;
	}

	public void setColor(String color){
		this.color=color;
	}

	public Boolean isTrace(){
		return trace;
	}

	public void setTrace(Boolean trace){
		this.trace=trace;
	}

	public Boolean isHidden(){
		return hidden;
	}

	public void setHidden(Boolean hidden){
		this.hidden=hidden;
	}

	public String getType(){
		return type;
	}

	public void setType(String type){
		this.type=type;
	}


}
