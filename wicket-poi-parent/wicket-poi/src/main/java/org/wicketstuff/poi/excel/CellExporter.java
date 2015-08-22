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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.wicket.Component;
import org.apache.wicket.markup.parser.XmlPullParser;
import org.apache.wicket.markup.parser.XmlTag;

/**
 * Function interface to access the component table data and set in the {@link Cell}. A general
 * purpose one is provided, but it can be customized by setting
 * {@link TableComponentAsXlsHandler#setCellExporter(CellExporter)}
 * 
 * @author Pedro Santos
 */
public interface CellExporter
{
	/**
	 * @param tag
	 *            <td>HTML tag
	 * @param parser
	 *            the {@link XmlPullParser} in use, can be useful to parse nested elements
	 * @param cell
	 *            the target cell, where the value needs to be set
	 * @param tableComponent
	 *            the table components being parsed
	 * @throws ParseException
	 */
	public void exportCell(XmlTag tag, XmlPullParser parser, Cell cell, Component tableComponent)
		throws ParseException;
}
