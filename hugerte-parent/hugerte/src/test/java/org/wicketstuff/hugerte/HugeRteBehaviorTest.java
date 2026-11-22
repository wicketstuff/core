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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.core.util.lang.WicketObjects;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.MarkupException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.UrlResourceReference;
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
	void makesTheEditorReadonlyWhenTheComponentIsDisabled()
	{
		var page = new EditorPage(HugeRteConfig.defaults());
		page.form.setEnabled(false);
		tester.startPage(page);

		assertTrue(tester.getLastResponseAsString().contains(
			"Wicket.HugeRte.init(\"" + page.text.getMarkupId() + "\",{\"readonly\":true});"));
	}

	static final CssResourceReference CSS = new CssResourceReference(HugeRteBehaviorTest.class,
		"content.css");

	static final ResourceReference UPLOAD = new ResourceReference("image-upload")
	{
		private static final long serialVersionUID = 1L;

		@Override
		public IResource getResource()
		{
			return new ByteArrayResource("application/json", new byte[0]);
		}
	};

	private static final String CSS_URL = "\"content_css\":\"" + "/context/servlet/wicket/resource/" +
		"org.wicketstuff.hugerte.HugeRteBehaviorTest/content";

	@Test
	void writesResourceReferencesAsAbsolutePaths()
	{
		tester.startPage(new EditorPage(HugeRteConfig.defaults().withContentCss(CSS)));

		var html = tester.getLastResponseAsString();
		assertTrue(html.contains(CSS_URL), html);
	}

	@Test
	void writesResourceReferencesAsAbsolutePathsOnNestedMounts()
	{
		tester.getApplication().mountPage("admin/articles/edit", MountedEditorPage.class);
		tester.getApplication().mountResource("image-upload", UPLOAD);
		tester.startPage(MountedEditorPage.class);

		var html = tester.getLastResponseAsString();
		assertTrue(html.contains(CSS_URL), html);
		assertTrue(html.contains("\"images_upload_url\":\"/context/servlet/image-upload\""), html);
	}

	@Test
	void resolvesContextRelativeUrlsOutsideTheFilterPath()
	{
		tester.getApplication().mountPage("admin/articles/edit", ContextRelativeCssPage.class);
		tester.startPage(ContextRelativeCssPage.class);

		// Wicket renders the URL relative to the page; it must be written as the absolute path the
		// browser would resolve it to, not resolved again inside the filter path. (WicketTester's
		// mock request leaves the filter segment out of the prefix to the context path, so the
		// exact path differs from a servlet container.)
		var html = tester.getLastResponseAsString();
		assertTrue(html.matches("(?s).*\"content_css\":\"/context/[^\"]*css/editor\\.css\".*"), html);
		assertTrue(!html.contains("\"content_css\":\"/context/servlet/admin/"), html);
	}

	@Test
	void marksTheEditorSavedBeforeReplacingComponents()
	{
		var page = new EditorPage(HugeRteConfig.defaults());
		tester.startPage(page);
		tester.newFormTester("form").setValue("text", "<p>saved</p>");

		tester.executeAjaxEvent("form:save", "click");

		// Wicket's client runs <priority-evaluate> before it replaces any <component>
		var ajax = tester.getLastResponseAsString();
		int priority = ajax.indexOf("<priority-evaluate>");
		assertTrue(priority >= 0, ajax);
		String priorityScripts = ajax.substring(priority, ajax.indexOf("</priority-evaluate>", priority));
		assertTrue(priorityScripts.contains(
			"Wicket.HugeRte.markSaved(\"" + page.text.getMarkupId() + "\");"), ajax);
		assertTrue(ajax.contains("<component id=\"" + page.text.getMarkupId() + "\""), ajax);
	}

	@Test
	void rejectsOtherTagsThanTextarea()
	{
		assertThrows(MarkupException.class, () -> tester.startPage(new DivPage()));
	}

	@Test
	void marksTheEditorSavedFromAjax()
	{
		var page = new EditorPage(HugeRteConfig.defaults());
		tester.startPage(page);

		tester.clickLink("saved");

		assertTrue(tester.getLastResponseAsString()
			.contains("Wicket.HugeRte.markSaved(\"" + page.text.getMarkupId() + "\");"));
	}

	@Test
	void focusesTheEditorFromAjax()
	{
		var page = new EditorPage(HugeRteConfig.defaults());
		tester.startPage(page);

		tester.clickLink("focus");

		assertTrue(tester.getLastResponseAsString()
			.contains("Wicket.HugeRte.focus(\"" + page.text.getMarkupId() + "\");"));
	}

	@Test
	void canBeReferencedDuringApplicationInit()
	{
		var early = new WicketTester(new MockApplication()
		{
			@Override
			protected void init()
			{
				super.init();
				getHeaderContributorListeners().add(response -> response
					.render(JavaScriptHeaderItem.forReference(HugeRteJavaScriptReference.get())));
			}
		});
		try
		{
			early.startPage(new EditorPage(HugeRteConfig.defaults()));
			assertTrue(early.getLastResponseAsString().contains("webjars/hugerte/"));
		}
		finally
		{
			early.destroy();
		}
	}

	@Test
	void usesTheMinifiedEditorInDeployment()
	{
		var deployment = new WicketTester(new MockApplication()
		{
			@Override
			public RuntimeConfigurationType getConfigurationType()
			{
				return RuntimeConfigurationType.DEPLOYMENT;
			}
		});
		try
		{
			deployment.startPage(new EditorPage(HugeRteConfig.defaults()));
			var html = deployment.getLastResponseAsString();
			assertTrue(html.matches("(?s).*webjars/hugerte/[^\"]*/hugerte(-ver-[0-9]+)?\\.min\\.js.*"), html);
		}
		finally
		{
			deployment.destroy();
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

	public static class MountedEditorPage extends EditorPage
	{
		private static final long serialVersionUID = 1L;

		public MountedEditorPage()
		{
			super(HugeRteConfig.defaults().withContentCss(CSS).with("images_upload_url", UPLOAD));
		}
	}

	public static class ContextRelativeCssPage extends EditorPage
	{
		private static final long serialVersionUID = 1L;

		public ContextRelativeCssPage()
		{
			super(HugeRteConfig.defaults().withContentCss(
				new UrlResourceReference(Url.parse("css/editor.css")).setContextRelative(true)));
		}
	}

	public static class DivPage extends WebPage implements IMarkupResourceStreamProvider
	{
		private static final long serialVersionUID = 1L;

		DivPage()
		{
			add(new WebMarkupContainer("div").add(new HugeRteBehavior()));
		}

		@Override
		public IResourceStream getMarkupResourceStream(MarkupContainer container,
			Class<?> containerClass)
		{
			return new StringResourceStream("<html><body><div wicket:id=\"div\"></div></body></html>");
		}
	}

	public static class EditorPage extends WebPage implements IMarkupResourceStreamProvider
	{
		private static final long serialVersionUID = 1L;

		final Form<Void> form;

		final TextArea<String> text;

		EditorPage(HugeRteConfig config)
		{
			this(new HugeRteBehavior(config));
		}

		EditorPage(HugeRteBehavior behavior)
		{
			form = new Form<Void>("form");
			add(form);
			text = new TextArea<>("text", Model.of("<p>hello</p>"));
			text.add(behavior);
			form.add(text);
			form.add(new AjaxButton("save")
			{
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target)
				{
					HugeRteBehavior.markSaved(target, text);
					target.add(text);
				}
			});
			add(new AjaxLink<Void>("saved")
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target)
				{
					HugeRteBehavior.markSaved(target, text);
				}
			});
			add(new AjaxLink<Void>("focus")
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target)
				{
					HugeRteBehavior.focus(target, text);
				}
			});
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
				<form wicket:id="form"><textarea wicket:id="text"></textarea><button wicket:id="save">save</button></form>
				<a wicket:id="refresh">refresh</a>
				<a wicket:id="focus">focus</a>
				<a wicket:id="saved">saved</a>
				</body></html>""");
		}
	}
}
