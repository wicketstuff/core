package org.wicketstuff.springreference.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example service used by {@link AbstractFinalPage}. The special about this class is the
 * {@link #doPublicProtectedFinal()} method which is public final and its implementation calls
 * {@link #doProtected()} a protected method. Calling {@link #doPublicProtectedFinal()} on a dynamic
 * proxy will fail.
 * 
 * Note that calling all other methods, even {@link #doPublicProtectedFinal2()} works.
 * 
 * This class is instantiated in applicationContext.xml.
 * 
 * For more info see {@link AbstractFinalPage}.
 * 
 * @author akiraly
 */
public class FinalService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(FinalService.class);

	private void doPrivate()
	{
		LOGGER.info("doPrivate");
	}

	protected void doProtected()
	{
		LOGGER.info("doProtected");
	}

	public void doPublic()
	{
		LOGGER.info("doPublic");
	}

	public final void doPublicFinal()
	{
		LOGGER.info("doPublicFinal");
		doPublic();
	}

	public final void doPublicPrivateFinal()
	{
		LOGGER.info("doPublicPrivateFinal");
		doPrivate();
	}

	public void doPublicProtected()
	{
		LOGGER.info("doPublicProtected");
		doProtected();
	}

	/**
	 * The "special" method that fails with dynamic proxies.
	 */
	public final void doPublicProtectedFinal()
	{
		LOGGER.info("doPublicProtectedFinal");
		doProtected();
	}

	public void doPublicProtectedFinal2()
	{
		LOGGER.info("doPublicProtectedFinal2");
		doPublicProtectedFinal();
	}
}
