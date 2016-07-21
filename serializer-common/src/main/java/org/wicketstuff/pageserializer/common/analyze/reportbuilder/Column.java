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
package org.wicketstuff.pageserializer.common.analyze.reportbuilder;

/**
 * report column
 * @author mosmann
 *
 */
public class Column
{
	final IAttributes attributes;

	final String name;

	/**
	 * column with id and some attributes
	 * @param name
	 * @param attributes
	 */
	public Column(String name, IAttributes attributes)
	{
		this.name = name;
		this.attributes = attributes;
	}

	/**
	 * column with id and no custom attributes set
	 * @param name
	 */
	public Column(String name)
	{
		this(name, new AttributeBuilder().build());
	}

	public IAttributes attributes()
	{
		return attributes;
	}

	/**
	 * column alignment
	 * @author mosmann
	 *
	 */
	public static enum Align implements TypedAttribute<Align> {
		Left, Right;
	}

	static class IntegerType implements TypedAttribute<Integer>
	{

	}

	static class CharType implements TypedAttribute<Character>
	{

	}

	static class StringType implements TypedAttribute<String>
	{

	}

	static class AlignType implements TypedAttribute<Align>
	{

	}

	/**
	 * column filler before column content
	 */
	public static final TypedAttribute<Character> FillBefore = new CharType();
	/**
	 * column filler after column content
	 */
	public static final TypedAttribute<Character> FillAfter = new CharType();
	/**
	 * column separator
	 */
	public static final TypedAttribute<String> Separator = new StringType();
	/**
	 * column indent
	 */
	public static final TypedAttribute<String> Indent = new StringType();
}