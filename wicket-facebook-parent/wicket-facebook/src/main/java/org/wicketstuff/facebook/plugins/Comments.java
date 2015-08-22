package org.wicketstuff.facebook.plugins;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

/**
 * https://developers.facebook.com/docs/reference/plugins/comments/
 * 
 * @author Till Freier
 * 
 */
public class Comments extends AbstractFacebookPlugin
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int numPosts = 10;

	/**
	 * 
	 * @param id
	 *            wicket-id
	 */
	public Comments(final String id)
	{
		super(id, "fb-comments");

		add(new AttributeModifier("data-num-posts", new PropertyModel<Integer>(this, "numPosts")));
	}

	/**
	 * 
	 * @param id
	 *            wicket-id
	 * @param url
	 *            model for the URL for this Comments plugin. News feed stories on Facebook will
	 *            link to this URL.
	 */
	public Comments(final String id, final IModel<?> url)
	{
		this(id);

		add(new AttributeModifier("data-href", url));

	}

	/**
	 * 
	 * @param id
	 *            wicket-id
	 * @param url
	 *            model for the URL for this Comments plugin. News feed stories on Facebook will
	 *            link to this URL.
	 */
	public Comments(final String id, final String url)
	{
		this(id, Model.of(url));
	}

	/**
	 * @see #setNumPosts(int)
	 * @return the number of posts
	 */
	public int getNumPosts()
	{
		return numPosts;
	}

	/**
	 * 
	 * @param numPosts
	 *            the number of comments to show by default. Default: 10. Minimum: 1
	 */
	public void setNumPosts(final int numPosts)
	{
		this.numPosts = numPosts;
	}


}
