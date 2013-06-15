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

import java.io.Serializable;

import com.googlecode.wicket.jquery.core.renderer.ITextRenderer;
import com.googlecode.wicket.jquery.core.renderer.TextRenderer;

/**
 * TODO javadoc
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T>
 */
public class PropertyColumn<T extends Serializable> implements IColumn<T>
{
	private static final long serialVersionUID = 1L;

	/** Default width. */
	private static final int WIDTH = -1;

	private final String title;
	private final String property;
	private final ITextRenderer<T> renderer;

	private Integer width;

	/**
	 * @param title the text of the column header
	 */
	public PropertyColumn(String title)
	{
		this(title, title, WIDTH);
	}

	/**
	 * @param title the text of the column header
	 * @param width the desired width of the column
	 */
	public PropertyColumn(String title, int width)
	{
		this(title, title, width);
	}

	/**
	 * @param title the text of the column header
	 * @param property the object property name
	 */
	public PropertyColumn(String title, String property)
	{
		this(title, property, WIDTH);
	}

	/**
	 * @param title the text of the column header
	 * @param property the object property name
	 * @param width the desired width of the column
	 */
	public PropertyColumn(String title, String property, int width)
	{
		this.title = title;
		this.width = width;
		this.property = property;
		this.renderer = new TextRenderer<T>(property);
	}

	@Override
	public final String getTitle()
	{
		return this.title;
	}

	@Override
	public final String getField()
	{
		return this.property;
	}

	@Override
	public final String getValue(T object)
	{
		return this.renderer.getText(object);
	}

	@Override
	public int getWidth()
	{
		return this.width;
	}
}
