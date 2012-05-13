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
package com.googlecode.wicket.jquery.ui.form.autocomplete;

import com.googlecode.wicket.jquery.ui.renderer.TextRenderer;

@Deprecated
public class AutoCompleteRenderer<T> extends TextRenderer<T> /* implements IChoiceRenderer<T> */
{
	private static final long serialVersionUID = 1L;
//	private static final String TEXT_FIELD = "cb_text"; 
//	private static final String VALUE_FIELD = "cb_value"; 

	public AutoCompleteRenderer()
	{
		super();
	}
	
	public AutoCompleteRenderer(String textExpression)
	{
		super(textExpression);
	}

//	@Override
//	public String getTextField()
//	{
//		String expression = this.getTextExpression();
//
//		if (expression != null)
//		{
//			return expression;
//		}
//		
//		return TEXT_FIELD;
//	}
//
//	@Override
//	public String getValueField()
//	{
//		String expression = this.getValueExpression();
//
//		if (expression != null)
//		{
//			return expression;
//		}
//		
//		return VALUE_FIELD;
//	}


} 