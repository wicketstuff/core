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
package org.wicketstuff.poi.excel;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.parser.XmlPullParser;
import org.apache.wicket.markup.parser.XmlTag;

/**
 * First try to get the most nested component in the table data tag, if not possible set the most
 * nested value in markup as a {@link String}.
 *
 * @author Pedro Santos
 */
public class GeneralPurposeExporter implements CellExporter
{

	public void exportCell(XmlTag tag, XmlPullParser parser, Cell cell, Component gridComponent)
		throws ParseException
	{
		XmlTag firstMostNestedTag = tag;
		// find the most inner tag value
		while ((tag = parser.nextTag()).getName().equals("td") == false)
		{
			if (tag.isOpen() || tag.isOpenClose())
			{
				firstMostNestedTag = tag;
			}
			else
			{
				break;
			}
		}
		CharSequence possibleComponentReference = firstMostNestedTag.getAttribute(TableParser.OutputPathBehavior.PATH_ATTRIBUTE);
		if (possibleComponentReference != null)
		{
			Component firstMostNestedComponent = gridComponent.getPage().get(
				possibleComponentReference.toString());
			// exclude auto links
			if( firstMostNestedComponent.getClass().getName().contains( "ResourceReferenceAutolink" ) )
			{
				cell.setCellValue( "" );
				return;
			}
			// handle links
			if( firstMostNestedComponent instanceof Link)
			{
				Link<?> link = (Link<?>) firstMostNestedComponent;
				firstMostNestedComponent = getLinkInnerComponent(link);
				if( firstMostNestedComponent == null )
				{
					cell.setCellValue( "" );
					return;
				}
			}
			Object modelValue = firstMostNestedComponent.getDefaultModelObject();
			if (modelValue != null)
			{
				if (modelValue instanceof Number)
				{
					handleNumber(cell, (Number)modelValue);
				}
				else if (modelValue instanceof CharSequence)
				{
					cell.setCellValue(modelValue.toString());
				}
				else if (modelValue instanceof CharSequence)
				{
					cell.setCellValue(modelValue.toString());
				}
				else if (modelValue instanceof Boolean)
				{
					cell.setCellValue((Boolean)modelValue);
				}
				else if (modelValue instanceof Calendar)
				{
					handleCalendar(cell, (Calendar)modelValue);
				}
				else if (modelValue instanceof Date)
				{
					handleDate(cell, (Date)modelValue);
				}
				else
				{
					cell.setCellValue(modelValue.toString());
				}
			}
		}
		else
		{
			// simply set the first most nested tag value
			String value = parser.getInput(
				firstMostNestedTag.getPos() + firstMostNestedTag.getLength(), tag.getPos())
				.toString();
			cell.setCellValue(value);
		}
	}

	protected void handleNumber(Cell cell, Number number ) {
		cell.setCellValue(number.doubleValue());
	}

	protected void handleCalendar(Cell cell, Calendar calendar ) {
		cell.setCellValue(calendar);
	}

	protected void handleDate(Cell cell, Date date) {
		cell.setCellValue(date);
	}

	protected Component getLinkInnerComponent(Link<?> link) {
		return link.iterator().next();
	}
}
