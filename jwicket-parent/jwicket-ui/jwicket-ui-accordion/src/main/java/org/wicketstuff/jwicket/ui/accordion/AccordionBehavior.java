package org.wicketstuff.jwicket.ui.accordion;


import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.Request;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.jwicket.IStyleResolver;
import org.wicketstuff.jwicket.JQueryCssResourceReference;
import org.wicketstuff.jwicket.JQueryJavascriptResourceReference;
import org.wicketstuff.jwicket.ui.AbstractJqueryUiEmbeddedBehavior;
import org.wicketstuff.jwicket.ui.sortable.SortableBehavior;


public class AccordionBehavior extends AbstractJqueryUiEmbeddedBehavior implements IStyleResolver {

	private static final long serialVersionUID = 1L;

	public static final JQueryJavascriptResourceReference uiAccordionJs = new JQueryJavascriptResourceReference(AccordionBehavior.class, "jquery.ui.accordion.min.js");

	protected JsMap options = new JsMap();

	public AccordionBehavior() {
		super(
//AbstractJqueryUiEmbeddedBehavior.jQueryUiMouseJs,
				AbstractJqueryUiEmbeddedBehavior.jQueryUiWidgetJs,
				uiAccordionJs
//,SortableBehavior.uiSortableJs
			);
			addCssResources(getCssResources());
	}
	
	/**
	 * Handles the event processing during resizing.
	 */
	@Override
	protected void respond(final AjaxRequestTarget target) {
		Component component = getComponent();
		Request request;
		if (component != null && (request = component.getRequest()) != null) {
			EventType eventType = EventType.stringToType(request.getParameter(EventType.IDENTIFIER));

			String id1 = "";
			String id2 = "";
			String id3 = "";
			String id4 = "";

			id1 = request.getParameter("newHeader");
			id2 = request.getParameter("oldHeader");
			id3 = request.getParameter("newContent");
			id4 = request.getParameter("oldContent");


			/*
			if (component instanceof IResizable) {
				IResizable resizableComponent = (IResizable)component;
				if (eventType == EventType.RESIZE_END)
					resizableComponent.onResized(target, top, left, width, height, originalTop, originalLeft, originalWidth, originalHeight, new SpecialKeys(request));
				else if (eventType == EventType.RESIZE_START)
					resizableComponent.onResizeStart(target, top, left, width, height, new SpecialKeys(request));
				else if (eventType == EventType.RESIZE)
					resizableComponent.onResize(target, top, left, width, height, new SpecialKeys(request));
			}

			if (eventType == EventType.RESIZE_END)
				onResized(target, top, left, width, height, originalTop, originalLeft, originalWidth, originalHeight, new SpecialKeys(request));
			else if (eventType == EventType.RESIZE_START)
				onResizeStart(target, top, left, width, height, new SpecialKeys(request));
			else if (eventType == EventType.RESIZE)
				onResize(target, top, left, width, height, new SpecialKeys(request));
			*/
			
			System.out.println("----- respond: component = " + component);
			System.out.println("id1 = " + id1);
			System.out.println("id2 = " + id2);
			System.out.println("id3 = " + id3);
			System.out.println("id4 = " + id4);
		}
	}

	
	
	@Override
	protected JsBuilder getJsBuilder() {
		options.put(EventType.CHANGE.eventName,
			new JsFunction("function(ev,ui) { wicketAjaxGet('" +
							this.getCallbackUrl() +
							"&newHeader='+jQuery(ui.newHeader).attr('id')" +
							"+'&oldHeader='+jQuery(ui.oldHeader).attr('id')" +
							"+'&newContent='+jQuery(ui.newContent).attr('id')" +
							"+'&oldContent='+jQuery(ui.oldContent).attr('id')" +
							"+'&" + EventType.IDENTIFIER + "=" + EventType.CHANGE +
//							"&keys='+jQuery.jWicketSpecialKeysGetPressed()" +
							"'" +
							"); }"));


		JsBuilder builder = new JsBuilder();

		builder.append("jQuery('#" + getComponent().getMarkupId() + "').accordion(");
		builder.append("{");
		builder.append(options.toString(rawOptions));
		builder.append("}");
		builder.append(")");
		

//builder.append(".sortable({axis: 'y', handle: 'h3', stop: function(event, ui) { stop = true; } })");
		
		
		
				
		builder.append(";");

		return builder;
	}
	
	
	private enum EventType implements Serializable {

		UNKNOWN("*"),
		CHANGE("change"),
		CHANGE_START("changestart");

		public static final String IDENTIFIER="wicketAccordionEvent";

		private final String eventName;
		
		private EventType(final String eventName) {
			this.eventName = eventName;
		}
		
		public String getEventName() {
			return this.eventName;
		}
		
		public static EventType stringToType(final String s) {
			for (EventType t : EventType.values())
				if (t.getEventName().equals(s))
					return t;
			return UNKNOWN;
		}
		
		public String toString() {
			return this.eventName;
		}
	}

	
	
	@Override
	public JQueryCssResourceReference[] getCssResources() {
		return new JQueryCssResourceReference[] {
			AbstractJqueryUiEmbeddedBehavior.jQueryUiCss,
			AbstractJqueryUiEmbeddedBehavior.jQueryUiThemeCss,
			AbstractJqueryUiEmbeddedBehavior.jQueryUiAccordionCss
		};
	}


}
