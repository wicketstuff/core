package org.wicketstuff.twitter.intents;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.twitter.util.PageParameterUtil;

/**
 * https://dev.twitter.com/docs/intents#follow-intent
 * 
 * @author Till Freier
 * 
 */
public class FollowLink extends AbstractIntentLink
{
	private IModel<?> screenName;
	private IModel<?> userId;

	public FollowLink(final String id)
	{
		super(id, "https://twitter.com/intent/user");
	}

	@Override
	protected PageParameters getParameters()
	{

		final PageParameters params = new PageParameters();

		PageParameterUtil.addModelParameter(params, "screen_name", screenName);
		PageParameterUtil.addModelParameter(params, "user_id", userId);

		return params;
	}

	/**
	 * @return the screenName
	 */
	public IModel<?> getScreenName()
	{
		return screenName;
	}

	/**
	 * @return the userId
	 */
	public IModel<?> getUserId()
	{
		return userId;
	}

	/**
	 * Every Twitter user has a screen name, but they are subject to change. We recommend using
	 * user_id whenever possible.
	 * 
	 * @param screenName
	 *            the screenName to set
	 */
	public void setScreenName(final IModel<?> screenName)
	{
		this.screenName = screenName;
	}

	/**
	 * Every Twitter user has a screen name, but they are subject to change. We recommend using
	 * user_id whenever possible.
	 * 
	 * @param screenName
	 *            the screenName to set
	 */
	public void setScreenName(final String screenName)
	{
		setScreenName(Model.of(screenName));
	}

	/**
	 * Twitter User IDs are available from the API and uniquely identify a user.
	 * 
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(final IModel<?> userId)
	{
		this.userId = userId;
	}

	/**
	 * Twitter User IDs are available from the API and uniquely identify a user.
	 * 
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(final String userId)
	{
		setUserId(Model.of(userId));
	}


}
