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
package org.wicketstuff.javaee.naming;

import java.io.Serializable;

/**
 * Specifies a pluggable implementation for a naming strategy of EJB's
 * 
 * @see org.wicketstuff.javaee.naming.StandardJndiNamingStrategy
 * @see org.wicketstuff.javaee.naming.global.GlobalJndiNamingStrategy
 * 
 * @author Filippo Diotalevi
 */
public interface IJndiNamingStrategy extends Serializable
{

	/**
	 * Calculates the JNDI name based on the given name and type
	 * 
	 * @param ejbName
	 *            The name value for the EJB annotation.
	 * @param ejbType
	 *            The type of the injectable field.
	 * @return The full JNDI name for the given field based on a naming strategy.
	 */
	String calculateName(String ejbName, Class<?> ejbType);
}
