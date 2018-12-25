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
import org.wicketstuff.QuickGridView;
import org.wicketstuff.navigator.AjaxPageScrollEventBehavior;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import java.util.ArrayList;
import java.util.List;

/**
 * QuickGridView example with {@link AjaxPageScrollEventBehavior}
 * @author Vineet Semwal
 * 
 */
public class QuickGridViewWithPageScrollBehavior extends WebPage {
    private List<Integer> list=new ArrayList<Integer>();
    QuickGridView<Integer> gridView;
    public QuickGridViewWithPageScrollBehavior(){
        for(int i=0;i<200;i++){
            list.add(i)  ;
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        IDataProvider<Integer> data=new ListDataProvider<Integer>(list);
        WebMarkupContainer parent=new WebMarkupContainer("parent");
        parent.setOutputMarkupPlaceholderTag(true);
        Component start,end;
        parent.add(start=new EmptyPanel("start").setOutputMarkupPlaceholderTag(true));
        parent.add(end=new EmptyPanel("end").setOutputMarkupPlaceholderTag(true)) ;
        //read more about {@see ItemsNavigationStrategy} ,it is one of provided strategy that can be used in
        //cases where new items has to be added without re-rendering QuickView
         gridView=new QuickGridView<Integer>("gv",data,new ItemsNavigationStrategy(),start,end) {
            @Override
            protected void populateEmptyItem(final CellItem<Integer> item) {
                item.add(new Label("label"));
            }

            @Override
            protected void populate(final CellItem<Integer> item) {
                item.add(new Label("label", item.getModel()));

            }
        };
        gridView.setColumns(10);
        gridView.setRows(5);
        parent.add(gridView);
        add(parent);


        //pagescrollbehavior added to page
        add(new AjaxPageScrollEventBehavior(){
            @Override
            protected void onScroll(AjaxRequestTarget target) {
              addItemsForNextPage(gridView);
            }

            @Override
            protected boolean forceScrollBarForPage() {
                return true;
            }
            
        });

    }

}
