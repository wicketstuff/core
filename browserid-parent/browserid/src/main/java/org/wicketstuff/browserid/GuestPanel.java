package org.wicketstuff.browserid;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * A panel that is used for non-authenticated users
 */
public class GuestPanel extends Panel
{

	private static final long serialVersionUID = 1L;

	private static final String BROWSER_ID_JS = "https://browserid.org/include.js";

	private static final ResourceReference RED_ICON = new PackageResourceReference(
		GuestPanel.class, "sign_in_red.png");
	private static final ResourceReference BLUE_ICON = new PackageResourceReference(
		GuestPanel.class, "sign_in_blue.png");
	private static final ResourceReference GREEN_ICON = new PackageResourceReference(
		GuestPanel.class, "sign_in_green.png");
	private static final ResourceReference ORANGE_ICON = new PackageResourceReference(
		GuestPanel.class, "sign_in_orange.png");
	private static final ResourceReference GREY_ICON = new PackageResourceReference(
		GuestPanel.class, "sign_in_grey.png");

	/**
	 * The possible styles of the default "Sign In" image button provided by https://browserid.org
	 */
	public enum Style
	{
		RED, BLUE, GREEN, ORANGE, GREY
	}

	private final Style style;

	public GuestPanel(String id, Style style)
	{
		super(id);

		this.style = style;

		Component signInImage = createSignInButton("signInImage");
		signInImage.add(new VerifyBehavior()
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSuccess(AjaxRequestTarget target)
			{
				GuestPanel.this.onSuccess(target);
			}

			@Override
			protected void onFailure(AjaxRequestTarget target, String failureReason)
			{
				GuestPanel.this.onFailure(target, failureReason);
			}

		});
		add(signInImage);
	}

	protected Component createSignInButton(String componentId)
	{
		Image image = new Image(componentId, getImage(style));
		image.add(AttributeModifier.replace("alt", "Sign In"));
		return image;
	}

	protected ResourceReference getImage(final Style style)
	{
		final ResourceReference imageIcon;
		switch (style)
		{
			case RED :
				imageIcon = RED_ICON;
				break;
			case GREEN :
				imageIcon = GREEN_ICON;
				break;
			case ORANGE :
				imageIcon = ORANGE_ICON;
				break;
			case GREY :
				imageIcon = GREY_ICON;
				break;
			case BLUE :
			default :
				imageIcon = BLUE_ICON;
				break;
		}

		return imageIcon;
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		renderBrowserIdJavaScript(response);
	}

	/**
	 * Renders a reference for external browserid.js (loaded from browserid.org). <br/>
	 * Can be overridden with local reference to browserid.js if needed.
	 * 
	 * @param response
	 *            the current header response
	 */
	protected void renderBrowserIdJavaScript(final IHeaderResponse response)
	{
		response.render(JavaScriptHeaderItem.forUrl(BROWSER_ID_JS));
	}

	/**
	 * A callback called when the authentication is successful
	 * 
	 * @param target
	 *            the current request target
	 */
	protected void onSuccess(final AjaxRequestTarget target)
	{

	}

	/**
	 * A callback called when the authentication has failed
	 * 
	 * @param target
	 *            the current request target
	 * @param target
	 * @param failureReason
	 */
	protected void onFailure(final AjaxRequestTarget target, final String failureReason)
	{

	}
}
