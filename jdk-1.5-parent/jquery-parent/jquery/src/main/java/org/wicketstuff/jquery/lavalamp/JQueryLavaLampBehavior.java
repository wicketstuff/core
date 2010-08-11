package org.wicketstuff.jquery.lavalamp;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.wicketstuff.jquery.JQueryBehavior;

public class JQueryLavaLampBehavior extends JQueryBehavior {
    private static final long serialVersionUID = -428199323406701848L;
    public static final CompressedResourceReference JQUERY_EASING_JS = new CompressedResourceReference(
            JQueryLavaLampBehavior.class, "jquery.easing.1.1.js");
    public static final CompressedResourceReference JQUERY_LAVALAMP_JS = new CompressedResourceReference(
            JQueryLavaLampBehavior.class, "jquery.lavalamp.js");

    public JQueryLavaLampBehavior() {
        super();
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.renderJavascriptReference(JQUERY_EASING_JS);
        response.renderJavascriptReference(JQUERY_LAVALAMP_JS);
    }

    @Override
    protected void respond(AjaxRequestTarget target) {}

}
