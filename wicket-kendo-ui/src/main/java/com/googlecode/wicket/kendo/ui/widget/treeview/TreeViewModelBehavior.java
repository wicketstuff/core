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
package com.googlecode.wicket.kendo.ui.widget.treeview;

import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.util.lang.Args;

/**
 * Provides the behavior that loads {@link TreeNode}{@code s}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class TreeViewModelBehavior extends AbstractAjaxBehavior
{
	private static final long serialVersionUID = 1L;

	private final TreeViewModel model;
	private final TreeNodeFactory factory;

	/**
	 * Constructor
	 *
	 * @param model the {@link TreeViewModel}
	 * @param factory the {@link TreeNodeFactory}
	 */
	public TreeViewModelBehavior(final TreeViewModel model, TreeNodeFactory factory)
	{
		this.model = model;
		this.factory = Args.notNull(factory, "factory");
	}

	// Properties //

	/**
	 * Gets the {@link TreeNodeFactory}
	 * 
	 * @return the {@code TreeNodeFactory}
	 */
	public TreeNodeFactory getFactory()
	{
		return this.factory;
	}

	// Events //

	@Override
	public void onRequest()
	{
		final RequestCycle requestCycle = RequestCycle.get();
		IRequestParameters parameters = requestCycle.getRequest().getQueryParameters();

		this.model.setNodeId(parameters.getParameterValue(TreeNodeFactory.ID_FIELD).toInt(TreeNode.ROOT));

		requestCycle.scheduleRequestHandlerAfterCurrent(this.newRequestHandler());
	}

	// Factories //

	/**
	 * Gets the new {@link IRequestHandler} that will respond the list of {@link TreeNode}{@code s} in a JSON format
	 *
	 * @return the {@link IRequestHandler}
	 */
	protected IRequestHandler newRequestHandler()
	{
		return new TreeViewModelRequestHandler();
	}

	// Classes //

	/**
	 * Provides the {@link IRequestHandler}
	 */
	protected class TreeViewModelRequestHandler implements IRequestHandler
	{
		@Override
		public void respond(final IRequestCycle requestCycle)
		{
			WebResponse response = (WebResponse) requestCycle.getResponse();

			final String encoding = Application.get().getRequestCycleSettings().getResponseRequestEncoding();
			response.setContentType("text/json; charset=" + encoding);
			response.disableCaching();

			if (model != null)
			{
				List<? extends TreeNode<?>> objects = model.getObject(); // calls load()

				if (objects != null)
				{
					StringBuilder builder = new StringBuilder("[ ");

					for (int index = 0; index < objects.size(); index++)
					{
						TreeNode<?> object = objects.get(index);

						if (index > 0)
						{
							builder.append(", ");
						}

						builder.append(factory.toJson(index, object));
					}

					response.write(builder.append(" ]"));
				}
			}
		}

		@Override
		public void detach(final IRequestCycle requestCycle)
		{
			model.detach();
		}
	}
}
