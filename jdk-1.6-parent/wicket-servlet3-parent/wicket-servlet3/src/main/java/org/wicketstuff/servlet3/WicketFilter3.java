package org.wicketstuff.servlet3;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;

import org.apache.wicket.protocol.http.WicketFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An extension of {@link WicketFilter} that reads {@link WicketFilter#filterPath}
 * from {@link WebFilter} or {@link WebServlet}'s 'url-patterns' or 'value' attributes
 */
public class WicketFilter3 extends WicketFilter {

	private static final Logger log = LoggerFactory.getLogger(WicketFilter3.class);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.wicket.protocol.http.WicketFilter#getFilterPathFromAnnotation
	 * (boolean)
	 */
	@Override
	protected String getFilterPathFromAnnotation(boolean isServlet) {

		String[] patterns = null;

		if (isServlet) {
			WebServlet servlet = getClass().getAnnotation(WebServlet.class);
			if (servlet != null) {
				if (servlet.urlPatterns().length > 0) {
					patterns = servlet.urlPatterns();
				} else {
					patterns = servlet.value();
				}
			}
		} else {
			WebFilter filter = getClass().getAnnotation(WebFilter.class);
			if (filter != null) {
				if (filter.urlPatterns().length > 0) {
					patterns = filter.urlPatterns();
				} else {
					patterns = filter.value();	
				}
			}
		}
		if (patterns != null && patterns.length > 0) {
			String pattern = patterns[0];
			if (patterns.length > 1) {
				log.warn(
						"Multiple url patterns defined for Wicket filter/servlet, using the first: {}",
						pattern);
			}
			
			if ("/*".equals(pattern)) {
				pattern = "";
			}
			
			return pattern;
		}
		return null;
	}

}
