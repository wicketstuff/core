/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.jqueryui.test;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.jquery.ui.form.palette.Palette;
import org.wicketstuff.jquery.ui.samples.TemplatePage;

public class PalettePage extends TemplatePage
{
	private static final long serialVersionUID = 1L;

	public PalettePage()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// Palette //
		form.add(new Palette<String>("palette", Model.ofList(new ArrayList<String>()), Model.ofList(Arrays.asList("Choice #1", "Choice #2", "Choice #3", "Choice #4")), new ChoiceRenderer<String>(), 6, true));
	}
}
