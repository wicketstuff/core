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
package org.wicketstuff.jamon.monitor;

import java.util.List;

import com.jamonapi.Monitor;

/**
 * Data provider for monitoring statements.
 * 
 * @author rene-d-menoto
 */
public interface MonitoringRepository
{

	/**
	 * Returns the number of {@link Monitor}s in this repository.
	 * 
	 * @return the number of {@link Monitor}s.
	 */
	int count();

	/**
	 * Returns {@link Monitor} that registered under the given <code>monitorLabel</code>
	 * 
	 * @param monitorLabel
	 *            The label of the monitor to be returned
	 * @return The found monitor or <code>null</code>.
	 */
	Monitor findMonitorByLabel(String monitorLabel);

	/**
	 * Returns all {@link Monitor} that satisfy the given {@link MonitorSpecification}.
	 * 
	 * @param specification
	 *            The {@link MonitorSpecification} to satisfy
	 * @return All monitors that satisfy the given {@link MonitorSpecification}.
	 */
	List<Monitor> find(MonitorSpecification specification);
}