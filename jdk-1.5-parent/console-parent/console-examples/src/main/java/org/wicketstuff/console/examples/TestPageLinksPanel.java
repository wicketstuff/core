package org.wicketstuff.console.examples;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;

public class TestPageLinksPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public TestPageLinksPanel(String id) {
		super(id);

		RepeatingView r = new RepeatingView("testPageLinks");
		add(r);
		addLink(r, ClojureEngineTestPage.class);
		addLink(r, GroovyEngineTestPage.class);

	}

	private void addLink(RepeatingView r, Class<? extends Page> pageClass) {
		BookmarkablePageLink<String> link1 = new BookmarkablePageLink<String>(
				r.newChildId(), pageClass);
		link1.add(new Label("label", Model.of(pageClass.getSimpleName())));
		r.add(link1);
	}

}
