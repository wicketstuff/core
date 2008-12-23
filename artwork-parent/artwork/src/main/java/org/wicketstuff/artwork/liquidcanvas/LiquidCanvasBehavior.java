package org.wicketstuff.artwork.liquidcanvas;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.wicketstuff.artwork.liquidcanvas.graphics.Graphics;
import org.wicketstuff.jslibraries.JSReference;
import org.wicketstuff.jslibraries.Library;
import org.wicketstuff.jslibraries.VersionDescriptor;

/**
 * 
 * @author Nino Martinez (nino.martinez.wael *at* gmail *dot* com remember no
 *         stars)
 * @see http://www.ruzee.com/content/liquid-canvas
 */
public class LiquidCanvasBehavior extends AbstractBehavior implements
		IHeaderContributor {

	private List<Graphics> graphicsList;

	public LiquidCanvasBehavior(Graphics... graphics) {
		super();
		this.graphicsList = Arrays.asList(graphics);
	}

	/** The target component. */
	private Component component;

	@Override
	public void bind(Component component) {
		super.bind(component);
		this.component = component;
		component.setOutputMarkupId(true);

	}

	@Override
	public void onRendered(Component component) {
		super.onRendered(component);
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		response.renderJavascriptReference(getJQueryReference());
		response.renderJavascriptReference(getLiquidCanvasReference());
		response.renderJavascriptReference(getLiquidCanvasPluginsReference());
		response.renderString(getExcanvasReference());
		response.renderOnLoadJavascript(getCanvasJS());
	}

	/**
	 * Returns the liquid canvas js
	 * 
	 * @return
	 */
	private String getCanvasJS() {

		String js = "$(\"#" + component.getMarkupId() + "\").liquidCanvas(\"";
		String endJs = "\")";

		boolean first = true;
		for (Graphics g : graphicsList) {
			if (!first) {
				js += " => ";
			}
			js = fillChained(js, g);
			first = false;
		}

		js += endJs;
		return js;

	}

	private String fillChained(String js, Graphics g) {
		if (g.isChained()) {
			js += "[ ";
		}
		js += " " + g.getStringForJS();
		if (g.isChained()) {
			boolean moreChainedGraphics = true;
			Graphics inspect = g.getChainedGraphics();
			while (moreChainedGraphics) {
				moreChainedGraphics = inspect.isChained();
				js += " " + inspect.getStringForJS();
				inspect = inspect.getChainedGraphics();

			}

			js += " ]";
		}
		return js;
	}

	/**
	 * Returns a resource that only will be picked up by IE!
	 * @return
	 */
	private String getExcanvasReference(){
		
		String excanvasUrl = "";
		excanvasUrl="<!--[if IE]><script type=\"text/javascript\" src=\""+component.urlFor(new ResourceReference(LiquidCanvasBehavior.class, "excanvas.js")).toString()+"\"></script><![endif]-->";

		
		
		return excanvasUrl;
	}

	private ResourceReference getLiquidCanvasPluginsReference() {
		return new ResourceReference(LiquidCanvasBehavior.class,
				"liquid-canvas-plugins.js");

	}

	private ResourceReference getLiquidCanvasReference() {
		return new ResourceReference(LiquidCanvasBehavior.class,
				"liquid-canvas.js");

	}

	private ResourceReference getJQueryReference() {

		return JSReference.getReference(VersionDescriptor.exactVersion(
				Library.JQUERY, 1, 2, 6));

	}
}
