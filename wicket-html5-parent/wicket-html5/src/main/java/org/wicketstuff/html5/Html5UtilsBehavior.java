/*
 * Created by IntelliJ IDEA.
 * User: kinabalu
 * Date: Jan 31, 2010
 * Time: 9:29:13 PM
 */
package org.wicketstuff.html5;

import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;

/**
 *
 * @author Andrew Lombardi
 */
public class Html5UtilsBehavior extends AbstractBehavior {

    private final CompressedResourceReference H5UTILS_JS = new CompressedResourceReference(Html5Media.class, "h5utils.js");


	/**
	 * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response) {
        response.renderJavascriptReference(H5UTILS_JS);
	}
}