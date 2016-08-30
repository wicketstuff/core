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

import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.utils.BuilderUtils;
import com.googlecode.wicket.kendo.ui.datatable.editor.IKendoEditor;

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
	private final Integer width;
	private final IModel<String> titleModel;

	// TODO: add locked & lockable

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 */
	public AbstractColumn(String title)
	{
		this(Model.of(title), null, WIDTH);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param width the desired width of the column
	 */
	public AbstractColumn(String title, int width)
	{
		this(Model.of(title), null, width);
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
		this(title, null, WIDTH);
	}

	/**
	 * Constructor
	 *
	 * @param title the text of the column header
	 * @param width the desired width of the column
	 */
	public AbstractColumn(IModel<String> title, int width)
	{
		this(title, null, width);
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
		this.titleModel = title;
		this.field = field;
		this.width = width;
	}

	@Override
	public String getTitle()
	{
		return this.titleModel.getObject();
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
	
	@Override
	public int getMinScreenWidth()
	{
		return 0;
	}

	public boolean isVisible()
	{
		return true;
	}

	@Override
	public IKendoEditor getEditor()
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
	public String getAttributes()
	{
		return null;
	}

	@Override
	public String getFooterTemplate()
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
	protected final String getAggregatesAsString()
	{
		List<String> aggregates = Generics.newArrayList();

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

	// schema model //

	@Override
	public Boolean isEditable()
	{
		return null;
	}

	@Override
	public Boolean isNullable()
	{
		return null;
	}

	@Override
	public String getType()
	{
		return null;
	}

	// Methods //

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		BuilderUtils.append(builder, "title", this.getTitle());

		builder.append(", ");
		BuilderUtils.append(builder, "hidden", !this.isVisible());

		if (this.getWidth() > 0)
		{
			builder.append(", ");
			BuilderUtils.append(builder, "width", this.getWidth());
		}

		if (this.getMinScreenWidth() > 0)
		{
			builder.append(", ");
			BuilderUtils.append(builder, "minScreenWidth", this.getMinScreenWidth());
		}

		// nullable options (string) //

		if (this.getField() != null)
		{
			builder.append(", ");
			BuilderUtils.append(builder, "field", this.getField());
		}

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

		if (this.getAttributes() != null)
		{
			builder.append(", ");
			builder.append(JSONObject.quote("attributes")).append(": ").append(this.getAttributes()); // caution, not a String value
		}

		if (this.getFooterTemplate() != null)
		{
			builder.append(", ");
			BuilderUtils.append(builder, "footerTemplate", this.getFooterTemplate());
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

		// nullable options (function) //

		if (this.getEditor() != null)
		{
			builder.append(", ");
			builder.append(Options.QUOTE).append("editor").append(Options.QUOTE).append(": ").append(this.getEditor());
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
