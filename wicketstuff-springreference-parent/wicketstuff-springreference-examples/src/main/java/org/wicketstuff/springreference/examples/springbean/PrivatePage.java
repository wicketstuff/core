package org.wicketstuff.springreference.examples.springbean;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.springreference.examples.AbstractPrivatePage;
import org.wicketstuff.springreference.examples.PrivateService;

/**
 * Example page implementation of {@link AbstractPrivatePage} using
 * <code>&#64;{@link SpringBean}</code> for integration.
 * 
 * This page will not load because the injection will fail.
 * 
 * @author akiraly
 */
public class PrivatePage extends AbstractPrivatePage
{
	private static final long serialVersionUID = -1409779948214620992L;

	// this fails because PrivateService has only private constructor
	@SpringBean
	private PrivateService privateService;

	@Override
	public PrivateService getPrivateService()
	{
		return privateService;
	}
}
