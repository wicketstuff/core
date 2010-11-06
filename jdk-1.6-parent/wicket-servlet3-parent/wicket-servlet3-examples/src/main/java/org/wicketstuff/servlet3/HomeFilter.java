package org.wicketstuff.servlet3;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.apache.wicket.protocol.http.WicketFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(value = "/*", urlPatterns = "/*", 
		initParams = { 
			@WebInitParam(name = "applicationClassName", value = "org.wicketstuff.servlet3.WicketApplication")
		}
)
public class HomeFilter extends WicketFilter {

	private static final Logger log = LoggerFactory.getLogger(HomeFilter.class);
	
	
	
	/* (non-Javadoc)
	 * @see org.apache.wicket.protocol.http.WicketFilter#init(boolean, javax.servlet.FilterConfig)
	 */
	@Override
	public void init(boolean isServlet, FilterConfig filterConfig)
			throws ServletException {
		// TODO Auto-generated method stub
		super.init(isServlet, filterConfig);
	}



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
				patterns = servlet.urlPatterns();
			}
		} else {
			WebFilter filter = getClass().getAnnotation(WebFilter.class);
			if (filter != null) {
				patterns = filter.urlPatterns();
			}
		}
		if (patterns != null && patterns.length > 0) {
			String pattern = patterns[0];
			if (patterns.length > 1) {
				log.warn(
						"Multiple url patterns defined for Wicket filter/servlet, using the first: {}",
						pattern);
			}
			return pattern;
		}
		return null;
	}

}
