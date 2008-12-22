package org.wicketstuff.artwork;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.wicketstuff.artwork.graphics.Graphics;
import org.wicketstuff.jslibraries.JSReference;
import org.wicketstuff.jslibraries.Library;
import org.wicketstuff.jslibraries.VersionDescriptor;

/**
 * 
 * @author Nino Martinez (nino.martinez.wael *at* gmail *dot* com remember no
 *         stars)
 * 
 */
public class ArtworkCanvasBehavior extends AbstractBehavior implements
		IHeaderContributor {

	private List<Graphics> graphicsList;

	public ArtworkCanvasBehavior(Graphics... graphics) {
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
			if (g.isChained()) {
				js += "[ ";
			}
			js += " " + g.getStringForJS();
			if (g.isChained()) {
				boolean moreChainedGraphics = true;
				Graphics inspect = g;
				while (moreChainedGraphics) {
					if (inspect.isChained()) {
						js += " ," + inspect.getStringForJS();
						inspect = inspect.getChainedGraphics();
					} else {
						moreChainedGraphics = false;
					}

				}

				js += " ]";
			}
			first = false;
		}

		js += endJs;
		return js;

	}

	private ResourceReference getLiquidCanvasPluginsReference() {
		return new ResourceReference(ArtworkCanvasBehavior.class,
				"liquid-canvas-plugins.js");

	}

	private ResourceReference getLiquidCanvasReference() {
		return new ResourceReference(ArtworkCanvasBehavior.class,
				"liquid-canvas.js");

	}

	private ResourceReference getJQueryReference() {

		return JSReference.getReference(VersionDescriptor.alwaysLatest(Library.JQUERY));
// Change to below, waiting for author to fix!
//		return JSReference.getReference(VersionDescriptor.exactVersion(
//				Library.JQUERY, 1, 2, 6));

	}
}
