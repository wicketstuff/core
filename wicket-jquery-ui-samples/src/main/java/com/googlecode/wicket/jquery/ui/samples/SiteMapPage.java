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

import com.googlecode.wicket.jquery.ui.samples.jqueryui.accordion.AccordionPanelPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.accordion.DefaultAccordionPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.autocomplete.ConverterAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.autocomplete.CustomAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.autocomplete.DefaultAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.autocomplete.RendererAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.autocomplete.TemplateAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.button.AjaxButtonPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.button.AjaxSplitButtonPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.button.CheckButtonPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.button.ConfirmAjaxButtonPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.button.ConfirmButtonPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.button.DefaultButtonPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.button.IndicatingAjaxButtonPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.button.RadioButtonPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.button.SecuredButtonPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.button.SplitButtonPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.calendar.CustomCalendarPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.calendar.DefaultCalendarPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.calendar.ExtendedCalendarPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.calendar.ObjectCalendarPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.datepicker.AjaxDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.datepicker.DefaultDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.dialog.CustomDialogPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.dialog.FormDialogPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.dialog.FragmentDialogPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.dialog.InputDialogPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.dialog.MessageDialogPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.dialog.UploadDialogPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.dialog.UserDialogPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.draggable.BehaviorOptionDraggablePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.draggable.ComponentDraggablePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.draggable.ComponentOptionDraggablePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.draggable.DefaultDraggablePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.droppable.DefaultDroppablePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.droppable.ShoppingDroppablePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.effect.ContainerEffectPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.effect.DefaultEffectPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.effect.DynamicEffectPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.menu.ContextMenuPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.menu.DefaultMenuPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins.DefaultSnippetPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins.EmoticonsPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins.FontSizePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins.OptionSnippetPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins.WidgetSnippetPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins.datepicker.RangeDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins.datepicker.RangeDatePickerTextFieldPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins.sfmenu.DefaultSfMenuPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins.sfmenu.VerticalSfMenuPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins.wysiwyg.WysiwygEditorPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.progressbar.ButtonProgressBarPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.progressbar.DefaultProgressBarPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.progressbar.SliderProgressBarPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.resizable.DefaultResizablePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.resizable.ResizablePanelPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.selectable.DefaultSelectablePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.selectable.DraggableSelectablePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.selectable.TableDraggableSelectablePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.slider.AjaxRangeSliderPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.slider.AjaxSliderPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.slider.ColorPickerPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.slider.DefaultSliderPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.slider.InputAjaxRangeSliderPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.slider.InputAjaxSliderPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.slider.InputRangeSliderPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.slider.InputSliderPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.slider.OptionSliderPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.slider.RangeSliderPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.sortable.ConnectSortablePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.sortable.CustomSortablePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.sortable.DefaultSortablePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.sortable.SelectableSortablePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.spinner.CultureSpinnerPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.spinner.DefaultSpinnerPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.tabs.AdvancedTabsPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.tabs.DefaultTabsPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.tabs.TabbedPanelPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.test.PalettePage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.test.TestPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.test.editor.EditorPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.tooltip.CustomTooltipPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.tooltip.DefaultTooltipPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.wizard.DefaultWizardPage;
import com.googlecode.wicket.jquery.ui.samples.jqueryui.wizard.DynamicWizardPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.accordion.KendoAccordionPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.accordion.KendoAccordionPanelPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.autocomplete.KendoAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.autocomplete.KendoConverterAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.autocomplete.KendoCustomAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.autocomplete.KendoRendererAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.autocomplete.KendoTemplateAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.button.IndicatingButtonPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.button.KendoButtonPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.combobox.CustomComboBoxPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.combobox.DefaultComboBoxPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.combobox.RendererComboBoxPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.combobox.TemplateComboBoxPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.console.DefaultConsolePage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.console.FeedbackConsolePage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.datatable.CommandsDataTablePage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.datatable.DefaultDataTablePage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.datatable.InfiniteDataTablePage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.datetimepicker.AjaxDateTimePickerPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.datetimepicker.AjaxTimePickerPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.datetimepicker.KendoDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.datetimepicker.KendoDateTimePickerPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.datetimepicker.KendoTimePickerPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.datetimepicker.LocaleDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.datetimepicker.LocaleDateTimePickerPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.datetimepicker.LocaleTimePickerPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.datetimepicker.PatternDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.datetimepicker.PatternDateTimePickerPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.datetimepicker.PatternTimePickerPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.dragdrop.BehaviorDragDropPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.dragdrop.ComponentDragDropPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.dropdown.AjaxDropDownPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.dropdown.DefaultDropDownPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.editor.DefaultEditorPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.menu.KendoContextMenuPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.menu.KendoMenuPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.multiselect.DefaultMultiSelectPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.multiselect.LazyMultiSelectPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.notification.DefaultNotificationPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.notification.FeedbackPanelPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.progressbar.KendoProgressBarPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.radio.DefaultRadioPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.scheduler.DefaultSchedulerPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.scheduler.MultipleResourceSchedulerPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.scheduler.SingleResourceSchedulerPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.splitter.BorderLayoutPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.splitter.DefaultSplitterPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.tabs.KendoTabsPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.tooltip.DefaultKendoTooltipPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.window.ActionWindowPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.window.DefaultWindowPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.window.InputWindowPage;
import com.googlecode.wicket.jquery.ui.samples.kendoui.window.MessageWindowPage;

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
