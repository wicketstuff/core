package org.wicketstuff.twitter.behavior;

/**
 * 
 * @author Till Freier
 * 
 */
public class LinkifyBehavior extends AbstractAnywhereBehavior
{

	public LinkifyBehavior(final String apiKey)
	{
		super(apiKey);
	}

	@Override
	protected String getAnywhereMethod()
	{
		return "linkifyUsers()";
	}

}
