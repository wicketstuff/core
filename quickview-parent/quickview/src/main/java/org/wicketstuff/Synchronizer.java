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
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.util.*;

/**
 * Synchronizer basically adds components(repeater's items) and scripts to the associated
 * {@link IPartialPageRequestHandler}  after checking parent is not added to AjaxRequestTarget.
 * If parent is added scripts and items are not added to the requesthandler
 *
 * @author Vineet Semwal
 */
public class Synchronizer {
    private List<String> prependScripts = new ArrayList<String>();
    /**
     * mostly contains items od repeater that will be added to AjaxRequestTarget
     */
    private List<Component> components = new ArrayList<Component>();

    public List<String> _getPrependScripts() {
        return prependScripts;
    }

    private List<String> appendScripts = new ArrayList<String>();

    public List<String> _getAppendScripts() {
        return appendScripts;
    }

    public List<Component> getComponents() {
        return components;
    }

    private MarkupContainer searchFor;

    public MarkupContainer getSearchFor() {
        return searchFor;
    }

    private IPartialPageRequestHandler requestHandler;

    public IPartialPageRequestHandler getRequestHandler() {
        return requestHandler;
    }

    public Synchronizer(final MarkupContainer parent,
                        final IPartialPageRequestHandler requestHandler) {
        this.searchFor = parent;
        this.requestHandler = requestHandler;
    }

    public boolean isRequestHandlerAjaxRequestTarget() {
        return requestHandler instanceof AjaxRequestTarget;
    }

    public void add(Component... cs) {
        for (final Component component : cs) {
            Args.notNull(component, "component");
            components.add(component);
        }
    }

    public void appendScript(final String... scripts) {
        _getAppendScripts().addAll(Arrays.asList(scripts));
    }

    public void prependScript(final String... scripts) {
        _getPrependScripts().addAll(Arrays.asList(scripts));
    }

    /**
     * checks if parent of repeater is added to the components added to
     * A.R.T(ajaxrequesttarget)
     *
     * @return true if parent of repeatingview is added to A.R.T
     */
    public boolean isParentAddedInPartialPageRequestHandler() {
        Collection<? extends Component> cs = getRequestHandler().getComponents();
        if (cs == null) {
            return false;
        }
        if (cs.isEmpty()) {
            return false;
        }
        //if repeater's parent is added to component return true
        if (cs.contains(getSearchFor())) {
            return true;
        }
        //search repeater's parent in children of components added in A.R.T
        boolean found = false;
        for (Component c : cs) {
            if (c instanceof MarkupContainer) {
                MarkupContainer mc = (MarkupContainer) c;
                Boolean result = addNewChildVisitor(mc, getSearchFor());
                if (Boolean.TRUE.equals(result)) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }

    /**
     * @param parent    parent on which ChildVisitor is added
     * @param searchFor ,searchFor is the component which visitor search for
     * @return true if searchFor is found
     */
    protected Boolean addNewChildVisitor(MarkupContainer parent, Component searchFor) {
        return parent.visitChildren(new ChildVisitor(searchFor));
    }

    /**
     * add script s and components to request handler
     */
    public void submit() {
        final IPartialPageRequestHandler requestHandler = getRequestHandler();
        //
        //noop if parent is already added
        //
        if (isParentAddedInPartialPageRequestHandler()) {
            return;
        }

        for (String script : _getPrependScripts()) {
            requestHandler.prependJavaScript(script);
        }

        for (String script : _getAppendScripts()) {
            requestHandler.appendJavaScript(script);
        }
        Component[] components = getComponents().toArray(new Component[0]);
        requestHandler.add(components);
    }

    public static class ChildVisitor implements IVisitor<Component, Boolean> {
        private Component searchFor;

        public ChildVisitor(Component searchFor) {
            this.searchFor = searchFor;
        }

        public void component(Component c, IVisit<Boolean> visit) {
            if (searchFor.getPageRelativePath().equals(c.getPageRelativePath())) {
                visit.stop(true);
            }
        }
    }
}
