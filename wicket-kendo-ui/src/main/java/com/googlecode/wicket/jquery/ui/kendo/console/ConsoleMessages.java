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
package com.googlecode.wicket.jquery.ui.kendo.console;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Provides the {@link Console} model object
 *
 * @author Sebastien Briquet - sebfz1
 */
public class ConsoleMessages extends LinkedHashMap<Serializable, Boolean>
{
	private static final long serialVersionUID = 1L;

	/** Default capacity */
	private static final int CAPACITY = 25;

	private final int capacity;

	/**
	 * Constructor
	 */
	public ConsoleMessages()
	{
		this.capacity = CAPACITY;
	}

	/**
	 * Constructor
	 *
	 * @param capacity the max capacity
	 */
	public ConsoleMessages(int capacity)
	{
		this.capacity = capacity;
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<Serializable, Boolean> eldest)
	{
		return this.size() > this.capacity;
	}
}
