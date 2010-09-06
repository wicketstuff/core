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

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;

import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.RuntimeMBeanException;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class AttributeValuesPanel extends Panel
{

	private ModalWindow modalOutput;

	public AttributeValuesPanel(String id, final ObjectName objectName,
			MBeanAttributeInfo[] beanAttributeInfos, final MbeanServerLocator mbeanServerLocator)
	{
		super(id);
		add(modalOutput = new ModalWindow("modalOutput"));
		modalOutput.setCookieName("modalOutput");
		Form form = new Form("form");
		add(form);
		form.add(new ListView("attributes", Arrays.asList(beanAttributeInfos))
		{

			protected void populateItem(ListItem item)
			{
				final MBeanAttributeInfo info = (MBeanAttributeInfo)item.getModelObject();
				item.add(new Label("name", info.getName()));
				try
				{
					Object value = null;
					// UnsupportedOperationException
					if (info.isReadable())
					{
						try
						{
							value = mbeanServerLocator.get().getAttribute(objectName,
									info.getName());
						}
						catch (RuntimeMBeanException e)
						{
							StringWriter sw = new StringWriter();
							PrintWriter pw = new PrintWriter(sw);
							e.printStackTrace(pw);
							item.error(sw.toString());
						}
					}
					AjaxLink link = null;
					item.add(link = new AjaxLink("value", new Model((Serializable)value))
					{
						@Override
						public void onClick(AjaxRequestTarget target)
						{
							modalOutput.setContent(new DataViewPanel(modalOutput.getContentId(),
									getModelObject()));
							modalOutput.setTitle(info.getName());
							modalOutput.show(target);
						}

						@Override
						public boolean isEnabled()
						{
							return (getModelObject() instanceof Collection)
									|| (getModelObject() != null && getModelObject().getClass()
											.isArray());
						}

						@Override
						public boolean isVisible()
						{
							return !info.isWritable();
						}

					});
					link.add(new Label("label", value == null ? null : value.toString()));
					item.add(new TextField("editableValue", new AttributeModel(info,
							mbeanServerLocator, objectName))
					{
						@Override
						public boolean isVisible()
						{
							return info.isWritable();
						}
					});
					item.add(new Button("submit", new Model("Submit"))
					{
						@Override
						public boolean isVisible()
						{
							return info.isWritable();
						}
					});
					item.add(new FeedbackPanel("feedback"));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

			}
		});
	}

	public class AttributeModel implements IModel
	{
		private MBeanAttributeInfo attributeInfo;
		private MbeanServerLocator mbeanServerLocator;
		private ObjectName objectName;

		public AttributeModel(MBeanAttributeInfo attributeInfo,
				MbeanServerLocator mbeanServerLocator, ObjectName objectName)
		{
			this.attributeInfo = attributeInfo;
			this.mbeanServerLocator = mbeanServerLocator;
			this.objectName = objectName;
		}

		public Object getObject()
		{
			if (attributeInfo.isReadable())
			{
				try
				{
					return mbeanServerLocator.get().getAttribute(objectName,
							attributeInfo.getName());
				}
				catch (AttributeNotFoundException e)
				{
					e.printStackTrace();
				}
				catch (InstanceNotFoundException e)
				{
					e.printStackTrace();
				}
				catch (MBeanException e)
				{
					e.printStackTrace();
				}
				catch (ReflectionException e)
				{
					e.printStackTrace();
				}
				catch (RuntimeMBeanException e)
				{
					e.printStackTrace();
					return null;
				}
			}
			return null;
		}

		public void setObject(Object object)
		{
			Attribute attribute = null;
			try
			{
				Object paramWithCorrectType = null;
				if (object != null)
				{
					Class clazz = DataUtil.getClassFromInfo(attributeInfo);
					paramWithCorrectType = DataUtil.tryParseToType(object, clazz);
				}
				attribute = new Attribute(attributeInfo.getName(), object == null
						? null
						: paramWithCorrectType);
				mbeanServerLocator.get().setAttribute(objectName, attribute);
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}

		public void detach()
		{
		}

	}
}
