/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.wicket.markup.MarkupType;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.util.lang.Generics;
import org.wicketstuff.jquery.ui.samples.jqueryui.accordion.AccordionPanelPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.accordion.DefaultAccordionPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.autocomplete.ConverterAutoCompletePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.autocomplete.CustomAutoCompletePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.autocomplete.DefaultAutoCompletePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.autocomplete.RendererAutoCompletePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.autocomplete.TemplateAutoCompletePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.button.AjaxButtonPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.button.AjaxSplitButtonPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.button.CheckButtonPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.button.ConfirmAjaxButtonPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.button.ConfirmButtonPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.button.DefaultButtonPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.button.IndicatingAjaxButtonPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.button.RadioButtonPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.button.SecuredButtonPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.button.SplitButtonPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.calendar.CustomCalendarPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.calendar.DefaultCalendarPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.calendar.ExtendedCalendarPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.calendar.ObjectCalendarPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.calendar6.CustomCalendar6Page;
import org.wicketstuff.jquery.ui.samples.jqueryui.calendar6.DefaultCalendar6Page;
import org.wicketstuff.jquery.ui.samples.jqueryui.calendar6.ExtendedCalendar6Page;
import org.wicketstuff.jquery.ui.samples.jqueryui.calendar6.ObjectCalendar6Page;
import org.wicketstuff.jquery.ui.samples.jqueryui.datepicker.AjaxDatePickerPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.datepicker.DefaultDatePickerPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.dialog.CustomDialogPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.dialog.FormDialogPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.dialog.FragmentDialogPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.dialog.InputDialogPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.dialog.MessageDialogPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.dialog.UserDialogPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.draggable.BehaviorOptionDraggablePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.draggable.ComponentDraggablePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.draggable.ComponentOptionDraggablePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.draggable.DefaultDraggablePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.droppable.DefaultDroppablePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.droppable.ShoppingDroppablePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.effect.ContainerEffectPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.effect.DefaultEffectPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.effect.DynamicEffectPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.menu.ContextMenuPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.menu.DefaultMenuPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.plugins.DefaultSnippetPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.plugins.EmoticonsPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.plugins.FontSizePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.plugins.OptionSnippetPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.plugins.WidgetSnippetPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.plugins.sfmenu.DefaultSfMenuPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.plugins.sfmenu.VerticalSfMenuPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.plugins.wysiwyg.WysiwygEditorPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.progressbar.ButtonProgressBarPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.progressbar.DefaultProgressBarPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.progressbar.SliderProgressBarPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.resizable.DefaultResizablePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.resizable.ResizablePanelPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.selectable.DefaultSelectablePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.selectable.DraggableSelectablePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.selectable.TableDraggableSelectablePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.slider.AjaxRangeSliderPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.slider.AjaxSliderPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.slider.ColorPickerPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.slider.DefaultSliderPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.slider.InputAjaxRangeSliderPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.slider.InputAjaxSliderPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.slider.InputRangeSliderPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.slider.InputSliderPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.slider.OptionSliderPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.slider.RangeSliderPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.sortable.ConnectSortablePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.sortable.CustomSortablePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.sortable.DefaultSortablePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.sortable.SelectableSortablePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.spinner.CultureSpinnerPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.spinner.DefaultSpinnerPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.tabs.AdvancedTabsPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.tabs.DefaultTabsPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.tabs.TabbedPanelPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.test.PalettePage;
import org.wicketstuff.jquery.ui.samples.jqueryui.test.TestPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.tooltip.CustomTooltipPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.tooltip.DefaultTooltipPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.wizard.DefaultWizardPage;
import org.wicketstuff.jquery.ui.samples.jqueryui.wizard.DynamicWizardPage;
import org.wicketstuff.jquery.ui.samples.kendoui.accordion.KendoAccordionPage;
import org.wicketstuff.jquery.ui.samples.kendoui.accordion.KendoAccordionPanelPage;
import org.wicketstuff.jquery.ui.samples.kendoui.autocomplete.KendoAutoCompletePage;
import org.wicketstuff.jquery.ui.samples.kendoui.autocomplete.KendoConverterAutoCompletePage;
import org.wicketstuff.jquery.ui.samples.kendoui.autocomplete.KendoCustomAutoCompletePage;
import org.wicketstuff.jquery.ui.samples.kendoui.autocomplete.KendoRendererAutoCompletePage;
import org.wicketstuff.jquery.ui.samples.kendoui.autocomplete.KendoTemplateAutoCompletePage;
import org.wicketstuff.jquery.ui.samples.kendoui.button.IndicatingButtonPage;
import org.wicketstuff.jquery.ui.samples.kendoui.button.KendoButtonPage;
import org.wicketstuff.jquery.ui.samples.kendoui.combobox.CustomComboBoxPage;
import org.wicketstuff.jquery.ui.samples.kendoui.combobox.DefaultComboBoxPage;
import org.wicketstuff.jquery.ui.samples.kendoui.combobox.RendererComboBoxPage;
import org.wicketstuff.jquery.ui.samples.kendoui.combobox.TemplateComboBoxPage;
import org.wicketstuff.jquery.ui.samples.kendoui.console.DefaultConsolePage;
import org.wicketstuff.jquery.ui.samples.kendoui.console.FeedbackConsolePage;
import org.wicketstuff.jquery.ui.samples.kendoui.datatable.CommandsDataTablePage;
import org.wicketstuff.jquery.ui.samples.kendoui.datatable.DefaultDataTablePage;
import org.wicketstuff.jquery.ui.samples.kendoui.datatable.InfiniteDataTablePage;
import org.wicketstuff.jquery.ui.samples.kendoui.datetimepicker.AjaxDateTimePickerPage;
import org.wicketstuff.jquery.ui.samples.kendoui.datetimepicker.AjaxTimePickerPage;
import org.wicketstuff.jquery.ui.samples.kendoui.datetimepicker.KendoDatePickerPage;
import org.wicketstuff.jquery.ui.samples.kendoui.datetimepicker.KendoDateTimePickerPage;
import org.wicketstuff.jquery.ui.samples.kendoui.datetimepicker.KendoTimePickerPage;
import org.wicketstuff.jquery.ui.samples.kendoui.datetimepicker.LocaleDatePickerPage;
import org.wicketstuff.jquery.ui.samples.kendoui.datetimepicker.LocaleDateTimePickerPage;
import org.wicketstuff.jquery.ui.samples.kendoui.datetimepicker.LocaleTimePickerPage;
import org.wicketstuff.jquery.ui.samples.kendoui.datetimepicker.PatternDatePickerPage;
import org.wicketstuff.jquery.ui.samples.kendoui.datetimepicker.PatternDateTimePickerPage;
import org.wicketstuff.jquery.ui.samples.kendoui.datetimepicker.PatternTimePickerPage;
import org.wicketstuff.jquery.ui.samples.kendoui.dragdrop.BehaviorDragDropPage;
import org.wicketstuff.jquery.ui.samples.kendoui.dragdrop.ComponentDragDropPage;
import org.wicketstuff.jquery.ui.samples.kendoui.dropdown.AjaxDropDownPage;
import org.wicketstuff.jquery.ui.samples.kendoui.dropdown.DefaultDropDownPage;
import org.wicketstuff.jquery.ui.samples.kendoui.editor.DefaultEditorPage;
import org.wicketstuff.jquery.ui.samples.kendoui.menu.KendoContextMenuPage;
import org.wicketstuff.jquery.ui.samples.kendoui.menu.KendoMenuPage;
import org.wicketstuff.jquery.ui.samples.kendoui.multiselect.DefaultMultiSelectPage;
import org.wicketstuff.jquery.ui.samples.kendoui.multiselect.LazyMultiSelectPage;
import org.wicketstuff.jquery.ui.samples.kendoui.notification.DefaultNotificationPage;
import org.wicketstuff.jquery.ui.samples.kendoui.notification.FeedbackPanelPage;
import org.wicketstuff.jquery.ui.samples.kendoui.progressbar.KendoProgressBarPage;
import org.wicketstuff.jquery.ui.samples.kendoui.radio.DefaultRadioPage;
import org.wicketstuff.jquery.ui.samples.kendoui.scheduler.DefaultSchedulerPage;
import org.wicketstuff.jquery.ui.samples.kendoui.scheduler.MultipleResourceSchedulerPage;
import org.wicketstuff.jquery.ui.samples.kendoui.scheduler.SingleResourceSchedulerPage;
import org.wicketstuff.jquery.ui.samples.kendoui.splitter.BorderLayoutPage;
import org.wicketstuff.jquery.ui.samples.kendoui.splitter.DefaultSplitterPage;
import org.wicketstuff.jquery.ui.samples.kendoui.tabs.KendoTabsPage;
import org.wicketstuff.jquery.ui.samples.kendoui.tooltip.DefaultKendoTooltipPage;
import org.wicketstuff.jquery.ui.samples.kendoui.window.ActionWindowPage;
import org.wicketstuff.jquery.ui.samples.kendoui.window.DefaultWindowPage;
import org.wicketstuff.jquery.ui.samples.kendoui.window.InputWindowPage;
import org.wicketstuff.jquery.ui.samples.kendoui.window.MessageWindowPage;

public class SiteMapPage extends WebPage
{
	private static final long serialVersionUID = 1L;

	private static final String BASE_URL = "http://www.7thweb.net/wicket-jquery-ui/";
	private static final Pattern PATTERN = Pattern.compile("^\\./");
	private static final String LASTMOD = "2016-01-03";

	private static final List<? extends Class<? extends WebPage>> LIST = Arrays.asList(
			AccordionPanelPage.class,
			ActionWindowPage.class,
			AdvancedTabsPage.class,
			AjaxButtonPage.class,
			AjaxDatePickerPage.class,
			AjaxDateTimePickerPage.class,
			AjaxDropDownPage.class,
			AjaxRangeSliderPage.class,
			AjaxSliderPage.class,
			AjaxSplitButtonPage.class,
			AjaxTimePickerPage.class,
			BehaviorDragDropPage.class,
			BehaviorOptionDraggablePage.class,
			BorderLayoutPage.class,
			ButtonProgressBarPage.class,
			CheckButtonPage.class,
			ColorPickerPage.class,
			CommandsDataTablePage.class,
			ComponentDragDropPage.class,
			ComponentDraggablePage.class,
			ComponentOptionDraggablePage.class,
			ConfirmAjaxButtonPage.class,
			ConfirmButtonPage.class,
			ConnectSortablePage.class,
			ContainerEffectPage.class,
			ContextMenuPage.class,
			ConverterAutoCompletePage.class,
			CustomAutoCompletePage.class,
			CustomCalendarPage.class,
			CustomCalendar6Page.class,
			CustomComboBoxPage.class,
			CustomDialogPage.class,
			CustomSortablePage.class,
			CustomTooltipPage.class,
			KendoDateTimePickerPage.class,
			DefaultAccordionPage.class,
			DefaultAutoCompletePage.class,
			DefaultButtonPage.class,
			DefaultCalendarPage.class,
			DefaultCalendar6Page.class,
			DefaultComboBoxPage.class,
			DefaultConsolePage.class,
			DefaultDataTablePage.class,
			DefaultDatePickerPage.class,
			DefaultDraggablePage.class,
			DefaultDropDownPage.class,
			DefaultDroppablePage.class,
			DefaultEditorPage.class,
			DefaultEffectPage.class,
			DefaultKendoTooltipPage.class,
			DefaultMenuPage.class,
			DefaultMultiSelectPage.class,
			DefaultNotificationPage.class,
			DefaultProgressBarPage.class,
			DefaultRadioPage.class,
			DefaultResizablePage.class,
			DefaultSchedulerPage.class,
			DefaultSelectablePage.class,
			DefaultSfMenuPage.class,
			DefaultSliderPage.class,
			DefaultSnippetPage.class,
			DefaultSortablePage.class,
			DefaultSpinnerPage.class,
			DefaultSplitterPage.class,
			DefaultTabsPage.class,
			KendoTimePickerPage.class,
			DefaultTooltipPage.class,
			DefaultWindowPage.class,
			DefaultWizardPage.class,
			DraggableSelectablePage.class,
			DynamicEffectPage.class,
			DynamicWizardPage.class,
			EmoticonsPage.class,
			ExtendedCalendarPage.class,
			ExtendedCalendar6Page.class,
			FeedbackConsolePage.class,
			FeedbackPanelPage.class,
			FontSizePage.class,
			FormDialogPage.class,
			FragmentDialogPage.class,
			IndicatingAjaxButtonPage.class,
			IndicatingButtonPage.class,
			InfiniteDataTablePage.class,
			InputAjaxRangeSliderPage.class,
			InputAjaxSliderPage.class,
			InputDialogPage.class,
			InputRangeSliderPage.class,
			InputSliderPage.class,
			InputWindowPage.class,
			KendoAccordionPage.class,
			KendoAccordionPanelPage.class,
			KendoAutoCompletePage.class,
			KendoButtonPage.class,
			KendoContextMenuPage.class,
			KendoConverterAutoCompletePage.class,
			KendoCustomAutoCompletePage.class,
			KendoDatePickerPage.class,
			KendoMenuPage.class,
			KendoProgressBarPage.class,
			KendoRendererAutoCompletePage.class,
			KendoTabsPage.class,
			KendoTemplateAutoCompletePage.class,
			LazyMultiSelectPage.class,
			LocaleDatePickerPage.class,
			LocaleDateTimePickerPage.class,
			LocaleTimePickerPage.class,
			MessageDialogPage.class,
			MessageWindowPage.class,
			MultipleResourceSchedulerPage.class,
			ObjectCalendarPage.class,
			ObjectCalendar6Page.class,
			OptionSliderPage.class,
			OptionSnippetPage.class,
			CultureSpinnerPage.class,
			PalettePage.class,
			PatternDatePickerPage.class,
			PatternDateTimePickerPage.class,
			PatternTimePickerPage.class,
			RadioButtonPage.class,
			RangeSliderPage.class,
			RendererAutoCompletePage.class,
			RendererComboBoxPage.class,
			ResizablePanelPage.class,
			SecuredButtonPage.class,
			SelectableSortablePage.class,
			ShoppingDroppablePage.class,
			SingleResourceSchedulerPage.class,
			SliderProgressBarPage.class,
			SplitButtonPage.class,
			TabbedPanelPage.class,
			TableDraggableSelectablePage.class,
			TemplateAutoCompletePage.class,
			TemplateComboBoxPage.class,
			TestPage.class,
			UserDialogPage.class,
			VerticalSfMenuPage.class,
			WidgetSnippetPage.class,
			WysiwygEditorPage.class
			);

	public SiteMapPage()
	{
		this.add(new PropertyListView<SiteUrl>("urls", this.newListModel()) { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<SiteUrl> item)
			{
				item.add(new Label("loc"));
				item.add(new Label("lastmod", LASTMOD));
			}
		});
	}

	private IModel<List<SiteUrl>> newListModel()
	{
		return new LoadableDetachableModel<List<SiteUrl>>() { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected List<SiteUrl> load()
			{
				final List<SiteUrl> list = Generics.newArrayList();

				for (Class<? extends WebPage> type : LIST)
				{
					String loc = PATTERN.matcher(urlFor(type, null)).replaceFirst(BASE_URL);

					if (loc.endsWith("/."))
					{
						loc = loc.replace("/.", "");
					}

					list.add(new SiteUrl(loc));
				}

				return list;
			}
		};
	}

	@Override
	public MarkupType getMarkupType()
	{
		return new MarkupType("html", "text/xml");
	}

	static class SiteUrl
	{
		private String loc;

		public SiteUrl(String loc)
		{
			this.loc = loc;
		}

		public String getLoc()
		{
			return this.loc;
		}
	}
}
