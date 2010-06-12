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
	
	public static final String SORTABLE_COMPONENT_ID = "sortable";

	SortableBehavior sortableBehavior;
	
	private WebMarkupContainer sortable;

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
		
		sortable = new WebMarkupContainer(SORTABLE_COMPONENT_ID);
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

	
	
	/**
	 * Sets the 'placeholder' property for this sortable. Please consult the
	 * jquery documentation for a detailled description of this property.
	 * @param value the placeholder css class name
	 * @return this object
	 */
	public Sortable<T> setPlaceholder(final String value) {
		if (value == null)
			sortableBehavior.getOptions().remove("placeholder");
		else
			sortableBehavior.getOptions().put("placeholder", value);
		return this;
	}
	public Sortable<T> setPlaceholder(final AjaxRequestTarget target, final String value) {
		setPlaceholder(value);
		target.appendJavascript("jQuery('#" + sortable.getMarkupId() + "').sortable('option','placeholder','" + value + "');");
		return this;
	}

	/**
	 * Sets the default 'placeholder' property for this sortable: 'ui-state-highlight'. Please consult the
	 * jquery documentation for a detailled description of this property.
	 * @return this object
	 */
	public Sortable<T> setPlaceholder() {
		sortableBehavior.getOptions().put("placeholder", "ui-state-highlight");
		return this;
	}
	public Sortable<T> setPlaceholder(final AjaxRequestTarget target) {
		setPlaceholder("ui-state-highlight");
		target.appendJavascript("jQuery('#" + sortable.getMarkupId() + "').sortable('option','placeholder','ui-state-highlight');");
		return this;
	}
	
	
	

}
