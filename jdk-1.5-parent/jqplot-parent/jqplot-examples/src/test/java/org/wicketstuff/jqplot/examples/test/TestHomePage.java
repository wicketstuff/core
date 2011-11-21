package org.wicketstuff.jqplot.examples.test;

import junit.framework.TestCase;

import org.apache.wicket.util.tester.WicketTester;
import org.wicketstuff.jqplot.examples.HomePage;
import org.wicketstuff.jqplot.examples.WicketApplication;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage extends TestCase {

    private WicketTester tester;

    @Override
    public void setUp() {
        tester = new WicketTester(new WicketApplication());
    }

    public void testRenderMyPage() {
        tester.startPage(HomePage.class);
        tester.assertRenderedPage(HomePage.class);
    }
}
