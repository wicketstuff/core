package org.wicketstuff.jquery.crop;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.jquery.FunctionString;
import org.wicketstuff.jquery.JQueryBehavior;

/**
 * 
 * @author Edvin Syse <edvin@sysedata.no>
 * 
 */
public class CropBehaviour extends JQueryBehavior
{
	public static final ResourceReference IRESIZABLE_JS = new PackageResourceReference(
		CropBehaviour.class, "iresizable.js");
	public static final ResourceReference IUTIL_JS = new PackageResourceReference(
		CropBehaviour.class, "iutil.js");
	public static final ResourceReference CROP_BG = new PackageResourceReference(
		CropBehaviour.class, "crop_bg.png");

	private CropOptions options;

	public CropBehaviour()
	{
		this(new CropOptions());
	}

	public CropBehaviour(CropOptions options)
	{
		super();
		this.options = options != null ? options : new CropOptions();
	}

	@Override
	protected CharSequence getOnReadyScript()
	{
		StringBuilder onReady = new StringBuilder("$('#" + getComponent().getMarkupId() +
			"').Resizable(");

		options.set("onStop", new FunctionString("cropCallback"));
		options.set("onDragStop", new FunctionString("cropCallback"));

		options.set(
			"onResize",
			new FunctionString(
				"function(size, position) {\n this.style.backgroundPosition = '-' + (position.left) + 'px -' + (position.top) + 'px'; \n}\n"));
		options.set(
			"onDrag",
			new FunctionString(
				"function(x, y) {\n this.style.backgroundPosition = '-' + (x) + 'px -' + (y) + 'px'; \n}\n"));

		options.set(
			"handlers",
			new FunctionString(
				"{ se: '#resizeSE', e: '#resizeE', ne: '#resizeNE', n: '#resizeN', nw: '#resizeNW', w: '#resizeW', sw: '#resizeSW', s: '#resizeS' }\n"));

		onReady.append(options.toString(false));
		onReady.append(");");

		return onReady;
	}

	@Override
	protected CharSequence getCallbackScript()
	{
		String c = "$('#" + getComponent().getMarkupId() + "')";

		return generateCallbackScript("wicketAjaxGet('" + getCallbackUrl() + "&width=' + " + c +
			".width() + '" + "&height=' + " + c + ".height() + '" + "&top=' + " + c +
			".css('top') + '" + "&left=' + " + c + ".css('left')");
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);
		response.renderJavaScriptReference(JQUERY_JS);
		response.renderJavaScriptReference(INTERFACE_JS);
		response.renderJavaScriptReference(IRESIZABLE_JS);
		response.renderJavaScriptReference(IUTIL_JS);
		response.renderJavaScript("function cropCallback() {\n" + getCallbackScript() + "\n}\n",
			"crop-init-for-" + getComponent().getMarkupId());
	}

	@Override
	protected void respond(AjaxRequestTarget target)
	{
		Request req = RequestCycle.get().getRequest();
		onCropped(
			target,
			req.getQueryParameters().getParameterValue("width").toInt(),
			req.getQueryParameters().getParameterValue("height").toInt(),
			Integer.parseInt(req.getQueryParameters()
				.getParameterValue("top")
				.toString()
				.replace("px", "")),
			Integer.parseInt(req.getQueryParameters()
				.getParameterValue("left")
				.toString()
				.replace("px", "")));
	}

	public void onCropped(AjaxRequestTarget target, int width, int height, int top, int left)
	{
		System.out.println("Height: " + height);
		System.out.println("Width: " + width);
		System.out.println("Top: " + top);
		System.out.println("Left: " + left);
	}
}
