package org.wicketstuff.jwicket.ui.accordion;


import java.io.Serializable;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.wicketstuff.jwicket.ui.sortable.Sortable;


public abstract class AbstractAccordion<T extends Serializable> extends Panel {

	private static final long serialVersionUID = 1L;

	protected final WebMarkupContainer accordion;
	
	protected final AccordionBehavior accordionBehavior;
	
//	protected final T firstToExpand;

	public AbstractAccordion(final String id, final IModel<? extends List<T>> list) {
		super(id, list);
		
		ListView<T> repeater = new ListView<T>("repeater", list) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void populateItem(final ListItem<T> item) {
				WebMarkupContainer headerAnchor = new WebMarkupContainer("headerAnchor");
				headerAnchor.setOutputMarkupId(true);
				headerAnchor.add(getHeader("header", item.getModel()).setOutputMarkupId(true));					
				item.add(headerAnchor);

				WebMarkupContainer jQueryContentAnchor = new WebMarkupContainer("jQueryContentAnchor");
				jQueryContentAnchor.setOutputMarkupId(true);
				item.add(jQueryContentAnchor);

				WebMarkupContainer contentAnchor = new WebMarkupContainer("contentAnchor");
				contentAnchor.setOutputMarkupId(true);
				contentAnchor.add(getContent("content", item.getModel()).setOutputMarkupId(true));					
				jQueryContentAnchor.add(contentAnchor);

				item.setRenderBodyOnly(true);
			}
		};

		accordion = new WebMarkupContainer("accordion");
		accordion.setOutputMarkupId(true);
		accordion.setRenderBodyOnly(false);
		accordion.add(repeater);

		accordion.add(accordionBehavior = getAccordionBehavior());

		add(accordion);
	}


	protected abstract AccordionBehavior getAccordionBehavior();



	/**
	 * Sets the 'autoHeight' property for this accordion. Please consult the
	 * jquery documentation for a detailled description of this property.
	 * @param value the autoHeight value
	 * @return this object
	 */
	public AbstractAccordion<T> setAutoHeight(final boolean value) {
		accordionBehavior.getOptions().put("autoHeight", value);
		return this;
	}
	public AbstractAccordion<T> setAutoHeight(final AjaxRequestTarget target, final boolean value) {
		setAutoHeight(value);
		target.appendJavascript("jQuery('#" + accordion.getMarkupId() + "').accordion('option','autoHeight'," + value + ");");
		return this;
	}


	protected abstract Component getHeader(final String id, final IModel<T> t);


	protected abstract Component getContent(final String id, final IModel<T> t);

}
