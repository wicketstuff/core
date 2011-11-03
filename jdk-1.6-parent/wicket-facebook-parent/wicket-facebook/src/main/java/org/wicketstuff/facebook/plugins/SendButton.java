package org.wicketstuff.facebook.plugins;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;

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

	// TODO font

	public SendButton(String id, IModel<?> url)
	{
		super(id, "fb-send");

		if (url != null)
			add(new AttributeModifier("data-href", url));

	}

}
