package org.wicketstuff.minis.component;

import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * <p>
 * A simple {@link FeedbackPanel} variant which is only visible if there are messages attached to
 * it.
 * </p>
 * 
 * <p>
 * Useful if your designer is putting always visible elements (like border, sign image) on your
 * feedback panels.
 * </p>
 * 
 * @author akiraly
 */
public class DefaultInvisibleFeedbackPanel extends FeedbackPanel
{
	private static final long serialVersionUID = -7309405061061951309L;

	/**
	 * Constructor. Delegates to super.
	 * 
	 * @param id
	 *            not-null id of this component
	 * @param filter
	 *            used to filter messages relevant for this panel, can be null
	 */
	public DefaultInvisibleFeedbackPanel(String id, IFeedbackMessageFilter filter)
	{
		super(id, filter);
	}

	/**
	 * Constructor. Delegates to super.
	 * 
	 * @param id
	 *            not-null id of this component
	 */
	public DefaultInvisibleFeedbackPanel(String id)
	{
		super(id);
	}

	@Override
	protected void onConfigure()
	{
		super.onConfigure();
		setVisible(anyMessage());
	}
}
