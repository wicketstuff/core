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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.wicket.util.parse.metapattern.MetaPattern;

/**
 * This kind of segment can contain more than one path parameter, for example
 * "/message-{day}-{month}-{year}/".
 * 
 * @author andrea del bene
 * 
 */
public class MultiParamSegment extends AbstractURLSegment
{
	private volatile List<AbstractURLSegment> subSegments;

	MultiParamSegment(String text)
	{
		super(text);
	}

	/**
	 * Split the segment in input in sub segments like fixed segments ( {@link FixedURLSegment}) or
	 * parameter segments ({@link ParamSegment}).
	 * 
	 * @param text
	 *            the segment in input.
	 * @return the list of sub segments.
	 */
	private List<AbstractURLSegment> loadSubSegments(String text)
	{
		Matcher matcher = SEGMENT_PARAMETER.matcher(text);
		List<AbstractURLSegment> subSegments = new ArrayList<AbstractURLSegment>();
		int fixedTextIndex = 0;

		while (matcher.find())
		{
			String group = matcher.group();
			AbstractURLSegment segment = AbstractURLSegment.newSegment(group);
			String fixedText = text.substring(fixedTextIndex, matcher.start());

			fixedTextIndex = matcher.end();

			if (!fixedText.isEmpty())
			{
				subSegments.add(AbstractURLSegment.newSegment(fixedText));
			}

			subSegments.add(segment);
		}

		if (fixedTextIndex < text.length())
		{
			String fixedText = text.substring(fixedTextIndex, text.length());
			subSegments.add(AbstractURLSegment.newSegment(fixedText));
		}

		return subSegments;
	}

	@Override
	protected MetaPattern loadMetaPattern()
	{
		List<MetaPattern> patterns = new ArrayList<MetaPattern>();

		this.subSegments = Collections.unmodifiableList(loadSubSegments(toString()));

		for (AbstractURLSegment segment : subSegments)
		{
			patterns.add(segment.getMetaPattern());
		}

		return new MetaPattern(patterns);
	}

	@Override
	public int calculateScore(String actualSegment)
	{
		Matcher matcher = getMetaPattern().matcher(actualSegment);

		return matcher.matches() ? 1 : 0;
	}

	@Override
	public void populatePathVariables(Map<String, String> variables, String segment)
	{
		int startingIndex = 0;

		if (!getMetaPattern().matcher(segment).matches())
			return;

		for (AbstractURLSegment subSegment : subSegments)
		{
			MetaPattern pattern = subSegment.getMetaPattern();
			segment = segment.substring(startingIndex);
			Matcher matcher = pattern.matcher(segment);

			if (matcher.find())
			{
				String group = matcher.group();

				subSegment.populatePathVariables(variables, group);

				startingIndex = matcher.end();
			}
		}
	}

	public List<AbstractURLSegment> getSubSegments()
	{
		return subSegments;
	}
}
