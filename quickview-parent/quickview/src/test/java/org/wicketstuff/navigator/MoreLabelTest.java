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

import org.wicketstuff.IQuickView;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;

/**
 * @author Vineet Semwal
 *
 */
public class MoreLabelTest {

    @Test(groups = {"wicketTests"})
    public void constructor_1(){
        IModel model= Mockito.mock(IModel.class);
        ItemsNavigatorBase navigator=Mockito.mock(ItemsNavigatorBase.class);
         MoreLabel label=new MoreLabel("id",model,navigator)  ;
        Assert.assertEquals(label.getNavigator(),navigator);
        Assert.assertTrue(label.getOutputMarkupId());

    }
    @Test(groups = {"wicketTests"},expectedExceptions = IllegalArgumentException.class)
    public void constructor_2(){
        IModel model= Mockito.mock(IModel.class);
         MoreLabel label=new MoreLabel("id",model,null) ;
    }
    @Test(groups = {"wicketTests"})
    public void newOnClickBehavior(){
        IModel model= Mockito.mock(IModel.class);
        ItemsNavigatorBase navigator=Mockito.mock(ItemsNavigatorBase.class);
        MoreLabel label=new MoreLabel("id",model,navigator)  ;
        AjaxEventBehavior behavior=label.newOnClickBehavior();
        Assert.assertEquals(behavior.getEvent(), "click");
    }

    @Test(groups = {"wicketTests"})
    public void newOnClickBehavior_onclick(){
        IModel model= Mockito.mock(IModel.class);
        ItemsNavigatorBase navigator=Mockito.mock(ItemsNavigatorBase.class);
        MoreLabel label=new MoreLabel("id",model,navigator){

            @Override
            protected void onClick(AjaxRequestTarget target) {
            }
        };
         AjaxRequestTarget target=Mockito.mock(AjaxRequestTarget.class);
         MoreLabel spy=Mockito.spy(label);
         MoreLabel.OnClickBehavior behavior=(MoreLabel.OnClickBehavior)spy.newOnClickBehavior() ;
          behavior.onEvent(target);
           Mockito.verify(spy,Mockito.times(1)).onClick(target);
    }


    /**
     * current page=2 pages=4
     */

    @Test(groups = {"wicketTests"})
    public void onConfigure_1() {
        final String id = "id";
        final long currentpage = 2,pages = 4;
        ItemsNavigatorBase navigator = Mockito.mock(ItemsNavigatorBase.class);
        IQuickView repeater = mock(IQuickView.class);
        Mockito.when(repeater.getCurrentPage()).thenReturn(currentpage);
        Mockito.when(repeater.getPageCount()).thenReturn(pages) ;
        Mockito.when(navigator.getRepeater()).thenReturn(repeater);
        IModel model=Mockito.mock(IModel.class);
        MoreLabel more = new MoreLabel(id,model, navigator);
        more.onConfigure();
        Assert.assertTrue(more.isVisible());
    }

    /**
     * current page=3 pages=4
     */

    @Test(groups = {"wicketTests"})
    public void onConfigure_2() {
        final String id = "id";
        final long currentpage = 3,pages = 4;
        ItemsNavigatorBase navigator = Mockito.mock(ItemsNavigatorBase.class);
        IQuickView repeater = mock(IQuickView.class);
        Mockito.when(repeater.getCurrentPage()).thenReturn(currentpage);
        Mockito.when(repeater.getPageCount()).thenReturn(pages) ;
        Mockito.when(navigator.getRepeater()).thenReturn(repeater);
        IModel model=Mockito.mock(IModel.class);
        MoreLabel more = new MoreLabel(id,model, navigator);
        more.onConfigure();
        Assert.assertFalse(more.isVisible());
    }

    /**
     * current page=4 pages=4
     */

    @Test(groups = {"wicketTests"})
    public void onConfigure_3() {
        final String id = "id";
        final long currentpage = 4,pages = 4;
        ItemsNavigatorBase navigator = Mockito.mock(ItemsNavigatorBase.class);
        IQuickView repeater = mock(IQuickView.class);
        Mockito.when(repeater.getCurrentPage()).thenReturn(currentpage);
        Mockito.when(repeater.getPageCount()).thenReturn(pages) ;
        Mockito.when(navigator.getRepeater()).thenReturn(repeater);
        IModel model=Mockito.mock(IModel.class);
        MoreLabel more = new MoreLabel(id,model, navigator);
        more.onConfigure();
        Assert.assertFalse(more.isVisible());
    }
    /**
     * current page=5 pages=4
     */

    @Test(groups = {"wicketTests"})
    public void onConfigure_4() {
        final String id = "id";
        final long currentpage = 5,pages = 4;
        ItemsNavigatorBase navigator = Mockito.mock(ItemsNavigatorBase.class);
        IQuickView repeater = mock(IQuickView.class);
        Mockito.when(repeater.getCurrentPage()).thenReturn(currentpage);
        Mockito.when(repeater.getPageCount()).thenReturn(pages) ;
        Mockito.when(navigator.getRepeater()).thenReturn(repeater);
        IModel model=Mockito.mock(IModel.class);
        MoreLabel more = new MoreLabel(id,model, navigator);
        more.onConfigure();
        Assert.assertFalse(more.isVisible());
    }

    @Test(groups = {"wicketTests"})
    public void renderHead_1(){
        IHeaderResponse response=Mockito.mock(IHeaderResponse.class);
        ItemsNavigatorBase navigator = Mockito.mock(ItemsNavigatorBase.class);
        IQuickView repeater = mock(IQuickView.class);
        Mockito.when(navigator.getRepeater()).thenReturn(repeater);
        IModel model=Mockito.mock(IModel.class);
        MoreLabel more = new MoreLabel("more",model, navigator);
        more.renderHead(response);
        Mockito.verify(response,Mockito.times(1)).render(CssHeaderItem.forReference(NavigatorCssReference.get()));
    }

    private static WebApplication createMockApplication() {
        WebApplication app = new MockApplication();
        return app;
    }
}
