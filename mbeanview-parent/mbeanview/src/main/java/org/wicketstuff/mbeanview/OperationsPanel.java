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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalDialog;
import org.apache.wicket.feedback.FencedFeedbackPanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.IConverter;

/**
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class OperationsPanel extends Panel
{

	private static final long serialVersionUID = 1L;

	private final ObjectName objectName;

	private ModalDialog modalOutput;

	public OperationsPanel(String id, final IModel<MBeanServer> server,
		final ObjectName objectName, MBeanOperationInfo[] beanOperationInfos)
	{
		super(id);

		this.objectName = objectName;

		add(modalOutput = new ModalDialog("modalOutput"));

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

				IModel<Object[]> values = Model.of(new Object[info.getSignature().length]);

				ParameterRepeater parameterRepeater = new ParameterRepeater("parameters",
					info.getSignature(), values);
				item.add(parameterRepeater);

				final FencedFeedbackPanel feedback = new FencedFeedbackPanel("feedback", item);
				feedback.setOutputMarkupId(true);
				item.add(feedback);
				item.add(new OperationButton("method", server, info, values)
				{
					private static final long serialVersionUID = 1L;

					@Override
					protected void onFailure(Exception e, AjaxRequestTarget target)
					{
						List<String> returnList = new ArrayList<String>();
						returnList.add(e.getMessage());
						StringWriter sw = new StringWriter();
						PrintWriter pw = new PrintWriter(sw);
						e.printStackTrace(pw);
						returnList.add(sw.toString());

						modalOutput.setContent(new DataViewPanel(ModalDialog.CONTENT_ID,
							Model.ofList(returnList)));
						modalOutput.open(target);
					}

					@Override
					protected void onSuccess(Object returnObj, AjaxRequestTarget target)
					{
						if (returnObj == null)
						{
							item.info("Success");
							target.add(feedback);
						}
						else
						{
							modalOutput.setContent(new DataViewPanel(ModalDialog.CONTENT_ID,
								Model.of((Serializable)returnObj)));
							modalOutput.open(target);
						}
					}
				});
			}

		};
		operations.setReuseItems(true);
		form.add(operations);
	}

	@SuppressWarnings("unchecked")
	public IModel<MBeanServer> getModel()
	{
		return (IModel<MBeanServer>)getDefaultModel();
	}

	private abstract class OperationButton extends AjaxButton
	{
		private static final long serialVersionUID = 1L;

		private IModel<MBeanServer> server;

		private final MBeanOperationInfo info;

		private IModel<Object[]> values;

		public OperationButton(String id, IModel<MBeanServer> server, MBeanOperationInfo info,
			IModel<Object[]> values)
		{
			super(id);

			setModel(Model.of(info.getName()));

			this.server = server;
			this.info = info;
			this.values = values;
		}

		@Override
		public void detachModels()
		{
			super.detachModels();

			server.detach();
			values.detach();
		}

		public String[] getSignatures()
		{
			String[] params = new String[info.getSignature().length];
			for (int i = 0; i < params.length; i++)
			{
				params[i] = info.getSignature()[i].getType();
			}
			return params;
		}

		@Override
		protected void onSubmit(AjaxRequestTarget target)
		{
			Object returnObj;
			try
			{
				returnObj = server.getObject().invoke(objectName, info.getName(),
					values.getObject(), getSignatures());
				onSuccess(returnObj, target);
			}
			catch (Exception e)
			{
				onFailure(e, target);
			}
		}

		protected abstract void onFailure(Exception e, AjaxRequestTarget target);

		protected abstract void onSuccess(Object returnObj, AjaxRequestTarget target);
	}

	private static class ParameterRepeater extends ListView<MBeanParameterInfo>
	{
		private static final long serialVersionUID = 1L;

		private final IModel<Object[]> parameterValues;

		public ParameterRepeater(String id, MBeanParameterInfo[] beanParameterInfos,
			IModel<Object[]> values)
		{
			super(id, Arrays.asList(beanParameterInfos));

			this.parameterValues = values;
		}

		@Override
		public void detachModels()
		{
			super.detachModels();

			parameterValues.detach();
		}

		@Override
		protected void populateItem(final ListItem<MBeanParameterInfo> item)
		{
			MBeanParameterInfo param = item.getModelObject();

			item.add(new Label("parameterName", param.getName()));

			item.add(new TextField<Object>("parameterValue", new IModel<Object>()
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void detach()
				{
				}

				@Override
				public Object getObject()
				{
					return parameterValues.getObject()[item.getIndex()];
				}

				@Override
				public void setObject(Object object)
				{
					parameterValues.getObject()[item.getIndex()] = object;
				}
			}, Object.class)
			{
				private static final long serialVersionUID = 1L;

				@SuppressWarnings("unchecked")
				@Override
				public <C> IConverter<C> getConverter(Class<C> type)
				{
					return new ValueConverter(item.getModelObject().getType());
				}
			});
		}
	}
}
