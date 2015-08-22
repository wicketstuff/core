package org.wicketstuff.springreference.examples.springreference;

import org.wicketstuff.springreference.SpringReference;
import org.wicketstuff.springreference.examples.AbstractFinalPage;
import org.wicketstuff.springreference.examples.FinalService;

/**
 * Example page implementation of {@link AbstractFinalPage} using {@link SpringReference} for
 * integration.
 * 
 * @author akiraly
 */
public class HomePage extends AbstractFinalPage
{
	private static final long serialVersionUID = 3805643588849930152L;

	private final SpringReference<FinalService> finalServiceRef = SpringReference.of(FinalService.class);

	@Override
	public FinalService getFinalService()
	{
		return finalServiceRef.get();
	}
}
