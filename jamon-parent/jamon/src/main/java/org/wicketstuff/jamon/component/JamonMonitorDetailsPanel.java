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

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.jamonapi.FrequencyDist;
import com.jamonapi.Monitor;


/**
 * Panel that shows the details of a Monitor.
 * 
 * @author lars
 * 
 */
@SuppressWarnings("serial")
public class JamonMonitorDetailsPanel extends Panel
{

	/**
	 * ID of the title of this panel.
	 */
	private static final String ID_OF_TITLE = "detailTitle";

	/**
	 * ID of the {@link ListView} of the frequencies.
	 */
	private static final String ID_OF_LIST_VIEW = "detailRow";

	/*
	 * ListView that shows the Frequencies.
	 */
	private final static class FrequencyListView extends ListView<FrequencyDist>
	{
		/*
		 * Keep the previous because we need to show the range from begin to end.
		 */
		private transient FrequencyDist previous = null;

		private transient int index = 0;

		private FrequencyListView(String id, List<FrequencyDist> list)
		{
			super(id, new ThrowAwayModel<List<FrequencyDist>>(list));
		}

		@Override
		protected void populateItem(ListItem<FrequencyDist> item)
		{
			FrequencyDist frequencyDist = item.getModelObject();
			item.add(new TimeWindowLabel("timeWindow", frequencyDist, previous));
			item.add(new Label("numberOfHits", String.valueOf(frequencyDist.getHits())));
			IndexBasedMouseOverMouseOutSupport.add(item, index);
			item.add(AttributeModifier.append("class",
				Model.<String> of((index % 2 == 0) ? "even" : "odd")));
			previous = frequencyDist;
			index++;
		}
	}

	/*
	 * Label for displaying the time frames.
	 */
	private final static class TimeWindowLabel extends Label
	{

		public TimeWindowLabel(String id, FrequencyDist current, FrequencyDist previous)
		{
			super(id);
			String label = format("%s - %s",
				(previous == null ? "0.0" : valueOf(previous.getEndValue() + 0.1)),
				(current.getEndValue() == Double.MAX_VALUE
					? "MAX"
					: valueOf(current.getEndValue())));
			setDefaultModel(new ThrowAwayModel<String>(label));
		}
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 *            The id.
	 * @param monitorLabel
	 *            The label of the {@link Monitor} to show the details of.
	 */
	public JamonMonitorDetailsPanel(String id, String monitorLabel)
	{
		super(id);
		setOutputMarkupId(true);
		final Monitor monitor = getApplication().getMetaData(MonitoringRepositoryKey.KEY)
			.findMonitorByLabel(monitorLabel);
		FrequencyDist[] frequencyDists = monitor.getRange().getFrequencyDists();
		add(new FrequencyListView(ID_OF_LIST_VIEW, asList(frequencyDists)));
		add(new Label(ID_OF_TITLE,
			format("Detail of Monitor %s in %s", monitorLabel, monitor.getUnits())));
	}
}
