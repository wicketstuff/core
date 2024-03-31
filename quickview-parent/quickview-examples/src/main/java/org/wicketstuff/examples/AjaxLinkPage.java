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
package org.wicketstuff.examples;

import org.wicketstuff.QuickView;
import org.wicketstuff.ReuseAllStrategy;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vineet Semwal
 */
public class AjaxLinkPage extends WebPage {
    private List<Integer> list = new ArrayList<Integer>();

    public AjaxLinkPage() {
        for (int i = 0; i < 4; i++) {
            list.add(i);
        }
    }


    @Override
    protected void onInitialize() {
        super.onInitialize();
        IDataProvider<Integer> data = new ListDataProvider<Integer>(list);
        WebMarkupContainer numbers = new WebMarkupContainer("numbers");   //parent for quickview
        numbers.setOutputMarkupId(true);  //needed for ajax
        //
        //boundaries that you may or may not create for quickview but creating it
        //gives the benefit that quickview will only work inside boundaries
        //and you can add markup or other components outside boundaries ,without it
        //quickview can be the only child of parent
        //
        Component start, end;
        numbers.add(start = new EmptyPanel("start").setOutputMarkupPlaceholderTag(true));
        numbers.add(end = new EmptyPanel("end").setOutputMarkupPlaceholderTag(true));

        final QuickView<Integer> number = new QuickView<Integer>("number", data, new ReuseAllStrategy(), start, end) {
            @Override
            protected void populate(Item<Integer> item) {
                item.add(new Label("display", item.getModel()));
            }
        };
        numbers.add(number);
        add(numbers);


        AjaxLink addLink = new AjaxLink<Void>("addLink") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                int newObject = list.get(list.size() - 1) + 1;
                list.add(newObject);
                number.addNewItems(newObject);  //just enough to create a new row at last
            }

        };
        addLink.setOutputMarkupPlaceholderTag(true);
        add(addLink);


        AjaxLink addAtStartLink = new AjaxLink<Void>("addAtStartLink") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                int newObject = list.get(0) - 1;
                list.add(0, newObject);
                number.addNewItemsAtStart(newObject);  //just enough to create a new row at start
            }

        };
        addAtStartLink.setOutputMarkupPlaceholderTag(true);
        add(addAtStartLink);

    }

}
