package org.wicketstuff.twitter.behavior;


/**
 * 
 * @author Till Freier
 * 
 */
public class FollowButtonBehavior extends AbstractAnywhereBehavior
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String account;

	public FollowButtonBehavior(String apiKey, String account)
	{
		super(apiKey);
		this.account = account;
	}

	@Override
	protected String getAnywhereMethod()
	{
		final StringBuilder js = new StringBuilder();
		js.append("followButton('");
		js.append(account);
		js.append("')");

		return js.toString();
	}

}
