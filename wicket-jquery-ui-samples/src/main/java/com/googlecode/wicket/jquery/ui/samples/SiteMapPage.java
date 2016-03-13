package com.googlecode.wicket.jquery.ui.samples;

import java.util.ArrayList;
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

import com.googlecode.wicket.jquery.ui.samples.pages.accordion.AccordionPanelPage;
import com.googlecode.wicket.jquery.ui.samples.pages.accordion.DefaultAccordionPage;
import com.googlecode.wicket.jquery.ui.samples.pages.autocomplete.ConverterAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.pages.autocomplete.CustomAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.pages.autocomplete.DefaultAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.pages.autocomplete.RendererAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.pages.autocomplete.TemplateAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.pages.button.AjaxButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.button.AjaxSplitButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.button.CheckButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.button.ConfirmAjaxButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.button.ConfirmButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.button.DefaultButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.button.IndicatingAjaxButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.button.RadioButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.button.SecuredButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.button.SplitButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.calendar.CustomCalendarPage;
import com.googlecode.wicket.jquery.ui.samples.pages.calendar.DefaultCalendarPage;
import com.googlecode.wicket.jquery.ui.samples.pages.calendar.ExtendedCalendarPage;
import com.googlecode.wicket.jquery.ui.samples.pages.calendar.ObjectCalendarPage;
import com.googlecode.wicket.jquery.ui.samples.pages.datepicker.AjaxDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.datepicker.DefaultDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.dialog.CustomDialogPage;
import com.googlecode.wicket.jquery.ui.samples.pages.dialog.FormDialogPage;
import com.googlecode.wicket.jquery.ui.samples.pages.dialog.FragmentDialogPage;
import com.googlecode.wicket.jquery.ui.samples.pages.dialog.InputDialogPage;
import com.googlecode.wicket.jquery.ui.samples.pages.dialog.MessageDialogPage;
import com.googlecode.wicket.jquery.ui.samples.pages.dialog.UploadDialogPage;
import com.googlecode.wicket.jquery.ui.samples.pages.dialog.UserDialogPage;
import com.googlecode.wicket.jquery.ui.samples.pages.draggable.BehaviorOptionDraggablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.draggable.ComponentDraggablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.draggable.ComponentOptionDraggablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.draggable.DefaultDraggablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.droppable.DefaultDroppablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.droppable.ShoppingDroppablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.effect.ContainerEffectPage;
import com.googlecode.wicket.jquery.ui.samples.pages.effect.DefaultEffectPage;
import com.googlecode.wicket.jquery.ui.samples.pages.effect.DynamicEffectPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.accordion.KendoAccordionPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.accordion.KendoAccordionPanelPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.autocomplete.KendoAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.autocomplete.KendoConverterAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.autocomplete.KendoCustomAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.autocomplete.KendoRendererAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.autocomplete.KendoTemplateAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.button.IndicatingButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.button.KendoButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.combobox.CustomComboBoxPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.combobox.DefaultComboBoxPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.combobox.RendererComboBoxPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.combobox.TemplateComboBoxPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.console.DefaultConsolePage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.console.FeedbackConsolePage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datatable.CommandsDataTablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datatable.DefaultDataTablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datatable.InfiniteDataTablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker.AjaxDateTimePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker.AjaxTimePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker.KendoDateTimePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker.KendoTimePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker.KendoDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker.LocaleDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker.LocaleDateTimePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker.LocaleTimePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker.PatternDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker.PatternDateTimePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker.PatternTimePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.dragdrop.BehaviorDragDropPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.dragdrop.ComponentDragDropPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.dropdown.AjaxDropDownPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.dropdown.DefaultDropDownPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.editor.DefaultEditorPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.menu.KendoContextMenuPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.menu.KendoMenuPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.multiselect.DefaultMultiSelectPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.multiselect.LazyMultiSelectPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.notification.DefaultNotificationPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.notification.FeedbackPanelPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.progressbar.KendoProgressBarPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.radio.DefaultRadioPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.scheduler.DefaultSchedulerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.scheduler.MultipleResourceSchedulerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.scheduler.SingleResourceSchedulerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.splitter.BorderLayoutPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.splitter.DefaultSplitterPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.tabs.KendoTabsPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.tooltip.DefaultKendoTooltipPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.window.ActionWindowPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.window.DefaultWindowPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.window.InputWindowPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.window.MessageWindowPage;
import com.googlecode.wicket.jquery.ui.samples.pages.menu.ContextMenuPage;
import com.googlecode.wicket.jquery.ui.samples.pages.menu.DefaultMenuPage;
import com.googlecode.wicket.jquery.ui.samples.pages.plugins.DefaultSnippetPage;
import com.googlecode.wicket.jquery.ui.samples.pages.plugins.EmoticonsPage;
import com.googlecode.wicket.jquery.ui.samples.pages.plugins.FontSizePage;
import com.googlecode.wicket.jquery.ui.samples.pages.plugins.OptionSnippetPage;
import com.googlecode.wicket.jquery.ui.samples.pages.plugins.WidgetSnippetPage;
import com.googlecode.wicket.jquery.ui.samples.pages.plugins.datepicker.RangeDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.plugins.datepicker.RangeDatePickerTextFieldPage;
import com.googlecode.wicket.jquery.ui.samples.pages.plugins.sfmenu.DefaultSfMenuPage;
import com.googlecode.wicket.jquery.ui.samples.pages.plugins.sfmenu.VerticalSfMenuPage;
import com.googlecode.wicket.jquery.ui.samples.pages.plugins.wysiwyg.WysiwygEditorPage;
import com.googlecode.wicket.jquery.ui.samples.pages.progressbar.ButtonProgressBarPage;
import com.googlecode.wicket.jquery.ui.samples.pages.progressbar.DefaultProgressBarPage;
import com.googlecode.wicket.jquery.ui.samples.pages.progressbar.SliderProgressBarPage;
import com.googlecode.wicket.jquery.ui.samples.pages.resizable.DefaultResizablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.resizable.ResizablePanelPage;
import com.googlecode.wicket.jquery.ui.samples.pages.selectable.DefaultSelectablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.selectable.DraggableSelectablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.selectable.TableDraggableSelectablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.slider.AjaxRangeSliderPage;
import com.googlecode.wicket.jquery.ui.samples.pages.slider.AjaxSliderPage;
import com.googlecode.wicket.jquery.ui.samples.pages.slider.ColorPickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.slider.DefaultSliderPage;
import com.googlecode.wicket.jquery.ui.samples.pages.slider.InputAjaxRangeSliderPage;
import com.googlecode.wicket.jquery.ui.samples.pages.slider.InputAjaxSliderPage;
import com.googlecode.wicket.jquery.ui.samples.pages.slider.InputRangeSliderPage;
import com.googlecode.wicket.jquery.ui.samples.pages.slider.InputSliderPage;
import com.googlecode.wicket.jquery.ui.samples.pages.slider.OptionSliderPage;
import com.googlecode.wicket.jquery.ui.samples.pages.slider.RangeSliderPage;
import com.googlecode.wicket.jquery.ui.samples.pages.sortable.ConnectSortablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.sortable.CustomSortablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.sortable.DefaultSortablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.sortable.SelectableSortablePage;
import com.googlecode.wicket.jquery.ui.samples.pages.spinner.DefaultSpinnerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.spinner.CultureSpinnerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.tabs.AdvancedTabsPage;
import com.googlecode.wicket.jquery.ui.samples.pages.tabs.DefaultTabsPage;
import com.googlecode.wicket.jquery.ui.samples.pages.tabs.TabbedPanelPage;
import com.googlecode.wicket.jquery.ui.samples.pages.test.PalettePage;
import com.googlecode.wicket.jquery.ui.samples.pages.test.TestPage;
import com.googlecode.wicket.jquery.ui.samples.pages.test.editor.EditorPage;
import com.googlecode.wicket.jquery.ui.samples.pages.tooltip.CustomTooltipPage;
import com.googlecode.wicket.jquery.ui.samples.pages.tooltip.DefaultTooltipPage;
import com.googlecode.wicket.jquery.ui.samples.pages.wizard.DefaultWizardPage;
import com.googlecode.wicket.jquery.ui.samples.pages.wizard.DynamicWizardPage;

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
			CustomComboBoxPage.class,
			CustomDialogPage.class,
			CustomSortablePage.class,
			CustomTooltipPage.class,
			KendoDateTimePickerPage.class,
			DefaultAccordionPage.class,
			DefaultAutoCompletePage.class,
			DefaultButtonPage.class,
			DefaultCalendarPage.class,
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
			EditorPage.class,
			EmoticonsPage.class,
			ExtendedCalendarPage.class,
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
			OptionSliderPage.class,
			OptionSnippetPage.class,
			CultureSpinnerPage.class,
			PalettePage.class,
			PatternDatePickerPage.class,
			PatternDateTimePickerPage.class,
			PatternTimePickerPage.class,
			RadioButtonPage.class,
			RangeDatePickerPage.class,
			RangeDatePickerTextFieldPage.class,
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
			UploadDialogPage.class,
			UserDialogPage.class,
			VerticalSfMenuPage.class,
			WidgetSnippetPage.class,
			WysiwygEditorPage.class
			);

	public SiteMapPage()
	{
		this.add(new PropertyListView<SiteUrl>("urls", this.newListModel()) {

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
		return new LoadableDetachableModel<List<SiteUrl>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<SiteUrl> load()
			{
				final List<SiteUrl> list = new ArrayList<SiteUrl>();

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
