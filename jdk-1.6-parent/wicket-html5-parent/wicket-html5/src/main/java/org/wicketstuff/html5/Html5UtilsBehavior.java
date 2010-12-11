/*
 * Created by IntelliJ IDEA.
 * User: kinabalu
 * Date: Jan 31, 2010
 * Time: 9:29:13 PM
 */
package org.wicketstuff.html5;

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
	 * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response) {
        response.renderJavaScriptReference(H5UTILS_JS);
	}
}