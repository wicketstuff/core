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

package org.wicketstuff.console;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;

/**
 * @author cretzel
 */
public class OutputTextArea extends TextArea<String>
{

	private class ErrorClassAttributeAppender extends AttributeAppender
	{
		private static final long serialVersionUID = 1L;

		private ErrorClassAttributeAppender()
		{
			super("class", Model.of("error"), " ");
		}

		@Override
		public boolean isEnabled(final Component component)
		{
			return super.isEnabled(component) && !enginePanel.isSuccess();
		}
	}

	private static final long serialVersionUID = 1L;
	private final ScriptEnginePanel enginePanel;

	public OutputTextArea(final String id, final ScriptEnginePanel enginePanel)
	{
		super(id);
		this.enginePanel = enginePanel;
		add(new ErrorClassAttributeAppender());
	}

}
