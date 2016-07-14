package com.googlecode.wicket.jquery.ui.samples;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

import com.googlecode.wicket.jquery.ui.samples.jqueryui.accordion.DefaultAccordionPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.autocomplete.DefaultAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.button.DefaultButtonPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.calendar.DefaultCalendarPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.datepicker.DefaultDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.dialog.MessageDialogPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.draggable.DefaultDraggablePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.dropdown.DefaultSelectMenuPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.droppable.DefaultDroppablePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.effect.DefaultEffectPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.menu.DefaultMenuPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins.FontSizePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins.datepicker.RangeDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins.sfmenu.DefaultSfMenuPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins.wysiwyg.WysiwygEditorPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.progressbar.ButtonProgressBarPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.resizable.DefaultResizablePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.selectable.DefaultSelectablePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.slider.DefaultSliderPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.sortable.DefaultSortablePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.spinner.DefaultSpinnerPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.tabs.DefaultTabsPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.test.TestPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.tooltip.DefaultTooltipPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.wizard.DefaultWizardPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.accordion.KendoAccordionPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.autocomplete.KendoAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.button.KendoButtonPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.combobox.DefaultComboBoxPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.console.DefaultConsolePage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.datatable.DefaultDataTablePage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.datetimepicker.KendoDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.datetimepicker.local.LocalDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.dragdrop.BehaviorDragDropPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.dropdown.DefaultDropDownPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.editor.DefaultEditorPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.menu.KendoMenuPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.multiselect.DefaultMultiSelectPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.notification.DefaultNotificationPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.progressbar.KendoProgressBarPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.radio.DefaultRadioPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.scheduler.DefaultSchedulerPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.splitter.DefaultSplitterPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.tabs.KendoTabsPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.tooltip.DefaultKendoTooltipPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.treeview.DefaultTreeViewPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.window.DefaultWindowPage;

public class SampleApplication extends WebApplication
{
	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();

		this.getResourceSettings().setThrowExceptionOnMissingResource(false);

		// SiteMap //
		this.mountPage("/sitemap.xml", SiteMapPage.class);

		// Links //
		this.mountPage("/howto", HowtoPage.class);
		this.mountPage("/about", AboutPage.class);

		// jQuery UI widgets //
		this.mountPackage("/accordion", DefaultAccordionPage.class);
		this.mountPackage("/autocomplete", DefaultAutoCompletePage.class);
		this.mountPackage("/button", DefaultButtonPage.class);
		this.mountPackage("/datepicker", DefaultDatePickerPage.class);
		this.mountPackage("/dialog", MessageDialogPage.class);
		this.mountPackage("/menu", DefaultMenuPage.class);
		this.mountPackage("/progressbar", ButtonProgressBarPage.class);
		this.mountPackage("/selectmenu", DefaultSelectMenuPage.class);
		this.mountPackage("/slider", DefaultSliderPage.class);
		this.mountPackage("/spinner", DefaultSpinnerPage.class);
		this.mountPackage("/tabs", DefaultTabsPage.class);
		this.mountPackage("/tooltip", DefaultTooltipPage.class);
		this.mountPackage("/wizard", DefaultWizardPage.class);

		// jQuery UI interactions //
		this.mountPackage("/draggable", DefaultDraggablePage.class);
		this.mountPackage("/droppable", DefaultDroppablePage.class);
		this.mountPackage("/resizable", DefaultResizablePage.class);
		this.mountPackage("/selectable", DefaultSelectablePage.class);
		this.mountPackage("/sortable", DefaultSortablePage.class);

		// jQuery UI effects //
		this.mountPackage("/effect", DefaultEffectPage.class);

		// Kendo UI //
		this.mountPackage("/kendo/accordion", KendoAccordionPage.class);
		this.mountPackage("/kendo/autocomplete", KendoAutoCompletePage.class);
		this.mountPackage("/kendo/button", KendoButtonPage.class);
		this.mountPackage("/kendo/console", DefaultConsolePage.class);
		this.mountPackage("/kendo/combobox", DefaultComboBoxPage.class);
		this.mountPackage("/kendo/datatable", DefaultDataTablePage.class);
		this.mountPackage("/kendo/datetimepicker", KendoDatePickerPage.class);
		this.mountPackage("/kendo/datetimepicker/local", LocalDatePickerPage.class);
		this.mountPackage("/kendo/dropdown", DefaultDropDownPage.class);
		this.mountPackage("/kendo/editor", DefaultEditorPage.class);
		this.mountPackage("/kendo/menu", KendoMenuPage.class);
		this.mountPackage("/kendo/multiselect", DefaultMultiSelectPage.class);
		this.mountPackage("/kendo/notification", DefaultNotificationPage.class);
		this.mountPackage("/kendo/progressbar", KendoProgressBarPage.class);
		this.mountPackage("/kendo/radio", DefaultRadioPage.class);
		this.mountPackage("/kendo/scheduler", DefaultSchedulerPage.class);
		this.mountPackage("/kendo/splitter", DefaultSplitterPage.class);
		this.mountPackage("/kendo/tabs", KendoTabsPage.class);
		this.mountPackage("/kendo/tooltip", DefaultKendoTooltipPage.class);
		this.mountPackage("/kendo/treeview", DefaultTreeViewPage.class);
		this.mountPackage("/kendo/window", DefaultWindowPage.class);

		// Kendo UI interactions //
		this.mountPackage("/kendo/draggable", BehaviorDragDropPage.class);

		// Calendar //
		this.mountPackage("/calendar", DefaultCalendarPage.class);

		// Plugins //
		this.mountPackage("/plugins", FontSizePage.class);
		this.mountPackage("/plugins/datepicker", RangeDatePickerPage.class);
		this.mountPackage("/plugins/sfmenu", DefaultSfMenuPage.class);
		this.mountPackage("/plugins/wysiwyg", WysiwygEditorPage.class);

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
