/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins.sfmenu;

import java.util.List;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.plugins.sfmenu.ISfMenuItem;
import com.googlecode.wicket.jquery.ui.plugins.sfmenu.SfMenu;
import com.googlecode.wicket.jquery.ui.plugins.sfmenu.SfMenuItem;
import com.googlecode.wicket.jquery.ui.samples.HomePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.accordion.DefaultAccordionPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.button.DefaultButtonPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.datepicker.DefaultDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.editor.DefaultEditorPage;

public class VerticalSfMenuPage extends AbstractSfMenuPage
{
	private static final long serialVersionUID = 1L;

	static List<ISfMenuItem> newSfMenuItemList()
	{
		List<ISfMenuItem> list = Generics.newArrayList();

		list.add(new SfMenuItem("Home", HomePage.class));
		list.add(new SfMenuItem("Widgets", widgetSubMenuList()));
		list.add(new SfMenuItem("Another Way Home", HomePage.class));
		list.add(new SfMenuItem("Second Sub-menu", newSubMenuList()));
		list.add(new SfMenuItem("Kendo Editor", DefaultEditorPage.class));

		return list;
	}

	static List<ISfMenuItem> newSubMenuList()
	{
		List<ISfMenuItem> list = Generics.newArrayList();

		list.add(new SfMenuItem("Sub-menu #1", HomePage.class));
		list.add(new SfMenuItem("Sub-menu #2", HomePage.class));
		list.add(new SfMenuItem("Sub-menu #3", HomePage.class));

		return list;
	}

	static List<ISfMenuItem> widgetSubMenuList()
	{
		List<ISfMenuItem> list = Generics.newArrayList();

		list.add(new SfMenuItem("Accordion", DefaultAccordionPage.class));
		list.add(new SfMenuItem("Button", DefaultButtonPage.class));
		list.add(new SfMenuItem("Datepicker", DefaultDatePickerPage.class));

		return list;
	}


	public VerticalSfMenuPage()
	{
		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));

		// Menu //
		this.add(new SfMenu("menu", VerticalSfMenuPage.newSfMenuItemList(), true));
	}
}
