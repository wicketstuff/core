package org.wicketstuff.jwicket;


import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.util.string.JavascriptUtils;



/**
 * Common superclass for all behaviors that render javascript into HTML's head or body.
 * The default setting let's all javascript code be rendered into the <head> section
 * of a page. If you want to render the javascript code into the <body> part of the page
 * use {@link #setRenderToBody()}.
 *
 * If you use {@link #setRenderToHead()} a page is rendered like this:
 *
 * <pre>
 * &lt;html&gt;
 *    &lt;head&gt;
 *       &lt;script type="text/javascript" src="jquery.js" /&gt;
 *       &lt;script type="text/javascript"&gt;&lt;!--/&lowast;--&gt;&lt;![CDATA[/&lowast;&gt;&lt;!--&lowast;/
 *          jQuery(function(){jQuery('#id20').draggable({});});
 *       /&lowast;--&gt;]]&gt;&lowast;/&lt;/script&gt;
 *    &lt;/head&gt;
 *    &lt;body&gt;
 *       &lt;div id="id20"/&gt;
 *    &lt;/body&gt;
 * &lt;/html&gt;
 * </pre>
 *
 * If you use {@link #setRenderToBody()} instead then a page is rendered like this:
 *
 * <pre>
 * &lt;html&gt;
 *    &lt;head&gt;
 *       &lt;script type="text/javascript" src="jquery.js" /&gt;
 *    &lt;/head&gt;
 *    &lt;body&gt;
 *       &lt;div id="id20"/&gt;
 *       &lt;script type="text/javascript"&gt;&lt;!--/&lowast;--&gt;&lt;![CDATA[/&lowast;&gt;&lt;!--&lowast;/
 *          jQuery(function(){jQuery('#id20').draggable({});});
 *       /&lowast;--&gt;]]&gt;&lowast;/&lt;/script&gt;
 *    &lt;/body&gt;
 * &lt;/html&gt;
 * </pre>
 *
 * You may control the behavior dynamically in two different ways (e.g. a {@code DraggableBehavior}).
 * <br/>
 * Version 1:
 * <pre>
 * DraggableBehavior behavior = new DraggableBehavior();
 * ...
 * void on click(AjaxRequestTarget target) {
 *    behavior.setPropertyA(propA).setPropertyB(propB).setPropertyC(propC);
 *    updateBehavior(target);
 * }
 * </pre>
 * Version 2:
 * <pre>
 * DraggableBehavior behavior = new DraggableBehavior();
 * ...
 * void on click(AjaxRequestTarget target) {
 *    behavior.setPropertyA(target, propA);
 *    behavior.setPropertyB(target, propB);
 * }
 * </pre>
 */
public abstract class JQueryEmbeddedAjaxBehavior extends JQueryAjaxBehavior {

	private static final long serialVersionUID = 1L;

	private boolean renderToHead = false;
	/*	true does not work when
	 *
	 * onclick(AjaxRequestTarget) {
	 *    Component c = new Component();
	 *    c.add(new DraggableBehavior());
	 *    addOrReplace(c);
	 *    target.addComponent(c);
	 * }
	 *
	 * The JavaScript is rendered to the head but it is not execute becaus the COM rendered
	 * event seems not to be fired in an AJAX response!
	 */
	private int rendered = 0;

	protected JQueryEmbeddedAjaxBehavior(
			final JQueryJavascriptResourceReference baseLibrary,
			final JQueryJavascriptResourceReference[] requiredLibraries) {
		super(baseLibrary, requiredLibraries);
	}


	@Override
	protected void onBind() {
		super.onBind();
		rendered = 0;
	}



	/**
	 * Render as much javascript as possible to HTML's head.
	 */
	public void setRenderToHead() {
		this.renderToHead = true;
	}

	/**
	 * Render as much javascript as possible to HTML's body.
	 */
	public void setRenderToBody() {
		this.renderToHead = false;
	}


	protected abstract JsBuilder getJsBuilder();


	public void updateBehavior(final AjaxRequestTarget target) {
		target.appendJavascript(getJsBuilder().toString());
	}


	@Override
	/**
	 * Do not override this method unless you know exactly what you do.
	 * It is an internal method that handle the rendering of the JavaScript
	 * stuff for dragging.
	 */
	protected void onComponentRendered() {
		super.onComponentRendered();

		if (renderToHead && rendered< 2) {
			return;
		}

		JsBuilder builder = getJsBuilder();

		if (builder.length() > 0) {
			RequestCycle tequestCycle = RequestCycle.get();
			if (tequestCycle != null) {
				Response response = tequestCycle.getResponse();
				if (response != null) {
					JavascriptUtils.writeJavascript(response, builder.toScriptTag());
				}
			}
		}
		rendered++;
	}



	@Override
	/**
	 * Do not override this method unless you know exactly what you do.
	 * It is an internal method that handle the rendering of the JavaScript
	 * stuff for dragging.
	 */
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		if (!renderToHead) {
			return;
		}

		JsBuilder builder = getJsBuilder();




		if (builder.length() > 0)
			response.renderJavascript(builder.toScriptTag(), null);
		rendered++;
	}
}
