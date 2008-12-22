package org.wicketstuff;

import org.apache.wicket.util.tester.WicketTester;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ArtWorkCanvasBehaviorTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ArtWorkCanvasBehaviorTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ArtWorkCanvasBehaviorTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testOneGraphic()
    {
    	WicketTester tester=new WicketTester();
    	tester.startPage(OneGraphicPage.class);
    	tester.assertRenderedPage(OneGraphicPage.class) ;
//    	create test for one graphich
//    	create test for two graphich
//    	create test for two graphich & chained
    }
}
