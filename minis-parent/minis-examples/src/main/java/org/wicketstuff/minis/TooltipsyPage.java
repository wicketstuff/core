package org.wicketstuff.minis;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.wicketstuff.minis.behavior.tooltipsy.TooltipsyBehavior;
import org.wicketstuff.minis.behavior.tooltipsy.TooltipsyOptions.Offset;
import org.wicketstuff.minis.behavior.tooltipsy.TooltipsyOptions.TooltipsyOptionsBuilder;

public class TooltipsyPage extends WebPage {

	private static final long serialVersionUID = 1L;
	private static final String STYLE_FILENAME = "TooltipsyPageStyle.css";

	public TooltipsyPage() {
		// sample 1
		Label sample1 = new Label("sample1", Model.of("Without CSS"));
		add(sample1);
		sample1.add(new TooltipsyBehavior());

		// sample 2
		Label sample2 = new Label("sample2", Model.of("Simple tooltip"));
		add(sample2);
		TooltipsyOptionsBuilder builder2 = new TooltipsyOptionsBuilder();
		builder2.setCss(
				"'padding': '10px'," +
				"'max-width': '200px'," +
				"'color': '#303030'," +
				"'background-color': '#f5f5b5'," +
				"'border': '1px solid #deca7e'," +
				"'-moz-box-shadow': '0 0 10px rgba(0, 0, 0, .5)'," +
				"'-webkit-box-shadow': '0 0 10px rgba(0, 0, 0, .5)'," +
				"'box-shadow': '0 0 10px rgba(0, 0, 0, .5)'," +
				"'text-shadow': 'none'");
		sample2.add(new TooltipsyBehavior(builder2.getResult()));
		
		// sample 3
		Label sample3 = new Label("sample3", Model.of("Speech tooltip"));
		add(sample3);
		TooltipsyOptionsBuilder builder3 = new TooltipsyOptionsBuilder();
		builder3.setTooltipsyClassName("bubbletooltip_tip");
		builder3.setOffset(new Offset(0, 10));
		builder3.setShowJavaScript("function (e, $el) { $el.fadeIn(100); }");
		builder3.setHideJavaScript("function (e, $el) { $el.fadeOut(1000); }");
		sample3.add(new TooltipsyBehavior(builder3.getResult()));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(CssHeaderItem.forReference(new CssResourceReference(
				this.getClass(), STYLE_FILENAME)));
	}
}
