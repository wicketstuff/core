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

import java.io.Serializable;

import com.jamonapi.Monitor;

/**
 * Specification interface used by the {@link JamonRepository}. Implementations are responsible to
 * check whether or not a {@link Monitor} satisfies this specification or not. Implementations
 * should also be {@link Serializable}.
 * 
 * @author lars
 *
 */
public interface MonitorSpecification extends Serializable
{
	/**
	 * Does the given {@link Monitor} satisfies this {@link MonitorSpecification}.
	 * 
	 * @param monitor
	 *            The {@link Monitor} to check
	 * @return <code>true</code> if satistied, <code>false</code> if not.
	 */
	public boolean isSatisfiedBy(Monitor monitor);
}
