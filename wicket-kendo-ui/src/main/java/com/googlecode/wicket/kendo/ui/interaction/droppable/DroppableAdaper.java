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
package com.googlecode.wicket.kendo.ui.interaction.droppable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Adapter class for {@link IDroppableListener}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class DroppableAdaper implements IDroppableListener
{
	private static final long serialVersionUID = 1L;

	@Override
	public boolean isDragEnterEventEnabled()
	{
		return false;
	}

	@Override
	public boolean isDragLeaveEventEnabled()
	{
		return false;
	}

	@Override
	public void onDragEnter(AjaxRequestTarget target, Component component)
	{
		// noop
	}

	@Override
	public void onDragLeave(AjaxRequestTarget target, Component component)
	{
		// noop
	}

	@Override
	public void onDrop(AjaxRequestTarget target, Component component)
	{
		// noop
	}
}
