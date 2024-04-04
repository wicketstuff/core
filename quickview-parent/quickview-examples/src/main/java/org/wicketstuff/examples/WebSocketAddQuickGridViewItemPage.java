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

import org.wicketstuff.ItemsNavigationStrategy;
import org.wicketstuff.QuickGridView;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.protocol.ws.api.IWebSocketRequestHandler;
import org.apache.wicket.protocol.ws.api.WebSocketBehavior;
import org.apache.wicket.protocol.ws.api.WebSocketRequestHandler;
import org.apache.wicket.protocol.ws.api.message.ConnectedMessage;
import org.apache.wicket.protocol.ws.api.message.IWebSocketPushMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * QuickGridView with AjaxLink
 *
 * @author Vineet Semwal
 */
public class WebSocketAddQuickGridViewItemPage extends WebPage {
    private List<Integer> list = new ArrayList<>();

    public WebSocketAddQuickGridViewItemPage() {

    }


    @Override
    protected void onInitialize() {
        super.onInitialize();

        IDataProvider<Integer> data = new ListDataProvider<Integer>(list);
        WebMarkupContainer numbers = new WebMarkupContainer("numbers");   //parent for quickview
        numbers.setOutputMarkupPlaceholderTag(true);  //needed for ajax
        Component start, end;
        numbers.add(start = new EmptyPanel("start").setOutputMarkupPlaceholderTag(true));
        numbers.add(end = new EmptyPanel("end").setOutputMarkupPlaceholderTag(true));

        final QuickGridView<Integer> number = new QuickGridView<Integer>("number", data, new ItemsNavigationStrategy(), start, end) {
            @Override
            protected void populate(CellItem<Integer> item) {
                item.add(new Label("display", item.getModel()));
            }

            @Override
            protected void populateEmptyItem(CellItem<Integer> item) {
                item.add(new Label("display"));
            }
        };
        number.setColumns(2);
        numbers.add(number);
        add(numbers);
        //
        //register partial page request handler
        //
        number.register(IWebSocketRequestHandler.class);

        add(new WebSocketBehavior() {
            @Override
            protected void onConnect(ConnectedMessage message) {
                super.onConnect(message);
                WicketApplication.get().addGridRowIncrementConnect(message);

            }

            @Override
            protected void onPush(WebSocketRequestHandler handler, IWebSocketPushMessage message) {
                super.onPush(handler, message);
                if (message instanceof CounterMessage) {
                    CounterMessage counterMessage = (CounterMessage) message;
                    int newObject=counterMessage.getCounter();
                    list.add( newObject);
                    int newObject2=newObject+1;
                    list.add(newObject2);
                    List<Integer>newOnes=new ArrayList<>();
                    newOnes.add(newObject);
                    newOnes.add(newObject2);
                    number.addRows(newOnes.iterator());//just enough to add new rows and respective cells

                }
            }
        });

    }

}
