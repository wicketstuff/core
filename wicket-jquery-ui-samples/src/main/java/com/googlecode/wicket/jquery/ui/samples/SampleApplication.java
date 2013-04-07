package com.googlecode.wicket.jquery.ui.samples;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

import com.googlecode.wicket.jquery.core.resource.JQueryGlobalizeResourceReference;
import com.googlecode.wicket.jquery.core.settings.IJQueryLibrarySettings;
import com.googlecode.wicket.jquery.core.settings.JQueryLibrarySettings;
import com.googlecode.wicket.jquery.ui.samples.pages.accordion.DefaultAccordionPage;
import com.googlecode.wicket.jquery.ui.samples.pages.autocomplete.DefaultAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.pages.button.DefaultButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.calendar.DefaultCalendarPage;
import com.googlecode.wicket.jquery.ui.samples.pages.datepicker.DefaultDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.dialog.MessageDialogPage;
import com.googlecode.wicket.jquery.ui.samples.pages.draggable.DefaultDraggablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.droppable.DefaultDroppablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.effect.DefaultEffectPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.combobox.DefaultComboBoxPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker.KendoDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.dropdown.DefaultDropDownPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.editor.DefaultEditorPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.splitter.DefaultSplitterPage;
import com.googlecode.wicket.jquery.ui.samples.pages.menu.DefaultMenuPage;
import com.googlecode.wicket.jquery.ui.samples.pages.plugins.FontSizePage;
import com.googlecode.wicket.jquery.ui.samples.pages.plugins.datepicker.RangeDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.progressbar.ButtonProgressBarPage;
import com.googlecode.wicket.jquery.ui.samples.pages.resizable.DefaultResizablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.selectable.DefaultSelectablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.slider.DefaultSliderPage;
import com.googlecode.wicket.jquery.ui.samples.pages.spinner.DefaultSpinnerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.tabs.DefaultTabsPage;
import com.googlecode.wicket.jquery.ui.samples.pages.test.TestPage;
import com.googlecode.wicket.jquery.ui.samples.pages.wizard.DefaultWizardPage;

public class SampleApplication extends WebApplication
{
	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();

		this.getMarkupSettings().setStripWicketTags(true); //IMPORTANT!
		this.getResourceSettings().setThrowExceptionOnMissingResource(false);

		// jQuery Globalize Resource References //
		IJQueryLibrarySettings librarySettings = new JQueryLibrarySettings();
		librarySettings.setJQueryGlobalizeReference(JQueryGlobalizeResourceReference.get());
		this.setJavaScriptLibrarySettings(librarySettings);

		// SiteMap //
		this.mountPage("/sitemap.xml", SiteMapPage.class);

		// widgets //
		this.mountPackage("/accordion", DefaultAccordionPage.class);
		this.mountPackage("/autocomplete", DefaultAutoCompletePage.class);
		this.mountPackage("/button", DefaultButtonPage.class);
		this.mountPackage("/datepicker", DefaultDatePickerPage.class);
		this.mountPackage("/dialog", MessageDialogPage.class);
		this.mountPackage("/menu", DefaultMenuPage.class);
		this.mountPackage("/progressbar", ButtonProgressBarPage.class);
		this.mountPackage("/slider", DefaultSliderPage.class);
		this.mountPackage("/spinner", DefaultSpinnerPage.class);
		this.mountPackage("/tabs", DefaultTabsPage.class);
//		this.mountPackage("/tooltip",
		this.mountPackage("/wizard", DefaultWizardPage.class);

		// interactions //
		this.mountPackage("/draggable", DefaultDraggablePage.class);
		this.mountPackage("/droppable", DefaultDroppablePage.class);
		this.mountPackage("/resizable", DefaultResizablePage.class);
		this.mountPackage("/selectable", DefaultSelectablePage.class);

		// Effects //
		this.mountPackage("/effect", DefaultEffectPage.class);

		// Kendo //
		this.mountPackage("/kendo/editor", DefaultEditorPage.class);
		this.mountPackage("/kendo/dropdown", DefaultDropDownPage.class);
		this.mountPackage("/kendo/combobox", DefaultComboBoxPage.class);
		this.mountPackage("/kendo/datetimepicker", KendoDatePickerPage.class);
		this.mountPackage("/kendo/splitter", DefaultSplitterPage.class);

		// Calendar //
		this.mountPackage("/calendar", DefaultCalendarPage.class);

		// Plugins //
		this.mountPackage("/plugins", FontSizePage.class);
		this.mountPackage("/plugins/datepicker", RangeDatePickerPage.class);

		// Test //
		this.mountPackage("/test", TestPage.class);
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	public Session newSession(Request request, Response response)
	{
		return new SampleSession(request);
	}
}
