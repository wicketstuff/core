package org.wicketstuff.twitter.behavior;

/**
 *
 * @author Till Freier
 *
 */
public class HovercardBehavior extends AbstractAnywhereBehavior
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public HovercardBehavior(String apiKey)
	{
		super(apiKey);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getAnywhereMethod()
	{
		return "hovercards()";
	}

}
