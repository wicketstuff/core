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
package org.wicketstuff.lazymodel.reflect;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * A resolver of {@link Method}s.
 * 
 * @author svenmeier
 */
public interface IMethodResolver {

	/**
	 * Get method by identifier.
	 * 
	 * @param owner
	 *            the owning class
	 * @param id
	 *            method identifier
	 * @return method
	 */
	public Method getMethod(Class<?> owner, Serializable id);

	/**
	 * Get the identifier for the given method.
	 * 
	 * @param method
	 *            method
	 * @return method identifier
	 */
	public Serializable getId(Method method);
	
	/**
	 * Get a setter for the given method.
	 * 
	 * @param getter
	 * @return
	 */
	public Method getSetter(Method getter);
}