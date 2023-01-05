package org.wicketstuff.servlet3;

import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;

import org.apache.wicket.protocol.http.WicketFilter;

@WebFilter(value = "/*", /* urlPatterns = "/*", */
initParams = { @WebInitParam(name = "applicationClassName", value = "org.wicketstuff.servlet3.WicketApplication") })
public class HomeFilter extends WicketFilter
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.wicket.protocol.http.WicketFilter#init(boolean, javax.servlet.FilterConfig)
	 */
	@Override
	public void init(boolean isServlet, FilterConfig filterConfig) throws ServletException
	{
		// TODO Auto-generated method stub
		super.init(isServlet, filterConfig);
	}

}
