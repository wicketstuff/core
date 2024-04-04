package org.wicketstuff.facebook.plugins;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * https://developers.facebook.com/docs/reference/plugins/send/
 * 
 * @author Till Freier
 * 
 */
public class SendButton extends AbstractFacebookPlugin
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param id
	 *            wicket-id
	 * @param url
	 *            the URL to send.
	 */
	public SendButton(final String id, final IModel<?> url)
	{
		super(id, "fb-send");

		if (url != null)
			add(new AttributeModifier("data-href", url));

	}

	/**
	 * 
	 * @param id
	 *            wicket-id
	 * @param url
	 *            the URL to send.
	 */
	public SendButton(final String id, final String url)
	{
		this(id, Model.of(url));
	}

}
