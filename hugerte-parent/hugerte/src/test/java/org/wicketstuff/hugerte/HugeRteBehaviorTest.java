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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.core.util.lang.WicketObjects;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringResourceStream;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.agilecoders.wicket.webjars.WicketWebjars;
import de.agilecoders.wicket.webjars.settings.WebjarsSettings;

class HugeRteBehaviorTest
{
	private WicketTester tester;

	@BeforeEach
	void setUp()
	{
		tester = new WicketTester(new MockApplication());
	}

	@AfterEach
	void tearDown()
	{
		tester.destroy();
	}

	@Test
	void installsWebjarsWhenTheApplicationDidNot()
	{
		assertTrue(WicketWebjars.isInstalled(tester.getApplication()));
	}

	@Test
	void keepsTheWebjarsSettingsOfTheApplication()
	{
		var settings = new WebjarsSettings();
		var own = new WicketTester(new MockApplication()
		{
			@Override
			protected void init()
			{
				super.init();
				WicketWebjars.install(this, settings);
			}
		});
		try
		{
			assertSame(settings, WicketWebjars.settings(own.getApplication()));
		}
		finally
		{
			own.destroy();
		}
	}

	@Test
	void rendersScriptsAndInitCall()
	{
		var page = new EditorPage(HugeRteConfig.defaults().withMenubar(false));
		tester.startPage(page);

		var html = tester.getLastResponseAsString();
		assertTrue(html.contains("hugerte-wicket"), "glue script");
		assertTrue(html.contains("webjars/hugerte/"), "HugeRTE webjar");
		assertTrue(html.contains("Wicket.HugeRte.init(\"" + page.text.getMarkupId() +
			"\",{\"menubar\":false});"), html);
	}

	@Test
	void escapesConfigurationInThePage()
	{
		tester.startPage(new EditorPage(HugeRteConfig.defaults().with("placeholder", "</script>")));

		var html = tester.getLastResponseAsString();
		assertTrue(html.contains("\"placeholder\":\"\\u003c/script\\u003e\""), html);
	}

	@Test
	void initialisesAgainAfterAjaxRender()
	{
		var page = new EditorPage(HugeRteConfig.defaults());
		tester.startPage(page);

		tester.clickLink("refresh");

		tester.assertComponentOnAjaxResponse(page.text);
		var ajax = tester.getLastResponseAsString();
		assertTrue(ajax.contains("Wicket.HugeRte.init(\"" + page.text.getMarkupId() + "\""), ajax);
	}

	@Test
	void readsTheConfigurationModelOnEveryRender()
	{
		var model = Model.of(HugeRteConfig.defaults().with("height", 100));
		var page = new EditorPage(new HugeRteBehavior(model));
		tester.startPage(page);
		model.setObject(HugeRteConfig.defaults().with("height", 200));

		tester.clickLink("refresh");

		assertTrue(tester.getLastResponseAsString().contains("{\"height\":200}"));
	}

	@Test
	void isSerializable()
	{
		var behavior = new HugeRteBehavior(HugeRteConfig.defaults().withPlugins(HugeRtePlugin.LINK));

		var copy = WicketObjects.cloneObject(behavior);

		assertNotNull(copy);
		assertEquals(behavior.getConfig(), copy.getConfig());
	}

	public static class EditorPage extends WebPage implements IMarkupResourceStreamProvider
	{
		private static final long serialVersionUID = 1L;

		final TextArea<String> text;

		EditorPage(HugeRteConfig config)
		{
			this(new HugeRteBehavior(config));
		}

		EditorPage(HugeRteBehavior behavior)
		{
			var form = new Form<Void>("form");
			add(form);
			text = new TextArea<>("text", Model.of("<p>hello</p>"));
			text.add(behavior);
			form.add(text);
			add(new AjaxLink<Void>("refresh")
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target)
				{
					target.add(text);
				}
			});
		}

		@Override
		public IResourceStream getMarkupResourceStream(MarkupContainer container,
			Class<?> containerClass)
		{
			return new StringResourceStream("""
				<html><head></head><body>
				<form wicket:id="form"><textarea wicket:id="text"></textarea></form>
				<a wicket:id="refresh">refresh</a>
				</body></html>""");
		}
	}
}
