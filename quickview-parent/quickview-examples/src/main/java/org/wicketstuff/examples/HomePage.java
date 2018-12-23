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

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

    public HomePage(final PageParameters parameters) {
	super(parameters);
       BookmarkablePageLink rowsNavLink=new BookmarkablePageLink("rowsNavLink",RowsNavigatorPage.class);

        BookmarkablePageLink ajaxPagingLink=new BookmarkablePageLink("ajaxPagingLink",AjaxPagingNavigatorPage.class);
        add(rowsNavLink,ajaxPagingLink);

        BookmarkablePageLink ajaxLink=new BookmarkablePageLink("ajaxLink",AjaxLinkPage.class);
        add(ajaxLink);


        BookmarkablePageLink removeLink=new BookmarkablePageLink("removeLink",RemoveItemsPage.class);
        add(removeLink);

        BookmarkablePageLink scrollLink=new BookmarkablePageLink("scrollLink",ParentScrollBar.class);
        add(scrollLink);

        BookmarkablePageLink pageScrollLink=new BookmarkablePageLink("pageScrollLink",PageScrollBar.class);
        add(pageScrollLink);

        BookmarkablePageLink gvLink=new BookmarkablePageLink("gvLink",QuickGridViewWithItemsNavigatorPage.class);
        add(gvLink);

        BookmarkablePageLink gvScrollLink=new BookmarkablePageLink("gvScrollLink",QuickGridViewWithPageScrollBehavior.class);
        add(gvScrollLink);

        BookmarkablePageLink gvPagingLink=new BookmarkablePageLink("gvPagingLink",QuickGridViewWithAjaxPagingNavigator.class);
        add(gvPagingLink);

        BookmarkablePageLink gvAjaxLink=new BookmarkablePageLink("gvAjaxLink",QuickGridViewWithAjaxLink.class);
        add(gvAjaxLink);

        BookmarkablePageLink addItemSocketExampleLink=new BookmarkablePageLink("addItemSocket", WebSocketAddQuickViewItemPage.class);
        add(addItemSocketExampleLink);

        BookmarkablePageLink removeItemSocketLink=new BookmarkablePageLink("removeItemSocket",WebSocketRemoveQuickViewItemPage.class);
        add(removeItemSocketLink);

        BookmarkablePageLink addGridItemSocketLink=new BookmarkablePageLink("addGridItemSocket",WebSocketAddQuickGridViewItemPage.class);
        add(addGridItemSocketLink);
    }
}
