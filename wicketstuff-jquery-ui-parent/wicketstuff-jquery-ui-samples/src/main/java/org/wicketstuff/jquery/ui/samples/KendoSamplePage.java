/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.wicketstuff.kendo.ui.widget.tabs.TabbedPanel;

public abstract class KendoSamplePage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	/**
	 * Kendo themes bundled by the samples app (see the theme dependencies in pom.xml). Each entry matches a {@code <theme>-main.css} shipped in the
	 * {@code org.wicketstuff.kendo.ui.theme} package of the corresponding {@code wicketstuff-kendo-ui-theme-*} module.
	 */
	private static final List<String> THEMES = Arrays.asList("default", "bootstrap", "material", "meridian");

	/**
	 * Scope class used to resolve the {@code <theme>-main.css} resources. All theme modules place their CSS in the {@code org.wicketstuff.kendo.ui.theme}
	 * package under distinct file names, so a single scope class in that package addresses every theme regardless of which theme jar happens to provide
	 * the (identically named) {@code Initializer} on the classpath.
	 */
	private static final Class<?> THEME_SCOPE = org.wicketstuff.kendo.ui.theme.Initializer.class;

	public KendoSamplePage()
	{
		this.add(new TabbedPanel("sources", this.newSourceTabList()));

		this.add(newThemeForm("theme-form"));
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		// contribute the session-selected theme stylesheet (the global theme reference is cleared in SampleApplication)
		String theme = SampleSession.get().getKendoTheme();
		response.render(CssHeaderItem.forReference(new CssResourceReference(THEME_SCOPE, theme + "-main.css")));
	}

	// Factories //

	private static Form<Void> newThemeForm(String id)
	{
		Form<Void> form = new Form<Void>(id); // NOSONAR

		IModel<String> model = LambdaModel.of(() -> SampleSession.get().getKendoTheme(), value -> SampleSession.get().setKendoTheme(value));

		DropDownChoice<String> choice = new DropDownChoice<String>("theme", model, THEMES); // NOSONAR
		choice.add(new OnChangeAjaxBehavior() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target)
			{
				// the theme stylesheet is contributed in renderHead(), so reload the page to swap it in
				target.appendJavaScript("window.location.reload();");
			}
		});

		form.add(choice);

		return form;
	}
}
