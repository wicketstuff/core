package org.wicketstuff.twitter;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.twitter.behavior.TwitterApiBehavior;

/**
 * https://dev.twitter.com/docs/tweet-button
 * 
 * @author Till Freier
 * 
 */
public class TweetButton extends WebComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final IModel<?> countUrl;
	private final IModel<?> related;
	private final IModel<?> relatedSummery;
	private final IModel<?> text;
	private final IModel<?> url;
	private final IModel<?> via;

	/**
	 * @param id
	 *            wicket-id
	 * @param url
	 *            URL to Tweet
	 */
	public TweetButton(final String id, final IModel<?> url)
	{
		this(id, url, Model.of(), Model.of(), Model.of(), Model.of(), Model.of());
	}

	/**
	 * @param id
	 *            wicket-id
	 * @param url
	 *            URL to Tweet
	 * @param via
	 *            via user
	 * @param text
	 *            Tweet text
	 */
	public TweetButton(final String id, final IModel<?> url, final IModel<?> text,
		final IModel<?> via)
	{
		this(id, url, text, via, Model.of(), Model.of(), Model.of());
	}

	/**
	 * @param id
	 *            wicket-id
	 * @param url
	 *            URL to Tweet
	 * @param via
	 *            via user
	 * @param text
	 *            Tweet text
	 * @param related
	 *            Recommended accounts
	 * @param countUrl
	 *            URL to which your shared URL resolves to
	 */
	public TweetButton(final String id, final IModel<?> url, final IModel<?> text,
		final IModel<?> via, final IModel<?> countUrl, final IModel<?> related,
		final IModel<?> relatedSummery)
	{
		super(id);
		this.url = url;
		this.via = via;
		this.text = text;
		this.related = related;
		this.relatedSummery = relatedSummery;
		this.countUrl = countUrl;

		initTweetButton();
	}

	/**
	 * creates the related field content
	 * 
	 * @return
	 */
	public String getRelatedModelString()
	{
		if (related == null || related.getObject() == null)
			return null;

		if (relatedSummery == null || relatedSummery.getObject() == null)
			return related.getObject().toString();

		final StringBuilder sb = new StringBuilder();
		sb.append(related.getObject()).append(':').append(relatedSummery.getObject());

		return sb.toString();
	}

	private void initTweetButton()
	{
		add(new TwitterApiBehavior());

		add(new AttributeModifier("class", "twitter-share-button"));
		add(new AttributeModifier("data-url", url));
		add(new AttributeModifier("data-via", via));
		add(new AttributeModifier("data-text", text));
		add(new AttributeModifier("data-counturl", countUrl));
		add(new AttributeModifier("data-related", new PropertyModel<String>(this,
			"relatedModelString")));

		add(new AttributeModifier("href", "https://twitter.com/share"));
	}

}
