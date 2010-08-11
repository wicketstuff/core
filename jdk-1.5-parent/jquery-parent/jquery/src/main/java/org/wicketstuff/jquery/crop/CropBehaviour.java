package org.wicketstuff.jquery.crop;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;

import org.wicketstuff.jquery.FunctionString;
import org.wicketstuff.jquery.JQueryBehavior;

/**
 * 
 * @author Edvin Syse <edvin@sysedata.no>
 *
 */
public class CropBehaviour extends JQueryBehavior {
	public static final CompressedResourceReference IRESIZABLE_JS = new CompressedResourceReference(CropBehaviour.class, "iresizable.js");
	public static final CompressedResourceReference IUTIL_JS = new CompressedResourceReference(CropBehaviour.class, "iutil.js");
	public static final CompressedResourceReference CROP_BG = new CompressedResourceReference(CropBehaviour.class, "crop_bg.png");

	private CropOptions options;

	public CropBehaviour() {
		this(new CropOptions());
	}

	public CropBehaviour(CropOptions options) {
		super();
		this.options = options != null ? options : new CropOptions();
	}

	@Override protected CharSequence getOnReadyScript() {
		StringBuilder onReady = new StringBuilder("$('#" + getComponent().getMarkupId() + "').Resizable(");

		options.set("onStop", new FunctionString("cropCallback"));
		options.set("onDragStop", new FunctionString("cropCallback"));

		options.set("onResize", new FunctionString("function(size, position) {\n this.style.backgroundPosition = '-' + (position.left) + 'px -' + (position.top) + 'px'; \n}\n"));
		options.set("onDrag", new FunctionString("function(x, y) {\n this.style.backgroundPosition = '-' + (x) + 'px -' + (y) + 'px'; \n}\n"));

		options.set("handlers", new FunctionString("{ se: '#resizeSE', e: '#resizeE', ne: '#resizeNE', n: '#resizeN', nw: '#resizeNW', w: '#resizeW', sw: '#resizeSW', s: '#resizeS' }\n"));
		
		onReady.append(options.toString(false));
		onReady.append(");");

		return onReady;
	}

	@Override
	protected CharSequence getCallbackScript() {
		String c = "$('#" + getComponent().getMarkupId() + "')";
		
		return generateCallbackScript("wicketAjaxGet('" + getCallbackUrl()
				+ "&width=' + " + c + ".width() + '"
				+ "&height=' + " + c + ".height() + '"
				+ "&top=' + " + c + ".css('top') + '"
				+ "&left=' + " + c + ".css('left')");
	}

	@Override public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.renderJavascriptReference(JQUERY_JS);
		response.renderJavascriptReference(INTERFACE_JS);
		response.renderJavascriptReference(IRESIZABLE_JS);
		response.renderJavascriptReference(IUTIL_JS);
		response.renderJavascript("function cropCallback() {\n" + getCallbackScript() + "\n}\n", "crop-init-for-" + getComponent().getMarkupId());
	}

	@Override protected void respond(AjaxRequestTarget target) {
		Request req = RequestCycle.get().getRequest();
		onCropped(target, Integer.parseInt(req.getParameter("width")), Integer.parseInt(req.getParameter("height")),
				Integer.parseInt(req.getParameter("top").replace("px", "")), Integer.parseInt(req.getParameter("left").replace("px", "")));
	}

	public void onCropped(AjaxRequestTarget target, int width, int height, int top, int left) {
		System.out.println("Height: " + height);
		System.out.println("Width: " + width);
		System.out.println("Top: " + top);
		System.out.println("Left: " + left);
	}
}
