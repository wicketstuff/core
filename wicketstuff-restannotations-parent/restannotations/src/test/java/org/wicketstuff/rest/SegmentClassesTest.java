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
package org.wicketstuff.rest;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.wicket.util.parse.metapattern.MetaPattern;
import org.junit.Assert;
import org.junit.Test;
import org.wicketstuff.rest.resource.urlsegments.AbstractURLSegment;
import org.wicketstuff.rest.resource.urlsegments.MultiParamSegment;
import org.wicketstuff.rest.resource.urlsegments.ParamSegment;

public class SegmentClassesTest extends Assert
{

	@Test
	public void testStandardUrlSegmentPattern()
	{
		MetaPattern pattern = new MetaPattern(AbstractURLSegment.SEGMENT_PARAMETER);

		Matcher matcher = pattern.matcher("");
		assertFalse(matcher.matches());

		matcher = pattern.matcher("seg&ment");
		assertFalse(matcher.matches());

		matcher = pattern.matcher("segment:");
		assertFalse(matcher.matches());

		matcher = pattern.matcher("{*}");
		assertFalse(matcher.matches());

		matcher = pattern.matcher("{segment}");
		assertTrue(matcher.matches());

		matcher = pattern.matcher("{segment0} a segment {segment1} another segment {segment2}");
		assertTrue(matcher.find());

		matcher.reset();
		assertFalse(matcher.matches());

		matcher = pattern.matcher("{117}");
		assertFalse(matcher.matches());

		pattern = new MetaPattern(AbstractURLSegment.REGEXP_BODY);
		matcher = pattern.matcher("[0-9]*:abba");
		assertTrue(matcher.matches());

		matcher = pattern.matcher("^\\(?\\d{3}\\)?[ -]?\\d{3}[ -]?\\d{4}$anotherseg");
		assertTrue(matcher.matches());
	}

	@Test
	public void testMatrixParameters()
	{
		String segment = "segment";
		String segmentMatrixParam = segment + ";param=value";

		String segmentValue = AbstractURLSegment.getActualSegment(segment);
		assertEquals(segment, segmentValue);

		Map<String, String> matrixParams = AbstractURLSegment.getSegmentMatrixParameters(segment);
		assertTrue(matrixParams.size() == 0);

		segmentValue = AbstractURLSegment.getActualSegment(segmentMatrixParam);
		assertEquals(segment, segmentValue);

		matrixParams = AbstractURLSegment.getSegmentMatrixParameters(segmentMatrixParam);

		assertEquals(1, matrixParams.size());

		assertNotNull(matrixParams.get("param"));

		String segmentMatrixParamsQuotes = segment + ";param=value;param1='hello world'";
		matrixParams = AbstractURLSegment.getSegmentMatrixParameters(segmentMatrixParamsQuotes);

		assertEquals(2, matrixParams.size());
		assertEquals("value", matrixParams.get("param"));
		assertEquals("'hello world'", matrixParams.get("param1"));
	}

	@Test
	public void testParamSegment() throws Exception
	{
		String segmentWithRegEx = "{id:[0-9]*:abba}";
		AbstractURLSegment segment = AbstractURLSegment.newSegment(segmentWithRegEx);

		assertTrue(segment instanceof ParamSegment);

		ParamSegment paramSegment = (ParamSegment)segment;

		assertEquals(paramSegment.getParamName(), "id");
		assertEquals(paramSegment.getMetaPattern().toString(), "[0-9]*:abba");

		MetaPattern metaPattern = paramSegment.getMetaPattern();

		assertTrue(metaPattern.matcher("1:abba").matches());
		assertTrue(metaPattern.matcher("1234521:abba").matches());
		assertTrue(metaPattern.matcher(":abba").matches());

		String segmentMultiParam = "{segment0}asegment{segment1:^\\(?\\d{3}\\)?[ -]?\\d{3}[ -]?\\d{4}$}anotherseg";
		segment = AbstractURLSegment.newSegment(segmentMultiParam);

		assertTrue(segment instanceof MultiParamSegment);

		MultiParamSegment multiParamSegment = (MultiParamSegment)segment;
		List<AbstractURLSegment> subSegments = multiParamSegment.getSubSegments();

		assertEquals(4, subSegments.size());
		metaPattern = subSegments.get(2).getMetaPattern();
		assertEquals(metaPattern.toString(), "^\\(?\\d{3}\\)?[ -]?\\d{3}[ -]?\\d{4}$");

		segmentMultiParam = "filename-{symbolicName:[a-z]+}-{version:\\d\\.\\d\\.\\d}{extension:\\.[a-z]+}";
		segment = AbstractURLSegment.newSegment(segmentMultiParam);
		multiParamSegment = (MultiParamSegment)segment;
		metaPattern = multiParamSegment.getMetaPatternWithGroups();

		String fileName = "filename-gsaon-1.2.3.zip";
		Matcher matcher = metaPattern.matcher(fileName);

		assertTrue(matcher.matches());

		// testing segment parsing with regular expressions					
		assertEquals("gsaon", matcher.group(1));
		assertEquals("1.2.3", matcher.group(2));
		assertEquals(".zip", matcher.group(3));


		matcher = metaPattern.matcher("gsaon-1.2.3.zip");

		assertFalse(matcher.matches());
		


	}
}
