package org.wicketstuff.springreference.examples.springdependencies;

import org.springframework.beans.factory.annotation.Autowired;
import org.wicketstuff.springreference.AbstractSpringDependencies;
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
	private static final long serialVersionUID = 1L;

	/**
	 * This nested class holds all our spring dependencies. Annotated in spring style.
	 */
	static class Deps extends AbstractSpringDependencies
	{
		private static final long serialVersionUID = 1L;

		@Autowired
		transient PrivateService privateService;
	}

	private final Deps deps = new Deps();

	@Override
	public PrivateService getPrivateService()
	{
		return deps.privateService;
	}
}
