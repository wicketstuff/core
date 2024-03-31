package org.wicketstuff;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.mockito.Mockito;

import java.util.*;

public class SynchronizerTest {

    /**
     * components not empty  and repeater's parent is added to another component which is added to A.R.T
     */
    @WicketTest
    public void Synchronizer_isParentAdded_1() {
        final IPartialPageRequestHandler target = Mockito.mock(IPartialPageRequestHandler.class);
        WebMarkupContainer one = Mockito.mock(WebMarkupContainer.class);
        WebMarkupContainer two = Mockito.mock(WebMarkupContainer.class);
        Label three = Mockito.mock(Label.class);
        List components = Arrays.asList(one, two, three);
        Mockito.when(target.getComponents()).thenReturn(components);
        final MarkupContainer parent = Mockito.mock(MarkupContainer.class);
        Synchronizer synchronizer = new Synchronizer(parent, target) {
            @Override
            protected Boolean addNewChildVisitor(MarkupContainer parent, Component searchFor) {
                return true;
            }

        };
        Synchronizer spy = Mockito.spy(synchronizer);
        boolean result = spy.isParentAddedInPartialPageRequestHandler();
        assertTrue(result);
        Mockito.verify(spy).addNewChildVisitor(one, parent);
        Mockito.verify(spy, Mockito.never()).addNewChildVisitor(two, parent);

    }


    /**
     * components not empty  and repeater's parent is added directly in requesthandler
     */
    @WicketTest
    public void Synchronizer_isParentAdded_2() {
        final IPartialPageRequestHandler target = Mockito.mock(IPartialPageRequestHandler.class);
        List cs = new ArrayList();
        Mockito.when(target.getComponents()).thenReturn(cs);
        final WebMarkupContainer one = Mockito.mock(WebMarkupContainer.class);
        WebMarkupContainer two = Mockito.mock(WebMarkupContainer.class);
        cs.add(one);
        cs.add(two);
        WebMarkupContainer parent = Mockito.mock(WebMarkupContainer.class);
        cs.add(parent);
        Synchronizer synchronizer = new Synchronizer(parent, target);
        Synchronizer spy = Mockito.spy(synchronizer);
        boolean result = spy.isParentAddedInPartialPageRequestHandler();
        assertTrue(result);
        Mockito.verify(spy, Mockito.never()).addNewChildVisitor(one, one);
        Mockito.verify(spy, Mockito.never()).addNewChildVisitor(two, one);

    }


    /**
     * components is empty  and repeater's parent is added directly in A.R.T
     */
    @WicketTest
    public void Synchronizer_isParentAdded_3() {
        final IPartialPageRequestHandler target = Mockito.mock(IPartialPageRequestHandler.class);
        List cs = new ArrayList();
        Mockito.when(target.getComponents()).thenReturn(cs);
        WebMarkupContainer parent = Mockito.mock(WebMarkupContainer.class);
        cs.add(parent);
        Synchronizer synchronizer = new Synchronizer(parent, target) {
            @Override
            protected Boolean addNewChildVisitor(MarkupContainer parent, Component searchFor) {
                return true;
            }

        };
        Synchronizer spy = Mockito.spy(synchronizer);
        boolean result = spy.isParentAddedInPartialPageRequestHandler();
        assertTrue(result);
        Mockito.verify(spy, Mockito.never()).addNewChildVisitor(Mockito.any(MarkupContainer.class), Mockito.any(MarkupContainer.class));
    }

    /**
     * parent not added in AjaxRequestTarget
     */
    @UtilTest
    public void submit_1() {
        IPartialPageRequestHandler requestHandler = Mockito.mock(IPartialPageRequestHandler.class);
        final MarkupContainer parent = Mockito.mock(MarkupContainer.class);
        Synchronizer synchronizer = new Synchronizer(parent, requestHandler);
        final Item item1 = Mockito.mock(Item.class);
        final Item item2 = Mockito.mock(Item.class);
        final String script1 = "script1", script2 = "script2..";
        synchronizer.prependScript(script1, script2);
        synchronizer.add(item1, item2);
        Synchronizer spy = Mockito.spy(synchronizer);
        Mockito.doReturn(false).when(spy).isParentAddedInPartialPageRequestHandler();
        spy.submit();
        Mockito.verify(requestHandler).add(item1, item2);
        Mockito.verify(requestHandler).prependJavaScript(script1);
        Mockito.verify(requestHandler).prependJavaScript(script2);
    }

    /**
     * parent added in AjaxRequestTarget
     */
    @UtilTest
    public void Synchronizer_submit_2() {
        final MarkupContainer parent = Mockito.mock(MarkupContainer.class);
        IPartialPageRequestHandler requestHandler = Mockito.mock(IPartialPageRequestHandler.class);
        Synchronizer synchronizer = new Synchronizer(parent, requestHandler);
        final Item item1 = Mockito.mock(Item.class);
        final Item item2 = Mockito.mock(Item.class);

        final String script1 = "script1", script2 = "script2..";
        synchronizer.prependScript(script1, script2);
        synchronizer.add(item1, item2);
        Synchronizer spy = Mockito.spy(synchronizer);
        Mockito.doReturn(true).when(spy).isParentAddedInPartialPageRequestHandler();
        spy.submit();
        Mockito.verify(requestHandler, Mockito.never()).add(item1, item2);
        Mockito.verify(requestHandler, Mockito.never()).prependJavaScript(script1);
        Mockito.verify(requestHandler, Mockito.never()).prependJavaScript(script2);
    }

}
