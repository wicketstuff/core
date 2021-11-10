/**
 *
 Copyright 2012 Vineet Semwal

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package org.wicketstuff.examples;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.wicketstuff.ItemsNavigationStrategy;
import org.wicketstuff.QuickView;
import org.wicketstuff.navigator.AjaxItemsNavigator;

/**
 * @author Vineet Semwal
 *
 */
public class RowsNavigatorPage extends WebPage {

	private List<Integer> list = new ArrayList<Integer>();

	public RowsNavigatorPage() {
		for (int i = 0; i < 20; i++) {
			list.add(i);
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		IDataProvider<Integer> data = new ListDataProvider<Integer>(list);
		final int itemsPerRequest = 4;// rows created per request
		WebMarkupContainer numbers = new WebMarkupContainer("numbers"); // don't forget adding quickview to parent with
																		// any ajax navigator
		Component start, end;
		numbers.add(start = new EmptyPanel("start").setOutputMarkupPlaceholderTag(true));
		numbers.add(end = new EmptyPanel("end").setOutputMarkupPlaceholderTag(true));
		// read more about @see ItemsNavigationStrategy ,it is one of provided strategy
		// that can be used in
		// cases where new items has to be added without re-rendering QuickView
		QuickView<Integer> quickView = new QuickView<Integer>("number", data, new ItemsNavigationStrategy(),
				itemsPerRequest, start, end) {
			@Override
			protected void populate(Item<Integer> item) {
				item.add(new Label("display", item.getModel()));
			}
		};
		numbers.add(quickView);
		numbers.setOutputMarkupId(true); // don't forget required when using ajaxrownavigator
		add(numbers);
		AjaxItemsNavigator navigator = new AjaxItemsNavigator("navigator", quickView);
		add(navigator);
	}
}
