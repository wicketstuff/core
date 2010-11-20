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
package org.wicketstuff.minis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.minis.util.ListViewFormComponentReuseManager;

/**
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
public class ListViewFormComponentReuseManagerPage extends WebPage
{
	public static class Row implements Serializable
	{
		private static final long serialVersionUID = 1L;

		public String key;
		public String value;
	}

	private static final long serialVersionUID = 1L;

	private final List<Row> rows = new ArrayList<Row>();

	@SuppressWarnings("serial")
	public ListViewFormComponentReuseManagerPage(final PageParameters parameters)
	{
		final Form<List<Row>> form = new Form<List<Row>>("rowsForm");
		add(form);

		form.add(new Button("addRowButton")
		{
			@Override
			public void onSubmit()
			{
				rows.add(new Row());
			}
		}.setDefaultFormProcessing(false));

		form.add(new ListView<Row>("rowsList", new PropertyModel<List<Row>>(this, "rows"))
		{
			@Override
			protected void populateItem(final ListItem<Row> item)
			{
				final Row row = item.getModelObject();

				item.add(new Label("index", new AbstractReadOnlyModel<Integer>()
				{
					@Override
					public Integer getObject()
					{
						return item.getIndex() + 1;
					}
				}));
				ListViewFormComponentReuseManager.addOrReuse(item, new RequiredTextField<String>("key",
					new PropertyModel<String>(row, "key")));
				ListViewFormComponentReuseManager.addOrReuse(item, new TextField<String>("value",
					new PropertyModel<String>(row, "value")));

				item.add(new SubmitLink("removeRowLink")
				{
					@Override
					public void onSubmit()
					{
						getList().remove(item.getModelObject());
						getParent().getParent().removeAll();
					};
				}.setDefaultFormProcessing(false));
				item.add(new SubmitLink("moveUpLink")
				{
					@Override
					public boolean isVisible()
					{
						return getList().indexOf(item.getModelObject()) > 0;
					};

					@Override
					public void onSubmit()
					{
						final int index = getList().indexOf(item.getModelObject());
						Collections.swap(getList(), index, index - 1);
						getParent().getParent().removeAll();
					};
				}.setDefaultFormProcessing(false));
				item.add(new SubmitLink("moveDownLink")
				{
					@Override
					public boolean isVisible()
					{
						return getList().indexOf(item.getModelObject()) < getList().size() - 1;
					};

					@Override
					public void onSubmit()
					{
						final int index = getList().indexOf(item.getModelObject());
						Collections.swap(getList(), index, index + 1);
						getParent().getParent().removeAll();
					};
				}.setDefaultFormProcessing(false));
			}
		}.setReuseItems(true));
	}
}
