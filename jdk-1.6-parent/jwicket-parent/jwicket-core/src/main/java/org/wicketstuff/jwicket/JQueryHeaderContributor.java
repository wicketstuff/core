package org.wicketstuff.jwicket;


import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class JQueryHeaderContributor extends Behavior {

    private static final long serialVersionUID = 1L;

    public static final JQueryResourceReference jQueryCoreJs
            = JQuery.isDebug()
            ? new JQueryResourceReference(JQuery.class, "jquery-1.4.4.js")
            : new JQueryResourceReference(JQuery.class, "jquery-1.4.4.min.js");


    private final JQueryResourceReference baseLibrary;
    private final JQueryResourceReference[] requiredLibraries;
    private static final List<JavaScriptResourceReference> userProvidedResourceReferences = new ArrayList<JavaScriptResourceReference>();

    public JQueryHeaderContributor(final JQueryResourceReference baseLibrary) {
        super();
        this.baseLibrary = baseLibrary;
        this.requiredLibraries = new JQueryResourceReference[0];
    }

    public JQueryHeaderContributor(
            final JQueryResourceReference baseLibrary,
            final JQueryResourceReference... requiredLibraries) {
        super();
        this.baseLibrary = baseLibrary;
        this.requiredLibraries = requiredLibraries;
    }

    protected void addJavascriptReference(IHeaderResponse response, JavaScriptResourceReference resource) {
        if (!response.wasRendered(resource)) {
            response.render(JavaScriptHeaderItem.forReference(resource));
            response.markRendered(resource);
        }
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        if (userProvidedResourceReferences.size() == 0) {
            // No user provided Resources, use internal resources
            addJavascriptReference(response, jQueryCoreJs);
//            response.renderJavaScript("jQuery.noConflict();", "noConflict");
            response.render(JavaScriptHeaderItem.forScript("jQuery.noConflict();", "noConflict"));

            if (this.baseLibrary != null) {
                addJavascriptReference(response, this.baseLibrary);
            }
            if (this.requiredLibraries != null)
                for (JQueryResourceReference requiredLibrary : this.requiredLibraries) {
                    addJavascriptReference(response, requiredLibrary);
                }
        } else {
            // Userdefined resources, use them but also use the NOT_OVERRIDABLE internal resources
            for (JavaScriptResourceReference userLibrary : userProvidedResourceReferences) {
                addJavascriptReference(response, userLibrary);
            }

            if (this.baseLibrary != null && this.baseLibrary.getType() == JQueryResourceReferenceType.NOT_OVERRIDABLE) {
                addJavascriptReference(response, this.baseLibrary);
            }
            if (this.requiredLibraries != null)
                for (JQueryResourceReference requiredLibrary : this.requiredLibraries)
                    if (requiredLibrary.getType() == JQueryResourceReferenceType.NOT_OVERRIDABLE) {
                        addJavascriptReference(response, requiredLibrary);
                    }
        }
    }

    public static void addUserProvidedResourceReferences(final JavaScriptResourceReference... resources) {
        userProvidedResourceReferences.addAll(Arrays.asList(resources));
    }

    public static List<JavaScriptResourceReference> getUserProvidedResourceReferences() {
        return userProvidedResourceReferences;
    }
}
