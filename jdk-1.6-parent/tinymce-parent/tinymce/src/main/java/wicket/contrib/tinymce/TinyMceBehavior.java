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
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;

import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.TinyMCESettings.Mode;

/**
 * Renders a component (textarea) as WYSIWYG editor, using TinyMce.
 */
public class TinyMceBehavior extends Behavior
{
	private static final long serialVersionUID = 3L;

	private Component component;
	private TinyMCESettings settings;
	private boolean rendered = false;

	public TinyMceBehavior()
	{
		this(new TinyMCESettings());
	}

	public TinyMceBehavior(TinyMCESettings settings)
	{
		this.settings = settings;
	}

	@Override
	public void renderHead(Component c, IHeaderResponse response)
	{
		super.renderHead(c, response);
		if (component == null)
			throw new IllegalStateException("TinyMceBehavior is not bound to a component");

		// TinyMce javascript:
		if (mayRenderJavascriptDirect())
		{
			response.render(JavaScriptHeaderItem.forReference(TinyMCESettings.javaScriptReference()));
		}
		else
		{
			TinyMCESettings.lazyLoadTinyMCEResource(response);
		}

		String renderOnDomReady = getAddTinyMceSettingsScript(Mode.exact,
				Collections.singletonList(component));
		response.render(wrapTinyMceSettingsScript(renderOnDomReady, component));
	}
	
	/**
	 * Wrap the initialization script for TinyMCE into a HeaderItem. In this way we can control
	 * when and how the script should be executed.
	 * 
	 * @param settingScript
	 * 			the actual initialization script for TinyMCE
	 * @param component
	 * 			the target component that must be decorated with TinyMCE 
	 * @return
	 * 			the HeaderItem containing {@paramref settingScript}
	 * 
	 */
	protected HeaderItem wrapTinyMceSettingsScript(String settingScript, Component component){
		return OnDomReadyHeaderItem.forScript(settingScript);
	}

	private boolean mayRenderJavascriptDirect()
	{
		return RequestCycle.get().getRequest() instanceof WebRequest
				&& !((WebRequest)RequestCycle.get().getRequest()).isAjax();
	}


	protected String getAddTinyMceSettingsScript(Mode mode, Collection<Component> components)
	{
		StringBuffer script = new StringBuffer();
		// If this behavior is run a second time, it means we're redrawing this
		// component via an ajax call. The tinyMCE javascript does not handle
		// this scenario, so we must remove the old editor before initializing
		// it again.
		if (rendered)
		{
			for (Component c : components)
			{
				String tryToRemoveJS = "try{tinyMCE.remove(tinyMCE.get('%s'));}catch(e){}\n";
				script.append(String.format(tryToRemoveJS, c.getMarkupId()));
			}
		}

		script.append(settings.getLoadPluginJavaScript());
		script.append(" tinyMCE.init({" + settings.toJavaScript(mode, components) + " });\n");
		script.append(settings.getAdditionalPluginJavaScript());
		rendered = true;

		return script.toString();
	}

	@Override
	public void bind(Component component)
	{
		if (this.component != null)
			throw new IllegalStateException(
					"TinyMceBehavior can not bind to more than one component");
		super.bind(component);
		if (isMarkupIdRequired())
			component.setOutputMarkupId(true);
		this.component = component;
	}

	protected boolean isMarkupIdRequired()
	{
		return true;
	}

	protected Component getComponent()
	{
		return component;
	}
}
