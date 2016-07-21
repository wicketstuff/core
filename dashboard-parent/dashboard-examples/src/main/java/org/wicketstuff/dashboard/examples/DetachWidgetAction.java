/*
 * Copyright 2013 Decebal Suiu
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
package org.wicketstuff.dashboard.examples;

import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.dashboard.Widget;
import org.wicketstuff.dashboard.web.AbstractWidgetAction;

/**
 * @author Decebal Suiu
 */
public class DetachWidgetAction extends AbstractWidgetAction {

	private static final long serialVersionUID = 1L;

	public DetachWidgetAction(Widget widget) {
		super(widget);

		tooltip = new ResourceModel("detach");

        setImage(HomePage.class, "detach.png");
	}

	@Override
	public AbstractLink getLink(String id) {
		PageParameters parameters = new PageParameters();
		parameters.add("id", widget.getId());
		BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>(id, WidgetPage.class, parameters);

		PopupSettings popupSettings = new PopupSettings(widget.getTitle()).setHeight(320).setWidth(550);
	    link.setPopupSettings(popupSettings);

	    return link;
	}

}
