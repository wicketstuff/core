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
package org.wicketstuff.security.swarm.models;

import org.apache.wicket.Component;
import org.wicketstuff.security.hive.authorization.permissions.DataPermission;
import org.wicketstuff.security.models.ISecureModel;
import org.wicketstuff.security.swarm.strategies.SwarmStrategy;

/**
 * A customized {@link ISecureModel} used to check {@link DataPermission}s. Note that although the
 * current implementation is required to work with {@link ISecureModel}s, it is recommended to
 * implement SwarmModel instead as it allows you more control to specify a name for the permission.
 * If you do use a regular ISecureModel we fall back to the toString method.
 * 
 * @author marrink
 * @see SwarmStrategy#isModelAuthorized(ISecureModel, Component,
 *      org.wicketstuff.security.actions.WaspAction)
 */
public interface SwarmModel<T> extends ISecureModel<T>
{
	/**
	 * Returns a string identifying this model for security purposes. This id is used as the name of
	 * a {@link DataPermission} by
	 * {@link SwarmStrategy#isModelAuthorized(ISecureModel, Component, org.wicketstuff.security.actions.WaspAction)}
	 * And is therefore required to remain consistent or each invocation provided the same component
	 * is used.
	 * 
	 * @param component
	 *            the component requesting the id
	 * @return a non null id
	 */
	public String getSecurityId(Component component);
}
