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
package org.wicketstuff.objectautocomplete;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.IAutoCompleteRenderer;
import org.apache.wicket.request.Response;

/**
 * @author roland
 * @since May 20, 2008
 */
public class ObjectAutoCompleteRenderer<O> extends AbstractObjectAutoCompleteRenderer<O> implements
	IAutoCompleteRenderer<O>
{

	private static final long serialVersionUID = 1L;

	public static final <O> IAutoCompleteRenderer<O> instance()
	{
		return new ObjectAutoCompleteRenderer<O>();
	}

	/** {@inheritDoc} */
	public void render(O object, Response response, String criteria)
	{
		renderObject(object, response, criteria);
	}

	public void renderHeader(Response response)
	{
		response.write("<ul>");
	}

	public void renderFooter(Response response, int renderedChoices)
	{
		response.write("</ul>");
	}

}
