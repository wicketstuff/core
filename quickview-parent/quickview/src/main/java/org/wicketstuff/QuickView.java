/**
 * Copyright 2012 Vineet Semwal
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff;


import org.apache.wicket.Component;
import org.apache.wicket.markup.repeater.data.IDataProvider;

/**
 * adds,deletes elements without the need to re-render the View
 * <p>
 * QuickView's default behavior is of paging ie. items are added to view on re-render . it uses
 * {@link DefaultQuickReuseStrategy}  by default so It works fine with
 * {@link org.apache.wicket.markup.html.navigation.paging.PagingNavigator}
 * or {@link org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator}  by default
 * <p>
 * however on setting strategy like {@link ItemsNavigationStrategy} or {@link ReuseAllStrategy} QuickView get capability
 * to add  new items without the need to re-render the view
 * <p>
 * the preferred way to use quickview is with boundaries ie.  two components, one placed before and one placed
 * after quickview in markup ,together they determine start and end of quickview
 */
public abstract class QuickView<T> extends QuickViewBase<T> {

    /**
     * @param id              component id
     * @param dataProvider    data provider
     * @param itemsPerRequest items to be constructed per Page or request
     */
    public QuickView(String id, IDataProvider<T> dataProvider, IQuickReuseStrategy reuseStrategy, int itemsPerRequest) {
        super(id, dataProvider, reuseStrategy);
        setItemsPerRequest(itemsPerRequest);

    }

    /**
     * @param id            component id
     * @param dataProvider  data provider
     * @param reuseStrategy strategy that tells how to reuse elements
     */
    public QuickView(String id, IDataProvider<T> dataProvider, IQuickReuseStrategy reuseStrategy) {
        super(id, dataProvider, reuseStrategy);
    }

    /**
     * @param id           component id
     * @param dataProvider data provider
     */
    public QuickView(String id, IDataProvider<T> dataProvider) {
        super(id, dataProvider, new DefaultQuickReuseStrategy());
    }

    /**
     * @param id              component id
     * @param dataProvider    data provider
     * @param itemsPerRequest items created per request ,if used with PagingNavigator/AjaxPagingNavigator then it's the items per page
     */
    public QuickView(String id, IDataProvider<T> dataProvider, int itemsPerRequest) {
        super(id, dataProvider, new DefaultQuickReuseStrategy());
        setItemsPerRequest(itemsPerRequest);
    }

    /**
     * @param id
     * @param dataProvider
     * @param itemsPerRequest items created per request ,if used with PagingNavigator/AjaxPagingNavigator then it's the items per page
     */
    public QuickView(String id, IDataProvider<T> dataProvider, IQuickReuseStrategy reuseStrategy, int itemsPerRequest, Component start, Component end) {
        super(id, dataProvider, reuseStrategy, start, end);
        setItemsPerRequest(itemsPerRequest);

    }

    /**
     * @param id
     * @param dataProvider
     * @param start        start of the boundary where elements will be placed
     * @param  end of the boundary,beyond which elements will not be placed
     */
    public QuickView(String id, IDataProvider<T> dataProvider, IQuickReuseStrategy reuseStrategy, Component start, Component end) {
        super(id, dataProvider, reuseStrategy, start, end);
    }

    /**
     * @param id
     * @param dataProvider
     * @param start        start of the boundary where elements will be placed
     * @param  end of the boundary,beyond which elements will not be placed
     */
    public QuickView(String id, IDataProvider<T> dataProvider, Component start, Component end) {
        super(id, dataProvider, new DefaultQuickReuseStrategy(), start, end);
    }

    /**
     * @param id              component id
     * @param dataProvider    data provider
     * @param itemsPerRequest items created per request ,if used with PagingNavigator/AjaxPagingNavigator then it's the items per page
     * @param start           start of the boundary where elements will be placed
     * @param  end of the boundary,beyond which elements will not be placed
     */
    public QuickView(String id, IDataProvider<T> dataProvider, int itemsPerRequest, Component start, Component end) {
        super(id, dataProvider, new DefaultQuickReuseStrategy(), start, end);
        setItemsPerRequest(itemsPerRequest);
    }
}
