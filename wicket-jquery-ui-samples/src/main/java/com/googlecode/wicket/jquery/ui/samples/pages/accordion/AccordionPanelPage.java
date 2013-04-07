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
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.accordion.AccordionPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.SimpleTab;

public class AccordionPanelPage extends AbstractAccordionPage
{
	private static final long serialVersionUID = 1L;

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
		final AccordionPanel accordion = new AccordionPanel("accordion", this.newTabList(), options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onActivate(AjaxRequestTarget target, int index, ITab tab)
			{
				info(String.format("selected tab: #%d - %s", index, tab.getTitle().getObject()));
				target.add(feedback);
			}
		};

		form.add(accordion);

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				target.add(accordion.setActiveTab(2));
			}
		});
	}

	private List<ITab> newTabList()
	{
		List<ITab> tabs = new ArrayList<ITab>();

		// tab #1, using SimpleTab //
		tabs.add(new SimpleTab(new Model<String>("Tab (SimpleTab)"), new Model<String>("My content !")));

		// tab #2, using AbstractTab //
		tabs.add(new AbstractTab(new Model<String>("Tab (AbstractTab)")) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer getPanel(String panelId)
			{
				return new Fragment(panelId, "panel-1", AccordionPanelPage.this);
			}
		});

		// tab #3, using AjaxTab //
		tabs.add(new AjaxTab(new Model<String>("Tab (AjaxTab)")) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer getLazyPanel(String panelId)
			{
				try
				{
					// sleep the thread to simulate a long load
					Thread.sleep(750);
				}
				catch (InterruptedException e)
				{
					error(e.getMessage());
				}

				return new Fragment(panelId, "panel-2", AccordionPanelPage.this);
			}
		});

		return tabs;
	}
}
