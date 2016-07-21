/*
 * Created by IntelliJ IDEA.
 * User: kinabalu
 * Date: Jan 31, 2010
 * Time: 9:29:13 PM
 */
package org.wicketstuff.html5;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * 
 * @author Andrew Lombardi
 */
public class Html5UtilsBehavior extends Behavior {

	private static final long serialVersionUID = 1L;

	private static final PackageResourceReference H5UTILS_JS = new PackageResourceReference(
			Html5UtilsBehavior.class, "h5utils.js");

	/**
	 * @see org.apache.wicket.behavior.Behavior#renderHead(org.apache.wicket.Component,
	 *      org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(Component c, IHeaderResponse response) {
		response.render(JavaScriptHeaderItem.forReference(H5UTILS_JS));
	}
}