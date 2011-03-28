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

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.model.Model;

/**
 * A {@link ModalWindow} displaying a {@link ClojureScriptEnginePanel}.
 * 
 * @author cretzel
 */
public class ClojureScriptEngineWindow extends ModalWindow {

	private static final long serialVersionUID = 1L;
	private ClojureScriptEnginePanel enginePanel;

	public ClojureScriptEngineWindow(String id) {
		super(id);

		setTitle(Model.of("Wicket Console"));
		setAutoSize(true);
		setResizable(false);

		enginePanel = new ClojureScriptEnginePanel(getContentId(), Model.of(""));
		enginePanel.add(new AttributeAppender("style", Model.of("width:500px"),
				";"));
		setContent(enginePanel);
	}

	public ClojureScriptEnginePanel getEnginePanel() {
		return enginePanel;
	}

}
