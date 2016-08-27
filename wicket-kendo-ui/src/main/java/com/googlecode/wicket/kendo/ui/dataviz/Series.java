/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.wicket.kendo.ui.dataviz;

import java.io.Serializable;

/**
 * Provides a chart series object.<br/>
 * <b>Note:</b> The field should correspond to a property of the {@link Chart}'s model object  
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class Series implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private final String name;
	private final String field;

	/**
	 * Constructor
	 */
	public Series(String name, String field)
	{
		this.name = name;
		this.field = field;
	}

	public String getName()
	{
		return this.name;
	}
	
	public String getField()
	{
		return this.field;
	}
}
