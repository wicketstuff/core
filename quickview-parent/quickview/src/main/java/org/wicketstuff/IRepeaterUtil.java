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
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.ComponentTag;

/**
 * @author Vineet Semwal
 */
public interface IRepeaterUtil {
    /**
     * prepend js call ,creates a new dom tag element as the first element of parent
     *
     * @param tag      repeater tag
     * @param id       repeater markupid
     * @param parentId parent markupid
     * @return prepend call of js
     */
    String prepend(String tag, String id, String parentId, String startId, String endId);

    /**
     * prepend js call ,creates a new dom tag element as the first element of parent
     *
     * @param component repeater
     * @param parent    parent to which repeater is added
     * @return prepend call of js
     */
    String prepend(MarkupContainer component, MarkupContainer parent, Component start, Component end);

    /**
     * creates a new dom tag element as the last element of parent with parentId
     *
     * @param tag      repeater tag
     * @param id       repeater markupid
     * @param parentId parent markupid
     * @return append call of js
     */
    String append(String tag, String id, String parentId, String startId, String endId);

    /**
     * creates a new dom tag element as the last element of parent
     *
     * @param c      repeater
     * @param parent parent
     * @return append js call
     */
    String append(MarkupContainer c, MarkupContainer parent, Component start, Component end);


    /**
     * finds {@link ComponentTag} of the component passed
     *
     * @param c component whose componenttag has to be found
     * @return {@link ComponentTag}
     */
    ComponentTag getComponentTag(Component c);


    /**
     * removes js call for item whose markupid is passed
     *
     * @param id       markupid of the element which needs to be removed
     * @param parentId markupId of parent
     * @return remove js call
     */
    String removeItem(String id, String parentId);

    /**
     * removes js call for component which is provided
     *
     * @param component
     * @param parent
     * @return remove js call
     */
    String removeItem(Component component, Component parent);

    /**
     * safely converts long to int
     *
     * @param l
     * @return int value for long passed
     */
    int safeLongToInt(long l);


    /**
     * throws exception if no suitable unary parent is found,unary parent is one which only has one child
     *
     * @param repeater
     */
    void parentNotSuitable(IQuickView repeater);

    /**
     * throws exception if reuse strategy is not supported  for items navigation
     *
     * @param repeater
     */
    void reuseStategyNotSupportedForItemsNavigation(IQuickView repeater);

    /**
     * throws exception if outmarkupid of parent is not set true and outputMarkupPlaceholderTag is not set true
     *
     * @param repeater
     */
    void outPutMarkupIdNotTrue(IQuickView repeater);


    /**
     * js call to scroll to top
     *
     * @param quickView
     * @return js call  string
     */
    String scrollToTop(IQuickView quickView);

    String scrollToTop(String markupId);

    /**
     * js call to scroll to bottom
     *
     * @param quickView
     * @return js call  string
     */
    String scrollToBottom(IQuickView quickView);

    String scrollToBottom(String markupId);

    String scrollTo(String markupId, int height);

    /**
     * js call to scroll to height
     *
     * @param quickView
     * @param height
     * @return js call string
     */
    String scrollTo(IQuickView quickView, int height);

    /**
     * js calls which calls methods that when fired returns true if component's navigation-bar is at the bottom
     *
     * @param component
     * @return js call string
     */
    String isComponentScrollBarAtBottom(MarkupContainer component);

    /**
     * js calls which calls methods that when fired returns true if page's navigation-bar is at the bottom
     *
     * @return js call string
     */
    String isPageScrollBarAtBottom();

    String showPageScrollBar();

    /**
     * throw this exception if quickview's parent is not found
     *
     * @author Vineet Semwal
     */
    public static class QuickViewNotAddedToParentException extends RuntimeException {
        public QuickViewNotAddedToParentException(String message) {
            super(message);
        }
    }

    /**
     * throw this exception if outmarkupid is not set to true
     *
     * @author Vineet Semwal
     */
    public static class OutputMarkupIdNotTrueException extends RuntimeException {
        public OutputMarkupIdNotTrueException(String message) {
            super(message);
        }
    }

    /**
     * throw this exception if reuse constant set is not supported
     *
     * @author Vineet Semwal
     */
    public static class ReuseStrategyNotSupportedException extends RuntimeException {
        public ReuseStrategyNotSupportedException(String message) {
            super(message);
        }
    }


}
