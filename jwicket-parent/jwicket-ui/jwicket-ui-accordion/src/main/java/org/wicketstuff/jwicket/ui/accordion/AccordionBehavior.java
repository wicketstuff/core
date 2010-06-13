package org.wicketstuff.jwicket.ui.accordion;


import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.Request;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.jwicket.ComponentFinder;
import org.wicketstuff.jwicket.IStyleResolver;
import org.wicketstuff.jwicket.JQueryCssResourceReference;
import org.wicketstuff.jwicket.JQueryJavascriptResourceReference;
import org.wicketstuff.jwicket.JsMap;
import org.wicketstuff.jwicket.ui.AbstractJqueryUiEmbeddedBehavior;


public class AccordionBehavior extends AbstractJqueryUiEmbeddedBehavior implements IStyleResolver {

	private static final long serialVersionUID = 1L;

	public static final JQueryJavascriptResourceReference uiAccordionJs = new JQueryJavascriptResourceReference(AccordionBehavior.class, "jquery.ui.accordion.min.js");

	protected JsMap options = new JsMap();

	public JsMap getOptions() {
		return options;
	}

	public AccordionBehavior() {
		super(
				AbstractJqueryUiEmbeddedBehavior.jQueryUiWidgetJs,
				uiAccordionJs
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

			String newHeader = "";
			String oldHeader = "";
			String newContent = "";
			String oldContent = "";

			newHeader = request.getParameter("newHeader");
			oldHeader = request.getParameter("oldHeader");
			newContent = request.getParameter("newContent");
			oldContent = request.getParameter("oldContent");

			
			System.out.println("---------- respond ----------------------------");
			System.out.println("\toldHeader = " + oldHeader);
			System.out.println("\tnewHeader = " + newHeader);
			System.out.println("\toldContent = " + oldContent);
			System.out.println("\tnewContent = " + newContent);
			
			
			
			
			if (newContent != null && newHeader != null) {
				ComponentFinder finder = new ComponentFinder(newHeader);
				component.getPage().visitChildren(finder);
				Component newHeaderComponent = finder.getFoundComponent();
System.out.println("\tnewHeaderComponent for ID '" + newHeader + "' = " + ((newHeaderComponent==null)?"<null>":newHeaderComponent));
				finder = new ComponentFinder(newContent);
				component.getPage().visitChildren(finder);
				Component newContentComponent = finder.getFoundComponent();
System.out.println("\tnewContentComponent for ID '" + newContent + "' = " + ((newContentComponent==null)?"<null>":newContentComponent));

				onExpand(target, newHeaderComponent, newContentComponent);
			}
			
			if (oldContent != null && oldHeader != null) {
				onCollapse(target, oldHeader, oldContent);
			}
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
							"'" +
							"); }"));


		JsBuilder builder = new JsBuilder();

		builder.append("jQuery('#" + getComponent().getMarkupId() + "').accordion(");
		builder.append("{");
		builder.append(options.toString(rawOptions));
		builder.append("}");
		builder.append(")");
				
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


	
	
	protected void onExpand(final AjaxRequestTarget target, final Component headerToExpand, final Component contentToExpand) {
		
	}


	protected void onCollapse(final AjaxRequestTarget target, final String headerToExpandId, final String contentToExpandId) {
		
	}

}
