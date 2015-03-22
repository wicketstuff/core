package com.googlecode.wicket.jquery.ui.samples.pages.accordion;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
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

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.accordion.AccordionPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.SimpleTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabListModel;

public class AccordionPanelPage extends AbstractAccordionPage
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(AccordionPanelPage.class);

	public AccordionPanelPage()
	{
		final Form<?> form = new Form<Void>("form");
		this.add(form);

		// Feedback Panel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Recommended options when using dynamic content (AjaxTab) //
		Options options = new Options();
		options.set("heightStyle", Options.asString("content"));

		// Accordion //
		final AccordionPanel accordion = new AccordionPanel("accordion", this.newTabModel(), options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onActivate(AjaxRequestTarget target, int index, ITab tab)
			{
				info(String.format("selected tab: #%d - %s", index, tab.getTitle().getObject()));
				target.add(feedback);
			}
		};

		form.add(accordion);

		// Button //
		form.add(new AjaxButton("reload") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				accordion.setActiveTab(0).refresh(target); // resets active tab and forces reload model
			}
		});
 
		form.add(new AjaxButton("activate") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				target.add(accordion.setActiveTab(accordion.getLastTabIndex()));
			}
		});
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
						return new Fragment(panelId, "panel-1", AccordionPanelPage.this);
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

						return new Fragment(panelId, "panel-2", AccordionPanelPage.this);
					}
				});

				return tabs;
			}
		};
	}
}
