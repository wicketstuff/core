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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.repeater.IItemFactory;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.lang.Args;

/**
 * base class for {@link QuickView}
 *
 * @author Vineet Semwal
 */
public abstract class QuickViewBase<T> extends RepeatingView implements IQuickView {
    private IQuickReuseStrategy reuseStrategy7;

    public void setReuseStrategy(final IQuickReuseStrategy reuseStrategy) {
        Args.notNull(reuseStrategy, "reuseStrategy");
        this.reuseStrategy7 = reuseStrategy;
    }


    private IAddAtStartStore _addAtStartStore;

    protected IAddAtStartStore getAddAtStartStore() {
        return _addAtStartStore;
    }

    protected void initializeAddAtStartStoreIfRequired() {
        if (_addAtStartStore == null) {
            _addAtStartStore = newAddAtStartStore();
        }
    }

    protected IAddAtStartStore newAddAtStartStore() {
        return new DefaultAddAtStartStore();
    }


    @Override
	public IQuickReuseStrategy getReuseStrategy() {
        return reuseStrategy7;
    }

    //items created per request ,if used with PagingNavigator/AjaxPagingNavigator then it's the items per page
    private int itemsPerRequest7 = Integer.MAX_VALUE;

    public int getItemsPerRequest() {
        return itemsPerRequest7;
    }

    private long childId = 0;

    @Override
    public String newChildId() {
        childId++;
        return String.valueOf(childId);
    }

    public void setItemsPerRequest(int items) {
        if (items < 1) {
            throw new IllegalArgumentException("itemsPerRequest cannot be less than 1");
        }

        if (this.itemsPerRequest7 != items) {
            if (isVersioned()) {
                addStateChange();
            }
            this.itemsPerRequest7 = items;

            // because items per page can effect the total number of pages we always
            // reset the current page back to zero
            _setCurrentPage(0);
        }

    }

    /**
     * cached only for a request
     */
    private transient Long itemsCount = null;

    private IDataProvider<T> dataProvider;

    public IDataProvider<T> getDataProvider() {
        return dataProvider;
    }


    protected IRepeaterUtil getRepeaterUtil() {
        return RepeaterUtil.get();
    }

    private long currentPage;


    private Component start, end;

    /**
     * represents start of view ,can be any component it's position in the markup should be just before view
     * this is done so that the new children doesn't get mixedup with the other markup or another components
     * specified in immediate parent
     *
     * @return component using as start boundary
     */
    public final Component getStart() {
        return start;
    }

    /**
     * represents end of view ,can be any component it's position in the markup should be just after the view
     * this is done so that the new children doesn't get mixedup with the other markup or another components
     * specified in immediate parent
     *
     * @return component used as end boundary
     */
    public final Component getEnd() {
        return end;
    }

    /**
     * @param id            component id
     * @param dataProvider  dataprovider of objects
     * @param reuseStrategy children are created again on render
     */
    public QuickViewBase(String id, IDataProvider<T> dataProvider, IQuickReuseStrategy reuseStrategy) {
        super(id);
        Args.notNull(dataProvider, "dataProvider");
        Args.notNull(reuseStrategy, "reuseStrategy");
        this.dataProvider = dataProvider;
        setReuseStrategy(reuseStrategy);
    }


    /**
     * @param id            component id
     * @param dataProvider  dataprovider of objects
     * @param reuseStrategy children are created again on render
     * @param start         start of view
     * @param end           end of view
     */
    public QuickViewBase(String id, IDataProvider<T> dataProvider, IQuickReuseStrategy reuseStrategy, Component start, Component end) {
        super(id);
        Args.notNull(dataProvider, "dataProvider");
        Args.notNull(reuseStrategy, "reuseStrategy");
        this.dataProvider = dataProvider;
        this.start = start;
        if (start != null) {
            start.setOutputMarkupPlaceholderTag(true);
        }
        this.end = end;
        if (end != null) {
            end.setOutputMarkupPlaceholderTag(true);
        }
        setReuseStrategy(reuseStrategy);

    }


    protected Item<T> newItem(String id, int index, IModel<T> model) {
        Item<T> item = new Item<T>(id, getRepeaterUtil().safeLongToInt(index), model);
        item.setOutputMarkupId(true);
        return item;
    }

    /**
     * use in stateless environment as there is no state ,it's user's responsiblity to give unique id and index
     *
     * @param id
     * @param index
     * @param object
     * @return item
     */
    public Item<T> buildItem(String id, int index, T object) {
        return buildItem(id, index, getDataProvider().model(object));
    }

    protected Item<T> buildItem(String id, int index, IModel<T> model) {
        Item<T> item = newItem(id, index, model);
        populate(item);
        return item;
    }


    /**
     * creates new item,for stateless environment,you can use {@link QuickViewBase#buildItem} or {@link QuickViewBase#buildItem}
     *
     * @param object model object
     * @return item
     */
    public Item buildItem(int index, T object) {
        return buildItem(newChildId(), index, object);
    }

    protected Item buildItem(int index, IModel<T> model) {
        return buildItem(newChildId(), index, model);
    }


    public boolean isAjax() {
        return getWebRequest().isAjax();
    }


    /**
     * it's a simple add,new item is not drawn just added,no js fired
     *
     * @param components component to be added
     * @return this
     */
    public MarkupContainer simpleAdd(Component... components) {
        super.add(components);
        return this;
    }


    /**
     * it's a simple remove,the item is just removed from quickview ,no js fired
     *
     * @param c
     * @return this
     */
    public MarkupContainer simpleRemove(Component c) {
        super.remove(c);
        return this;
    }

    public MarkupContainer simpleRemoveAll() {
        return super.removeAll();
    }

    /**
     * this iterator doesn't iterate through the elements in the order they are rendered in view,
     * use {@link QuickViewBase#getItems()}
     *
     * @return iterator
     */
    @Override
    public Iterator<Component> iterator() {
        return super.iterator();
    }

    /**
     * iterator which iterates through the items in the order they are rendered in view
     * ie. first items added using addAtStart(*) are fetched and then items
     * added using add(*)
     *
     * @return items iterator
     */
    public Iterator<Component> getItems() {
        return new ItemsIterator();
    }


    /**
     * iterator that iterates through the elements in the order they are rendered in view
     * element added using addAtStart(*) are fetched before continuing on to other elements
     */
    private class ItemsIterator implements Iterator<Component> {
        private Iterator<Component> iterator;
        private Component nextComponent;

        private Iterator<String> addAtStartIterator;

        private Component currentComponent;


        public ItemsIterator() {
            if (getAddAtStartStore() != null) {
                addAtStartIterator = getAddAtStartStore().iterator();
            }
            iterator = iterator();
            nextComponent = findNext();
        }

        public Iterator<String> getAddAtStartIterator() {
            return addAtStartIterator;
        }

        public Iterator<Component> getIterator() {
            return iterator;
        }

        @Override
        public boolean hasNext() {
            return nextComponent != null;
        }

        @Override
        public Component next() {
            currentComponent = nextComponent;
            nextComponent = null;
            nextComponent = findNext();
            return currentComponent;
        }


        public Component findNext() {
            //
            //addatstartstore items should get fetched before  items add at end
            //
            IAddAtStartStore addAtStartStore = getAddAtStartStore();
            if (addAtStartStore != null && getAddAtStartIterator().hasNext()) {
                String id = getAddAtStartIterator().next();
                Component component = get(id);
                return component;
            }

            //
            //fetch items added at end now ,skip the item if it is in addatstartstore too
            //
            while (getIterator().hasNext()) {
                Component found = getIterator().next();
                if (getAddAtStartStore() == null) {
                    return found;
                }

                if (!getAddAtStartStore().contains(found)) {
                    return found;
                }

                continue;
            }

            return null;

        }

        @Override
        public void remove() {
            if (currentComponent == null) {
                return;
            }
            QuickViewBase.this.remove(currentComponent);

        }

    }

    @Override
    protected void onPopulate() {
        super.onPopulate();
        clearCachedItemCount();
        long pageToBeCreated = getReuseStrategy().getPageCreatedOnRender();
        //
        // if page to be created is different then the last current page rendered
        //
        if (pageToBeCreated >= 0) {
            _setCurrentPage(pageToBeCreated);
        }
        long page = _getCurrentPage();
        Iterator<Item<T>> existing = (Iterator) iterator();

        //
        // if add items supported and page to be created is not defined
        // then all models for items upto the page last set should be fetched
        //this is useful for ReuseAllSStrategy
        //
        Iterator<IModel<T>> newModels;
        if (getReuseStrategy().isPartialUpdatesSupported() && pageToBeCreated < 0) {
            long modelsCount = (page + 1) * getItemsPerRequest();
            newModels = newModels(0, modelsCount);
        } else {
            //
            //create models only for the desired page
            //
            long offset = page * getItemsPerRequest();
            newModels = newModels(offset, getItemsPerRequest());
        }
        Iterator<Item<T>> newIterator = getReuseStrategy().getItems(factory(), newModels, existing);
        simpleRemoveAll();

        createChildren(newIterator);
    }


    public IItemFactory<T> factory() {
        return new IItemFactory<T>() {
            @Override
            public Item<T> newItem(int index, IModel<T> model) {
                return buildItem(index, model);
            }
        };
    }


    @Override
    public List<Item<T>> addItemsForPage(final long page) {
        long offset = page * getItemsPerRequest();
        Iterator<IModel<T>> newModels = newModels(offset, getItemsPerRequest());
        Iterator<Item<T>> newIterator = getReuseStrategy().addItems(getRepeaterUtil().safeLongToInt(offset), factory(), newModels);
        List<Item<T>> components = new ArrayList<Item<T>>();
        while (newIterator.hasNext()) {
            Item<T> temp = newIterator.next();
            components.add(temp);
            add(temp);
        }
        return components;
    }


    /**
     * Helper class that converts input from IDataProvider to an iterator over view items.
     *
     * @param <T> Model object type
     * @author Igor Vaynberg (ivaynberg)
     */
    protected static final class ModelIterator<T> implements Iterator<IModel<T>> {
        private final Iterator<? extends T> items;
        private final IDataProvider<T> dataProvider;
        private final long max;
        private int index;

        /**
         * Constructor
         *
         * @param dataProvider data provider
         * @param offset       index of first item
         * @param count        max number of items to return
         */
        public ModelIterator(IDataProvider<T> dataProvider, long offset, long count) {
            this.dataProvider = dataProvider;
            max = count;

            items = count > 0 ? dataProvider.iterator(offset, count) : null;
        }

        /**
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /**
         * @see java.util.Iterator#hasNext()
         */
        @Override
        public boolean hasNext() {
            return items != null && items.hasNext() && (index < max);
        }

        /**
         * @see java.util.Iterator#next()
         */
        @Override
        public IModel<T> next() {
            index++;
            return dataProvider.model(items.next());
        }
    }

    protected Iterator<IModel<T>> newModels(long offset, long count) {
        return new ModelIterator<T>(dataProvider, offset, count);
    }


    protected void createChildren(Iterator<Item<T>> iterator) {
        Args.notNull(iterator, "iterator");
        while (iterator.hasNext()) {
            Item<T> item = iterator.next();
            simpleAdd(item);
        }
    }

    protected Iterator<Item<T>> buildItems(final int index, Iterator<? extends T> iterator) {
        return buildItemsList(index, iterator).iterator();
    }


    protected List<Item<T>> buildItemsList(final int index, Iterator<? extends T> iterator) {
        List<Item<T>> items = new ArrayList<Item<T>>();
        for (int i = index; iterator.hasNext(); i++) {
            T object = iterator.next();
            Item<T> item = buildItem(i, object);
            items.add(item);
        }
        return items;
    }


    /*
     * build items from index=size
     */
    protected Iterator<Item<T>> buildItems(Iterator<? extends T> iterator) {
        int index = size();
        return buildItems(index, iterator);
    }


    /*
     * build items from index=size
     */

    protected List<Item<T>> buildItemsList(Iterator<? extends T> iterator) {
        int index = size();
        return buildItemsList(index, iterator);
    }


    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptHeaderItem.forReference(RepeaterUtilReference.get()));
        /**
         * jquery reference added,it's not important as wicket itself uses jquery in ajax use case but still added to be safe
         */
        response.render(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
    }

    /**
     * same as dataprovider size but cached for request to improve performance  in case of multiple call to avoid
     * unnecessary expensive call of {@link org.apache.wicket.markup.repeater.data.IDataProvider#size()}
     *
     * @return dataprovider's size
     */

    public final long getItemsCount() {
        if (itemsCount != null) {
            return itemsCount.longValue();
        }
        itemsCount = getDataProvider().size();
        return itemsCount;
    }

    private void clearCachedItemCount() {
        itemsCount = null;
    }

    /**
     * same as {@link QuickViewBase#getItemsCount()} but takes into account hierarchy so if the view is not visible in hierarchy
     * the returned value is zero else return the getItemsCount() value
     *
     * @return items count visible
     */
    public final long getRowsCount() {
        if (!isVisibleInHierarchy()) {
            return 0;
        }
        return getItemsCount();
    }

    /**
     * calculates the number of pages
     *
     * @return number of pages
     */

    @Override
    public final long getPageCount() {
        return _getPageCount();
    }

    /**
     * don't override ,it's used for testing purpose
     *
     * @return number of pages
     */
    protected long _getPageCount() {
        long total = getRowsCount();

        long count = total / getItemsPerRequest();
        if ((getItemsPerRequest() * count) < total) {
            count++;
        }
        return count;
    }

    /**
     * @see org.apache.wicket.markup.html.navigation.paging.IPageable#getCurrentPage()
     * <p/>
     */
    @Override
    public final long getCurrentPage() {
        return _getCurrentPage();
    }

    /**
     * don't override,it's for internal use
     */
    protected long _getCurrentPage() {
        long page = currentPage;

        /*
         * trim current page if its out of bounds this can happen if items are added/deleted between
         * requests
         */

        final long count = _getPageCount();
        if (page > 0 && page >= count) {
            page = Math.max(count - 1, 0);
            currentPage = page;
            return page;
        }
        return page;
    }

    /**
     * @see org.apache.wicket.markup.html.navigation.paging.IPageable#setCurrentPage(long)
     */

    @Override
	public final void setCurrentPage(long page) {
        _setCurrentPage(page);
    }

    /**
     * don't override,it's for internal use
     */
    protected void _setCurrentPage(long page) {
        if (currentPage != page) {
            if (isVersioned()) {
                addStateChange();

            }
        }
        currentPage = page;
    }


    public AjaxRequestTarget getAjaxRequestTarget() {
        Optional<AjaxRequestTarget> target = RequestCycle.get().find(AjaxRequestTarget.class);
        if (target.isPresent()) {
            return target.get();
        }
        return null;
    }

    public IPartialPageRequestHandler findPartialPageRequestHandler(final Class<? extends IPartialPageRequestHandler> requestHandlerClass) {
        Optional<? extends IPartialPageRequestHandler> requestHandlerOptional = RequestCycle.get().find(requestHandlerClass);
        if (requestHandlerOptional.isPresent()) {
            return requestHandlerOptional.get();
        }
        return null;
    }


    protected abstract void populate(Item<T> item);


    /**
     * don't override,it's for internal use
     */
    protected MarkupContainer _getParent() {
        return getParent();
    }


    @Override
    public MarkupContainer add(final Component... components) {
        simpleAdd(components);
        Synchronizer synchronizer = getSynchronizer();
        if (synchronizer == null) {
            return this;
        }

        _contributeAddAtEndScripts(components);

        synchronizer.add(components);
        //
        //submit manually since request handler not for AjaxRequestTarget
        //
        if (!synchronizer.isRequestHandlerAjaxRequestTarget()) {
            synchronizer.submit();
        }
        return this;
    }


    protected void _contributeAddAtEndScripts(final Component... components) {
        Synchronizer synchronizer = getSynchronizer();
        for (int i = 0; i < components.length; i++) {
            MarkupContainer parent = _getParent();
            String script = getRepeaterUtil().append((Item) components[i], parent, getStart(), getEnd());
            synchronizer.prependScript(script);
        }

    }

    /**
     * this does 2 steps
     * <p/>
     * 1)creates children ,children will get the model object after iterating over objects passed as argument
     * 2)adds children to View using {@link QuickViewBase#add(org.apache.wicket.Component...)}
     *
     * @param objects iterator of model objects for children
     * @return this
     */


    public MarkupContainer addNewItems(T... objects) {
        List<T> list = new ArrayList<T>();
        for (T obj : objects) {
            list.add(obj);
        }
        Iterator<Item<T>> items = buildItems(list.iterator());
        while (items.hasNext()) {
            add(items.next());
        }
        return this;
    }


    /**
     * this does 2 steps
     * <p/>
     * 1)creates children ,children will get the model object after iterating over objects passed as argument
     * 2)adds children to View using {@link QuickViewBase#addAtStart(org.apache.wicket.Component...)}
     * <p>
     * the respective items for objects will be displayed at start of the view in the order of passed objects
     *
     * @param objects iterator of model objects for children
     * @return this
     */

    public MarkupContainer addNewItemsAtStart(T... objects) {
        List<T> list = new ArrayList<T>();
        for (T object : objects) {
            list.add(object);
        }
        List<Item<T>> items = buildItemsList(list.iterator());
        addAtStart(items.toArray(new Item[0]));
        return this;
    }


    /**
     * {@inheritDoc}
     */
    @Override
	public List<Item<T>> addItemsForNextPage() {
        List<Item<T>> list = new ArrayList<Item<T>>();
        long current = getCurrentPage();

        // page for which new items have to created

        long next = current + 1;
        if (next < _getPageCount()) {
            list = addItemsForPage(next);
            _setCurrentPage(next);
        }
        return list;
    }

    @Override
    public MarkupContainer remove(final Component component) {
        Args.notNull(component, "component can't be null");
        Synchronizer synchronizer = getSynchronizer();
        if (synchronizer != null) {
            String removeScript = getRepeaterUtil().removeItem(component, _getParent());
            synchronizer.prependScript(removeScript);
            //
            //if request handler is not for AjaxRequestTarget then manual submit
            //
            if (!synchronizer.isRequestHandlerAjaxRequestTarget()) {
                synchronizer.submit();
            }
        }
        IAddAtStartStore addAtStartStore = getAddAtStartStore();
        if (addAtStartStore != null) {
            addAtStartStore.remove(component);
        }
        return simpleRemove(component);

    }


    @Override
    public MarkupContainer remove(final String id) {
        final Component component = get(id);
        return remove(component);
    }


    /**
     * adds items at start of view
     * <p>
     * also see  {@link QuickViewBase#getItems()}
     *
     * @param components
     * @return this
     */
    public MarkupContainer addAtStart(final Component... components) {
        simpleAdd(components);
        initializeAddAtStartStoreIfRequired();
        getAddAtStartStore().add(components);
        if (!isAjax()) {
            return this;
        }

        Synchronizer synchronizer = getSynchronizer();
        if (synchronizer == null) {
            return this;
        }
        _contributeAddAtStartScripts(components);
        synchronizer.add(components);
        //
        //submit manually if request handler is not AjaxRequestTarget
        //
        if (!synchronizer.isRequestHandlerAjaxRequestTarget()) {
            synchronizer.submit();
        }
        return this;
    }

    protected void _contributeAddAtStartScripts(final Component... components) {
        Synchronizer synchronizer = getSynchronizer();
        for (int i = components.length - 1; i >= 0; i--) {
            MarkupContainer parent = _getParent();
            String updateBeforeScript = getRepeaterUtil().prepend((Item) components[i], parent,
                    getStart(), getEnd());
            synchronizer.prependScript(updateBeforeScript);
        }

    }


    /**
     * when called on ajax event ,this method moves navigation-bar to bottom,
     * this works when parent has scroll specified in css by defining overflow-y property
     */
    public void scrollToBottom() {
        if (isAjax()) {
            Synchronizer synchronizer = getSynchronizer();
            if (synchronizer == null) {
                return;
            }
            synchronizer.appendScript(getRepeaterUtil().scrollToBottom(this));
        }
    }

    /**
     * when called on ajax event, this method moves navigation-bar to top ,
     * this works when parent has scroll specified in css by defining overflow-y property
     */
    public void scrollToTop() {
        if (isAjax()) {
            Synchronizer synchronizer = getSynchronizer();
            if (synchronizer == null) {
                return;
            }
            synchronizer.appendScript(getRepeaterUtil().scrollToTop(this));
        }
    }

    /**
     * when called on ajax event, this method moves navigation-bar to height passed in method ,
     * this works when parent has scroll specified in css by defining overflow-y property
     */
    public void scrollTo(int height) {
        Synchronizer synchronizer = getSynchronizer();
        if (synchronizer != null) {
            synchronizer.appendScript(getRepeaterUtil().scrollTo(this, height));
        }
    }

    /**
     * register partial page request handler class,for eg. for websocket
     * register(IWebSocketRequestHandler.class) ,
     * <p>
     * NO need to register {@link AjaxRequestTarget}
     * quickview is already aware of that
     *
     * @param requestHandler
     */
    public void register(final Class<? extends IPartialPageRequestHandler> requestHandler) {
        //
        //request handler is for AjaxRequestTarget then don't register
        //
        if (AjaxRequestTarget.class.isAssignableFrom(requestHandler)) {
            return;
        }
        getPartialRequestHandlers().add(requestHandler);
    }

    /**
     * Synchronizer basically adds components(repeater's items) and scripts to the associated
     * {@link IPartialPageRequestHandler}  after checking parent is not added to AjaxRequestTarget.
     * If parent is added scripts and items are not added to the requesthandler
     */
    public Synchronizer getSynchronizer() {
        if (!isAjax()) {
            return null;
        }
        Synchronizer synchronizer = _getRequestMetaData(SYNCHRONIZER_KEY);
        if (synchronizer != null) {
            return synchronizer;
        }

        AjaxRequestTarget target = getAjaxRequestTarget();
        if (target != null) {
            DefaultSynchronizer defaultSynchronizer = newDefaultSynchronizer();
            synchronizer = defaultSynchronizer;
            target.addListener(defaultSynchronizer);
            _setRequestMetaData(SYNCHRONIZER_KEY, synchronizer);
            return synchronizer;
        }
        synchronizer = nonARTSynchronizer();
        _setRequestMetaData(SYNCHRONIZER_KEY, synchronizer);
        return synchronizer;
    }

    protected <T> void _setRequestMetaData(final MetaDataKey<T> key, T value) {
        getRequestCycle().setMetaData(key, value);
    }

    protected <T> T _getRequestMetaData(final MetaDataKey<T> key) {
        return getRequestCycle().getMetaData(key);
    }

    protected DefaultSynchronizer newDefaultSynchronizer() {
        return new DefaultSynchronizer(_getParent(), getAjaxRequestTarget());
    }

    public Synchronizer nonARTSynchronizer() {
        for (Class<? extends IPartialPageRequestHandler> requestHandlerClass : getPartialRequestHandlers()) {
            Optional<? extends IPartialPageRequestHandler> requestHandlerOptional = getRequestCycle().find(requestHandlerClass);
            if (requestHandlerOptional.isPresent()) {
                Synchronizer wrap = new Synchronizer(_getParent(), requestHandlerOptional.get());
                return wrap;
            }
        }
        return null;
    }

    private Set<Class<? extends IPartialPageRequestHandler>> partialRequestHandlers = new HashSet<>();

    public Set<Class<? extends IPartialPageRequestHandler>> getPartialRequestHandlers() {
        return partialRequestHandlers;
    }


    /**
     * key for {@link Synchronizer} in request metadata
     */
    protected final MetaDataKey<Synchronizer> SYNCHRONIZER_KEY = new MetaDataKey<Synchronizer>() {
        @Override
        public int hashCode() {
            return QuickViewBase.this.getId().hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return obj == this;
        }
    };

    @Override
    protected void onDetach() {
        dataProvider.detach();
        super.onDetach();
    }


}
