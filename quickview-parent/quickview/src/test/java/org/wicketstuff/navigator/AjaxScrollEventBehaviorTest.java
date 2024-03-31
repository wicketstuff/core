package org.wicketstuff.navigator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.wicketstuff.IQuickView;
import org.wicketstuff.WicketTest;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

/**
 * @author Vineet Semwal
 */
public class AjaxScrollEventBehaviorTest {
    WicketTester tester;

    @BeforeEach
    void setup() {
        tester = new WicketTester(createMockApplication());
        scrolled = false;
    }

    private static WebApplication createMockApplication() {
        WebApplication app = new MockApplication();
        return app;
    }

    private boolean scrolled = false;

    @WicketTest
    public void constructor() {
        AjaxScrollEventBehaviorBase behavior = new AjaxScrollEventBehaviorBase() {
            @Override
            protected void onScroll(AjaxRequestTarget target) {

            }
        };
        assertEquals(behavior.getEvent(), "scroll");
    }

    @WicketTest
    public void onScroll() {
        WebMarkupContainer container = new WebMarkupContainer("id");
        AjaxScrollEventBehaviorBase behavior = new AjaxScrollEventBehaviorBase() {
            @Override
            protected void onScroll(AjaxRequestTarget target) {
                scrolled = true;
            }
        };

        container.add(behavior);
        tester.startComponentInPage(container);
        tester.executeAjaxEvent(container, "scroll");
        assertTrue(scrolled);
    }

    @WicketTest
    public void addItemsForNextPage() {
        IQuickView quickView = Mockito.mock(IQuickView.class);
        AjaxScrollEventBehaviorBase behavior = new AjaxScrollEventBehaviorBase() {
            @Override
            protected void onScroll(AjaxRequestTarget target) {
            }
        };
        AjaxScrollEventBehaviorBase spy = Mockito.spy(behavior);
        spy.addItemsForNextPage(quickView);
        Mockito.verify(quickView, Mockito.times(1)).addItemsForNextPage();
    }

}
