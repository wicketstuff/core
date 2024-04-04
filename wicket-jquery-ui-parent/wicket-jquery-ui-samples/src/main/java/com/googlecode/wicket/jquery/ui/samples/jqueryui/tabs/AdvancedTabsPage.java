package com.googlecode.wicket.jquery.ui.samples.jqueryui.tabs;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Generics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.component.NavigationAjaxButton;
import com.googlecode.wicket.jquery.ui.samples.component.TabDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.SimpleTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabListModel;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;

public class AdvancedTabsPage extends AbstractTabsPage
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(AdvancedTabsPage.class);

	private final TabbedPanel tabPanel;
	final NavigationAjaxButton buttons;

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
		this.buttons = this.newNavigationAjaxButton("nav");
		form.add(this.buttons);

		// TabbedPanel //
		this.tabPanel = new TabbedPanel("tabs", this.newTabModel()) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onActivate(AjaxRequestTarget target, int index, ITab tab)
			{
				this.send(buttons, Broadcast.EXACT, new ChangeEvent(index, target));
				this.info("selected tab #" + index);

				target.add(feedback);
			}

			@Override
			protected WebMarkupContainer newTabContainer(String id, String tabId, ITab tab, int index)
			{
				Panel panel = new TabPanel(id);

				Label label = new Label("link", tab.getTitle());
				label.add(AttributeModifier.replace("href", "#" + tabId));
				panel.add(label);

				AbstractLink link = this.newRemoveLink("remove", index);
				panel.add(link);

				return panel;
			}

			private AbstractLink newRemoveLink(String id, final int index)
			{
				return new org.apache.wicket.ajax.markup.html.AjaxLink<Integer>(id, Model.of(index)) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target)
					{
						tabPanel.getModelObject().remove(index);
						target.add(tabPanel.setActiveTab(0));

						// refreshes the navigation buttons //
						this.send(buttons, Broadcast.EXACT, new ChangeEvent(0, target));
					}
				};
			}
		};

		form.add(this.tabPanel);

		// Buttons //
		form.add(new AjaxButton("reload") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				tabPanel.reload(target); // will force reload model
			}
		});

		form.add(new AjaxButton("add") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
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
			protected void onOpen(IPartialPageRequestHandler handler)
			{
				super.onOpen(handler);

				// Sets an empty model object //
				this.setModelObject(new TabItem());
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, DialogButton button)
			{
				// Adds the tab and re-attach the TabPanel //
				TabItem item = this.getModelObject();

				tabPanel.add(new SimpleTab(item.getTitle(), item.getContent()));
				int index = tabPanel.getLastTabIndex(); // gets last visible
				target.add(tabPanel.setActiveTab(index));

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
			private int index = 0;

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
					tabPanel.setActiveTab(this.index - 1, target); // fires onActivate
				}
			}

			@Override
			protected void onForward(AjaxRequestTarget target, AjaxButton button)
			{
				if (this.index < this.max)
				{
					tabPanel.setActiveTab(this.index + 1, target);
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

	/**
	 * Returning a TabListModel is not mandatory, unless the underlying the list of tabs is dynamic.<br>
	 * Do *not* use a LoadableDetachableModel if the model object contains AjaxTab(s)
	 */
	private IModel<List<ITab>> newTabModel()
	{
		return new TabListModel() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<ITab> load()
			{
				List<ITab> tabs = Generics.newArrayList();

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

	static class TabPanel extends Panel
	{
		private static final long serialVersionUID = 1L;

		public TabPanel(String id)
		{
			super(id);
		}
	}
}
