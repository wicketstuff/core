package org.wicketstuff.minis.resolver.taglib.el;

import org.apache.wicket.Page;
import org.apache.wicket.core.util.lang.WicketObjects;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * This class is used for JSP and JSF context to render a URLs with EL expressions:
 * <pre>
 * ${wicket:url('mypackage.MyPage')}
 * and 
 * ${wicket:urlWithQuery('mypackage.MyPage','param1=value1&param2=value2')}
 * </pre>
 */
public class WicketELURL {

    /**
     * Creates a URL based on the given page and the given query
     * 
     * @param page
     *            the page to be used within the link
     * @param query
     *            the query with all parameters
     * @return the url
     */
    public static String urlWithQuery(String page, String query) {
	PageParameters pageParameters = new PageParameters();
	if (query != null) {
	    RequestUtils.decodeParameters(query, pageParameters);
	}
	Class<Page> resolveClass = WicketObjects.resolveClass(page);
	final CharSequence urlFor = RequestCycle.get().urlFor(resolveClass,
		pageParameters);
	return urlFor.toString();
    }

    /**
     * Creates a URL based on the given page
     * 
     * @param page
     *            the page to be used within the link
     * @return the URL
     */
    public static String url(String page) {
	PageParameters pageParameters = new PageParameters();
	Class<Page> resolveClass = WicketObjects.resolveClass(page);
	final CharSequence urlFor = RequestCycle.get().urlFor(resolveClass,
		pageParameters);
	return urlFor.toString();
    }
}
