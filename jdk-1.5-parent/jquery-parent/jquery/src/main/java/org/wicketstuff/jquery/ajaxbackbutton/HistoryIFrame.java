package org.wicketstuff.jquery.ajaxbackbutton;

import org.apache.wicket.markup.html.link.InlineFrame;

/**
 * @author martin-g
 */
public class HistoryIFrame extends InlineFrame
{

	private static final long serialVersionUID = 1L;

	public HistoryIFrame(final String wid)
	{
		super(wid, HistoryIFramePage.class);

		setOutputMarkupId(true);
		setMarkupId("historyIframe");
	}

}
