package org.apache.wicket;

import org.apache.wicket.request.IRequestMapper;

/**
 * @author Konstantinos Karavitis
 */
public class RestartResponseAtInterceptPageExceptionMapper
{

	/**
	 * @return
	 */
	public static IRequestMapper getMapper()
	{
		return RestartResponseAtInterceptPageException.MAPPER;
	}

}
