package org.wicketstuff.springreference.examples;

/**
 * Example service used by {@link AbstractPrivatePage}. The special about this class is the lack of
 * public constructor. Instantiation is done with the static {@link #create()} method. Dynamic
 * proxies do not work with these kind of classes.
 * 
 * This class is instantiated in applicationContext.xml.
 * 
 * For more info see {@link AbstractPrivatePage}.
 * 
 * @author akiraly
 */
public class PrivateService
{

	private PrivateService()
	{
	}

	public static PrivateService create()
	{
		return new PrivateService();
	}

	public String getMessage()
	{
		return "Message from " + getClass();
	}
}
