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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
import org.wicketstuff.rest.utils.collection.CollectionUtils;

/**
 * Visitor implementation to assign a score to URL segments and to extract path variables.
 * The score is an integer positive value if the string in input is compatible with the current
 * segment, 0 otherwise. Segments of type FixedURLSegment have the priority over the
 * other types of segment. That's why positive matches has a score of 2 for FixedURLSegment, 
 * it's 1 for the other types of segment.
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
			List<ParamSegment> paramSegments = new ArrayList<>();
			CollectionUtils.filterCollectionByType(segment.getSubSegments(), 
				paramSegments, ParamSegment.class);
			
			for (int i = 1; i <= matcher.groupCount(); i++)
			{
				String group = matcher.group(i); 
				String groupName = paramSegments.get(i - 1).getParamName();
				
				addPathVariable(groupName, group);				
			}
			
			addScore(1);
		}
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
	
	/**
	 * Extract segment value from current page parameters.
	 * 
	 * @param segment
	 * @return
	 */
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
		this.score += partialSocre;
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
