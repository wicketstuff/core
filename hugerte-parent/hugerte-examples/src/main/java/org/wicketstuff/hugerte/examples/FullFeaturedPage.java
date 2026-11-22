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
package org.wicketstuff.hugerte.examples;

import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;
import org.wicketstuff.hugerte.HugeRteBehavior;
import org.wicketstuff.hugerte.HugeRteConfig;
import org.wicketstuff.hugerte.HugeRtePlugin;

/**
 * An editor with every bundled plugin and a three-row toolbar.
 */
public class FullFeaturedPage extends BasePage
{
	private static final long serialVersionUID = 1L;

	public FullFeaturedPage()
	{
		var config = HugeRteConfig.defaults()
			.withPlugins(HugeRtePlugin.values())
			.withToolbar(
				"undo redo | blocks | bold italic underline strikethrough | alignleft aligncenter alignright alignjustify",
				"bullist numlist outdent indent | link anchor image media table accordion | forecolor backcolor removeformat",
				"charmap emoticons insertdatetime pagebreak nonbreaking | template codesample code | searchreplace visualblocks visualchars | preview fullscreen | ltr rtl | help")
			.withStatusbar(true)
			.with("height", 450)
			.with("templates", List.of(Map.of("title", "Greeting", "description",
				"A short greeting", "content", "<p>Hello from a template.</p>")));

		var text = new TextArea<>("text", Model.of(
			"<h2>All plugins</h2><p>HugeRTE is the MIT-licensed fork of TinyMCE 6.</p>"));
		text.add(new HugeRteBehavior(config));
		add(text);
	}
}
