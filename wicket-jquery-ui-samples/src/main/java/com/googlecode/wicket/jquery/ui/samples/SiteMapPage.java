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

import com.googlecode.wicket.jquery.ui.samples.pages.accordion.DefaultAccordionPage;
import com.googlecode.wicket.jquery.ui.samples.pages.autocomplete.ConverterAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.pages.autocomplete.CustomAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.pages.autocomplete.DefaultAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.pages.autocomplete.RendererAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.pages.autocomplete.TemplateAutoCompletePage;
import com.googlecode.wicket.jquery.ui.samples.pages.button.AjaxButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.button.CheckButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.button.ConfirmAjaxButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.button.ConfirmButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.button.DefaultButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.button.IndicatingAjaxButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.button.RadioButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.button.SecuredButtonPage;
import com.googlecode.wicket.jquery.ui.samples.pages.calendar.CustomCalendarPage;
import com.googlecode.wicket.jquery.ui.samples.pages.calendar.DefaultCalendarPage;
import com.googlecode.wicket.jquery.ui.samples.pages.calendar.ExtendedCalendarPage;
import com.googlecode.wicket.jquery.ui.samples.pages.datepicker.AjaxDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.datepicker.DefaultDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.dialog.CustomDialogPage;
import com.googlecode.wicket.jquery.ui.samples.pages.dialog.FormDialogPage;
import com.googlecode.wicket.jquery.ui.samples.pages.dialog.FragmentDialogPage;
import com.googlecode.wicket.jquery.ui.samples.pages.dialog.InputDialogPage;
import com.googlecode.wicket.jquery.ui.samples.pages.dialog.MessageDialogPage;
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
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.combobox.CustomComboBoxPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.combobox.DefaultComboBoxPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.combobox.RendererComboBoxPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.combobox.TemplateComboBoxPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker.DateTimePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker.DefaultTimePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker.KendoDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker.PatternDatePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker.PatternDateTimePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.datetimepicker.PatternTimePickerPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.dropdown.AjaxDropDownPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.dropdown.DefaultDropDownPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.editor.DefaultEditorPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.splitter.BorderLayoutPage;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.splitter.DefaultSplitterPage;
import com.googlecode.wicket.jquery.ui.samples.pages.menu.DefaultMenuPage;
import com.googlecode.wicket.jquery.ui.samples.pages.plugins.DefaultSnippetPage;
import com.googlecode.wicket.jquery.ui.samples.pages.plugins.FontSizePage;
import com.googlecode.wicket.jquery.ui.samples.pages.plugins.OptionSnippetPage;
import com.googlecode.wicket.jquery.ui.samples.pages.plugins.WidgetSnippetPage;
import com.googlecode.wicket.jquery.ui.samples.pages.plugins.sfmenu.DefaultSfMenuPage;
import com.googlecode.wicket.jquery.ui.samples.pages.progressbar.ButtonProgressBarPage;
import com.googlecode.wicket.jquery.ui.samples.pages.progressbar.DefaultProgressBarPage;
import com.googlecode.wicket.jquery.ui.samples.pages.progressbar.SliderProgressBarPage;
import com.googlecode.wicket.jquery.ui.samples.pages.resizable.DefaultResizablePage;
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
import com.googlecode.wicket.jquery.ui.samples.pages.tabs.DefaultTabsPage;
import com.googlecode.wicket.jquery.ui.samples.pages.tabs.TabbedPanelPage;
import com.googlecode.wicket.jquery.ui.samples.pages.wizard.DefaultWizardPage;
import com.googlecode.wicket.jquery.ui.samples.pages.wizard.DynamicWizardPage;

public class SiteMapPage extends WebPage
{
	private static final long serialVersionUID = 1L;

	private static final String BASE_URL = "http://www.7thweb.net/wicket-jquery-ui/";
	private static final Pattern PATTERN = Pattern.compile("^\\./");

	@SuppressWarnings("unchecked")
	private static final List<? extends Class<?>> LIST = Arrays.asList(
			HomePage.class,
			DefaultAccordionPage.class,
			AjaxButtonPage.class,
			AjaxDatePickerPage.class,
			AjaxDropDownPage.class,
			AjaxRangeSliderPage.class,
			AjaxSliderPage.class,
			BehaviorOptionDraggablePage.class,
			BorderLayoutPage.class,
			ButtonProgressBarPage.class,
			CheckButtonPage.class,
			ColorPickerPage.class,
			ComponentDraggablePage.class,
			ComponentOptionDraggablePage.class,
			ConfirmAjaxButtonPage.class,
			ConfirmButtonPage.class,
			ContainerEffectPage.class,
			ConverterAutoCompletePage.class,
			CustomAutoCompletePage.class,
			CustomCalendarPage.class,
			CustomComboBoxPage.class,
			CustomDialogPage.class,
			DateTimePickerPage.class,
			DefaultAutoCompletePage.class,
			DefaultButtonPage.class,
			DefaultCalendarPage.class,
			DefaultComboBoxPage.class,
			DefaultDatePickerPage.class,
			DefaultDraggablePage.class,
			DefaultDropDownPage.class,
			DefaultDroppablePage.class,
			DefaultEditorPage.class,
			DefaultEffectPage.class,
			DefaultMenuPage.class,
			DefaultProgressBarPage.class,
			DefaultSelectablePage.class,
			DefaultSliderPage.class,
			DefaultSnippetPage.class,
			DefaultSplitterPage.class,
			DefaultTabsPage.class,
			DefaultTimePickerPage.class,
			DefaultWizardPage.class,
			DraggableSelectablePage.class,
			DynamicEffectPage.class,
			DynamicWizardPage.class,
			ExtendedCalendarPage.class,
			FontSizePage.class,
			FormDialogPage.class,
			FragmentDialogPage.class,
			IndicatingAjaxButtonPage.class,
			InputAjaxRangeSliderPage.class,
			InputAjaxSliderPage.class,
			InputDialogPage.class,
			InputRangeSliderPage.class,
			InputSliderPage.class,
			KendoDatePickerPage.class,
			MessageDialogPage.class,
			OptionSliderPage.class,
			OptionSnippetPage.class,
			PatternDatePickerPage.class,
			PatternDateTimePickerPage.class,
			PatternTimePickerPage.class,
			RadioButtonPage.class,
			RangeSliderPage.class,
			RendererAutoCompletePage.class,
			RendererComboBoxPage.class,
			DefaultResizablePage.class,
			SecuredButtonPage.class,
			DefaultSfMenuPage.class,
			ShoppingDroppablePage.class,
			SliderProgressBarPage.class,
			TableDraggableSelectablePage.class,
			TemplateAutoCompletePage.class,
			TemplateComboBoxPage.class,
			UserDialogPage.class,
			WidgetSnippetPage.class,
			TabbedPanelPage.class
			);

	public SiteMapPage()
	{
		this.add(new PropertyListView<SiteUrl>("urls", this.newListModel()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<SiteUrl> item)
			{
				item.add(new Label("loc"));
				item.add(new Label("lastmod", "2012-09-08"));
			}

		});
	}

	private IModel<List<SiteUrl>> newListModel()
	{
		return new LoadableDetachableModel<List<SiteUrl>>() {

			private static final long serialVersionUID = 1L;

			@Override
			@SuppressWarnings("unchecked")
			protected List<SiteUrl> load()
			{
				final List<SiteUrl> list = new ArrayList<SiteUrl>();

				for (Class<?> type : LIST)
				{
					String loc = PATTERN.matcher(SiteMapPage.this.urlFor((Class<? extends WebPage>)type, null)).replaceFirst(BASE_URL);

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

	class SiteUrl
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
