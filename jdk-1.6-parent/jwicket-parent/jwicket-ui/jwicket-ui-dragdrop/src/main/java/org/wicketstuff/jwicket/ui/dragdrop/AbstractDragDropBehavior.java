package org.wicketstuff.jwicket.ui.dragdrop;


import org.wicketstuff.jwicket.JQueryResourceReference;
import org.wicketstuff.jwicket.ui.AbstractJqueryUiEmbeddedBehavior;

import java.io.Serializable;


public abstract class AbstractDragDropBehavior extends AbstractJqueryUiEmbeddedBehavior {

	private static final long serialVersionUID = 1L;


	public AbstractDragDropBehavior(final JQueryResourceReference... requiredLibraries) {
		super(requiredLibraries);
	}


	/**
	 * This is an internal enumeration of events happening during dragging and dropping.
	 */
	protected enum EventType implements Serializable {

		UNKNOWN("*"),
		DRAG_START("start"),
		DRAG("drag"),
		DRAG_END("stop"),
		DROP("drop"),
		DROP_ACTIVATE("dropActivate"),
		DROP_DEACTIVATE("dropDeactivate");

		public static final String IDENTIFIER="wicketDragDropEvent";

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

}
