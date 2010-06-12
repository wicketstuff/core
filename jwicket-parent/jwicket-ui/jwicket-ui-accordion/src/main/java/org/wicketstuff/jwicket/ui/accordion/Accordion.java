package org.wicketstuff.jwicket.ui.accordion;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.jwicket.ui.sortable.SortableBehavior;

public class Accordion<T extends Serializable> extends Panel {

	private static final long serialVersionUID = 1L;

	public Accordion(final String id, final List<T> list) {
		super(id);
		ListView<T> repeater = new ListView<T>("repeater", list) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void populateItem(final ListItem<T> item) {
				WebMarkupContainer headerAnchor = new WebMarkupContainer("headerAnchor");
				headerAnchor.setOutputMarkupId(true);
				headerAnchor.add(getHeader("header", item.getModelObject()).setOutputMarkupId(true));					
				item.add(headerAnchor);
				item.add(getContent("content", item.getModelObject()).setOutputMarkupId(true));
				item.setRenderBodyOnly(true);
			}
		};

		WebMarkupContainer accordion = new WebMarkupContainer("accordion");
		accordion.setOutputMarkupId(true);
		accordion.setRenderBodyOnly(false);
		accordion.add(repeater);
		accordion.add(new AccordionBehavior());
		accordion.add(new SortableBehavior()); // experimental
		add(accordion);
	}
	
	
	
	protected Component getHeader(final String id, final T t) {
		return new Label(id, "Header: " + String.valueOf(t));
	}

	
	protected Component getContent(final String id, final T t) {
		return new Label(id, "Content: " + String.valueOf(t));
	}
	

}
