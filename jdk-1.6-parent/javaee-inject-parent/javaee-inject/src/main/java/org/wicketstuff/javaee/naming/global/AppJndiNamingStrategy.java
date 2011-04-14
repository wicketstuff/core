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
 * Simple Java EE 6 Global JNDI naming support for java:app prefixed JNDI names based on the EJB 3.1
 * specification Section 4.4.1.1, page 84 <br />
 * The <i>java:app</i> prefix allows a component executing within a Java EE application to access an
 * application-specific namespace. <br />
 * With this you can use JNDI names in the following format: <br />
 * <code>java:app/&lt;moduleName&gt;/&lt;bean-name&gt;[!&lt;fully-qualified-interface-name&gt;]</code>
 * <br />
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
public class AppJndiNamingStrategy implements IJndiNamingStrategy
{

	private static final long serialVersionUID = 1L;
	private String baseName;

	/**
	 * This naming strategy will use the java:global JNDI name format for lookups. Use this
	 * constructor, if the app-name is not defined.
	 * 
	 * @param moduleName
	 *            The name of the module
	 */
	public AppJndiNamingStrategy(String moduleName)
	{
		baseName = "java:app/" + moduleName + "/";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String calculateName(String ejbName, Class<?> ejbType)
	{
		return baseName + (ejbName == null ? ejbType.getName() : ejbName);
	}
}
