package org.wicketstuff.twitter;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.twitter.behavior.TwitterApiBehavior;

/**
 * https://dev.twitter.com/docs/follow-button
 * 
 * @author Till Freier
 * 
 */
public class FollowButton extends WebComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean showFollowerCount = true;

	/**
	 * 
	 * @param id
	 * @param accountModel
	 *            model for twitter login without '@'
	 */
	public FollowButton(final String id, final IModel<?> accountModel)
	{
		super(id, accountModel);

		initFollowButton();
	}

	/**
	 * 
	 * @param id
	 *            wicket-id
	 * @param account
	 *            twitter login without '@'
	 */
	public FollowButton(final String id, final String account)
	{
		this(id, Model.of(account));
	}

	private CharSequence getInnerButtonString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("Follow @");
		sb.append(getDefaultModelObjectAsString());

		return sb.toString();
	}

	/**
	 * 
	 * @return the link to the twitter profile
	 */
	public String getTwitterUrl()
	{
		final StringBuilder url = new StringBuilder();
		url.append("https://twitter.com/").append(getDefaultModelObjectAsString());

		return url.toString();
	}

	private void initFollowButton()
	{
		add(new TwitterApiBehavior());

		add(new AttributeModifier("class", "twitter-follow-button"));
		add(new AttributeModifier("data-show-count", new PropertyModel<Boolean>(this,
			"showFollowerCount")));
		add(new AttributeModifier("href", new PropertyModel<Boolean>(this, "twitterUrl")));
	}

	/**
	 * @see #setShowFollowerCount(boolean)
	 * @return weather the follower count is shown
	 */
	public boolean isShowFollowerCount()
	{
		return showFollowerCount;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag)
	{
		replaceComponentTagBody(markupStream, openTag, getInnerButtonString());

	}

	/**
	 * By default, the User's followers count is displayed with the Follow Button.
	 * 
	 * @param showFollowerCount
	 *            weather the follower count should be shown
	 */
	public void setShowFollowerCount(final boolean showFollowerCount)
	{
		this.showFollowerCount = showFollowerCount;
	}

}
