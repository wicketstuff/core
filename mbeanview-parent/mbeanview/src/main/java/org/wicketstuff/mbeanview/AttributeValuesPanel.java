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
package org.wicketstuff.mbeanview;

import java.util.Arrays;
import java.util.Collection;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalDialog;
import org.apache.wicket.feedback.FencedFeedbackPanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

/**
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class AttributeValuesPanel extends Panel
{

	private static final long serialVersionUID = 1L;

	private ModalDialog modalOutput;

	public AttributeValuesPanel(String id, final IModel<MBeanServer> server,
		final ObjectName objectName, MBeanAttributeInfo[] beanAttributeInfos)
	{
		super(id, server);

		add(modalOutput = new ModalDialog("modalOutput"));

		Form<Void> form = new Form<Void>("form");
		add(form);

		ListView<MBeanAttributeInfo> attributes = new ListView<MBeanAttributeInfo>("attributes", Arrays.asList(beanAttributeInfos))
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<MBeanAttributeInfo> item)
			{
				final MBeanAttributeInfo info = item.getModelObject();

				item.add(new Label("name", info.getName()));

				final AttributeModel model = new AttributeModel(server, objectName, info)
				{
					@Override
					protected void onError(Throwable throwable)
					{
						item.error(throwable.toString());
					}
				};

				AjaxLink<Void> link = new AjaxLink<Void>("value")
				{
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target)
					{
						modalOutput.setContent(new DataViewPanel(ModalDialog.CONTENT_ID, model));
						modalOutput.open(target);
					}

					@Override
					public boolean isEnabled()
					{
						return model.getObject() instanceof Collection ||
							model.getObject() != null && model.getObject().getClass().isArray();
					}

					@Override
					public boolean isVisible()
					{
						return !info.isWritable();
					}

				};
				item.add(link);
				link.add(new Label("label", model));

				item.add(new TextField<Object>("editableValue", model, Object.class)
				{
					private static final long serialVersionUID = 1L;

					@Override
					public boolean isVisible()
					{
						return info.isWritable();
					}

					@Override
					public <C> IConverter<C> getConverter(Class<C> type)
					{
						return new ValueConverter(item.getModelObject().getType());
					}
				});
				item.add(new FencedFeedbackPanel("feedback", item));
			}
		};
		attributes.setReuseItems(true);
		form.add(attributes);

		form.add(new Button("submit"));
	};
}
