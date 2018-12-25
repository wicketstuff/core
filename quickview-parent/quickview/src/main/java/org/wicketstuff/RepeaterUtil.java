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

import org.apache.wicket.*;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.util.lang.Args;

/**
 * @author Vineet Semwal
 */
public class RepeaterUtil implements IRepeaterUtil {
    private static final MetaDataKey<RepeaterUtil> REPEATER_UTIL_KEY = new MetaDataKey<RepeaterUtil>() {
    };

    public static RepeaterUtil get() {
        Application app = Application.get();
        RepeaterUtil util = app.getMetaData(REPEATER_UTIL_KEY);
        if (util != null) {
            return util;
        }
        util = new RepeaterUtil(app); //this registers too
        return util;
    }

    private Application application;

    public RepeaterUtil(Application application) {
        this.application = application;
        application.setMetaData(REPEATER_UTIL_KEY, this);
    }

    /**
     * {@inheritDoc}
     */
    public String prepend(String tag, String markupId, String parentMarkupId, String firstChildId, String markId) {
        String script = String.format("QuickView.prepend('%s','%s','%s','%s','%s');", tag,
                markupId, parentMarkupId, firstChildId, markId);
        return script;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String prepend(MarkupContainer component, MarkupContainer parent, Component start, Component end) {
        String startId = "";
        if (end != null) {
            startId = end.getMarkupId();
        }
        String endId = "";
        if (start != null) {
            endId = start.getMarkupId();
        }

        String script = prepend(getComponentTag(component).getName(),
                component.getMarkupId(), parent.getMarkupId(), endId, startId);
        return script;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String append(String tag, String markupId, String parentMarkupId, String startId, String endId) {
        String script = String.format("QuickView.append('%s','%s','%s','%s','%s');", tag, markupId,
                parentMarkupId, startId, endId);
        return script;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String append(MarkupContainer c, MarkupContainer parent, Component start, Component end) {
        String startId = "";
        if (start != null) {
            startId = start.getMarkupId();
        }
        String endIdId = "";
        if (end != null) {
            endIdId = end.getMarkupId();
        }

        return append(getComponentTag(c).getName(), c.getMarkupId(), parent.getMarkupId()
                , startId, endIdId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComponentTag getComponentTag(Component c) {
        IMarkupFragment markup = c.getMarkup();
        MarkupStream stream = new MarkupStream(markup);
        return stream.getTag();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String removeItem(String markupId, String parentId) {
        String script = String.format("QuickView.removeItem('%s','%s');", markupId, parentId);
        return script;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String removeItem(Component component, Component parent) {
        Args.notNull(component, "component");
        Args.notNull(parent, "parent");
        return removeItem(component.getMarkupId(), parent.getMarkupId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int safeLongToInt(long value) {
        if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(value + " cannot be cast to int ");
        }
        return (int) value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void parentNotSuitable(IQuickView quickView) {
        Args.notNull(quickView, "quickview");
        if (!quickView.getReuseStrategy().isPartialUpdatesSupported()) {
            return;
        }
        MarkupContainer parent = quickView.getParent();
        if (parent == null) {
            throw new QuickViewNotAddedToParentException("add quickview to a markupcontainer");
        }
        if (parent instanceof Page) {
            throw new QuickViewNotAddedToParentException("add quickview to a markupcontainer");
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public final void outPutMarkupIdNotTrue(IQuickView quickView) {
        Args.notNull(quickView, "quickview");
        MarkupContainer container = quickView.getParent();
        if (container.getOutputMarkupId() == false && container.getOutputMarkupPlaceholderTag() == false) {
            throw new OutputMarkupIdNotTrueException("parent doesn't have setOutputMarkupId to true");
        }
    }


    @Override
    public final void reuseStategyNotSupportedForItemsNavigation(IQuickView quickView) {
        Args.notNull(quickView, "quickview");
        if (!quickView.getReuseStrategy().isPartialUpdatesSupported()) {
            throw new ReuseStrategyNotSupportedException(" stategy is not supported for itemsnavigator ");
        }
    }

    @Override
    public String scrollToBottom(String markupId) {
        return String.format("QuickView.scrollToBottom('%s');", markupId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String scrollToBottom(IQuickView quickView) {
        return scrollToBottom(quickView.getParent().getMarkupId());
    }

    @Override
    public String scrollToTop(String markupId) {
        return String.format("QuickView.scrollToTop('%s');", markupId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String scrollToTop(IQuickView quickView) {
        return scrollToTop(quickView.getParent().getMarkupId());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String scrollTo(IQuickView quickView, int height) {
        return scrollTo(quickView.getParent().getMarkupId(), height);
    }

    @Override
    public String scrollTo(String markupId, int height) {
        return String.format("QuickView.scrollTo('%s',%d);", markupId, height);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String isComponentScrollBarAtBottom(MarkupContainer component) {
        return String.format("QuickView.isComponentScrollBarAtBottom('%s');", component.getMarkupId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String isPageScrollBarAtBottom() {
        return "QuickView.isPageScrollBarAtBottom();";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String showPageScrollBar() {
        return "QuickView.showPageScrollBar();";
    }
}
