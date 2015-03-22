package com.googlecode.wicket.jquery.ui.samples.pages.tabs;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.component.NavigationAjaxButton;
import com.googlecode.wicket.jquery.ui.samples.component.TabDialog;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.SimpleTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabListModel;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;

public class AdvancedTabsPage extends AbstractTabsPage
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(AdvancedTabsPage.class);

	private int tabIndex = 0;
	private final TabbedPanel tabPanel;

	public AdvancedTabsPage()
	{
		// Dialog //
		final TabDialog dialog = this.newTabDialog("dialog");
		this.add(dialog);

		// Form //
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// Feedback Panel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Navigation //
		final NavigationAjaxButton buttons = this.newNavigationAjaxButton("nav");
		form.add(buttons);

		// TabbedPanel //
		this.tabPanel = new TabbedPanel("tabs", this.newTabModel()) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onActivate(AjaxRequestTarget target, int index, ITab tab)
			{
				tabIndex = index;

				this.info("selected tab #" + index);
				this.send(buttons, Broadcast.EXACT, new ChangeEvent(target));

				target.add(feedback);
			}
		};

		form.add(this.tabPanel);

		// Buttons //
		form.add(new AjaxButton("reload") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				tabPanel.refresh(target); // will force reload model
			}
		});

		form.add(new AjaxButton("add") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				dialog.open(target);
			}
		});

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
				target.add(tabPanel);
			}
		};
	}

	private NavigationAjaxButton newNavigationAjaxButton(String id)
	{
		return new NavigationAjaxButton(id) {

			private static final long serialVersionUID = 1L;
			private int max = 0;

			@Override
			protected void onConfigure()
			{
				super.onConfigure();

				this.max = this.count() - 1;

				this.getBackwardButton().setEnabled(tabIndex > 0);
				this.getForwardButton().setEnabled(tabIndex < this.max);
			}

			@Override
			protected void onBackward(AjaxRequestTarget target, AjaxButton button)
			{
				if (tabIndex > 0)
				{
					tabPanel.setActiveTab(tabIndex - 1, target); // fires onActivate
				}
			}

			@Override
			protected void onForward(AjaxRequestTarget target, AjaxButton button)
			{
				if (tabIndex < this.max)
				{
					tabPanel.setActiveTab(tabIndex + 1, target);
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

	/**
	 * Returning a TabListModel is not mandatory, unless the underlying the list of tabs is dynamic.<br/>
	 * Do *not* use a LoadableDetachableModel if the model object contains AjaxTab(s)
	 */
	private IModel<List<ITab>> newTabModel()
	{
		return new TabListModel() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<ITab> load()
			{
				List<ITab> tabs = new ArrayList<ITab>();

				// tab #1, using SimpleTab //
				tabs.add(new SimpleTab(Model.of("Simple Tab"), Model.of("my content")));

				// tab #2, invisible Tab //
				tabs.add(new SimpleTab(Model.of("Tab (randow visibility)"), Model.of("now visible")) {

					private static final long serialVersionUID = 1L;

					private final boolean visible = Math.random() > 0.5; // makes the model dynamic

					@Override
					public boolean isVisible()
					{
						return this.visible;
					}
				});

				// tab #3, using AbstractTab //
				tabs.add(new AbstractTab(Model.of("Abstract Tab")) {

					private static final long serialVersionUID = 1L;

					@Override
					public WebMarkupContainer getPanel(String panelId)
					{
						return new Fragment(panelId, "panel-1", AdvancedTabsPage.this);
					}
				});

				// tab #4, using AjaxTab //
				tabs.add(new AjaxTab(Model.of("Ajax Tab")) {

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
		};
	}

	static class ChangeEvent extends JQueryEvent
	{
		private final AjaxRequestTarget target;

		public ChangeEvent(AjaxRequestTarget target)
		{
			this.target = target;
		}

		public AjaxRequestTarget getTarget()
		{
			return this.target;
		}
	}
}
