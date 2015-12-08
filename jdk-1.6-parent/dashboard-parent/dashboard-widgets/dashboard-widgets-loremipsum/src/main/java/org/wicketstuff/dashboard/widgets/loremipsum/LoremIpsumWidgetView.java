/*
 * Copyright 2012 Decebal Suiu
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.wicketstuff.dashboard.widgets.loremipsum;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.wicketstuff.dashboard.Widget;
import org.wicketstuff.dashboard.web.WidgetView;

/**
 * @author Decebal Suiu
 */
public class LoremIpsumWidgetView extends WidgetView {

	private static final long serialVersionUID = 1L;

	public LoremIpsumWidgetView(String id, Model<Widget> model) {
		super(id, model);

		LoremIpsumWidget widget = (LoremIpsumWidget) model.getObject();
		add(new Label("text", widget.getText()));
	}

}
