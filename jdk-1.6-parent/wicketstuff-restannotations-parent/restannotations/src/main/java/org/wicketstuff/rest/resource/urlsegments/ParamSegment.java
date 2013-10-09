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

import java.util.Map;
import java.util.regex.Matcher;

import org.apache.wicket.util.parse.metapattern.MetaPattern;
import org.apache.wicket.util.string.StringValue;

/**
 * {@link StringValue} subtype that represents a mounted segment containing a parameter's value (for
 * example '/{id}/').
 * 
 * @author andrea del bene
 * 
 */
public class ParamSegment extends AbstractURLSegment
{

	final private String paramName;

	ParamSegment(String text)
	{
		super(text);

		this.paramName = loadParamName();
	}

	@Override
	public int calculateScore(String actualSegment)
	{
		Matcher matcher = getMetaPattern().matcher(actualSegment);

		return matcher.matches() ? 1 : 0;
	}

	private String loadParamName()
	{
		String segmentContent = this.toString();
		Matcher matcher = MetaPattern.VARIABLE_NAME.matcher(segmentContent);

		matcher.find();
		return matcher.group();
	}

	@Override
	protected MetaPattern loadMetaPattern()
	{
		String segmentContent = this.toString();
		int semicolonIndex = segmentContent.indexOf(':');

		if (semicolonIndex < 0)
			return MetaPattern.ANYTHING_NON_EMPTY;

		String regExp = segmentContent.substring(semicolonIndex + 1, segmentContent.length() - 1);
		Matcher matcher = REGEXP_BODY.matcher(regExp);

		matcher.matches();

		String group = matcher.group();

		return new MetaPattern(group);
	}

	@Override
	public void populatePathVariables(Map<String, String> variables, String segment)
	{
		Matcher matcher = getMetaPattern().matcher(segment);
		matcher.matches();
		variables.put(paramName, matcher.group());
	}

	public String getParamName()
	{
		return paramName;
	}
}