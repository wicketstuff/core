package com.googlecode.wicket.jquery.ui.samples.pages.tabs;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;

public class WidgetTabsPage extends AbstractTabsPage
{
	private static final long serialVersionUID = 1L;
	
	public WidgetTabsPage()
	{
		Options options = new Options();
		options.set("collapsible", true);

		this.add(new TabbedPanel("tabs", this.newTabList(), options));
	}

	private List<ITab> newTabList()
	{
		List<ITab> tabs = new ArrayList<ITab>();

		// tab #1 //
		tabs.add(new AbstractTab(new Model<String>("My Tab")) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer getPanel(String panelId)
			{
				return new Fragment(panelId, "panel-1", WidgetTabsPage.this);
			}
		});

		// tab #2 //
		tabs.add(new AjaxTab(new Model<String>("Tab (ajax)")) {

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
					error(e.getMessage());
				}

				return new Fragment(panelId, "panel-2", WidgetTabsPage.this);
			}
		});
		
		return tabs;
	}
}
