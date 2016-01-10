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

import com.jamonapi.Monitor;

/**
 * Specification that is satisfied whenever the label of the {@link Monitor} is the same as the
 * {@link #label} of this specification. Case is not ignored.
 * 
 * @author lars
 *
 */
@SuppressWarnings("serial")
public class MonitorLabelSpecification implements MonitorSpecification
{

	private final String label;

	/**
	 * Construct.
	 * 
	 * @param label
	 *            The label for this specification.
	 */
	public MonitorLabelSpecification(String label)
	{
		this.label = label;
	}

	/**
	 * @return <code>true</code> is the label of the given {@link Monitor} is exactly the same as
	 *         this {@link #label}, <code>false</code> if otherwise.
	 */
	public boolean isSatisfiedBy(Monitor monitor)
	{
		return label.equals(monitor.getLabel());
	}

}
