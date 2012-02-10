package org.wicketstuff.browserid;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.IFeedback;
import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.browserid.GuestPanel.Style;

/**
 * This is the main panel which loads specific panels depending on whether there is a logged in user
 * or not.
 */
public class BrowserIdPanel extends Panel
{

	private static final long serialVersionUID = 1L;

	private static final String CONTENT_ID = "content";

	/**
	 * The style of the default "Sign In" image button provided by https://browserid.org
	 */
	private final Style style;

	public BrowserIdPanel(String id)
	{
		this(id, Style.BLUE);
	}

	public BrowserIdPanel(String id, Style style)
	{
		super(id);

		this.style = style;

		setOutputMarkupId(true);
	}

	@Override
	protected void onConfigure()
	{
		super.onConfigure();

		if (SessionHelper.isLoggedIn(getSession()))
		{
			addOrReplace(getLoggedInPanel(CONTENT_ID));
		}
		else
		{
			addOrReplace(getGuestPanel(CONTENT_ID));
		}
	}

	protected Component getGuestPanel(String componentId)
	{
		return new GuestPanel(componentId, style)
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSuccess(AjaxRequestTarget target)
			{
				super.onSuccess(target);

				target.add(BrowserIdPanel.this);
			}

			@Override
			protected void onFailure(AjaxRequestTarget target, final String failureReason)
			{
				super.onFailure(target, failureReason);

				error("The authentication failed: " + failureReason);
				target.addChildren(getPage(), IFeedback.class);
			}
		};
	}

	protected Component getLoggedInPanel(String componentId)
	{
		return new LoggedInPanel(componentId)
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void onLoggedOut(AjaxRequestTarget target)
			{
				super.onLoggedOut(target);
				target.add(BrowserIdPanel.this);
			}
		};
	}
}
