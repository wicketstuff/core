package com.googlecode.wicket.jquery.ui.samples.pages.kendo.accordion;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.widget.accordion.AccordionPanel;
import com.googlecode.wicket.kendo.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.kendo.ui.widget.tabs.SimpleTab;

public class KendoAccordionPanelPage extends AbstractAccordionPage
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(KendoAccordionPanelPage.class);
	
	public KendoAccordionPanelPage()
	{
		final Form<?> form = new Form<Void>("form");
		this.add(form);

		// Feedback Panel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Accordion //
		final AccordionPanel accordion = new AccordionPanel("accordion", this.newTabList()) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSelect(AjaxRequestTarget target, int index, ITab tab)
			{
				info(String.format("selected tab: #%d - %s", index, tab.getTitle().getObject()));
				target.add(feedback);
			}
		};

		form.add(accordion);

		// Button //
		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				accordion.setTabIndex(accordion.getLastTabIndex(), target);
			}
		});
	}

	private List<ITab> newTabList()
	{
		List<ITab> tabs = new ArrayList<ITab>();

		// tab #1, using SimpleTab //
		tabs.add(new SimpleTab(Model.of("Tab (SimpleTab)"), Model.of("My content !")));

		// tab #2, invisible Tab //
		tabs.add(new SimpleTab(Model.of("Tab (invisible)"), Model.of("")) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible()
			{
				return false;
			}
		});

		// tab #3, using AbstractTab //
		tabs.add(new AbstractTab(Model.of("Tab (AbstractTab)")) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer getPanel(String panelId)
			{
				return new Fragment(panelId, "panel-1", KendoAccordionPanelPage.this);
			}
		});

		// tab #4, using AjaxTab //
		tabs.add(new AjaxTab(Model.of("Tab (AjaxTab)")) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer getLazyPanel(String panelId)
			{
				try
				{
					// sleep the thread to simulate a long load
					Thread.sleep(500);
				}
				catch (InterruptedException e)
				{
					if (LOG.isDebugEnabled())
					{
						LOG.debug(e.getMessage(), e);
					}
				}

				return new Fragment(panelId, "panel-2", KendoAccordionPanelPage.this);
			}
		});

		return tabs;
	}
}
