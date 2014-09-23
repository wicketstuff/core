/**
 * 
 */
package org.wicketstuff.stateless;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Test;

/**
 * @author jfk
 * 
 */
public class StatelessEncoderTest {

    /**
     * Test method for
     * {@link org.wicketstuff.stateless.StatelessEncoder#mergeParameters(Url, PageParameters)}.
     */
    @Test
    public void testAppendnULLParameters() {
        final Url encoded = StatelessEncoder.mergeParameters(null, null);

        assertNull(encoded);
    }

    /**
     * Test method for
     * {@link StatelessEncoder#mergeParameters(Url, PageParameters)}.
     */
    @Test
    public void testAppendParameters() {
        final PageParameters params = new PageParameters();

        params.add("test1", "val1");
        params.add("test2", new String[] { "val2", "val3" });

        StatelessEncoder.mergeParameters(null, null);
        final Url originalUrl = Url.parse("");
        final Url encoded = StatelessEncoder.mergeParameters(originalUrl, params);

        // XXX compares Map content vs. its toString(). Improve the assertion!
        assertEquals(Url.parse("?test1=val1&test2=val2&test2=val3"), encoded);
    }

    @Test
    public void overrideOriginalParameter() {

        Url originalUrl = Url.parse("./home?0-1.ILinkListener-c2--link&test2=original");

        final PageParameters params = new PageParameters();
        params.add("test1", "value1");
        params.add("test2", new String[] { "val2", "val3" });

        Url mergedParameters = StatelessEncoder.mergeParameters(originalUrl, params);

        assertEquals(Url.parse("./home?0-1.ILinkListener-c2--link&test1=value1&test2=val2&test2=val3"), mergedParameters);
    }

    /**
     * https://github.com/wicketstuff/core/issues/223
     */
    @Test
    public void mergeIndexedParameters()
    {
        final PageParameters params = new PageParameters();

        params.set(0, "val1");
        params.set(1, "val2");
        params.add("test2", new String[] { "val3", "val4" });

        final Url originalUrl = Url.parse("");
        final Url encoded = StatelessEncoder.mergeParameters(originalUrl, params);

        // XXX compares Map content vs. its toString(). Improve the assertion!
        assertEquals(Url.parse("val1/val2?test2=val3&test2=val4"), encoded);
    }

    @Test
    public void putIndexedParametersBeforeJsessionId()
    {
        Url originalUrl = Url.parse("/wicket/page;jsessionid=1255ckl9n31uj1baf4wv9yqv7r?0-1.ILinkListener-link");
        final PageParameters params = new PageParameters();

        params.set(0, "val1");
        params.set(1, "val2");
        params.add("test2", new String[] { "val3", "val4" });

        final Url encoded = StatelessEncoder.mergeParameters(originalUrl, params);

        assertEquals(Url.parse("/wicket/page/val1/val2;jsessionid=1255ckl9n31uj1baf4wv9yqv7r?0-1.ILinkListener-link&test2=val3&test2=val4"), encoded);
    }
}
