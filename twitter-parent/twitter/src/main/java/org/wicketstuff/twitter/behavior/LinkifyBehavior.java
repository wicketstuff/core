package org.wicketstuff.twitter.behavior;

/**
 * 
 * @author Till Freier
 * 
 */
public class LinkifyBehavior extends AbstractAnywhereBehavior
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param apiKey
	 */
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
