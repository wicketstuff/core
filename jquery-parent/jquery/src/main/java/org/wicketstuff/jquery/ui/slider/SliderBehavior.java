package org.wicketstuff.jquery.ui.slider;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.protocol.http.RequestUtils;
import org.wicketstuff.jquery.JQueryBehavior;
import org.wicketstuff.jquery.ui.UIResources;

/**
 * An integration of JQuery UI Slider widget
 * (http://docs.jquery.com/UI/Slider/slider)
 * 
 * @author Martin Grigorov <martingrigorov @ users.sf.net>
 */
public class SliderBehavior extends JQueryBehavior {

	private static final long serialVersionUID = 1L;

	private static final ResourceReference UI_SLIDER_RESOURCE_REFERENCE = new CompressedResourceReference(
			SliderBehavior.class, "ui.slider.js");

	public static final ResourceReference WICKET_SLIDER_JS = new CompressedResourceReference(
			SliderBehavior.class, "wicket-jquery.slider.js");

	public SliderBehavior() {
	}

	@Override
	protected CharSequence getOnReadyScript() {

		final SliderOptions sliderOptions = getSlider().getOptions();

		if (sliderOptions.getOnChange() == null) {

			final String body = getCallbackScript();

			sliderOptions.setOnChange(body, "e", "ui");
		}

		final StringBuilder onReady = new StringBuilder("$('#"
				+ getSlider().getMarkupId() + "').slider(");
		onReady.append(sliderOptions.toJSON());
		onReady.append(");\n");

		return onReady;
	}

	@Override
	protected String getCallbackScript() {
		return generateCallbackScript(
				"wicketAjaxGet('"
						+ getCallbackUrl()
						+ "&handleId=' + getHandleId(e, ui) + '&value=' + getValue(e, ui)")
				.toString();
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.renderCSSReference(UIResources.FLORA_CSS);
		response.renderCSSReference(UIResources.FLORA_SLIDER_CSS);
		response.renderJavascriptReference(JQUERY_UI_JS);
		response.renderJavascriptReference(UI_SLIDER_RESOURCE_REFERENCE);
		response.renderJavascriptReference(WICKET_SLIDER_JS);
	}

	@Override
	protected void respond(AjaxRequestTarget target) {
		final Request req = RequestCycle.get().getRequest();
		final Map<String, String[]> params = new HashMap<String, String[]>();
		RequestUtils.decodeUrlParameters(req.getQueryString(), params);
		final String handleId = params.get("handleId") != null 
			? params.get("handleId")[0] 
			: null;
		final int newValue = params.get("value") != null 
			? Integer.parseInt(params.get("value")[0]) 
			: -1;
		getSlider().onChange(target, handleId, newValue);
	}

	public Slider getSlider() {
		return (Slider) getComponent();
	}

}
