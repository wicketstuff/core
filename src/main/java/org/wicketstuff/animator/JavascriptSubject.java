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
package org.wicketstuff.animator;

/**
 * This class can be used to let any arbitrary javascript code be executed
 * during the animation. The code is embedded inside a function:
 * {@code function (value) { ... your javascript code here }}<br>
 * The function body can have references to the parameter value.
 * 
 * @author Gerolf Seitz
 * 
 */
public abstract class JavascriptSubject implements IAnimatorSubject
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wicketstuff.animator.IAnimatorSubject#getJavaScript()
	 */
	public String getJavaScript()
	{
		return "function (value) {" + getFunctionBody() + "}";
	}

	/**
	 * This method should return the body of the animation function. References
	 * to the parameter {@code value} can be made.
	 * 
	 * @return the javascript of the function body
	 */
	protected abstract String getFunctionBody();

}
