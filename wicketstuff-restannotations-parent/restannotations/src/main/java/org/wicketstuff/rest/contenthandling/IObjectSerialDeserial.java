/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.rest.contenthandling;

/**
 * General interface to implement object serializers/deserializers.
 * 
 * @author andrea del bene
 * 
 */
public interface IObjectSerialDeserial<T>
{

	/**
	 * Returns a given representation of the target object.
	 * 
	 * @param targetObject
	 *            the object to convert to the given format.
	 * @param mimeType
	 *            the target MIME type.
	 * @return the representation of the object.
	 */
	public T serializeObject(Object target, String mimeType);

	/**
	 * Extract an object instance from a given source object.
	 * 
	 * @param <T>
	 *            the source object type.
	 * @param source
	 *            the source object.
	 * @param targetClass
	 *            the type of the returned object.
	 * @param mimeType
	 *            the target MIME type.
	 * @return the extracted object.
	 */
	public <E> E deserializeObject(T source, Class<E> targetClass, String mimeType);
}
