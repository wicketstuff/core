package org.wicketstuff.jwicket.tooltip;


import org.apache.wicket.Component;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.wicketstuff.jwicket.JQueryAjaxBehavior;
import org.wicketstuff.jwicket.tooltip.res.wtooltip.Anchor;



/**
 * This Class is a wrapper around the jQuery plugin <a href="http://wayfarerweb.com/wtooltip.php">wTooltip</a> 
 */
public class WTooltip extends AbstractToolTip {
	private static final long serialVersionUID = 1L;

	private String style;
	private String cssClassName;
	private int offsetX = 1;
	private int offsetY = -10;
	private int fadeinMs = 0;
	private int fadeoutMs = 0;
	private int delayMs = 0;
	private int timeoutMs = 0;


	public WTooltip(final String tooltipText) {
		super(tooltipText);
	}


	@Override
	IHeaderContributor getHeadercontributor() {
		return new IHeaderContributor() {
			private static final long serialVersionUID = 1L;

			public void renderHead(final IHeaderResponse response) {
				response.renderJavascriptReference(new JavascriptResourceReference(JQueryAjaxBehavior.class, "jquery-1.3.2.js"));
				response.renderJavascript("jQuery.noConflict();", "noConflict");
				response.renderJavascriptReference(new JavascriptResourceReference(Anchor.class, "wTooltip.js"));

				response.renderJavascript(getJavaScript(), null);
			}
		};
	}
	
	
	public WTooltip setTooltipText(final String htmlCode) {
		this.tooltipText = htmlCode.replace("</", "<\\/");
		return this;
	}


	public WTooltip setStyle(final String styleDefinition) {
		this.style = styleDefinition;
		return this;
	}


	public WTooltip setCssClass(final String classNAme) {
		this.cssClassName = classNAme;
		return this;
	}


	public WTooltip setOffset(final int x, final int y) {
		this.offsetX = x;
		this.offsetY = y;
		return this;
	}


	public WTooltip setFadeIn(final int ms) {
		this.fadeinMs = ms;
		return this;
	}


	public WTooltip setFadeOut(final int ms) {
		this.fadeoutMs = ms;
		return this;
	}


	public WTooltip setDelay(final int ms) {
		this.delayMs = ms;
		return this;
	}


	public WTooltip setTimeout(final int ms) {
		this.timeoutMs = ms;
		return this;
	}


	String getJavaScript() {
		StringBuilder builder = new StringBuilder();
		for (Component component : components) {
			builder.append("jQuery(function(){jQuery('#");
			builder.append(component.getMarkupId());
			builder.append("').wTooltip({content:'");
			builder.append(tooltipText);
			builder.append("'");
			if (style != null && style.trim().length() > 0) {
				builder.append(",style:{");
				builder.append(style);
				builder.append("}");
			}
			if (cssClassName != null && cssClassName.trim().length() > 0) {
				builder.append(",className:'");
				builder.append(cssClassName);
				builder.append("'");
			}
			if (offsetX != 1) {
				builder.append(",offsetX:");
				builder.append(offsetX);
			}
			if (offsetY != -10) {
				builder.append(",offsetY:");
				builder.append(offsetY);
			}
			if (fadeinMs != 0) {
				builder.append(",fadeIn:");
				builder.append(fadeinMs);
			}
			if (fadeoutMs != 0) {
				builder.append(",fadeOut:");
				builder.append(fadeoutMs);
			}
			if (delayMs != 0) {
				builder.append(",delay:");
				builder.append(delayMs);
			}
			if (timeoutMs != 0) {
				builder.append(",timeout:");
				builder.append(timeoutMs);
			}
			builder.append("});});");
		}
		return builder.toString();
	}
}
