package org.wicketstuff.jwicket.ui.sortable;


import java.io.Serializable;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;


public class Sortable<T extends Serializable> extends Panel {

	private static final long serialVersionUID = 1L;

	SortableBehavior sortableBehavior;

	public Sortable(final String id, final List<T> list) {
		super(id);
		ListView<T> repeater = new ListView<T>("repeater", list) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void populateItem(final ListItem<T> item) {
				WebMarkupContainer li = new WebMarkupContainer("li");
				li.setOutputMarkupId(true);
				Component content = getContent("content", item.getModel());
				content.setOutputMarkupId(true);
				li.add(content);
				item.add(li);
				item.setRenderBodyOnly(true);
			}
		};

		sortableBehavior = new SortableBehavior() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSorted(final AjaxRequestTarget target, final Component movedComponent, final int newPosition) {
				Sortable.this.onSorted(target, movedComponent, newPosition);
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void onReceived(final AjaxRequestTarget target, final Component movedComponent, final int newPosition, final Sortable<?> from) {
				Sortable.this.onReceived(target, movedComponent, newPosition, (Sortable<T>)from);
			}

			@Override
			protected void onRemoved(final AjaxRequestTarget target, final Component movedComponent) {
				Sortable.this.onRemoved(target, movedComponent);
			}
		};
		
		WebMarkupContainer sortable = new WebMarkupContainer("sortable");
		sortable.setOutputMarkupId(true);
		sortable.setRenderBodyOnly(false);
		sortable.add(repeater);
		sortable.add(sortableBehavior);
		add(sortable);
	}


	public void connectWith(final Sortable<T> other) {
		sortableBehavior.connectWith(other);
	}



	protected Component getContent(final String id, final IModel<T> model) {
		return new Label(id, String.valueOf(model));
	}
	

	protected void onSorted(final AjaxRequestTarget target, final Component movedComponent, final int newPosition) {}
	
	protected void onReceived(final AjaxRequestTarget target, final Component movedComponent, final int newPosition, final Sortable<T> from) { }

	protected void onRemoved(final AjaxRequestTarget target, final Component movedComponent) {}

	
}
