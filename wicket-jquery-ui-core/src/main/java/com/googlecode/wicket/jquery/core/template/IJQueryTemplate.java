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
package com.googlecode.wicket.jquery.core.template;

import java.util.List;

import org.apache.wicket.util.io.IClusterable;

/**
 * Provides base interface for a jQuery template<br/>
 * Designed to be used in conjunction with {@link JQueryTemplateBehavior}
 * 
 * @author Sebastien Briquet - sebfz1
 */
public interface IJQueryTemplate extends IClusterable
{
	/**
	 * Gets the template text
	 * @return the template text
	 */
	String getText();

	/**
	 * Gets the list of properties used in the template text
	 * @return the list of properties
	 */
	List<String> getTextProperties();	
}
