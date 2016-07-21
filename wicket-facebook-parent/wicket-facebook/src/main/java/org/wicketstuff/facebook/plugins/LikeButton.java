package org.wicketstuff.facebook.plugins;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

/**
 * https://developers.facebook.com/docs/reference/plugins/like/
 * 
 * @author Till Freier
 * 
 */
public class LikeButton extends AbstractFacebookPlugin
{
	public enum LikeButtonAction
	{
		LIKE, RECOMMEND
	}

	public enum LikeButtonLayoutStyle
	{
		/**
		 * displays social text to the right of the button and friends' profile photos below.
		 * Minimum width: 225 pixels. Default width: 450 pixels. Height: 35 pixels (without photos)
		 * or 80 pixels (with photos).
		 */
		BOX_COUNT,

		/**
		 * displays the total number of likes to the right of the button. Minimum width: 90 pixels.
		 * Default width: 90 pixels. Height: 20 pixels.
		 */
		BUTTON_COUNT,

		/**
		 * displays the total number of likes above the button. Minimum width: 55 pixels. Default
		 * width: 55 pixels. Height: 65 pixels.
		 */
		STANDARD
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private LikeButtonAction action;
	private LikeButtonLayoutStyle layoutStyle;
	private boolean sendButton = true;
	private boolean showFaces = true;
	private final IModel<?> url;


	/**
	 * {@inheritDoc}
	 */
	public LikeButton(final String id)
	{
		this(id, Model.of());
	}

	/**
	 * 
	 * @param id
	 *            wicket-id
	 * @param url
	 *            the URL to like. The XFBML version defaults to the current page.
	 */
	public LikeButton(final String id, final IModel<?> url)
	{
		super(id, "fb-like");
		this.url = url;

		initPlugin();
	}


	/**
	 * 
	 * @param id
	 *            wicket-id
	 * @param url
	 *            the URL to like. The XFBML version defaults to the current page.
	 */
	public LikeButton(final String id, final String url)
	{
		this(id, Model.of(url));
	}

	/**
	 * @see #setAction(LikeButtonAction)
	 * @return
	 */
	public LikeButtonAction getAction()
	{
		return action;
	}

	/**
	 * @see #setLayoutStyle(LikeButtonLayoutStyle)
	 * @return
	 */
	public LikeButtonLayoutStyle getLayoutStyle()
	{
		return layoutStyle;
	}

	/**
	 * @see #setSendButton(boolean)
	 * @return
	 */
	public boolean isSendButton()
	{
		return sendButton;
	}

	/**
	 * @see #setShowFaces(boolean)
	 * @return
	 */
	public boolean isShowFaces()
	{
		return showFaces;
	}

	/**
	 * 
	 * @param displayVerb
	 *            the verb to display on the button. Options: {@link LikeButtonAction}
	 */
	public void setAction(final LikeButtonAction displayVerb)
	{
		action = displayVerb;
	}

	/**
	 * @see LikeButtonLayoutStyle
	 * @param layoutStyle
	 */
	public void setLayoutStyle(final LikeButtonLayoutStyle layoutStyle)
	{
		this.layoutStyle = layoutStyle;
	}

	/**
	 * 
	 * @param sendButton
	 *            whether to include a Send button with the Like button.
	 */
	public void setSendButton(final boolean sendButton)
	{
		this.sendButton = sendButton;
	}

	/**
	 * 
	 * @param showFaces
	 *            whether to display profile photos below the button (standard layout only)
	 */
	public void setShowFaces(final boolean showFaces)
	{
		this.showFaces = showFaces;
	}

	private void initPlugin()
	{
		add(new AttributeModifier("data-href", url));

		add(new AttributeModifier("data-send", new PropertyModel<Boolean>(this, "sendButton")));
		add(new AttributeModifier("data-layout", new EnumModel(
			new PropertyModel<LikeButtonLayoutStyle>(this, "layoutStyle"))));
		add(new AttributeModifier("data-action", new EnumModel(new PropertyModel<ColorScheme>(this,
			"action"))));
		add(new AttributeModifier("data-show-faces", new PropertyModel<Boolean>(this, "showFaces")));

	}


}
