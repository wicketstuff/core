/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.rest.resource.urlsegments;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.wicket.util.parse.metapattern.MetaPattern;
import org.wicketstuff.rest.resource.urlsegments.visitor.ISegmentVisitor;

public class PopulatePathVariablesVisitor implements ISegmentVisitor
{
	private final Map<AbstractURLSegment, String> segmentsAndValues;
	private final LinkedHashMap<String, String> paramsAndValues; 
	
	public PopulatePathVariablesVisitor(Map<AbstractURLSegment, String> segmentsAndValues)
	{
		this.segmentsAndValues = segmentsAndValues;
		this.paramsAndValues = new LinkedHashMap<>();
	}
	
	public Map<String, String> extract()
	{
		if(paramsAndValues.size() > 0)
		{
			return paramsAndValues;
		}
		
		for(AbstractURLSegment urlSegment : segmentsAndValues.keySet())
		{
			urlSegment.accept(this);
		}
		
		return paramsAndValues;
	}
	
	@Override
	public void visit(FixedURLSegment segment)
	{
	}

	@Override
	public void visit(MultiParamSegment segment)
	{
		int startingIndex = 0;
		
		String segmentValue = segmentsAndValues.get(segment);
		
		if (!segment.getMetaPattern().matcher(segmentValue).matches())
		{
			return;
		}
		
		for (AbstractURLSegment subSegment : segment.getSubSegments())
		{
			MetaPattern pattern = subSegment.getMetaPattern();
			segmentValue = segmentValue.substring(startingIndex);
			Matcher matcher = pattern.matcher(segmentValue);

			if (matcher.find())
			{
				String group = matcher.group();
				
				segmentsAndValues.put(subSegment, group);
				subSegment.accept(this);
				
				startingIndex = matcher.end();
			}
		}

	}

	@Override
	public void visit(ParamSegment segment)
	{
		String segmentValue = segmentsAndValues.get(segment);
		
		Matcher matcher = segment.getMetaPattern().matcher(segmentValue);
		matcher.matches();
		
		paramsAndValues.put(segment.getParamName(), matcher.group());
	}

}
