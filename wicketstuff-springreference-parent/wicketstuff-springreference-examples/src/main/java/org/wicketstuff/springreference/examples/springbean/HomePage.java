package org.wicketstuff.springreference.examples.springbean;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.springreference.examples.AbstractFinalPage;
import org.wicketstuff.springreference.examples.FinalService;

/**
 * Example page implementation of {@link AbstractFinalPage} using
 * <code>&#64;{@link SpringBean}</code> for integration.
 * 
 * Clicking the first link on this page will cause an exception.
 * 
 * @author akiraly
 */
public class HomePage extends AbstractFinalPage
{
	private static final long serialVersionUID = 4697187546238788036L;

	@SpringBean
	private FinalService finalService;

	@Override
	public FinalService getFinalService()
	{
		return finalService;
	}
}
