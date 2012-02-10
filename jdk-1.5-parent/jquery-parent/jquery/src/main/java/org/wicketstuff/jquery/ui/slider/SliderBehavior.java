package org.wicketstuff.jquery.ui.slider;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.jquery.JQueryBehavior;
import org.wicketstuff.jquery.ui.UIResources;

/**
 * An integration of JQuery UI Slider widget (http://docs.jquery.com/UI/Slider/slider)
 * 
 * @author Martin Grigorov <martingrigorov @ users.sf.net>
 */
public class SliderBehavior extends JQueryBehavior
{

	private static final long serialVersionUID = 1L;

	private static final ResourceReference UI_SLIDER_RESOURCE_REFERENCE = new PackageResourceReference(
		SliderBehavior.class, "ui.slider.js");

	public static final ResourceReference WICKET_SLIDER_JS = new PackageResourceReference(
		SliderBehavior.class, "wicket-jquery.slider.js");

	public SliderBehavior()
	{
	}

	@Override
	protected CharSequence getOnReadyScript()
	{

		final SliderOptions sliderOptions = getSlider().getOptions();

		if (sliderOptions.getOnChange() == null)
		{

			final String body = getCallbackScript();

			sliderOptions.setOnChange(body, "e", "ui");
		}

		final StringBuilder onReady = new StringBuilder("$('#" + getSlider().getMarkupId() +
			"').slider(");
		onReady.append(sliderOptions.toJSON());
		onReady.append(");\n");

		return onReady;
	}

	@Override
	protected String getCallbackScript()
	{
		return generateCallbackScript(
			"wicketAjaxGet('" + getCallbackUrl() +
				"&handleId=' + getHandleId(e, ui) + '&value=' + getValue(e, ui)").toString();
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);
		response.renderCSSReference(UIResources.FLORA_CSS);
		response.renderCSSReference(UIResources.FLORA_SLIDER_CSS);
		response.renderJavaScriptReference(JQUERY_UI_JS);
		response.renderJavaScriptReference(UI_SLIDER_RESOURCE_REFERENCE);
		response.renderJavaScriptReference(WICKET_SLIDER_JS);
	}

	@Override
	protected void respond(AjaxRequestTarget target)
	{
		final Request req = RequestCycle.get().getRequest();
		final String handleId = req.getQueryParameters().getParameterValue("handleId").toString();
		final int newValue = req.getQueryParameters().getParameterValue("value").toInt(-1);
		getSlider().onChange(target, handleId, newValue);
	}

	public Slider getSlider()
	{
		return (Slider)getComponent();
	}

}
