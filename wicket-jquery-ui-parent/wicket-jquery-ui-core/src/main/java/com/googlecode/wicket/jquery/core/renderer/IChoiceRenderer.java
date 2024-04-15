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
package com.googlecode.wicket.jquery.core.renderer;

/**
 * Provides the ability to get a specific value/text (likely from properties) of a bean type, for rendering purpose
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 */
public interface IChoiceRenderer<T> extends ITextRenderer<T>
{
	/**
	 * Gets the field name that will represents the 'value' of an object.
	 *
	 * @return the name of the text field
	 */
	String getValueField();

	/**
	 * Gets the value of an object.
	 *
	 * @param object the T object
	 * @return the value
	 */
	String getValue(T object);
}
