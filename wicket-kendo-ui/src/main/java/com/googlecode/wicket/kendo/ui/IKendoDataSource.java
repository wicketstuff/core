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
package com.googlecode.wicket.kendo.ui;

/**
 * Specifies a Kendo UI data-source
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public interface IKendoDataSource
{
	/**
	 * Gets the unique token that acts as the script id.
	 *
	 * @return the token
	 */
	String getToken();

	/**
	 * Prepares the data-source to be rendered
	 *
	 * @return this, for chaining
	 */
	IKendoDataSource prepareRender();

	/**
	 * Gets the data-source jQuery statement.
	 *
	 * @return the jQuery statement
	 */
	String toScript();

}
