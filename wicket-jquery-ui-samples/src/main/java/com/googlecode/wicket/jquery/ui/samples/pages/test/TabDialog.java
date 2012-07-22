package com.googlecode.wicket.jquery.ui.samples.pages.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.dialog.AbstractDialog;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;

public abstract class TabDialog extends AbstractDialog<String>
{
	private static final long serialVersionUID = 1L;
	
	public TabDialog(String id, String title, Model<String> model)
	{
		super(id, title, model, true);
		
		this.add(new TabbedPanel("tabs", this.newTabList()));
	}
	
	@Override
	public boolean isResizable()
	{
		return true;
	}

	@Override
	protected List<String> getButtons()
	{
		return Arrays.asList(BTN_OK, BTN_CANCEL);
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
				return new Fragment(panelId, "panel-1", TabDialog.this);
			}
		});
		
//		// tab #2 //
//		tabs.add(new AbstractTab(new Model<String>("My Tab 2")) {
//
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public WebMarkupContainer getPanel(String panelId)
//			{
//				return new Fragment(panelId, "panel-2", TabDialog.this);
//			}
//		});

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

				return new Fragment(panelId, "panel-2", TabDialog.this);
			}
		});
		
		return tabs;
	}
	
}
