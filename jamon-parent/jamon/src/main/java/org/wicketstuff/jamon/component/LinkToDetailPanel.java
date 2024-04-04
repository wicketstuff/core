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

import java.util.Optional;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Panel that serves as a wrapper for the link that is generated to show the details of a Monitor.
 * This is needed due to the way {@link DefaultDataTable} works.
 * 
 * @author lars
 *
 */
@SuppressWarnings("serial")
public class LinkToDetailPanel extends Panel
{

	/*
	 * The actual link to show the details.
	 */
	private static final class LinkToDetailLink extends AjaxFallbackLink<Void>
	{
		private final String monitorLabel;

		private LinkToDetailLink(String id, IModel<?> modelForLink)
		{
			super(id);
			add(new Label("linkText", modelForLink));
			monitorLabel = modelForLink.getObject().toString();
			add(AttributeModifier.append("class", Model.<String> of("jamonLinkToDetailPanel")));
		}

		@Override
		public void onClick(Optional<AjaxRequestTarget> targetOptional)
		{
			targetOptional.ifPresent(target -> {
				Component componentToBeReplaced = target.getPage().get(PATH_TO_MONITOR_DETAILS);
				JamonMonitorDetailsPanel replacement = new JamonMonitorDetailsPanel(
					PATH_TO_MONITOR_DETAILS, monitorLabel);
				componentToBeReplaced.replaceWith(replacement);
				target.add(replacement);
			});
		}
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 *            The id.
	 * @param modelForLink
	 *            The model for the link that is wrapped in this {@link Panel}.
	 */
	public LinkToDetailPanel(String id, IModel<?> modelForLink)
	{
		super(id);
		add(new LinkToDetailLink("linkToDetail", modelForLink));
	}

}
