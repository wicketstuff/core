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
package org.wicketstuff.minis.component.excel;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.wicket.Component;
import org.apache.wicket.markup.parser.XmlPullParser;
import org.apache.wicket.markup.parser.XmlTag;

/**
 * Fist try to get the most nested component in the table data tag, if not possible set the most
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
		CharSequence possibleComponentReference = firstMostNestedTag.getAttribute(TableComponentAsXlsHandler.OutputPathBehavior.PATH_ATTRIBUTE);
		if (possibleComponentReference != null)
		{
			Component firstMostNestedComponent = gridComponent.getPage().get(
				possibleComponentReference.toString());
			Object modelValue = firstMostNestedComponent.getDefaultModelObject();
			if (modelValue != null)
			{
				if (modelValue instanceof Number)
				{
					cell.setCellValue(((Number)modelValue).doubleValue());
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
					cell.setCellValue((Calendar)modelValue);
				}
				else if (modelValue instanceof Date)
				{
					cell.setCellValue((Date)modelValue);
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
}
