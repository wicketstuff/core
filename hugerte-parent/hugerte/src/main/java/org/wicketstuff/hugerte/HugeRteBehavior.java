/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.hugerte;

import java.util.Objects;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Turns a {@code <textarea>} (or, with the {@code inline} option, another element) into a HugeRTE
 * editor.
 *
 * <pre>{@code
 * TextArea<String> text = new TextArea<>("text", model);
 * text.add(new HugeRteBehavior(HugeRteConfig.defaults().withPlugins(HugeRtePlugin.LISTS)));
 * }</pre>
 *
 * The behavior works with Ajax without extra components:
 * <ul>
 * <li>Before every Wicket Ajax call the editors write their content back to their textareas, so
 * Ajax submits see the edited text.</li>
 * <li>When Wicket Ajax replaces or removes the component, its editor is removed, and a re-rendered
 * component gets a fresh editor.</li>
 * </ul>
 * One behavior instance can be added to several components.
 */
public class HugeRteBehavior extends Behavior
{
	private static final long serialVersionUID = 1L;

	private final IModel<HugeRteConfig> config;

	/**
	 * Creates an editor with the HugeRTE defaults.
	 */
	public HugeRteBehavior()
	{
		this(HugeRteConfig.defaults());
	}

	public HugeRteBehavior(HugeRteConfig config)
	{
		this(Model.of(Objects.requireNonNull(config, "config")));
	}

	/**
	 * @param config
	 *            a model of the configuration, read each time the component renders
	 */
	public HugeRteBehavior(IModel<HugeRteConfig> config)
	{
		this.config = Objects.requireNonNull(config, "config");
	}

	@Override
	public void bind(Component component)
	{
		super.bind(component);
		component.setOutputMarkupId(true);
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);
		response.render(JavaScriptHeaderItem.forReference(HugeRteJavaScriptReference.get()));
		response.render(OnDomReadyHeaderItem.forScript(getInitScript(component)));
	}

	/**
	 * @return the script that creates the editor for the component
	 */
	protected String getInitScript(Component component)
	{
		return "Wicket.HugeRte.init(" + JavaScriptWriter.write(component.getMarkupId()) + "," +
			getConfig().toJavaScript() + ");";
	}

	/**
	 * @return the current configuration
	 */
	public HugeRteConfig getConfig()
	{
		return Objects.requireNonNull(config.getObject(), "The configuration model returned null");
	}

	@Override
	public void detach(Component component)
	{
		config.detach();
		super.detach(component);
	}
}
