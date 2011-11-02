package org.wicketstuff.facebook.plugins;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

/**
 * https://developers.facebook.com/docs/reference/plugins/like/
 * 
 * @author Till Freier
 * 
 */
public class LikeButton extends AbstractFacebookPlugin
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum LikeButtonAction
	{
		LIKE, RECOMMEND
	}

	public enum LikeButtonLayoutStyle
	{
		BOX_COUNT, BUTTON_COUNT, STANDARD
	}


	private LikeButtonAction action;
	private Font font;
	private LikeButtonLayoutStyle layoutStyle;
	private boolean sendButton = true;
	private boolean showFaces = true;
	private final IModel<?> url;


	/**
	 * {@inheritDoc}
	 */
	public LikeButton(final String id)
	{
		this(id, null);
	}

	public LikeButton(final String id, final IModel<?> url)
	{
		super(id, "fb-like");
		this.url = url;

		initPlugin();
	}

	public LikeButtonAction getAction()
	{
		return action;
	}

	public Font getFont()
	{
		return font;
	}

	public LikeButtonLayoutStyle getLayoutStyle()
	{
		return layoutStyle;
	}

	public boolean isSendButton()
	{
		return sendButton;
	}

	public boolean isShowFaces()
	{
		return showFaces;
	}

	public void setAction(final LikeButtonAction displayVerb)
	{
		action = displayVerb;
	}

	public void setFont(final Font font)
	{
		this.font = font;
	}


	public void setLayoutStyle(final LikeButtonLayoutStyle layoutStyle)
	{
		this.layoutStyle = layoutStyle;
	}

	public void setSendButton(final boolean sendButton)
	{
		this.sendButton = sendButton;
	}

	public void setShowFaces(final boolean showFaces)
	{
		this.showFaces = showFaces;
	}

	private void initPlugin()
	{
		if (url != null)
			add(new AttributeModifier("data-href", url));

		add(new AttributeModifier("data-send", new PropertyModel<Boolean>(this, "sendButton")));
		add(new AttributeModifier("data-layout", new EnumModel(
			new PropertyModel<LikeButtonLayoutStyle>(this, "layoutStyle"))));
		add(new AttributeModifier("data-font", new EnumModel(new PropertyModel<Font>(this, "font"))));
		add(new AttributeModifier("data-action", new EnumModel(new PropertyModel<ColorScheme>(this,
			"action"))));
		add(new AttributeModifier("data-show-faces", new PropertyModel<Boolean>(this, "showFaces")));

	}


}
