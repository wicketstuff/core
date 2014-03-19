package org.wicketstuff;

import org.apache.wicket.util.tester.WicketTester;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class LiquidCanvasBehaviorTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public LiquidCanvasBehaviorTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( LiquidCanvasBehaviorTest.class );
    }

    private WicketTester tester=new WicketTester();
    
    /**
     * Rigourous Test :-)
     */
    public void testOneGraphic()
    {
    	tester.startPage(OneGraphicPage.class);
    	tester.assertRenderedPage(OneGraphicPage.class) ;
    }
    public void testTwoGraphic()
    {
    	tester.startPage(TwoGraphicPage.class);
    	tester.assertRenderedPage(TwoGraphicPage.class) ;
    }
    public void testOneGraphicChained()
    {
    	tester.startPage(OneGraphicChainedPage.class);
    	tester.assertRenderedPage(OneGraphicChainedPage.class) ;
    }

}
