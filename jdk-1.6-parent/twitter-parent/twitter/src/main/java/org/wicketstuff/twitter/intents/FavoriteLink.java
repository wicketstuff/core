package org.wicketstuff.twitter.intents;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.twitter.util.PageParameterUtil;

/**
 * https://dev.twitter.com/docs/intents#favorite-intent
 * 
 * @author Till Freier
 * 
 */
public class FavoriteLink extends AbstractIntentLink
{
	private IModel<?> related;
	private IModel<?> tweetId;

	public FavoriteLink(final String id)
	{
		super(id, "https://twitter.com/intent/favorite");
	}

	@Override
	protected PageParameters getParameters()
	{
		final PageParameters params = new PageParameters();

		PageParameterUtil.addModelParameter(params, "tweet_id", tweetId);
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
	 * @return the tweetId
	 */
	public IModel<?> getTweetId()
	{
		return tweetId;
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
	 * Every Tweet is identified by an ID. You can find this value from the API or by viewing the
	 * permalink page for any Tweet, usually accessible by clicking on the "published at" date of a
	 * tweet.
	 * 
	 * @param tweetId
	 *            the tweetId to set
	 */
	public void setTweetId(final IModel<?> tweetId)
	{
		this.tweetId = tweetId;
	}

	/**
	 * Every Tweet is identified by an ID. You can find this value from the API or by viewing the
	 * permalink page for any Tweet, usually accessible by clicking on the "published at" date of a
	 * tweet.
	 * 
	 * @param tweetId
	 *            the tweetId to set
	 */
	public void setTweetId(final String tweetId)
	{
		setTweetId(Model.of(tweetId));
	}

}
