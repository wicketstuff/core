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
package com.googlecode.wicket.kendo.ui.datatable.editor;

import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;

/**
 * Provides a {@link JavaScriptContentHeaderItem} for an {@link IKendoEditor}<br/>
 * Usage:<br/>
 * <code><pre>
 * new PropertyColumn("Status", "status") {
 * 
 * 	public String getEditor()
 * 	{
 * 		return EDITOR_NAME;
 * 	}
 * }
 * 
 * class MyDataTable
 * {
 * 	public void renderHead(IHeaderResponse response)
 * 	{
 * 		super.renderHead(response);
 * 
 * 		response.render(new KendoEditorHeaderItem(new DropDownListEditor(EDITOR_NAME, MyEnum.values()), EDITOR_NAME));
 * 	}
 * }
 * </pre></code>
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class KendoEditorHeaderItem extends JavaScriptContentHeaderItem
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param editor the {@link IKendoEditor}
	 * @param id the id of the javascript element
	 */
	public KendoEditorHeaderItem(IKendoEditor editor, String id)
	{
		this(editor, id, null);
	}

	/**
	 * Constructor
	 * 
	 * @param editor the {@link IKendoEditor}
	 * @param id the id of the javascript element
	 * @param condition the condition to use for Internet Explorer conditional comments. E.g. "IE 7".
	 */
	public KendoEditorHeaderItem(IKendoEditor editor, String id, String condition)
	{
		super(editor.toString(), id, condition);
	}
}
