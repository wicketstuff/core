/*
 * Copyright 2009 Michael Würtinger (mwuertinger@users.sourceforge.net)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.flot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Series implements Serializable
{
	/** Required by {@link Serializable} */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(Series.class);

	String label;
	Color color;
	Set<GraphType> graphTypes;
	List<DataSet> data;

	public Series(List<DataSet> data, String label, Color color, Set<GraphType> graphTypes)
	{
		this.label = label;
		this.color = color;
		this.graphTypes = new HashSet<GraphType>(graphTypes);
		this.data = new ArrayList<DataSet>(data);
	}

	public Series(List<DataSet> data, String label, Color color, GraphType... graphTypes)
	{
		this(data, label, color, new HashSet<GraphType>(Arrays.asList(graphTypes)));
	}

	public String getLabel()
	{
		return label;
	}

	public List<DataSet> getData()
	{
		return Collections.unmodifiableList(data);
	}

	@Override
	public String toString()
	{
		StringBuffer str = new StringBuffer();
		str.append("{data: [");
		for (DataSet dataSet : getData())
		{
			str.append(dataSet.toString());
			str.append(", ");
		}
		// Remove last ", "
		if (getData().size() > 0)
			str.setLength(str.length() - 2);
		str.append("], label: \"" + getLabel() + "\", color: \"" + color.html() + "\"");

		if (graphTypes.size() > 0)
			str.append(", ");

		for (GraphType graphType : graphTypes)
		{
			str.append(graphType);
			str.append(", ");
		}

		if (graphTypes.size() > 0)
		{
			str.setLength(str.length() - 2);
			str.append("}");
		}

		LOGGER.info("Series: " + str);

		return str.toString();
	}

	public Series addDataSet(DataSet dataSet, Color color)
	{
		List<DataSet> newData = new ArrayList<DataSet>(data);
		newData.add(dataSet);
		return new Series(newData, label, color, graphTypes);
	}
}
