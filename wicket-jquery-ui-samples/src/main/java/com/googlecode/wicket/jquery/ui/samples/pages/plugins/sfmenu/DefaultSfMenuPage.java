package com.googlecode.wicket.jquery.ui.samples.pages.plugins.sfmenu;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.plugins.sfmenu.ISfMenuItem;
import com.googlecode.wicket.jquery.ui.plugins.sfmenu.SfMenu;
import com.googlecode.wicket.jquery.ui.plugins.sfmenu.SfMenuItem;
import com.googlecode.wicket.jquery.ui.samples.HomePage;
import com.googlecode.wicket.jquery.ui.samples.pages.accordion.DefaultAccordionPage;
import com.googlecode.wicket.jquery.ui.samples.pages.button.DefaultButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.datepicker.DefaultDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.editor.DefaultEditorPage;

public class DefaultSfMenuPage extends AbstractSfMenuPage
{
	private static final long serialVersionUID = 1L;

	static List<ISfMenuItem> newSfMenuItemList()
	{
		List<ISfMenuItem> list = new ArrayList<ISfMenuItem>();

		list.add(new SfMenuItem("Home", HomePage.class));
		list.add(new SfMenuItem("Widgets", widgetSubMenuList()));
		list.add(new SfMenuItem("External Links", externalPagesList()));
		list.add(new SfMenuItem("Menu item, with sub-menu", newSubMenuList()));
		list.add(new SfMenuItem("Kendo Editor", DefaultEditorPage.class));

		return list;
	}

	static List<ISfMenuItem> newSubMenuList()
	{
		List<ISfMenuItem> list = new ArrayList<ISfMenuItem>();

		list.add(new SfMenuItem("Sub-menu #1", HomePage.class));
		SfMenuItem subMenuItem2 = new SfMenuItem("Disabled Menu Item", HomePage.class);
		subMenuItem2.setEnabled(false);
		list.add(subMenuItem2);
		list.add(new SfMenuItem("Sub-menu #3", HomePage.class));

		return list;
	}

	static List<ISfMenuItem> widgetSubMenuList()
	{
		List<ISfMenuItem> list = new ArrayList<ISfMenuItem>();

		list.add(new SfMenuItem("Accordion", DefaultAccordionPage.class));
		list.add(new SfMenuItem("Button", DefaultButtonPage.class));
		list.add(new SfMenuItem("Datepicker", DefaultDatePickerPage.class));

		return list;
	}
	
	static List<ISfMenuItem> externalPagesList()
	{
		List<ISfMenuItem> list = new ArrayList<ISfMenuItem>();
		
		list.add(new SfMenuItem("External Link to Google (new Window)", "http://www.google.com/", true));
		list.add(new SfMenuItem("External Link to Yahoo (this Window)", "http://www.yahoo.com/"));
		
		return list;
	}


	public DefaultSfMenuPage()
	{
		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));

		// Menu //
		this.add(new SfMenu("menu", DefaultSfMenuPage.newSfMenuItemList()));
	}
}
