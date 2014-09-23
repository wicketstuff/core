package org.wicketstuff.jwicket.ui.accordion;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.io.Serializable;
import java.util.List;


/**	This is the base class for all variations of jQuery's Accordion effect.
 *
 *	The general usage:
 *	<br/>
 *	HTML:
<pre>
	&lt;div wicket:id="accordion" /div&gt;
</pre>
 *
 *	Java:
<pre>
	class MyEntity implements Serializable {
	   ...
	}

	IModel<List<MyEntity>> listModel;

	add(new AbstractAccordion("accordion", listModel) {
		protected abstract Component getHeader(final String id, final IModel<T> t) {
		   // Return a Wicket Component for the header part
		}

		protected abstract Component getContent(final String id, final IModel<T> t) {
		   // Return a Wicket Component for the content part
		}

	});
</pre>
 *
 *	Customizing the behavior of an Accordion is done throug the associated
 *	{@link AccordionBehavior}. You may obtain the associated {@link AccordionBehavior}
 *	through {@link #getAccordionBehavior()}.
 */
public abstract class AbstractAccordion<T extends Serializable> extends Panel {

	private static final long serialVersionUID = 1L;

	protected final WebMarkupContainer accordion;

	protected final AccordionBehavior accordionBehavior;

	public AbstractAccordion(final String id, final IModel<? extends List<T>> list) {
		this(id, list, -1);
	}

	public AbstractAccordion(final String id, final IModel<? extends List<T>> list, final int expanded) {
		super(id, list);
		
		ListView<T> repeater = new ListView<T>("repeater", list) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void populateItem(final ListItem<T> item) {
				WebMarkupContainer headerAnchor = new WebMarkupContainer("headerAnchor");
				headerAnchor.setOutputMarkupId(true);
				headerAnchor.add(getHeader("header", item.getModel(), item.getIndex()).setOutputMarkupId(true));					
				item.add(headerAnchor);

				WebMarkupContainer jQueryContentAnchor = new WebMarkupContainer("jQueryContentAnchor");
				jQueryContentAnchor.setOutputMarkupId(true);
				item.add(jQueryContentAnchor);

				WebMarkupContainer contentAnchor = new WebMarkupContainer("contentAnchor");
				contentAnchor.setOutputMarkupId(true);
				contentAnchor.add(getContent("content", item.getModel(), item.getIndex()).setOutputMarkupId(true));					
				jQueryContentAnchor.add(contentAnchor);

				item.setRenderBodyOnly(true);
			}
		};

		accordion = new WebMarkupContainer("accordion");
		accordion.setOutputMarkupId(true);
		accordion.setRenderBodyOnly(false);
		accordion.add(repeater);

		accordion.add(accordionBehavior = initAccordionBehavior());

		if (expanded >= 0)
			getAccordionBehavior().setActive(expanded);

		add(accordion);
	}


	abstract AccordionBehavior initAccordionBehavior();


	public final AccordionBehavior getAccordionBehavior() {
		return this.accordionBehavior;
	}


	protected abstract Component getHeader(final String id, final IModel<T> t, final int index);


	protected abstract Component getContent(final String id, final IModel<T> t, final int index);


	protected abstract void onExpand(final AjaxRequestTarget target, final Component headerToExpand, final Component contentToExpand, final int index);


	protected abstract void onCollapse(final AjaxRequestTarget target, final Component headerToExpand, final Component contentToExpand, final int index);

	
	public int getCurrentExpandedIndex() {
		return getAccordionBehavior().getCurrentExpandedIndex();
	}

}
