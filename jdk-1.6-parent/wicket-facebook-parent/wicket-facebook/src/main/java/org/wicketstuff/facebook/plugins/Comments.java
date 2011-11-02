package org.wicketstuff.facebook.plugins;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;
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
	
	private int numPosts = 2;


	public Comments(final String id)
	{
		this(id, null);
	}

	public Comments(final String id, final IModel<?> url)
	{
		super(id, "fb-comments");


		if (url != null)
			add(new AttributeModifier("data-href", url));

		add(new AttributeModifier("data-num-posts", new PropertyModel<Integer>(this, "numPosts")));
	}

	public int getNumPosts()
	{
		return numPosts;
	}

	public void setNumPosts(final int numPosts)
	{
		this.numPosts = numPosts;
	}


}
