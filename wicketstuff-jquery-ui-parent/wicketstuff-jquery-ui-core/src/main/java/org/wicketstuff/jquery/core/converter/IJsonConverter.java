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
package org.wicketstuff.jquery.core.converter;

import com.github.openjson.JSONObject;

/**
 * Provides a converter for building {@code T}{@code s} as {@link JSONObject}, and vice-versa
 * 
 * @param <T> the object type
 * @author Sebastien Briquet - sebfz1
 *
 */
public interface IJsonConverter<T>
{
	/**
	 * Converts a {@code T} object to a {@link JSONObject}
	 *
	 * @param object the {@code T} object
	 * @return the {@link JSONObject}
	 */
	JSONObject toJson(T object);

	/**
	 * Converts a {@link JSONObject} to a {code T} object
	 *
	 * @param object the {@code JSONObject}
	 * @return the {code T} object
	 */
	T toObject(JSONObject object);
}
