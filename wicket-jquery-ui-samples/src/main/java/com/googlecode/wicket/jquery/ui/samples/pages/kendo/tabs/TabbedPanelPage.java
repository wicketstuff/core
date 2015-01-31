package com.googlecode.wicket.jquery.ui.samples.pages.kendo.tabs;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.kendo.ui.widget.tabs.SimpleTab;
import com.googlecode.wicket.kendo.ui.widget.tabs.TabbedPanel;

public class TabbedPanelPage extends AbstractTabsPage
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(TabbedPanelPage.class);

	public TabbedPanelPage()
	{
		Options options = new Options();
		options.set("collapsible", true);

		this.add(new TabbedPanel("tabs", this.newTabList(), options));
	}

	private List<ITab> newTabList()
	{
		List<ITab> tabs = new ArrayList<ITab>();

		// tab #1 //
		tabs.add(new SimpleTab(Model.of("Tab #1"), Model.of("my content")));

		// tab #2 //
		tabs.add(new AbstractTab(Model.of("Tab #2")) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer getPanel(String panelId)
			{
				return new Fragment(panelId, "panel-1", TabbedPanelPage.this);
			}
		});

		// tab #3 //
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

				return new Fragment(panelId, "panel-2", TabbedPanelPage.this);
			}
		});

		return tabs;
	}
}
