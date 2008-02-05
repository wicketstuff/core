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
package org.apache.wicket.security.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.security.SpeedTest;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.checks.ComponentSecurityCheck;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.components.SecureWebPage;


/**
 * Page with lots of secure components to test performance.
 * 
 * @author marrink
 */
public class SpeedPage extends SecureWebPage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean secured = false;

	/**
	 * 
	 */
	public SpeedPage()
	{
		super();
		final IModel columnModel = new ColumnModel();
		add(new Label("secure", new StatusModel()).setOutputMarkupId(true));
		add(new ListView("rows", new RowModel())
		{
			private static final long serialVersionUID = 1L;

			protected void populateItem(ListItem item)
			{
				item.add(new ListView("cols", columnModel)
				{
					private static final long serialVersionUID = 1L;

					protected void populateItem(ListItem item)
					{
						Label label = new Label("label", item.getModel());
						if (secured)
							item.add(SecureComponentHelper.setSecurityCheck(label,
									new ComponentSecurityCheck(label)));
						else
							item.add(label);
						// this will generate loads of distinct checks for
						// component permissions
						// usually you do not want this, instead you should use
						// a securemodel.
						// for a performance test however it is perfect
					}
				});
			}
		});

	}

	/**
	 * Shortcut to {@link WaspSession#logoff(Object)}
	 * 
	 * @param context
	 * @return true if the logoff was successful, false otherwise
	 */
	public boolean logoff(Object context)
	{
		return ((WaspSession)Session.get()).logoff(context);
	}

	private final class StatusModel implements IModel
	{

		/**
		 * 
		 * @see org.apache.wicket.model.IModel#getObject()
		 */
		public Object getObject()
		{
			return secured ? "secure" : "not secure";
		}

		/**
		 * 
		 * @see org.apache.wicket.model.IModel#setObject(java.lang.Object)
		 */
		public void setObject(Object object)
		{
			// noop
		}

		/**
		 * 
		 * @see org.apache.wicket.model.IDetachable#detach()
		 */
		public void detach()
		{
			// noop
		}

	}
	private static final class RowModel extends LoadableDetachableModel
	{

		private static final long serialVersionUID = 1L;
		private List rows = new ArrayList(SpeedTest.ROWS);

		/**
		 * Construct.
		 */
		public RowModel()
		{
			super();
			for (int i = 0; i < SpeedTest.ROWS; i++)
				rows.add("row_" + i);

		}

		/**
		 * 
		 * @see org.apache.wicket.model.LoadableDetachableModel#load()
		 */
		protected Object load()
		{
			return rows;
		}

	}
	private static final class ColumnModel extends LoadableDetachableModel
	{

		private static final long serialVersionUID = 1L;
		private List columns = new ArrayList(SpeedTest.COLS);

		/**
		 * Construct.
		 */
		public ColumnModel()
		{
			super();
			for (int i = 0; i < SpeedTest.COLS; i++)
				columns.add("col_" + i);

		}

		/**
		 * 
		 * @see org.apache.wicket.model.LoadableDetachableModel#load()
		 */
		protected Object load()
		{
			return columns;
		}

	}

	/**
	 * Gets secured.
	 * 
	 * @return secured
	 */
	public final boolean isSecured()
	{
		return secured;
	}

	/**
	 * Sets secured.
	 * 
	 * @param secured
	 *            secured
	 */
	public final void setSecured(boolean secured)
	{
		this.secured = secured;
	}
}
