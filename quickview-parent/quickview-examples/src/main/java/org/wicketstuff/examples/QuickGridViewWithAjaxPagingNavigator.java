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

import org.wicketstuff.QuickGridView;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import java.util.ArrayList;
import java.util.List;

/**
 * QuickGridView with {@link AjaxPagingNavigator}
 *
 * @author Vineet Semwal
 *
 */
public class QuickGridViewWithAjaxPagingNavigator extends WebPage {
    private List<Integer> list=new ArrayList<Integer>();
    QuickGridView<Integer> gridView;
    public QuickGridViewWithAjaxPagingNavigator(){
        for(int i=0;i<200;i++){
            list.add(i)  ;
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        IDataProvider<Integer> data=new ListDataProvider<Integer>(list);
        //quickview by default has DefaultReuseStrategy which works fine in case of paging

        gridView=new QuickGridView<Integer>("gv",data) {
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
        WebMarkupContainer parent=new WebMarkupContainer("parent");
        parent.add(gridView);
        parent.setOutputMarkupPlaceholderTag(true);
        add(parent);
        AjaxPagingNavigator navigator=new AjaxPagingNavigator("nav",gridView);
        add(navigator);

    }

}
