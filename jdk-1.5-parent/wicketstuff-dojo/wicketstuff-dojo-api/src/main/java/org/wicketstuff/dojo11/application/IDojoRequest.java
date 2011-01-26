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
package org.wicketstuff.dojo11.application;

import org.wicketstuff.dojo11.DojoTargetRefresherManager;

/**
 * @author Stefan Fussenegger
 */
public interface IDojoRequest
{
	/**
	 * @param create whether to create a new instance if current is null
	 * @return {@link DojoTargetRefresherManager} or null if not already created and create is false
	 */
	DojoTargetRefresherManager getDojoTargetRefresherManager(boolean create);
}
