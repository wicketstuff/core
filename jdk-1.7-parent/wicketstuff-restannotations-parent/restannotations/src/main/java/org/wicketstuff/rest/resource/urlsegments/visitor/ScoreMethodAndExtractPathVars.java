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
package org.wicketstuff.rest.resource.urlsegments.visitor;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.parse.metapattern.MetaPattern;
import org.apache.wicket.util.string.StringValue;
import org.wicketstuff.rest.resource.MethodMappingInfo;
import org.wicketstuff.rest.resource.urlsegments.AbstractURLSegment;
import org.wicketstuff.rest.resource.urlsegments.FixedURLSegment;
import org.wicketstuff.rest.resource.urlsegments.MultiParamSegment;
import org.wicketstuff.rest.resource.urlsegments.ParamSegment;

/**
 * Visitor implementation to assign a score to URL segments and to extract path variables..
 * 
 * @author andrea del bene
 *
 */
public class ScoreMethodAndExtractPathVars implements ISegmentVisitor
{
	/** The PageParameters for the current request. */
	private final PageParameters pageParameters;
	
	/** The MethodMappingInfo for the method we want to score. */
	private final MethodMappingInfo methodInfo;
	
	/** The extracted path variables. */
	private final Map<String, String> pathVariables;
	
	/** The score for the current method. */
	private int score;
	
	/** Indicates if the last segment was valid for the current method URL. */
	private boolean isSegmentValid = false;
	
	public ScoreMethodAndExtractPathVars(MethodMappingInfo methodInfo, PageParameters pageParameters)
	{
		this.methodInfo = methodInfo;
		this.pageParameters = pageParameters;
		this.pathVariables = new LinkedHashMap<>();

	}
	
	@Override
	public void visit(FixedURLSegment segment)
	{
		String segmentValue = segmentActualValue(segment);
		
		if (isSegmentValid = segmentValue.equals(segment.toString()))
		{
			addScore(2);
		}
	}

	@Override
	public void visit(MultiParamSegment segment)
	{
		String segmentValue = segmentActualValue(segment);
		
		MetaPattern metaPatternWithGroups = segment.getMetaPatternWithGroups();
		Matcher matcher = metaPatternWithGroups.matcher(segmentValue);

		if(isSegmentValid = matcher.matches())
		{
			Iterator<AbstractURLSegment> subSegments = segment.getSubSegments().iterator();
			
			addScore(1);
			
			for (int i = 1; i <= matcher.groupCount(); i++)
			{
				String group = matcher.group(i); 
				String groupName = findGroupName(subSegments);
				
				addPathVariable(groupName, group);				
			}
		}
	}

	private String findGroupName(Iterator<AbstractURLSegment> subSegments)
	{
		while (subSegments.hasNext())
		{
			AbstractURLSegment abstractURLSegment = subSegments.next();
			
			if(abstractURLSegment instanceof ParamSegment)
			{
				ParamSegment paramSegment = (ParamSegment)abstractURLSegment;
				
				return paramSegment.getParamName();
			}
		}
		
		return null;
	}

	@Override
	public void visit(ParamSegment segment)
	{
		String segmentValue = segmentActualValue(segment);
		Matcher matcher = segment.getMetaPattern().matcher(segmentValue);
		
		if(isSegmentValid = matcher.matches())
		{
			addScore(1);
			addPathVariable(segment.getParamName(), matcher.group());
		}		
	}
	
	protected String segmentActualValue(AbstractURLSegment segment)
	{
		int i = methodInfo.getSegments().indexOf(segment);
		StringValue pageParameter = pageParameters.get(i);
		
		return  AbstractURLSegment.getActualSegment(pageParameter
			.toString());
	}

	public boolean isSegmentValid()
	{
		return isSegmentValid;
	}

	public void addScore(int partialSocre)
	{
		this.score =+ partialSocre;
	}

	public int getScore()
	{
		return score;
	}

	public MethodMappingInfo getMethodInfo()
	{
		return methodInfo;
	}

	public Map<String, String> getPathVariables()
	{
		return pathVariables;
	}
	
	public void addPathVariable(String name, String value)
	{
		this.pathVariables.put(name, value);
	}

}
