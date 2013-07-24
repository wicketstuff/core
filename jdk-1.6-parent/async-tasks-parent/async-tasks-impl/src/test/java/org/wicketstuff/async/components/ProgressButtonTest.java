package org.wicketstuff.async.components;

import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ProgressButtonTest {

    private static final RuntimeException RUNTIME_EXCEPTION = new RuntimeException();
    private static final Runnable EXCEPTION_RUNNABLE = new Runnable() {
        @Override
        public void run() {
            throw RUNTIME_EXCEPTION;
        }
    };

    private WicketTester tester;
    private TestPage page;

    @BeforeMethod
    public void setUp() throws Exception {
        tester = new WicketTester();
        page = new TestPage();
    }

    @Test
    public void testButton() throws Exception {

        final boolean[] result = new boolean[1];

        page.setRunnable(new Runnable() {
            @Override
            public void run() {
                result[0] = true;
            }
        });

        tester.startPage(page);
        tester.assertRenderedPage(TestPage.class);

        FormTester formTester = tester.newFormTester("form");
        formTester.submit("button");

        assertTrue(result[0]);
        assertTrue(page.isTaskStart());
//        assertTrue(page.isTaskSuccess());
        assertFalse(page.isTaskCancel());
//        assertFalse(page.isTaskError());
    }

    @Test
    public void testButtonAjax() throws Exception {

        final boolean[] result = new boolean[1];

        page.setRunnable(new Runnable() {
            @Override
            public void run() {
                result[0] = true;
            }
        });

        tester.startPage(page);
        tester.assertRenderedPage(TestPage.class);

        tester.executeAjaxEvent("form:button", "click");
        executeRefresh(500L);

        assertTrue(result[0]);
        assertTrue(page.isTaskStart());
        assertTrue(page.isTaskSuccess());
        assertFalse(page.isTaskCancel());
        assertFalse(page.isTaskError());
    }

    @Test
    public void testException() throws Exception {

        page.setRunnable(EXCEPTION_RUNNABLE);

        tester.startPage(page);
        tester.assertRenderedPage(TestPage.class);

        FormTester formTester = tester.newFormTester("form");
        formTester.submit("button");

        assertTrue(page.isTaskStart());
//        assertFalse(page.isTaskSuccess());
        assertFalse(page.isTaskCancel());
//        assertTrue(page.isTaskError());
    }

    @Test
    public void testExceptionAjax() throws Exception {

        page.setRunnable(EXCEPTION_RUNNABLE);

        tester.startPage(page);
        tester.assertRenderedPage(TestPage.class);

        tester.executeAjaxEvent("form:button", "click");
        executeRefresh(500L);

        assertTrue(page.isTaskStart());
        assertFalse(page.isTaskSuccess());
        assertFalse(page.isTaskCancel());
        assertTrue(page.isTaskError());
    }

    private void executeRefresh(long deplay) throws Exception {
        Thread.sleep(deplay);
        for (Behavior b : page.getButton().getBehaviors()) {
            if (b instanceof AbstractAjaxTimerBehavior) {
                tester.executeBehavior((AbstractAjaxTimerBehavior) b);
            }
        }
    }
}
