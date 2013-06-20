/*
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
package com.googlecode.wicket.jquery.ui.kendo.datatable.column;

import com.googlecode.wicket.jquery.core.Options;

/**
 * Base class for {@link IColumn}<tt>s</tt> implementation
 *
 * @param <T> the type of the model object
 * @author Sebastien Briquet - sebfz1
 */
public abstract class AbstractColumn<T> implements IColumn<T>
{
	private static final long serialVersionUID = 1L;

	/** Default width. */
	static final int WIDTH = -1;

	private final String field;
	private final String title;
	private Integer width;

	/**
	 * Constructor
	 * @param title the text of the column header
	 */
	public AbstractColumn(String title)
	{
		this(title, "", WIDTH);
	}

	/**
	 * Constructor
	 * @param title the text of the column header
	 * @param width the desired width of the column
	 */
	public AbstractColumn(String title, int width)
	{
		this(title, "", width);
	}

	/**
	 * Constructor
	 * @param title the text of the column header
	 * @param field the object field name
	 */
	public AbstractColumn(String title, String field)
	{
		this(title, field, AbstractColumn.WIDTH);
	}

	/**
	 * Constructor
	 * @param title the text of the column header
	 * @param field the object field name
	 * @param width the desired width of the column
	 */
	public AbstractColumn(String title, String field, int width)
	{
		this.title = title;
		this.field = field;
		this.width = width;
	}

	@Override
	public final String getTitle()
	{
		return this.title;
	}

	@Override
	public final String getField()
	{
		return this.field;
	}

	@Override
	public int getWidth()
	{
		return this.width;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append(Options.QUOTE).append("title").append(Options.QUOTE).append(": ").append(Options.QUOTE).append(this.getTitle()).append(Options.QUOTE);
		builder.append(", ");
		builder.append(Options.QUOTE).append("field").append(Options.QUOTE).append(": ").append(Options.QUOTE).append(this.getField()).append(Options.QUOTE);

		if (this.getWidth() > 0)
		{
			builder.append(", ");
			builder.append(Options.QUOTE).append("width").append(Options.QUOTE).append(": ").append(this.getWidth());
		}

		return builder.toString();
	}
}
