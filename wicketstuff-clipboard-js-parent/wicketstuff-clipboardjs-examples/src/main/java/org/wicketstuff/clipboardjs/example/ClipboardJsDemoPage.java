package org.wicketstuff.clipboardjs.example;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.clipboardjs.ClipboardJsBehavior;

/**
 * Demo page of {@link ClipboardJsBehavior}
 */
public class ClipboardJsDemoPage extends WebPage {
	private static final long serialVersionUID = 1L;

	public ClipboardJsDemoPage(final PageParameters parameters) {
		super(parameters);

		TextArea target = new TextArea("target");
		add(target);

		final WebMarkupContainer copyBtn = new WebMarkupContainer("copyBtn");
		final ClipboardJsBehavior clipboardJsBehavior = new ClipboardJsBehavior();
		copyBtn.add(clipboardJsBehavior);
		clipboardJsBehavior
				// set Wicket Component (e.g. TextField or TextArea) as a target
				.setTarget(target)

				// set the target by using CSS selector. This way there is no need to make the target a Wicket Component
				// .setTarget(".target")

				// set the action to 'CUT'. Works only on non-disabled <input> and <textarea> targets
				// .setAction(ClipboardJsBehavior.Action.CUT)

				// set predefined text to be copied. This has higher priority than the target
				// .setText(Model.of("Some predefined text"))
		;
		add(copyBtn);
	}
}
