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
package org.wicketstuff.navigator;

import org.wicketstuff.*;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.protocol.http.WebApplication;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 *
 * @author Vineet Semwal
 *
 */
public class AjaxItemsNavigatorTest {

     private static WebApplication createMockApplication() {
        WebApplication app = new MockApplication();
        return app;
    }

    @Test(groups = {"wicketTests"})
    public void constructor_1(){
        IQuickView repeater=Mockito.mock(IQuickView.class);
        final AjaxRequestTarget target=Mockito.mock(AjaxRequestTarget.class);
        final List<Item> items=Mockito.mock(List.class);
        AjaxItemsNavigator navigator=new AjaxItemsNavigator("nav",repeater);
           Assert.assertEquals(navigator.getRepeater(),repeater);
    }

    @Test(groups = {"wicketTests"},expectedExceptions = RuntimeException.class)
    public void constructor_2(){
        IQuickView repeater=Mockito.mock(IQuickView.class);
        final AjaxRequestTarget target=Mockito.mock(AjaxRequestTarget.class);
        final List<Item> items=Mockito.mock(List.class);

        boolean isException=false;

        AjaxItemsNavigator navigator=new AjaxItemsNavigator("nav",null);

    }

    /**
     * when current page< pages count
     */

   @Test(groups = {"wicketTests"})
    public void OnStatefulEvent_1() {
       IQuickView quickView=Mockito.mock(IQuickView.class);
       final AjaxRequestTarget target=Mockito.mock(AjaxRequestTarget.class);
       final List<Item> items=Mockito.mock(List.class);
        AjaxItemsNavigator navigator=new AjaxItemsNavigator("nav",quickView){
            @Override
            public AjaxRequestTarget getAjaxRequestTarget() {
             return target;
            }
        };
       AjaxItemsNavigator spy=Mockito.spy(navigator);
       List<Item>actual= spy.onStatefulEvent();
       Mockito.verify(target,Mockito.times(1)).add(spy.getMore());
       Mockito.verify(quickView,Mockito.times(1)).addItemsForNextPage();
       Assert.assertEquals(actual,items);
    }

    /**
     *  parent not null ,OutputMarkupPlaceholderTag set to true
     * reuse stategy is correct
     */
    @Test(groups = {"wicketTests"})
    public void onBeforeRender_1(){
        WebMarkupContainer parent=new WebMarkupContainer("parent");
        IDataProvider data=Mockito.mock(IDataProvider.class);
        QuickViewBase repeater = new QuickView("id",data,10) {
            @Override
            protected void populate(Item item) { }
        };
        repeater.setReuseStrategy(new ItemsNavigationStrategy());
        parent.add(repeater);
        parent.setOutputMarkupPlaceholderTag(true);

        AjaxItemsNavigator navigator=new AjaxItemsNavigator("id",repeater);
        navigator.onBeforeRender();
    }

    @Test(groups = {"wicketTests"})
    public void onBeforeRender_2(){
        WebMarkupContainer parent=new WebMarkupContainer("parent");
        IDataProvider data=Mockito.mock(IDataProvider.class);
        QuickViewBase repeater = new QuickView("id",data,10) {
            @Override
            protected void populate(Item item) { }
        };
        repeater.setReuseStrategy(new ItemsNavigationStrategy());
        parent.add(repeater);
        parent.setOutputMarkupId(true);

        AjaxItemsNavigator navigator=new AjaxItemsNavigator("id",repeater);
        Assert.assertFalse(navigator.isProperInitializationCheckDone());
        navigator.onBeforeRender();
        Assert.assertTrue(navigator.isProperInitializationCheckDone());
    }

    @Test(groups = {"wicketTests"})
    public void repeaterNotProperlyInitializedForItemsNavigation(){
        IQuickView quickView=Mockito.mock(IQuickView.class);
        final IRepeaterUtil util=Mockito.mock(IRepeaterUtil.class);
         AjaxItemsNavigator navigator=new AjaxItemsNavigator("id",quickView){
             @Override
             public IRepeaterUtil getRepeaterUtil() {
                 return util;
             }
         };

        AjaxItemsNavigator spy=Mockito.spy(navigator);
        spy.repeaterNotProperlyInitializedForItemsNavigation(quickView);
        InOrder order= Mockito.inOrder(spy,util);
         order.verify(util,Mockito.times(1)).reuseStategyNotSupportedForItemsNavigation(quickView);
        order.verify(util,Mockito.times(1)).parentNotSuitable(quickView);
        order.verify(util,Mockito.times(1)).outPutMarkupIdNotTrue(quickView);
    }

    @Test(groups = {"wicketTests"})
    public void  doProperInitializationCheck(){
        IQuickView quickView=Mockito.mock(IQuickView.class);
        final IRepeaterUtil util=Mockito.mock(IRepeaterUtil.class);
        AjaxItemsNavigator navigator=new AjaxItemsNavigator("id",quickView){
            @Override
            public IRepeaterUtil getRepeaterUtil() {
                return util;
            }

            @Override
            protected void repeaterNotProperlyInitializedForItemsNavigation(IQuickView quickView) {
            }
        };

        AjaxItemsNavigator spy=Mockito.spy(navigator);
       spy.doProperInitializationCheck();
        Mockito.verify(spy,Mockito.times(1)).repeaterNotProperlyInitializedForItemsNavigation(quickView);
        Assert.assertTrue(spy.isProperInitializationCheckDone());
    }


}
