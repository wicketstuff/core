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
package org.wicketstuff.minis.model;

import java.io.Serializable;

/**
 * A codec used to convert an object between its attached and detached representations. Detached
 * representation should be something small so it is suitable for storage inside session. For
 * example, in a database entity the detached state would be the primary key of that entity.
 * 
 * @author Igor Vaynberg (ivaynberg)
 * 
 * @param <T>
 */
public interface IDetachCodec<T>
{
	/**
	 * Converts an object to its detached representation
	 * 
	 * @param object
	 * @return detached representation of <code>object</code>
	 */
	Serializable detach(T object);

	/**
	 * Converts an object into its attached representation. Usually the returned object is equal to
	 * the object that was passed into {@link #detach(Object)} method to obtain the detached state.
	 * 
	 * @param detached
	 *            detached representation obtained via a previous call to {@link #detach(Object)}
	 * @return attached representation
	 */
	T attach(Serializable detached);
}
