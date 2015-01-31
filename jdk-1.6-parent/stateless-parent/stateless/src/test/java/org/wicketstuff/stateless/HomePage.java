/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.stateless;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Homepage
 */
public class HomePage extends WebPage {
    static int itemCount = 0;

    private static final long serialVersionUID = 1L;

    static List<String> getList() {
        final ArrayList<String> list = new ArrayList<String>(itemCount);
        final int count = ++itemCount;

        for (int idx = 1; idx <= count; idx++) {
            list.add(Integer.toString(idx));
        }

        return list;
    }

    /**
     * Constructor that is invoked when page is invoked without a session.
     * 
     * @param parameters
     *            Page parameters
     */
    public HomePage(final PageParameters parameters) {
        super(parameters);

        final MarkupContainer list = new WebMarkupContainer("list");
        final List<String> data = getList();
        final ListView<String> listView = new ListView<String>("item", data) {
            private static final long serialVersionUID = 200478523599165606L;

            @Override
            protected void populateItem(final ListItem<String> item) {
                final String _item = item.getModelObject();

                item.add(new Label("value", _item));
            }
        };
        final Link<String[]> moreLink = new StatelessAjaxFallbackLink<String[]>(
                "more") {
            private static final long serialVersionUID = -1023445535126577565L;

            @Override
            public void onClick(final AjaxRequestTarget target) {
                final List<String> _data = getList();

                System.out.println(_data);

                listView.setModelObject(_data);

                if (target != null) {
                    target.add(list, "new");
                }
            }
        };
        final Link<String> homeLink = new BookmarkablePageLink<String>("home",
                HomePage.class);

        add(homeLink);
        // list.setOutputMarkupId(true);
        list.add(listView);
        add(list);
        add(moreLink);
    }
}
