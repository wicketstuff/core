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
package com.googlecode.wicket.kendo.ui.datatable.column;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.utils.BuilderUtils;

/**
 * Base class for {@link IColumn}{@code s} implementation
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
	 *
	 * @param title the text of the column header
	 */
	public AbstractColumn(String title)
	{
		this(Model.of(title), "", WIDTH);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param width the desired width of the column
	 */
	public AbstractColumn(String title, int width)
	{
		this(Model.of(title), "", width);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param field the object field name
	 */
	public AbstractColumn(String title, String field)
	{
		this(Model.of(title), field, WIDTH);
	}

	/**
	 * Constructor
	 *
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
	 *
	 * @param title the text of the column header
	 */
	public AbstractColumn(IModel<String> title)
	{
		this(title, "", WIDTH);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param width the desired width of the column
	 */
	public AbstractColumn(IModel<String> title, int width)
	{
		this(title, "", width);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param field the object field name
	 */
	public AbstractColumn(IModel<String> title, String field)
	{
		this(title, field, WIDTH);
	}

	/**
	 * Constructor
	 *
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
	public int getWidth()
	{
		return this.width;
	}

	public boolean isVisible()
	{
		return true;
	}

	@Override
	public String getType()
	{
		return null;
	}

	@Override
	public String getFormat()
	{
		return null;
	}

	@Override
	public String getTemplate()
	{
		return null;
	}

	@Override
	public String getFilterable()
	{
		return null;
	}

	@Override
	public String getMenu()
	{
		return null;
	}

	@Override
	public List<String> getAggregates()
	{
		return Collections.emptyList();
	}

	/**
	 * Gets the list of <tt>aggregates<tt> as json array
	 *
	 * @return the list of <tt>aggregates<tt> as json array
	 */
	protected String getAggregatesAsString()
	{
		List<String> aggregates = new ArrayList<String>();

		for (String aggregate : this.getAggregates())
		{
			aggregates.add(Options.asString(aggregate));
		}

		return aggregates.toString();
	}

	@Override
	public String getGroupHeaderTemplate()
	{
		return null;
	}

	@Override
	public String getGroupFooterTemplate()
	{
		return null;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		BuilderUtils.append(builder, "title", this.getTitle());
		builder.append(", ");
		BuilderUtils.append(builder, "field", this.getField());

		builder.append(", ");
		BuilderUtils.append(builder, "hidden", !this.isVisible());

		if (this.getWidth() > 0)
		{
			builder.append(", ");
			BuilderUtils.append(builder, "width", this.getWidth());
		}

		// nullable options (string) //

		if (this.getFormat() != null)
		{
			builder.append(", ");
			BuilderUtils.append(builder, "format", this.getFormat());
		}

		if (this.getTemplate() != null)
		{
			builder.append(", ");
			BuilderUtils.append(builder, "template", this.getTemplate());
		}

		if (this.getGroupHeaderTemplate() != null)
		{
			builder.append(", ");
			BuilderUtils.append(builder, "groupHeaderTemplate", this.getGroupHeaderTemplate());
		}

		if (this.getGroupFooterTemplate() != null)
		{
			builder.append(", ");
			BuilderUtils.append(builder, "groupFooterTemplate", this.getGroupFooterTemplate());
		}

		// nullable options (object) //

		if (this.getMenu() != null)
		{
			builder.append(", ");
			builder.append(Options.QUOTE).append("menu").append(Options.QUOTE).append(": ").append(this.getMenu());
		}

		if (!this.getAggregates().isEmpty())
		{
			builder.append(", ");
			builder.append(Options.QUOTE).append("aggregates").append(Options.QUOTE).append(": ").append(this.getAggregatesAsString());
		}

		if (this.getFilterable() != null)
		{
			builder.append(", ");
			builder.append(Options.QUOTE).append("filterable").append(Options.QUOTE).append(": ").append(this.getFilterable());
		}

		return builder.toString();
	}
}
