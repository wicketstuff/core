package org.wicketstuff.twitter.intents;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.twitter.util.PageParameterUtil;

/**
 * https://dev.twitter.com/docs/intents#tweet-intent
 * 
 * @author Till Freier
 * 
 */
public class TweetLink extends AbstractIntentLink
{

	private IModel<?> hashTags;
	private IModel<?> inReplyTo;
	private IModel<?> related;
	private IModel<?> text;
	private IModel<?> tweetUrl;
	private IModel<?> via;

	/**
	 * 
	 * @param id
	 *            wicket-id
	 * @param tweet
	 *            tweet-id
	 */
	public TweetLink(final String id)
	{
		super(id, "https://twitter.com/intent/tweet");
	}

	/**
	 * @return the hashTags
	 */
	public IModel<?> getHashTags()
	{
		return hashTags;
	}


	/**
	 * @return the inReplyTo
	 */
	public IModel<?> getInReplyTo()
	{
		return inReplyTo;
	}

	@Override
	protected PageParameters getParameters()
	{
		final PageParameters params = new PageParameters();

		PageParameterUtil.addModelParameter(params, "url", tweetUrl);
		PageParameterUtil.addModelParameter(params, "via", via);
		PageParameterUtil.addModelParameter(params, "text", text);
		PageParameterUtil.addModelParameter(params, "in_reply_to", inReplyTo);
		PageParameterUtil.addModelParameter(params, "hashtags", hashTags);
		PageParameterUtil.addModelParameter(params, "related", related);

		return params;
	}

	/**
	 * @return the related
	 */
	public IModel<?> getRelated()
	{
		return related;
	}

	/**
	 * @return the text
	 */
	public IModel<?> getText()
	{
		return text;
	}

	/**
	 * @return the tweetUrl
	 */
	public IModel<?> getTweetUrl()
	{
		return tweetUrl;
	}

	/**
	 * @return the via
	 */
	public IModel<?> getVia()
	{
		return via;
	}

	/**
	 * Add context to the pre-prepared status update by appending #hashtags. Omit the "#" symbol.
	 * 
	 * @param hashTags
	 *            the hashTags to set
	 */
	public <C extends Serializable> void setHashTags(final Collection<C> hashTags)
	{
		final IModel<LinkedList<C>> of = new Model<LinkedList<C>>(new LinkedList<C>(hashTags));

		setHashTags(of);
	}

	/**
	 * Add context to the pre-prepared status update by appending #hashtags. Omit the "#" symbol.
	 * 
	 * @param hashTags
	 *            the hashTags to set
	 */
	public void setHashTags(final IModel<?> hashTags)
	{
		this.hashTags = hashTags;
	}

	/**
	 * Associate this Tweet with a specific Tweet by indicating its status ID here. The originating
	 * Tweet Author's screen name will be automatically prepended to the reply.
	 * 
	 * @param inReplyTo
	 *            the inReplyTo to set
	 */
	public void setInReplyTo(final IModel<?> inReplyTo)
	{
		this.inReplyTo = inReplyTo;
	}

	/**
	 * Associate this Tweet with a specific Tweet by indicating its status ID here. The originating
	 * Tweet Author's screen name will be automatically prepended to the reply.
	 * 
	 * @param inReplyTo
	 *            the inReplyTo to set
	 */
	public void setInReplyTo(final String inReplyTo)
	{
		setInReplyTo(Model.of(inReplyTo));
	}

	/**
	 * Suggest accounts related to the your content.
	 * 
	 * @param related
	 *            the related to set
	 */
	public <C extends Serializable> void setRelated(final Collection<C> related)
	{
		final IModel<LinkedList<C>> of = new Model<LinkedList<C>>(new LinkedList<C>(related));

		setRelated(of);
	}

	/**
	 * Suggest accounts related to the your content.
	 * 
	 * @param related
	 *            the related to set
	 */
	public void setRelated(final IModel<?> related)
	{
		this.related = related;
	}

	/**
	 * Users will still be able to edit the pre-prepared text. This field has a potential of 140
	 * characters maximum, but consider the implications of other parameters like url and via.
	 * 
	 * @param text
	 *            the text to set
	 */
	public void setText(final IModel<?> text)
	{
		this.text = text;
	}

	/**
	 * Users will still be able to edit the pre-prepared text. This field has a potential of 140
	 * characters maximum, but consider the implications of other parameters like url and via.
	 * 
	 * @param text
	 *            the text to set
	 */
	public void setText(final String text)
	{
		setText(Model.of(text));
	}

	/**
	 * The provided URL will be shortened with t.co and appended to the end of the Tweet.
	 * 
	 * @param tweetUrl
	 *            the tweetUrl to set
	 */
	public void setTweetUrl(final IModel<?> tweetUrl)
	{
		this.tweetUrl = tweetUrl;
	}

	/**
	 * The provided URL will be shortened with t.co and appended to the end of the Tweet.
	 * 
	 * @param tweetUrl
	 *            the tweetUrl to set
	 */
	public void setTweetUrl(final String tweetUrl)
	{
		setTweetUrl(Model.of(tweetUrl));
	}

	/**
	 * A screen name to associate with the Tweet. The provided screen name will be appended to the
	 * end of the tweet with the text: "via @username" "Via" will be translated to the proper
	 * locality of the posting user, if supported. Potentially drives new followers to the target
	 * account.
	 * 
	 * @param via
	 *            the via to set
	 */
	public void setVia(final IModel<?> via)
	{
		this.via = via;
	}

	/**
	 * A screen name to associate with the Tweet. The provided screen name will be appended to the
	 * end of the tweet with the text: "via @username" "Via" will be translated to the proper
	 * locality of the posting user, if supported. Potentially drives new followers to the target
	 * account.
	 * 
	 * @param via
	 *            the via to set
	 */
	public void setVia(final String via)
	{
		setVia(Model.of(via));
	}


}
