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
package org.wicketstuff.dojo11;

/**
 * Interface to use with {link AbstractDojoWidgetBehavior}. Interface should be 
 * used on each dojo widget. 
 * 
 * @author Vincent Demay 
 */
public interface IDojoWidget
{
	
	/**
	 * This method should return the dojoTyme of this Components
	 * This method will be called on {link AbstractDojoWidgetBehavior} to add 
	 * the dojoType to the componentTag
	 * <p>
	 * DojoType constants are available in {@link DojoIdConstants}
	 * </p>
	 * @return the dojoType of the component
	 */
	public String getDojoType();
}
