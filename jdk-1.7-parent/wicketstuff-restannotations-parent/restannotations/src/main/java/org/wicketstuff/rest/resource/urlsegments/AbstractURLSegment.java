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

import static org.apache.wicket.util.parse.metapattern.MetaPattern.COLON;
import static org.apache.wicket.util.parse.metapattern.MetaPattern.LEFT_CURLY;
import static org.apache.wicket.util.parse.metapattern.MetaPattern.RIGHT_CURLY;
import static org.apache.wicket.util.parse.metapattern.MetaPattern.VARIABLE_NAME;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.util.parse.metapattern.MetaPattern;
import org.apache.wicket.util.parse.metapattern.OptionalMetaPattern;
import org.apache.wicket.util.parse.metapattern.parsers.VariableAssignmentParser;
import org.apache.wicket.util.string.StringValue;
import org.wicketstuff.rest.resource.urlsegments.visitor.ISegementElement;

/**
 * Base class to contain the informations of the segments that compose the URL used to map a method.
 * It's used to use simple segments with no path parameters.
 * 
 * @author andrea del bene
 * 
 */
public abstract class AbstractURLSegment extends StringValue implements ISegementElement
{
	private static final long serialVersionUID = 1L;
    
	/** MetaPattern to identify the content of a regular expression. */
	public static final MetaPattern REGEXP_BODY = new MetaPattern("([^\\}\\{]*|(\\{[\\d]+\\}))*");
	/** MetaPattern to identify the declaration of a regular expression. */
	public static final MetaPattern REGEXP_DECLARATION = new MetaPattern(COLON, REGEXP_BODY);
	/**
	 * MetaPattern to identify a path parameter inside a segment (i.e. "{paramName:regexp}")
	 */
	public static final MetaPattern SEGMENT_PARAMETER = new MetaPattern(LEFT_CURLY, VARIABLE_NAME,
		new OptionalMetaPattern(REGEXP_DECLARATION), RIGHT_CURLY);

	/** The MetaPattern (i.e regular expression) corresponding to the current segment. */
	private final MetaPattern metaPattern;

	AbstractURLSegment(String text)
	{
		super(text);
		this.metaPattern = loadMetaPattern();
	}

	/**
	 * Method invoked to load the MetaPattern for the current segment.
	 * 
	 * @return the MetaPattern for the current segment.
	 */
	protected abstract MetaPattern loadMetaPattern();

	/**
	 * Factory method to create new instances of AbstractURLSegment.
	 * 
	 * @param segment
	 *            The content of the new segment.
	 * @return the new instance of AbstractURLSegment.
	 */
	static public AbstractURLSegment newSegment(String segment)
	{
		if (SEGMENT_PARAMETER.matcher(segment).matches())
			return new ParamSegment(segment);

		if (SEGMENT_PARAMETER.matcher(segment).find())
			return new MultiParamSegment(segment);

		return new FixedURLSegment(segment);
	}

	/**
	 * Get the segment value without optional matrix parameters. For example given the following
	 * value 'segment;parm=value', the function returns 'segment'.
	 * 
	 * @param fullSegment
	 * @return the value of the segment without matrix parameters.
	 */
	static public String getActualSegment(String fullSegment)
	{
		String[] segmentParts = fullSegment.split(MetaPattern.SEMICOLON.toString());
		return segmentParts[0];
	}

	/**
	 * Extract matrix parameters from the segment in input.
	 * 
	 * @param fullSegment
	 *            the segment in input.
	 * @return a map containing matrix parameters.
	 */
	static public Map<String, String> getSegmentMatrixParameters(String fullSegment)
	{
		String[] segmentParts = fullSegment.split(MetaPattern.SEMICOLON.toString());
		HashMap<String, String> matrixParameters = new HashMap<String, String>();

		if (segmentParts.length < 2)
			return matrixParameters;

		for (int i = 1; i < segmentParts.length; i++)
		{
			String parameterDeclar = segmentParts[i];
			VariableAssignmentParser parser = new VariableAssignmentParser(parameterDeclar);

			parser.matcher().find();
			matrixParameters.put(parser.getKey(), parser.getValue());
		}

		return matrixParameters;
	}

	/**
	 * Getter method for segment MetaPattern.
	 **/
	public final MetaPattern getMetaPattern()
	{
		return metaPattern;
	}
}