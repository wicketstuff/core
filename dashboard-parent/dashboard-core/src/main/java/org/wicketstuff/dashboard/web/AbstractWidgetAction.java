/*
 * Copyright 2014 Decebal Suiu
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
package org.wicketstuff.dashboard.web;

import org.apache.wicket.model.IModel;
import org.wicketstuff.dashboard.Widget;
import org.wicketstuff.dashboard.WidgetAction;

/**
 * @author Decebal Suiu
 */
public abstract class AbstractWidgetAction implements WidgetAction {
	private static final long serialVersionUID = 1L;
	protected Widget widget;
	protected IModel<String> tooltip;
	private String cssClass;

	public AbstractWidgetAction(Widget widget) {
		this.widget = widget;
	}

	public Widget getWidget() {
		return widget;
	}

	@Override
	public IModel<String> getTooltip() {
		return tooltip;
	}

	public void setTooltip(IModel<String> tooltip) {
		this.tooltip = tooltip;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	@Override
	public String getCssClass() {
		return cssClass;
	}
}
