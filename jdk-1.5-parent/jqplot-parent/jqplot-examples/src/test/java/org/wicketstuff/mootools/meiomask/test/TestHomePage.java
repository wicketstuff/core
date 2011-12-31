package org.wicketstuff.mootools.meiomask.test;

import org.apache.wicket.util.tester.WicketTester;

import junit.framework.TestCase;
import org.wicketstuff.jqplot.example.HomePage;
import org.wicketstuff.jqplot.example.WicketApplication;

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
