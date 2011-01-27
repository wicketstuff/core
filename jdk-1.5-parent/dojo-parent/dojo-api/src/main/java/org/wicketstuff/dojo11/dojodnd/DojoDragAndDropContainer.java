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
package org.wicketstuff.dojo11.dojodnd;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * @author Stefan Fussenegger
 */
public abstract class DojoDragAndDropContainer extends DojoDragContainer
{

	/**
	 * Construct.
	 * @param id
	 */
	public DojoDragAndDropContainer(String id)
	{
		this(id, new Model(DEFAULT_ACCEPT));
	}
	
	/**
	 * Construct.
	 * @param id
	 * @param acceptModel 
	 */
	public DojoDragAndDropContainer(String id, IModel acceptModel)
	{
		this(id, acceptModel, false);
	}
	
	/**
	 * Construct.
	 * @param id
	 * @param acceptModel 
	 * @param copy
	 */
	public DojoDragAndDropContainer(String id, IModel acceptModel, boolean copy)
	{
		super(id, new DojoDragAndDropContainerHandler(copy, acceptModel));
	}

	/**
	 * @see org.wicketstuff.dojo11.dojodnd.DojoDragContainer#onDrop(org.apache.wicket.ajax.AjaxRequestTarget, org.apache.wicket.Component, int)
	 */
	@Override
	public abstract void onDrop(AjaxRequestTarget target, Component component, int position);
	
	
}
