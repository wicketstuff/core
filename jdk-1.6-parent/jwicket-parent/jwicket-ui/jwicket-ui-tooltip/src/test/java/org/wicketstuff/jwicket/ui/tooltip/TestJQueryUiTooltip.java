/* Copyright (c) 2013 Martin Knopf
 * 
 * Licensed under the MIT license;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://opensource.org/licenses/MIT
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.jwicket.ui.tooltip;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.wicketstuff.jwicket.JsOption;

/**
 * 
 * @author Martin Knopf
 * 
 */
public class TestJQueryUiTooltip
{
	private Component component;
	private JQueryUiTooltip tooltip;
	private JQueryUiWidget widget;
	private WicketTester tester;

	@Before
	public void setUp() throws Exception
	{
		tester = new WicketTester();
		component = new Label("id");
		tester.startComponentInPage(component);
		widget = Mockito.mock(JQueryUiWidget.class);
		when(widget.buildJS(Mockito.anyString())).thenReturn("test JavaScript");
		tooltip = new JQueryUiTooltip(widget);
		component.add(tooltip);
	}

	@Test
	public void shouldAddDefaultCssToResponse()
	{
		IHeaderResponse response = mock(IHeaderResponse.class);

		tooltip.renderHead(component, response);

		verify(response, times(1)).render(
			Mockito.eq(CssReferenceHeaderItem.forReference(tooltip.uiTooltipCss_1_10_3)));
	}

	@Test
	public void shouldAddCustomCssToResponse()
	{
		IHeaderResponse response = mock(IHeaderResponse.class);
		CssResourceReference customCssResourceReference = new CssResourceReference(
			JQueryUiTooltip.class, "test.css");

		tooltip.addCssResource(customCssResourceReference);
		tooltip.renderHead(component, response);

		verify(response, times(1)).render(
			Mockito.eq(CssReferenceHeaderItem.forReference(customCssResourceReference)));
	}

	@SuppressWarnings("static-access")
	@Test
	public void shouldAvoidAddingCssToResponseMultipleTimes()
	{
		IHeaderResponse response = mock(IHeaderResponse.class);
		CssResourceReference customCssResourceReference = new CssResourceReference(
			JQueryUiTooltip.class, "test.css");

		tooltip.renderHead(component, response);
		tooltip.addCssResource(customCssResourceReference);
		tooltip.renderHead(component, response);

		verify(response, times(1)).render(
			Mockito.eq(CssReferenceHeaderItem.forReference(tooltip.uiTooltipCss_1_10_3)));
	}

	@SuppressWarnings("static-access")
	@Test
	public void shouldNotAddCssToResponse()
	{
		IHeaderResponse response = mock(IHeaderResponse.class);
		CssResourceReference customCssResourceReference = new CssResourceReference(
			JQueryUiTooltip.class, "test.css");

		tooltip.addCssResource(customCssResourceReference);
		tooltip.withoutCss();
		tooltip.renderHead(component, response);

		verify(response, never()).render(
			CssReferenceHeaderItem.forReference(tooltip.uiTooltipCss_1_10_3));
	}

	@Test
	public void shouldUseDefaultContentWhenNoContentProvided()
	{
		verify(widget).setOption("content", "function(){" /**/
			+ "var title=$(this).attr('title');" /**/
			+ "if(typeof title!=='undefined' && title!==false){return title;}" /**/
			+ "else{return $(this).attr('data-tooltip');}" /**/
			+ "}");
	}

	@Test
	public void shouldSurroundContentOptionWithSingleQuotes()
	{
		tooltip.setContent("<h1>tooltip content</h1>");

		verify(widget).setOption("content", "'<h1>tooltip content</h1>'");
	}

	@Test
	public void shouldCreateMarkupGetterForContentOption()
	{
		tooltip.setContent(component);

		verify(widget).setOption("content", "$('#" + component.getMarkupId() + "').html()");
	}

	@Test
	public void shouldConvertDisabledOptionToString()
	{
		tooltip.setDisabled(true);

		verify(widget).setOption("disabled", "true");

		tooltip.setDisabled(false);

		verify(widget).setOption("disabled", "false");
	}

	@Test
	public void shouldConvertHideOptionFromBooleanToString()
	{
		tooltip.setHide(true);

		verify(widget).setOption("hide", "true");

		tooltip.setHide(false);

		verify(widget).setOption("hide", "false");
	}

	@Test
	public void shouldConvertHideOptionFromIntToString()
	{
		tooltip.setHide(5);

		verify(widget).setOption("hide", "5");
	}

	@Test
	public void shouldSurroundHideOptionWithSingleQuotes()
	{
		tooltip.setHide("slideUp");

		verify(widget).setOption("hide", "'slideUp'");
	}

	@Test
	public void shouldConvertHideOptionFromJsOptionsToString()
	{
		JsOption effect = new JsOption("effect", "'explode'");
		JsOption duration = new JsOption("duration", "1000");
		tooltip.setHide(effect, duration);

		verify(widget).setOption("hide", "{effect:'explode',duration:1000}");
	}

	@Test
	public void shouldSurroundItemsOptionWithSingleQuotes()
	{
		tooltip.setItems(".someItem");

		verify(widget).setOption("items", "'.someItem'");
	}

	@Test
	public void shouldUseMarkupIdAsDefaultItemsOption()
	{
		tooltip.getJsBuilder();

		verify(widget, atLeastOnce()).setOption("items", "'#" + component.getMarkupId() + "'");
	}

	@Test
	public void shouldUseDocumentAsDefaultItemsOptionWhenAddedToPage()
	{
		tester = new WicketTester();
		TestPage page = new TestPage();
		tester.startPage(page);
		JQueryUiTooltip tooltip = new JQueryUiTooltip(widget);
		page.add(tooltip);

		tooltip.getJsBuilder();

		verify(widget, atLeastOnce()).setOption("items", "document");
	}

	@Test
	public void shouldConvertPositionOptionFromJsOptionsToString()
	{
		JsOption my = new JsOption("my", "'left+15 center'");
		JsOption at = new JsOption("at", "'right center'");
		tooltip.setPosition(my, at);

		verify(widget).setOption("position", "{my:'left+15 center',at:'right center'}");
	}

	@Test
	public void shouldConvertShowOptionFromBooleanToString()
	{
		tooltip.setHide(true);

		verify(widget).setOption("hide", "true");

		tooltip.setHide(false);

		verify(widget).setOption("hide", "false");
	}

	@Test
	public void shouldConvertShowOptionFromIntToString()
	{
		tooltip.setHide(5);

		verify(widget).setOption("hide", "5");
	}

	@Test
	public void shouldSurroundShowOptionWithSingleQuotes()
	{
		tooltip.setHide("slideUp");

		verify(widget).setOption("hide", "'slideUp'");
	}

	@Test
	public void shouldonvertShowOptionFromJsOptionsToString()
	{
		JsOption effect = new JsOption("effect", "'explode'");
		JsOption duration = new JsOption("duration", "1000");
		tooltip.setHide(effect, duration);

		verify(widget).setOption("hide", "{effect:'explode',duration:1000}");
	}

	@Test
	public void shouldSurroundTooltipClassOptionWithSingleQuotes()
	{
		tooltip.setTooltipClass(".someClass");

		verify(widget).setOption("tooltipClass", "'.someClass'");
	}

	@Test
	public void shouldConvertTrackOptionToString()
	{
		tooltip.setTrack(true);

		verify(widget).setOption("track", "true");

		tooltip.setTrack(false);

		verify(widget).setOption("track", "false");
	}

	@Test
	public void shouldBuildTheJQuerySelector()
	{
		IHeaderResponse response = mock(IHeaderResponse.class);

		tooltip.renderHead(component, response);

		verify(widget).buildJS("'#" + component.getMarkupId() + "'");
	}

}
