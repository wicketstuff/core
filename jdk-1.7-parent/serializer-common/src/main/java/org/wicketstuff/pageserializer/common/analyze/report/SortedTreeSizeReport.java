/**
 * Copyright (C)
 * 	2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
 * 	2012 Michael Mosmann <michael@mosmann.de>
 *
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
package org.wicketstuff.pageserializer.common.analyze.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTree;

/**
 * sort and render the tree based on absolute sizes
 * @author mosmann
 *
 */
public class SortedTreeSizeReport extends TreeSizeReport
{
	public SortedTreeSizeReport(IReportOutput reportOutput) {
		super(reportOutput);
	}

	@Override
	protected List<? extends ISerializedObjectTree> preProcess(
		List<? extends ISerializedObjectTree> children)
	{

		List<ISerializedObjectTree> ret = new ArrayList<ISerializedObjectTree>();
		ret.addAll(children);
		Collections.sort(ret, new Comparator<ISerializedObjectTree>()
		{
			@Override
			public int compare(ISerializedObjectTree o1, ISerializedObjectTree o2)
			{
				int s1 = o1.size() + o1.childSize();
				int s2 = o2.size() + o2.childSize();
				return s1 == s2 ? 0 : s1 < s2 ? 1 : -1;
			}
		});
		return ret;
	}
}
