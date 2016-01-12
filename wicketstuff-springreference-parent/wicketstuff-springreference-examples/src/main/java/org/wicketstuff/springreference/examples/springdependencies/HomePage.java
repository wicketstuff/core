package org.wicketstuff.springreference.examples.springdependencies;

import org.springframework.beans.factory.annotation.Autowired;
import org.wicketstuff.springreference.AbstractSpringDependencies;
import org.wicketstuff.springreference.examples.AbstractFinalPage;
import org.wicketstuff.springreference.examples.FinalService;

/**
 * Example page implementation of {@link AbstractFinalPage} using {@link AbstractSpringDependencies}
 * for integration.
 * 
 * @author akiraly
 */
public class HomePage extends AbstractFinalPage
{
	private static final long serialVersionUID = 1L;

	/**
	 * This nested class holds all our spring dependencies. Annotated in spring style.
	 */
	static class Deps extends AbstractSpringDependencies
	{
		private static final long serialVersionUID = 1L;

		@Autowired
		transient FinalService finalService;
	}

	private final Deps deps = new Deps();

	@Override
	public FinalService getFinalService()
	{
		return deps.finalService;
	}
}
