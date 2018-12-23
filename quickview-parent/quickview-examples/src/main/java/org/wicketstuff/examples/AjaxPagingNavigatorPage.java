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

import org.wicketstuff.DefaultQuickReuseStrategy;
import org.wicketstuff.QuickView;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
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
 *
 */
public class AjaxPagingNavigatorPage extends WebPage {
    private List<Integer> list=new ArrayList<Integer>();

    public AjaxPagingNavigatorPage(){
              for(int i=0;i<20;i++){
                list.add(i)  ;
            }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        IDataProvider<Integer> data=new ListDataProvider<Integer>(list);
        final int itemsPerRequest=4;//rows created per request
        // boundaries not needed when parent the only use case is to ajax update the parent
        //quickview by default has DefaultReuseStrategy which works fine in case of paging
        QuickView<Integer> quickView=new QuickView<Integer>("number",data,itemsPerRequest) {
            @Override
            protected void populate(Item<Integer> item) {
                item.add(new Label("display",item.getModel()));
            }
        } ;
        quickView.setReuseStrategy(new DefaultQuickReuseStrategy());
        WebMarkupContainer numbers=new WebMarkupContainer("numbers");   //don't forget adding quickview to parent with any ajax navigator
        numbers.add(quickView);
        numbers.setOutputMarkupId(true); //don't forget required when using ajax navigators
        add(numbers);
        AjaxPagingNavigator navigator=new AjaxPagingNavigator("navigator",quickView);
         add(navigator) ;
    }
}
