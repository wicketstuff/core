package org.wicketstuff.jwicket;


import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class JQueryHeaderContributor extends AbstractBehavior {

    private static final long serialVersionUID = 1L;

    public static final JQueryJavaScriptResourceReference jQueryCoreJs
            = JQuery.isDebug()
            ? new JQueryJavaScriptResourceReference(JQuery.class, "jquery-1.4.4.js")
            : new JQueryJavaScriptResourceReference(JQuery.class, "jquery-1.4.4.min.js");


    private final JQueryJavaScriptResourceReference baseLibrary;
    private final JQueryJavaScriptResourceReference[] requiredLibraries;
    private static final List<JavaScriptResourceReference> userProvidedResourceReferences = new ArrayList<JavaScriptResourceReference>();

    public JQueryHeaderContributor(final JQueryJavaScriptResourceReference baseLibrary) {
        super();
        this.baseLibrary = baseLibrary;
        this.requiredLibraries = new JQueryJavaScriptResourceReference[0];
    }

    public JQueryHeaderContributor(
            final JQueryJavaScriptResourceReference baseLibrary,
            final JQueryJavaScriptResourceReference... requiredLibraries) {
        super();
        this.baseLibrary = baseLibrary;
        this.requiredLibraries = requiredLibraries;
    }

    protected void addJavascriptReference(IHeaderResponse response, JavaScriptResourceReference resource) {
        if (!response.wasRendered(resource)) {
            response.renderJavaScriptReference(resource);
            response.markRendered(resource);
        }
    }

    protected void addJavascriptReference(IHeaderResponse response, JQueryJavaScriptResourceReference resource) {
        if (!response.wasRendered(resource)) {
            response.renderJavaScriptReference(resource);
            response.markRendered(resource);
        }
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        if (userProvidedResourceReferences.size() == 0) {
            // No user provided Resources, use internal resources
            addJavascriptReference(response, jQueryCoreJs);
            response.renderJavaScript("jQuery.noConflict();", "noConflict");

            if (this.baseLibrary != null) {
                addJavascriptReference(response, this.baseLibrary);
            }
            if (this.requiredLibraries != null)
                for (JQueryJavaScriptResourceReference requiredLibrary : this.requiredLibraries) {
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
                for (JQueryJavaScriptResourceReference requiredLibrary : this.requiredLibraries)
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
