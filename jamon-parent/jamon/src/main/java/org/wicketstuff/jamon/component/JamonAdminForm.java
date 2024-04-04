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
package org.wicketstuff.jamon.component;

import static org.wicketstuff.jamon.component.JamonAdminPage.PATH_TO_MONITOR_DETAILS;
import static org.wicketstuff.jamon.component.JamonAdminPage.PATH_TO_STATISTICS_TABLE;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.wicketstuff.jamon.monitor.AlwaysHitedMonitorSpecification;
import org.wicketstuff.jamon.monitor.JamonRepository;
import org.wicketstuff.jamon.monitor.MonitorLabelPrefixSpecification;
import org.wicketstuff.jamon.monitor.MonitorSpecification;

/**
 * Form for filtering the {@link JamonMonitorTable}.
 * 
 * @author lars
 *
 */
@SuppressWarnings("serial")
public class JamonAdminForm extends Form<Void>
{

	private static final String ID_OF_RESET_BUTTON = "reset";
	private static final String ID_OF_MONITOR_LABEL = "monitorLabel";

	public JamonAdminForm(String id)
	{
		super(id);
		final TextField<String> monitorLabel = new TextField<String>(ID_OF_MONITOR_LABEL,
			new Model<>());
		monitorLabel.add(new AjaxFormComponentUpdatingBehavior("keyup")
		{
			@Override
			protected void onUpdate(AjaxRequestTarget target)
			{
				replaceJamonMonitorTable(monitorLabel, target,
					new MonitorLabelPrefixSpecification(monitorLabel.getValue()));
			}
		});
		add(monitorLabel);
		add(new AjaxButton(ID_OF_RESET_BUTTON)
		{
			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				JamonRepository.clear();
				replaceJamonMonitorTable(monitorLabel, target,
					new AlwaysHitedMonitorSpecification());
				replaceMonitorDetailsPanel(target);
			}

		});
	}

	private void replaceJamonMonitorTable(final TextField<?> monitorLabel, AjaxRequestTarget target,
		MonitorSpecification specification)
	{
		JamonMonitorTable toBeReplaced = (JamonMonitorTable)target.getPage()
			.get(PATH_TO_STATISTICS_TABLE);
		JamonMonitorTable replacement = new JamonMonitorTable(PATH_TO_STATISTICS_TABLE,
			specification, toBeReplaced.getItemsPerPage());
		toBeReplaced.replaceWith(replacement);
		target.add(replacement);
	}

	private void replaceMonitorDetailsPanel(AjaxRequestTarget target)
	{
		EmptyMarkupContainer emptyMarkupContainer = new EmptyMarkupContainer(
			PATH_TO_MONITOR_DETAILS);
		target.getPage().get(PATH_TO_MONITOR_DETAILS).replaceWith(emptyMarkupContainer);
		target.add(emptyMarkupContainer);
	}
}
