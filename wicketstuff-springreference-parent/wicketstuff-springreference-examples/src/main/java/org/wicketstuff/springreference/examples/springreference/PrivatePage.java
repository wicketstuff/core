package org.wicketstuff.springreference.examples.springreference;

import org.wicketstuff.springreference.SpringReference;
import org.wicketstuff.springreference.examples.AbstractPrivatePage;
import org.wicketstuff.springreference.examples.PrivateService;

/**
 * Example page implementation of {@link AbstractPrivatePage} using {@link SpringReference} for
 * integration.
 * 
 * @author akiraly
 */
public class PrivatePage extends AbstractPrivatePage
{
	private static final long serialVersionUID = -1409779948214620992L;

	private final SpringReference<PrivateService> privateServiceRef = SpringReference.of(PrivateService.class);

	@Override
	public PrivateService getPrivateService()
	{
		return privateServiceRef.get();
	}
}
