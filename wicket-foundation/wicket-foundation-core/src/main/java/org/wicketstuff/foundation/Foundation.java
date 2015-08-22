package org.wicketstuff.foundation;

import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * Foundation resources container. It is typically used by component base classes.
 * The resources are available through static getters.
 * @author ilkka
 *
 */
public class Foundation {

	private static ResourceReference foundationCssReference;

	private static ResourceReference foundationIconsCssReference;
	
	private static ResourceReference normalizeCssReference;
	
	private static ResourceReference foundationJsReference;

	private static ResourceReference modernizrJsReference;

	private static ResourceReference fastclickJsReference;
	
	public static ResourceReference getFoundationCssReference() {
		if (foundationCssReference == null) {
			foundationCssReference = new CssResourceReference(Foundation.class, "foundation.css");
		}
		return foundationCssReference;
	}

	public static ResourceReference getFoundationIconsCssReference() {
		if (foundationIconsCssReference == null) {
			foundationIconsCssReference = new CssResourceReference(Foundation.class, "foundation-icons.css");
		}
		return foundationIconsCssReference;
	}
	
	public static ResourceReference getNormalizeCssReference() {
		if (normalizeCssReference == null) {
			normalizeCssReference = new CssResourceReference(Foundation.class, "normalize.css");
		}
		return normalizeCssReference;
	}
	
	public static ResourceReference getFoundationJsReference() {
		if (foundationJsReference == null) {
			foundationJsReference = new JavaScriptResourceReference(Foundation.class, "foundation.min.js");
		}
		return foundationJsReference;
	}

	public static ResourceReference getModernizrJsReference() {
		if (modernizrJsReference == null) {
			modernizrJsReference = new JavaScriptResourceReference(Foundation.class, "modernizr.js");
		}
		return modernizrJsReference;
	}

	public static ResourceReference getFastclickJsReference() {
		if (fastclickJsReference == null) {
			fastclickJsReference = new JavaScriptResourceReference(Foundation.class, "fastclick.js");
		}
		return fastclickJsReference;
	}	
	
	public static String getFoundationInitScript() {
		return "jQuery(document).foundation();";
	}
}
