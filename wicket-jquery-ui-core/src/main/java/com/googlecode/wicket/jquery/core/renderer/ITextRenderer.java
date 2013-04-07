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

import org.apache.wicket.util.io.IClusterable;

/**
 * Provides the capability to get a specific text (likely from a property) for a bean type, for rendering purpose
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 */
public interface ITextRenderer<T> extends IClusterable
{
	/**
	 * Gets the text that should be renderer for the supplied object
	 * @param object the T object
	 * @return the text
	 */
	String getText(T object);

	/**
	 * Gets the text that should be renderer for the supplied object, for the given property
	 * @param object the T object
	 * @param expression the property expression
	 * @return the text
	 */
	String getText(T object, String expression);
}
