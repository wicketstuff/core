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
package org.wicketstuff.tinymce6;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.tinymce6.settings.TinyMCESettings;

/**
 * Renders a component (textarea) as WYSIWYG editor, using TinyMce.
 */
public class TinyMceBehavior extends Behavior
{
	private static final long serialVersionUID = 3L;

	private Component component;
	private final TinyMCESettings settings;
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
	public void renderHead(Component component, IHeaderResponse response)
	{
		// FIXME Problem when a previously rendered Tinymce textarea is made invisibale again, there
		// is no way to destroy the tinymce instance.

		super.renderHead(component, response);
		if (component == null)
		{
			throw new IllegalStateException("TinyMceBehavior is not bound to a component");
		}

		// TinyMce javascript:
		response.render(JavaScriptHeaderItem.forReference(getTinyMCEReference()));

		String renderOnDomReady = getAddTinyMceSettingsScript(Collections.singletonList(component));
		response.render(wrapTinyMceSettingsScript(renderOnDomReady, component));
	}

	/**
	 * Wrap the initialization script for TinyMCE into a HeaderItem. In this way we can control when
	 * and how the script should be executed.
	 *
	 * @param settingScript
	 *            the actual initialization script for TinyMCE
	 * @param component
	 *            the target component that must be decorated with TinyMCE
	 * @return the HeaderItem containing {@paramref settingScript}
	 */
	protected HeaderItem wrapTinyMceSettingsScript(String settingScript, Component component)
	{
		return OnDomReadyHeaderItem.forScript(settingScript);
	}

	protected String getAddTinyMceSettingsScript(Collection<Component> components)
	{
		final var script = new StringBuilder();
		// If this behavior is run a second time, it means we're redrawing this
		// component via an ajax call. The tinyMCE javascript does not handle
		// this scenario, so we must remove the old editor before initializing
		// it again.
		if (rendered)
		{
			for (Component c : components)
			{
				String tryToRemoveJS = "try {let editor = tinymce.get('%s'); if (editor) { editor.destroy(); }} catch (e) { console.debug(e); }\n;";
				script.append(String.format(tryToRemoveJS, c.getMarkupId()));
			}
		}

		script
			.append(";tinyMCE.init({").append(settings.toJavaScript(components.stream()
				.filter(c -> c.isVisibleInHierarchy()).collect(Collectors.toList())))
			.append(" });\n");
		rendered = true;

		return script.toString();
	}

	@Override
	public void bind(Component component)
	{
		if (this.component != null)
		{
			throw new IllegalStateException(
				"TinyMceBehavior can not bind to more than one component");
		}
		super.bind(component);
		if (isMarkupIdRequired())
		{
			component.setOutputMarkupId(true);
		}
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

	/**
	 * <p>
	 * TinyMCE javascript resource.
	 * </p>
	 * <p>
	 * <strong>Note</strong>: The TinyMCE source cannot be lazily loaded via ajax. Therefore, adding
	 * this in a {@link IHeaderContributor#renderHead(IHeaderResponse)} must be done in a component
	 * that is not rendered via Ajax.
	 * </p>
	 *
	 * @return Tinymce JS Resource Reference
	 */
	protected ResourceReference getTinyMCEReference()
	{
		Application app = Application.get();
		if (RuntimeConfigurationType.DEVELOPMENT.equals(app.getConfigurationType()))
		{
			return TinyMCESettings.TINYMCE_JS_REF;
		}
		return TinyMCESettings.TINYMCE_JS_REF_MIN;
	}
}
