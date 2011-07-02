/*
    This file is part of Wicket-Contrib-TinyMce. See
    <http://http://wicketstuff.org/confluence/display/STUFFWIKI/wicket-contrib-tinymce>

    Wicket-Contrib-TinyMce is free software: you can redistribute it and/
    or modify it under the terms of the GNU Lesser General Public License
    as published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    Wicket-Contrib-TinyMce is distributed in the hope that it will be
    useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with Wicket-Contrib-TinyMce.  If not, see
    <http://www.gnu.org/licenses/>.
 */
package wicket.contrib.tinymce;

import java.util.Collection;
import java.util.Collections;

import org.apache.wicket.Component;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.protocol.http.WebRequest;

import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.TinyMCESettings.Mode;

/**
 * Renders a component (textarea) as WYSIWYG editor, using TinyMce.
 */
public class TinyMceBehavior extends AbstractBehavior {
    private static final long serialVersionUID = 3L;

    private Component component;
    private TinyMCESettings settings;
    private boolean rendered = false;

    public TinyMceBehavior() {
        this(new TinyMCESettings());
    }

    public TinyMceBehavior(TinyMCESettings settings) {
        this.settings = settings;
    }

    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        if (component == null)
            throw new IllegalStateException("TinyMceBehavior is not bound to a component");

        // TinyMce javascript:
        if (mayRenderJavascriptDirect())
        	response.renderJavascriptReference(TinyMCESettings.javaScriptReference());
        else
        	TinyMCESettings.lazyLoadTinyMCEResource(response);
        String renderOnDomReady = getRenderOnDomReadyJavascript(response);
        if (renderOnDomReady != null)
            response.renderOnDomReadyJavascript(renderOnDomReady);

        String renderJavaScript = getRenderJavascript(response);
        if (renderJavaScript != null)
            response.renderJavascript(renderJavaScript, null);
    }

    protected String getRenderOnDomReadyJavascript(IHeaderResponse response) {
        if (component == null)
            throw new IllegalStateException("TinyMceBehavior is not bound to a component");
        if (! mayRenderJavascriptDirect())
            return getAddTinyMceSettingsScript(Mode.exact, Collections.singletonList(component));
        return null;
    }

    private boolean mayRenderJavascriptDirect() {
    	return RequestCycle.get().getRequest() instanceof WebRequest && !((WebRequest)RequestCycle.get().getRequest()).isAjax();
	}

	protected String getRenderJavascript(IHeaderResponse response) {
        if (component == null)
            throw new IllegalStateException("TinyMceBehavior is not bound to a component");
        if (mayRenderJavascriptDirect())
            return getAddTinyMceSettingsScript(Mode.exact, Collections.singletonList(component));
        return null;
    }

    protected String getAddTinyMceSettingsScript(Mode mode, Collection<Component> components) {
    	
    	StringBuilder script = new StringBuilder(128);

		// If this behavior is run a second time, it means we're redrawing this component via
		// an ajax call.  The tinyMCE javascript does not handle this scenario, so we must
		// remove the old editor before initializing it again.
		if (rendered) {
			for(Component c : components) {
				script.append("tinyMCE.remove(tinyMCE.get('");
				script.append(c.getMarkupId());
				script.append("'));\n");
			}
		}
	
		script.append(settings.getLoadPluginJavaScript());
		script.append(" tinyMCE.init({");
		script.append(settings.toJavaScript(mode, components));
		script.append(" });\n");
		script.append(settings.getAdditionalPluginJavaScript());
		rendered = true;
				
		return script.toString();
    }

    public void bind(Component component) {
        if (this.component != null)
            throw new IllegalStateException("TinyMceBehavior can not bind to more than one component");
        super.bind(component);
        if (isMarkupIdRequired())
            component.setOutputMarkupId(true);
        this.component = component;
    }

    protected boolean isMarkupIdRequired() {
        return true;
    }

    protected Component getComponent() {
        return component;
    }
    
    
}
