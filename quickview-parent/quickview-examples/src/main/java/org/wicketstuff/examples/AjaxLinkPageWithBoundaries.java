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

import org.wicketstuff.ItemsNavigationStrategy;
import org.wicketstuff.QuickView;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vineet Semwal
 */
public class AjaxLinkPageWithBoundaries extends WebPage {
    private List<Integer> list = new ArrayList<Integer>();

    public AjaxLinkPageWithBoundaries() {
        for (int i = 0; i < 4; i++) {
            list.add(i);
        }
    }


    @Override
    protected void onInitialize() {
        super.onInitialize();
        IDataProvider<Integer> data = new ListDataProvider<Integer>(list);
        WebMarkupContainer numbers = new WebMarkupContainer("numbers");   //parent for quickview
        Component start,end;
        numbers.add(start=new Label("start").setOutputMarkupPlaceholderTag(true));
        numbers.add(end=new Label("end").setOutputMarkupPlaceholderTag(true)) ;

        numbers.setOutputMarkupId(true);  //needed for ajax
        final QuickView<Integer> number = new QuickView<Integer>("number", data, new ItemsNavigationStrategy(),start,end ) {
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
                int newObject=list.get(list.size()-1) +1;
                list.add(newObject);
                number.addNewItems(newObject);  //just enough to create a new row at last
                 }

        };
        addLink.setOutputMarkupPlaceholderTag(true);
        add(addLink);


        AjaxLink addAtStartLink = new AjaxLink<Void>("addAtStartLink") {


            @Override
            public void onClick(AjaxRequestTarget target) {
                int newObject=list.get(0)-1;
                list.add(0,newObject);
                number.addNewItemsAtStart(newObject);  //just enough to create a new row at start
            }

        };
        addAtStartLink.setOutputMarkupPlaceholderTag(true);
        add(addAtStartLink);

    }

}
