package org.wicketstuff.jquery.ajaxbackbutton;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.wicketstuff.jquery.demo.PageSupport;

/**
 * A demo page for the Ajax History Manager
 * 
 * To add Ajax back button history support to any page you have to do the following things: 1) Add
 * {@code HistoryIFrame HistoryIFrame} to the page 2) Add
 * {@code HistoryAjaxBehavior HistoryAjaxBehavior} to the page 3) For every Ajax component which
 * should have to support back button call
 * "historyAjaxBehavior.registerAjaxEvent(target, theComponentItself)"
 * 
 * Note: This realization of Ajax back/forward button support doesn't work the same way as Wicket
 * supports non-Ajax back button. Wicket support for back button works by saving/serializing the
 * whole page instance into the page store HistoryAjaxBehavior don't use old instances but just
 * notifies that back/forward button is clicked and the wicket id of the component which had
 * triggered the previous Ajax history entry
 * 
 * @author martin-g
 */
public class Page4AjaxBackButton extends PageSupport
{

	private static final long serialVersionUID = 1L;

	public Page4AjaxBackButton()
	{

		/**
		 * The hidden (CSS position) iframe which will capture the back/forward button clicks
		 */
		final HistoryIFrame historyIFrame = new HistoryIFrame("historyIframe");
		add(historyIFrame);

		/**
		 * The Ajax behavior which will be called after each click on back/forward buttons
		 */
		final HistoryAjaxBehavior historyAjaxBehavior = new HistoryAjaxBehavior()
		{

			private static final long serialVersionUID = 1L;

			@Override
			@SuppressWarnings("unchecked")
			public void onAjaxHistoryEvent(final AjaxRequestTarget target, final String componentId)
			{

				((AjaxLink<Void>)Page4AjaxBackButton.this.get(componentId)).onClick(target);
			}

		};

		add(historyAjaxBehavior);

		final AjaxLink<Void> linkOne = new AjaxLink<Void>("linkOne")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(final AjaxRequestTarget target)
			{
				info("Link one has been clicked");
				target.addChildren(getPage(), FeedbackPanel.class);
				historyAjaxBehavior.registerAjaxEvent(target, this);
			}

		};
		add(linkOne);

		final AjaxLink<Void> linkTwo = new AjaxLink<Void>("linkTwo")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(final AjaxRequestTarget target)
			{
				info("Link two has been clicked");
				target.addChildren(getPage(), FeedbackPanel.class);
				historyAjaxBehavior.registerAjaxEvent(target, this);
			}

		};
		add(linkTwo);

		final AjaxLink<Void> linkThree = new AjaxLink<Void>("linkThree")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(final AjaxRequestTarget target)
			{
				info("Link three has been clicked");
				target.addChildren(getPage(), FeedbackPanel.class);
				historyAjaxBehavior.registerAjaxEvent(target, this);
			}

		};
		add(linkThree);
	}

}
