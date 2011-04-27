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
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.MBeanFeatureInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.ObjectName;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class OperationsPanel extends Panel
{

	private static final long serialVersionUID = 1L;
	private final MbeanServerLocator beanServerLocator;
	private final ObjectName objectName;
	private ModalWindow modalOutput;

	public OperationsPanel(String id, final ObjectName objectName,
		MBeanOperationInfo[] beanOperationInfos, final MbeanServerLocator beanServerLocator)
	{
		super(id);
		this.beanServerLocator = beanServerLocator;
		this.objectName = objectName;
		add(modalOutput = new ModalWindow("modalOutput"));
		modalOutput.setTitle("Operation result view.");
		modalOutput.setCookieName("modalOutput");
		Form<Void> form = new Form<Void>("form");
		add(form);
		ListView<MBeanOperationInfo> operations = new ListView<MBeanOperationInfo>("operations",
			Arrays.asList(beanOperationInfos))
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<MBeanOperationInfo> item)
			{
				final MBeanOperationInfo info = item.getModelObject();
				String returnLbl = info.getReturnType();
				try
				{
					Class<?> c = Class.forName(info.getReturnType());
					if (c.isArray())
					{
						returnLbl = c.getComponentType().getSimpleName() + "[]";
					}
					else
					{
						returnLbl = c.getSimpleName();
					}
				}
				catch (ClassNotFoundException e)
				{
				}
				item.add(new Label("return", returnLbl));
				final ParameterRepeater parameterRepeater = new ParameterRepeater("parameters",
					info.getSignature());
				item.add(parameterRepeater);
				final FeedbackPanel feedback = new ComponentFeedbackPanel("feedback", item);
				feedback.setOutputMarkupId(true);
				item.add(feedback);
				item.add(new OperationButton("method", parameterRepeater, info)
				{
					private static final long serialVersionUID = 1L;

					@Override
					protected void onSuccessful(Object returnObj, AjaxRequestTarget target)
					{
						if (returnObj == null)
						{
							item.info("Successful call");
							target.add(feedback);
						}
					}
				});
			}

		};
		form.add(operations);

	}

	private abstract class OperationButton extends AjaxButton
	{
		private static final long serialVersionUID = 1L;
		private final ParameterRepeater parameterRepeater;
		private final MBeanFeatureInfo info;

		public OperationButton(String id, ParameterRepeater parameterRepeater, MBeanFeatureInfo info)
		{
			super(id);
			setModel(Model.of(info.getName()));
			this.parameterRepeater = parameterRepeater;
			this.info = info;
		}

		@Override
		protected void onSubmit(AjaxRequestTarget target, Form<?> form)
		{
			Object returnObj = null;
			try
			{
				returnObj = beanServerLocator.get().invoke(objectName, info.getName(),
					parameterRepeater.getParams(), parameterRepeater.getSignatures());
				onSuccessful(returnObj, target);
			}
			catch (Exception e)
			{
				List<String> returnList = new ArrayList<String>();
				returnList.add(e.getMessage());
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				returnList.add(sw.toString());
				returnObj = returnList;
			}
			if (returnObj != null)
			{
				modalOutput.setContent(new DataViewPanel(modalOutput.getContentId(), returnObj));
				modalOutput.show(target);
			}
		}

		protected abstract void onSuccessful(Object returnObj, AjaxRequestTarget target);

		@Override
		protected void onError(AjaxRequestTarget target, Form<?> form)
		{

		}

	}

	private static class ParameterRepeater extends ListView<MBeanParameterInfo>
	{
		private static final long serialVersionUID = 1L;
		private final Map<MBeanParameterInfo, IModel<?>> parametersValues = new HashMap<MBeanParameterInfo, IModel<?>>();
		private final MBeanParameterInfo[] beanParameterInfos;

		public ParameterRepeater(String id, MBeanParameterInfo[] beanParameterInfos)
		{
			super(id, Arrays.asList(beanParameterInfos));
			this.beanParameterInfos = beanParameterInfos;
		}

		@Override
		protected void populateItem(ListItem<MBeanParameterInfo> item)
		{
			MBeanParameterInfo param = item.getModelObject();
			item.add(new Label("parameterName", param.getName()));
			parametersValues.put(param, new Model());
			item.add(new TextField("parameterValue", parametersValues.get(param)));
		}

		public Object[] getParams()
		{
			Object[] params = new Object[beanParameterInfos.length];
			for (int i = 0; i < params.length; i++)
			{
				try
				{
					params[i] = DataUtil.getCompatibleData(
						parametersValues.get(beanParameterInfos[i]).getObject(),
						beanParameterInfos[i]);
				}
				catch (ClassNotFoundException e)
				{
					throw new WicketRuntimeException(e);
				}
			}
			return params;
		}

		public String[] getSignatures()
		{
			String[] params = new String[beanParameterInfos.length];
			for (int i = 0; i < params.length; i++)
			{
				params[i] = beanParameterInfos[i].getType();
			}
			return params;
		}
	}
}
