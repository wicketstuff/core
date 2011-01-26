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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

/**
 * <p>
 * A Handler associated to {@link DojoDragContainer}
 * </p>
 * 
 * @author Vincent Demay
 *
 */
public class DojoDragContainerHandler extends DojoDropContainerHandler
{
	/** container handler is attached to. */
	private DojoDragContainer container;
	private boolean copy;
	

	/**
	 * Construct.
	 * @param copy
	 */
	public DojoDragContainerHandler(boolean copy)
	{
		this(copy, new Model(DojoDropContainerHandler.ACCEPT_NONE));
	}


	/**
	 * Construct.
	 * @param copy
	 * @param acceptModel
	 */
	public DojoDragContainerHandler(boolean copy, IModel acceptModel)
	{
		super(acceptModel);
		this.copy = copy;
	}


	/**
	 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#onBind()
	 */
	protected void onBind()
	{
		super.onBind();
		this.container = (DojoDragContainer)getComponent();
		this.container.add(new AttributeModifier("copyOnly", true, new PropertyModel(this, "copy")));
	}

	/**
	 * @return the drag container
	 */
	protected DojoDragContainer getDojoDragContainer() {
		return this.container;
	}

	/**
	 * @see org.wicketstuff.dojo11.AbstractRequireDojoBehavior#setRequire(org.wicketstuff.dojo11.AbstractRequireDojoBehavior.RequireDojoLibs)
	 */
	public void setRequire(RequireDojoLibs libs)
	{
		super.setRequire(libs);
		libs.add("dojo.dnd.Source");
	}


	/**
	 * @return are items copied (true) or moved
	 */
	public boolean isCopy()
	{
		return copy;
	}


	/**
	 * @param copy copy (true) or move items
	 */
	public void setCopy(boolean copy)
	{
		this.copy = copy;
	}

	protected boolean isDropCallbackEnabled()
	{
		return false;
	}
}
