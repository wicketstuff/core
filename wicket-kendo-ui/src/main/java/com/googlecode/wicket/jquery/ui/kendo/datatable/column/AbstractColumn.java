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

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.Options;

/**
 * Base class for {@link IColumn}<tt>s</tt> implementation
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class AbstractColumn implements IColumn
{
	private static final long serialVersionUID = 1L;

	/** Default width. */
	static final int WIDTH = -1;

	private final String field;
	private final IModel<String> title;
	private Integer width;

	/**
	 * Constructor
	 * @param title the text of the column header
	 */
	public AbstractColumn(String title)
	{
		this(Model.of(title), "", WIDTH);
	}

	/**
	 * Constructor
	 * @param title the text of the column header
	 * @param width the desired width of the column
	 */
	public AbstractColumn(String title, int width)
	{
		this(Model.of(title), "", width);
	}

	/**
	 * Constructor
	 * @param title the text of the column header
	 * @param field the object field name
	 */
	public AbstractColumn(String title, String field)
	{
		this(Model.of(title), field, WIDTH);
	}

	/**
	 * Constructor
	 * @param title the text of the column header
	 * @param field the object field name
	 * @param width the desired width of the column
	 */
	public AbstractColumn(String title, String field, int width)
	{
		this(Model.of(title), field, width);
	}


	/**
	 * Constructor
	 * @param title the text of the column header
	 */
	public AbstractColumn(IModel<String> title)
	{
		this(title, "", WIDTH);
	}

	/**
	 * Constructor
	 * @param title the text of the column header
	 * @param width the desired width of the column
	 */
	public AbstractColumn(IModel<String> title, int width)
	{
		this(title, "", width);
	}

	/**
	 * Constructor
	 * @param title the text of the column header
	 * @param field the object field name
	 */
	public AbstractColumn(IModel<String> title, String field)
	{
		this(title, field, WIDTH);
	}

	/**
	 * Constructor
	 * @param title the text of the column header
	 * @param field the object field name
	 * @param width the desired width of the column
	 */
	public AbstractColumn(IModel<String> title, String field, int width)
	{
		this.title = title;
		this.field = field;
		this.width = width;
	}

	@Override
	public String getTitle()
	{
		return this.title.getObject();
	}

	@Override
	public String getField()
	{
		return this.field;
	}

	@Override
	public String getFormat()
	{
		return null;
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

		if (this.getFormat() != null)
		{
			builder.append(", ");
			builder.append(Options.QUOTE).append("format").append(Options.QUOTE).append(": ").append(Options.QUOTE).append(this.getFormat()).append(Options.QUOTE);
		}

		if (this.getWidth() > 0)
		{
			builder.append(", ");
			builder.append(Options.QUOTE).append("width").append(Options.QUOTE).append(": ").append(this.getWidth());
		}

		return builder.toString();
	}
}
