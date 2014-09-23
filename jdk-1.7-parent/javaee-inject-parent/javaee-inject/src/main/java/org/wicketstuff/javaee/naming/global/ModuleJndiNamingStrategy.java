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
package org.wicketstuff.javaee.naming.global;

import org.wicketstuff.javaee.naming.IJndiNamingStrategy;

/**
 * Simple Java EE 6 Global JNDI naming support for <i>java:module</i> prefixed JNDI names based on
 * the EJB 3.1 specification Section 4.4.1.2, page 85 <br />
 * The java:module prefix allows a component executing within a Java EE application to access a
 * module-specific namespace. This strategy can lookup JNDI names only from the current webmodule. <br />
 * With this you can use JNDI names in the following format: <br />
 * <code>java:module/&lt;bean-name&gt;[!&lt;fully-qualified-interface-name&gt;]</code> <br />
 * <p>
 * The <i>moduleName</i> is the name of the module in which the session bean is packaged. In a
 * stand-alone ejb-jar file or .war file, the <i>moduleName</i> defaults to the base name of the
 * module with any filename extension removed. In an ear file, the <i>moduleName</i> defaults to the
 * pathname of the module with any filename extension removed, but with any directory names
 * included. The default <i>moduleName</i> can be overriden using the module-name element of
 * ejb-jar.xml (for ejb-jar files) or web.xml (for .war files).
 * </p>
 * 
 * @see <a href="http://jcp.org/aboutJava/communityprocess/final/jsr318/index.html"> EJB 3.1
 *      specification</a>
 * 
 * @author Peter Major
 */
public class ModuleJndiNamingStrategy implements IJndiNamingStrategy
{

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String calculateName(String ejbName, Class<?> ejbType)
	{
		return "java:module/" + (ejbName == null ? ejbType.getName() : ejbName);
	}
}
