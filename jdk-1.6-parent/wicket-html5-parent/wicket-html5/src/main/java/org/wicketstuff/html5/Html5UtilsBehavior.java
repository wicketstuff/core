/*
 * Created by IntelliJ IDEA.
 * User: kinabalu
 * Date: Jan 31, 2010
 * Time: 9:29:13 PM
 */
package org.wicketstuff.html5;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.resource.CompressedResourceReference;

/**
 *
 * @author Andrew Lombardi
 */
public class Html5UtilsBehavior extends Behavior {

	private static final long serialVersionUID = 1L;

	private static final CompressedResourceReference H5UTILS_JS = new CompressedResourceReference(Html5UtilsBehavior.class, "h5utils.js");

	/**
	 * @see org.apache.wicket.behavior.Behavior#renderHead(org.apache.wicket.Component, org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(Component c, IHeaderResponse response) {
        response.renderJavaScriptReference(H5UTILS_JS);
	}
}