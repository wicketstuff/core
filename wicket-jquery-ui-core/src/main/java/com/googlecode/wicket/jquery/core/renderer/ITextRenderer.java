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

import java.util.List;

import org.apache.wicket.util.io.IClusterable;

/**
 * Provides the ability to get a specific text (likely from a property) of a bean type, for rendering purpose
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 */
public interface ITextRenderer<T> extends IClusterable
{
	/**
	 * Gets the field name that will represents the 'text' of an object.
	 *
	 * @return the name of the text field
	 */
	String getTextField();

	/**
	 * Gets the text representation of an object.
	 *
	 * @param object the T object
	 * @return the text
	 */
	String getText(T object);

	/**
	 * Gets the text representation of an object, for its given property.
	 *
	 * @param object the T object
	 * @param expression the property expression
	 * @return the text
	 */
	String getText(T object, String expression);

	/**
	 * Gets the list of fields used by this renderer. This can be used to prevent duplication on rendering (if a template uses same fields for instance)
	 * 
	 * @return the {@code List} of fields
	 */
	List<String> getFields();

	/**
	 * Indicates whether a search criteria matches the text representation of the supplied object
	 *
	 * @param object the T object
	 * @param search the criteria
	 * @param sensitive whether the search is case sensitive or not
	 * @return {@code true} or {@code false}
	 */
	boolean match(T object, String search, boolean sensitive);

	/**
	 * Renders the object. This typically returns a JSON body (without brackets)
	 * 
	 * @param object the object to render
	 * @return the rendered object
	 */
	String render(T object);
}
