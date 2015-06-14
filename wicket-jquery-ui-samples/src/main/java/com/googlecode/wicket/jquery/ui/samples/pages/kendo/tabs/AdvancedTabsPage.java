package com.googlecode.wicket.jquery.ui.samples.pages.kendo.tabs;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.samples.component.NavigationAjaxButton;
import com.googlecode.wicket.jquery.ui.samples.component.TabDialog;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.kendo.ui.widget.tabs.SimpleTab;
import com.googlecode.wicket.kendo.ui.widget.tabs.TabbedPanel;
import com.googlecode.wicket.kendo.ui.widget.tabs.TabsBehavior;

public class AdvancedTabsPage extends AbstractTabsPage
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(AdvancedTabsPage.class);

	private final TabbedPanel tabPanel;
	private final NavigationAjaxButton buttons;

	public AdvancedTabsPage()
	{
		// Feedback//
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));

		// Dialog //
		final TabDialog dialog = this.newTabDialog("dialog");
		this.add(dialog);

		// Form //
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// Nav-tab Buttons //
		this.buttons = this.newNavigationAjaxButton("nav");
		form.add(this.buttons);

		// Add-tab Buttons //
		form.add(new com.googlecode.wicket.kendo.ui.form.button.AjaxButton("add") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				dialog.open(target);
			}
		});

		// TabbedPanel //
		this.tabPanel = new TabbedPanel("tabs", this.newTabList()) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSelect(AjaxRequestTarget target, int index, ITab tab)
			{
				this.send(buttons, Broadcast.EXACT, new ChangeEvent(index, target));
				this.info("Selected tab #" + index);

				target.add(feedback);
			}
		};

		form.add(this.tabPanel);
	}

	// Factories //

	private TabDialog newTabDialog(String id)
	{
		return new TabDialog(id, "Add Tab") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onOpen(AjaxRequestTarget target)
			{
				super.onOpen(target);

				// Sets an empty model object //
				this.setModelObject(new TabItem());
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				// Adds the tab and re-attach the TabPanel //
				TabItem item = this.getModelObject();

				tabPanel.add(new SimpleTab(item.getTitle(), item.getContent()));
				int index = tabPanel.getLastTabIndex(); // gets last visible
				target.add(tabPanel.setTabIndex(index));

				// refreshes the navigation buttons //
				this.send(buttons, Broadcast.EXACT, new ChangeEvent(index, target));
			}
		};
	}

	private NavigationAjaxButton newNavigationAjaxButton(String id)
	{
		return new NavigationAjaxButton(id) {

			private static final long serialVersionUID = 1L;
			private int max = 0;
			private int index = TabsBehavior.DEFAULT_TAB_INDEX;

			@Override
			protected void onConfigure()
			{
				super.onConfigure();

				this.max = this.count() - 1;

				this.getBackwardButton().setEnabled(this.index > 0);
				this.getForwardButton().setEnabled(this.index < this.max);
			}

			@Override
			protected void onBackward(AjaxRequestTarget target, AjaxButton button)
			{
				if (this.index > 0)
				{
					tabPanel.setTabIndex(this.index - 1, target); // fires onSelect
				}
			}

			@Override
			protected void onForward(AjaxRequestTarget target, AjaxButton button)
			{
				if (this.index < this.max)
				{
					tabPanel.setTabIndex(this.index + 1, target); // fires onSelect
				}
			}

			@Override
			public void onEvent(IEvent<?> event)
			{
				super.onEvent(event);

				// broadcasted by the TabbedPanel
				if (event.getPayload() instanceof ChangeEvent)
				{
					ChangeEvent payload = (ChangeEvent) event.getPayload();

					this.index = payload.getIndex();
					AjaxRequestTarget target = payload.getTarget();

					target.add(this);
				}
			}

			/**
			 * Gets count of visible tabs
			 *
			 * @return the count
			 */
			private int count()
			{
				int count = 0;

				for (ITab tab : tabPanel.getModelObject())
				{
					if (tab.isVisible())
					{
						count++;
					}
				}

				return count;
			}
		};
	}

	private List<ITab> newTabList()
	{
		List<ITab> tabs = new ArrayList<ITab>();

		// tab #1, using SimpleTab //
		tabs.add(new SimpleTab(Model.of("Tab #1"), Model.of("my content")));

		// tab #2, invisible Tab //
		tabs.add(new SimpleTab(Model.of("Tab #2"), Model.of("invisible")) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible()
			{
				return false;
			}
		});

		// tab #3, using AbstractTab //
		tabs.add(new AbstractTab(Model.of("Tab #3")) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer getPanel(String panelId)
			{
				return new Fragment(panelId, "panel-1", AdvancedTabsPage.this);
			}
		});

		// tab #4, using AjaxTab //
		tabs.add(new AjaxTab(Model.of("Tab (ajax)")) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer getLazyPanel(String panelId)
			{
				try
				{
					// sleep the thread for a half second to simulate a long load
					Thread.sleep(500);
				}
				catch (InterruptedException e)
				{
					if (LOG.isDebugEnabled())
					{
						LOG.debug(e.getMessage(), e);
					}
				}

				return new Fragment(panelId, "panel-2", AdvancedTabsPage.this);
			}
		});

		return tabs;
	}

	static class ChangeEvent extends JQueryEvent
	{
		private final AjaxRequestTarget target;
		private final int index;

		public ChangeEvent(int index, AjaxRequestTarget target)
		{
			this.index = index;
			this.target = target;
		}

		public int getIndex()
		{
			return this.index;
		}

		public AjaxRequestTarget getTarget()
		{
			return this.target;
		}
	}
}
