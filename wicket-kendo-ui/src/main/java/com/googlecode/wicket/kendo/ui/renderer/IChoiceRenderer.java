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
package com.googlecode.wicket.kendo.ui.renderer;

import com.googlecode.wicket.jquery.core.renderer.ITextRenderer;

/**
 * Specifies a choice renderer for Kendo UI datasources.
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the model object type
 */
public interface IChoiceRenderer<T> extends ITextRenderer<T>
{
	/**
	 * Gets the name of the field that acts as the 'dataTextField' in the JSON response.
	 *
	 * @return the name of the text field
	 */
	public abstract String getTextField();

	/**
	 * Gets the name of the field that acts as the 'dataValueField' in the JSON response.
	 *
	 * @return the name of the value field
	 */
	public abstract String getValueField();

	/**
	 * Gets the value that should be renderer for the supplied object
	 *
	 * @param object the T object
	 * @return the value
	 */
	public abstract String getValue(T object);

	/**
	 * Gets the JSON representation of the supplied object
	 *
	 * @param object the object
	 * @return the JSON representation of the object
	 */
	public abstract String toJson(T object);
}
