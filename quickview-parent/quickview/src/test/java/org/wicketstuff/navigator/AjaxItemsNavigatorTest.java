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
package org.wicketstuff.navigator;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.mockito.InOrder;
import org.wicketstuff.IQuickView;
import org.wicketstuff.IRepeaterUtil;
import org.wicketstuff.ItemsNavigationStrategy;
import org.wicketstuff.QuickView;
import org.wicketstuff.QuickViewBase;
import org.wicketstuff.WicketTest;

/**
 *
 * @author Vineet Semwal
 *
 */
public class AjaxItemsNavigatorTest extends TesterBase {
	@WicketTest
	public void constructor_1() {
		IQuickView repeater = mock(IQuickView.class);
		final AjaxRequestTarget target = mock(AjaxRequestTarget.class);
		final List<Item> items = mock(List.class);
		AjaxItemsNavigator navigator = new AjaxItemsNavigator("nav", repeater);
		assertEquals(navigator.getRepeater(), repeater);
	}

	@WicketTest
	public void constructor_2() {
		assertThrows(RuntimeException.class, () -> {
			IQuickView repeater = mock(IQuickView.class);
			final AjaxRequestTarget target = mock(AjaxRequestTarget.class);
			final List<Item> items = mock(List.class);

			boolean isException = false;

			AjaxItemsNavigator navigator = new AjaxItemsNavigator("nav", null);
		});
	}

	/**
	 * when current page< pages count
	 */

	@WicketTest
	public void OnStatefulEvent_1() {
		IQuickView quickView = mock(IQuickView.class);
		final AjaxRequestTarget target = mock(AjaxRequestTarget.class);
		final List<Item> items = new ArrayList<>();
		AjaxItemsNavigator navigator = new AjaxItemsNavigator("nav", quickView) {
			@Override
			public AjaxRequestTarget getAjaxRequestTarget() {
				return target;
			}
		};
		AjaxItemsNavigator spy = spy(navigator);
		List<Item> actual = spy.onStatefulEvent();
		verify(target, times(1)).add(spy.getMore());
		verify(quickView, times(1)).addItemsForNextPage();
		assertEquals(actual, items);
	}

	/**
	 * parent not null ,OutputMarkupPlaceholderTag set to true reuse stategy is
	 * correct
	 */
	@WicketTest
	public void onBeforeRender_1() {
		WebMarkupContainer parent = new WebMarkupContainer("parent");
		IDataProvider data = mock(IDataProvider.class);
		QuickViewBase repeater = new QuickView("id", data, 10) {
			@Override
			protected void populate(Item item) {
			}
		};
		repeater.setReuseStrategy(new ItemsNavigationStrategy());
		parent.add(repeater);
		parent.setOutputMarkupPlaceholderTag(true);

		AjaxItemsNavigator navigator = new AjaxItemsNavigator("id", repeater);
		navigator.onBeforeRender();
	}

	@WicketTest
	public void onBeforeRender_2() {
		WebMarkupContainer parent = new WebMarkupContainer("parent");
		IDataProvider data = mock(IDataProvider.class);
		QuickViewBase repeater = new QuickView("id", data, 10) {
			@Override
			protected void populate(Item item) {
			}
		};
		repeater.setReuseStrategy(new ItemsNavigationStrategy());
		parent.add(repeater);
		parent.setOutputMarkupId(true);

		AjaxItemsNavigator navigator = new AjaxItemsNavigator("id", repeater);
		assertFalse(navigator.isProperInitializationCheckDone());
		navigator.onBeforeRender();
		assertTrue(navigator.isProperInitializationCheckDone());
	}

	@WicketTest
	public void repeaterNotProperlyInitializedForItemsNavigation() {
		IQuickView quickView = mock(IQuickView.class);
		final IRepeaterUtil util = mock(IRepeaterUtil.class);
		AjaxItemsNavigator navigator = new AjaxItemsNavigator("id", quickView) {
			@Override
			public IRepeaterUtil getRepeaterUtil() {
				return util;
			}
		};

		AjaxItemsNavigator spy = spy(navigator);
		spy.repeaterNotProperlyInitializedForItemsNavigation(quickView);
		InOrder order = inOrder(spy, util);
		order.verify(util, times(1)).reuseStategyNotSupportedForItemsNavigation(quickView);
		order.verify(util, times(1)).parentNotSuitable(quickView);
		order.verify(util, times(1)).outPutMarkupIdNotTrue(quickView);
	}

	@WicketTest
	public void doProperInitializationCheck() {
		IQuickView quickView = mock(IQuickView.class);
		final IRepeaterUtil util = mock(IRepeaterUtil.class);
		AjaxItemsNavigator navigator = new AjaxItemsNavigator("id", quickView) {
			@Override
			public IRepeaterUtil getRepeaterUtil() {
				return util;
			}

			@Override
			protected void repeaterNotProperlyInitializedForItemsNavigation(IQuickView quickView) {
			}
		};

		AjaxItemsNavigator spy = spy(navigator);
		spy.doProperInitializationCheck();
		verify(spy, times(1)).repeaterNotProperlyInitializedForItemsNavigation(quickView);
		assertTrue(spy.isProperInitializationCheckDone());
	}

}
