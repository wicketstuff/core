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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.util.io.Streams;
import org.apache.wicket.util.string.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.common.analyze.ImmutableTree;

public class TreeReader
{
	private final static Logger LOG = LoggerFactory.getLogger(TreeReader.class);
	
	public static ISerializedObjectTree fromResource(Class<?> baseType, String resourceName)
		throws IOException {
		return fromResourceWithFilename(baseType, baseType.getSimpleName()+"-"+resourceName);
	}

	public static ISerializedObjectTree fromResourceWithFilename(Class<?> baseType, String resourceName)
		throws IOException
	{
		InputStream resourceAsStream = null;
		try
		{
			resourceAsStream = baseType.getResourceAsStream(resourceName);
			return fromString(Streams.readString(resourceAsStream));
		}
		finally
		{
			if (resourceAsStream != null)
				resourceAsStream.close();
		}
	}

	public static ISerializedObjectTree fromString(String asString) throws IOException
	{
		List<Line> lines = new ArrayList<Line>();
		String[] textLines = Strings.split(asString, '\n');
		for (String textLine : textLines)
		{
			if (!textLine.trim().isEmpty()) {
				lines.add(new Line(textLine));
			}
		}
		
		if (!lines.isEmpty()) {
			Line last=lines.get(0);
			IndentedLines parent=new IndentedLines(last);
			
			for (Line l : lines.subList(1, lines.size())) {
				if (l.indent>last.indent) {
					IndentedLines sub=new IndentedLines(parent,l);
					parent.children.add(sub);
					parent=sub;
				} else {
					for (int i=0,s=last.indent-l.indent;i<=s;i++) {
						parent=parent.parent;
					}
					IndentedLines sub=new IndentedLines(parent,l);
					parent.children.add(sub);
					parent=sub;
				}
				last=l;
			}
			
			while (parent.parent!=null) {
				parent=parent.parent;
			}
			
			return parent.asObjectTree();
		}
		throw new IOException("empty file");
	}


	// spaces (indent (spaces=2)) className ( label ) | size
	protected static class IndentedLines {
		IndentedLines parent;
		List<IndentedLines> children=new ArrayList<TreeReader.IndentedLines>();
		
		private final Line line;
		
		public IndentedLines(Line line)
		{
			this.line = line;
		}

		public ISerializedObjectTree asObjectTree()
		{
			List<ISerializedObjectTree> list=new ArrayList<ISerializedObjectTree>();
			for (IndentedLines il : children) {
				list.add(il.asObjectTree());
			}
			return new ImmutableTree(null, line.type, line.label, line.size, list);
		}

		public IndentedLines(IndentedLines parent, Line line)
		{
			this(line);
			this.parent=parent;
		}
	}
	
	public static class Line
	{
		private static Pattern pattern = Pattern.compile("^([ ]*)([a-zA-Z0-9\\.;\\[\\$]+)(\\([a-zA-Z0-9\\|]+\\))?[ ]+\\|[ ]+([0-9]+)$");

		final int indent;
		final Class<?> type;
		final String label;
		final int size;

		public Line(String textLine)
		{
			Matcher matcher = pattern.matcher(textLine);
			if (!matcher.matches())
			{
				throw new IllegalArgumentException("Could not parse '" + textLine+"'");
			} 
			String typeName = matcher.group(2);
			indent=matcher.group(1).length()/2;
			type=typeFromString(typeName);
			String labelFromMatcher=matcher.group(3);
			label = labelFromMatcher!=null ? labelFromMatcher.substring(1,labelFromMatcher.length()-1) : null;
			size=Integer.valueOf(matcher.group(4).trim());
		}

		private static Class<?> typeFromString(String typeName)
		{
			try
			{
				if (typeName.startsWith(".")) {
					typeName=TreeReader.class.getPackage().getName()+typeName;
				}
				return Class.forName(typeName);
			}
			catch (ClassNotFoundException e)
			{
				throw new IllegalArgumentException(e);
			}
		}
	}
}
