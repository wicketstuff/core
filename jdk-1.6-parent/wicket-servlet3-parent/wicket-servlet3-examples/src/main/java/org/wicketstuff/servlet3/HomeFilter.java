package org.wicketstuff.servlet3;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(value = "/*", /* urlPatterns = "/*",*/ 
		initParams = { 
			@WebInitParam(name = "applicationClassName", value = "org.wicketstuff.servlet3.WicketApplication")
		}
)
public class HomeFilter extends WicketFilter3 {
	
	/* (non-Javadoc)
	 * @see org.apache.wicket.protocol.http.WicketFilter#init(boolean, javax.servlet.FilterConfig)
	 */
	@Override
	public void init(boolean isServlet, FilterConfig filterConfig)
			throws ServletException {
		// TODO Auto-generated method stub
		super.init(isServlet, filterConfig);
	}

}
