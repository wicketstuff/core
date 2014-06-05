package org.wicketstuff.event.annotation;

public class AnnotationEventDispatcherConfig {
	
	private Class<?> eventFilter = null;
	
	public AnnotationEventDispatcherConfig() {
		super();
	}

	public Class<?> getEventFilter() {
		return eventFilter;
	}

	public void setEventFilter(final Class<?> eventFilter) {
		this.eventFilter = eventFilter;
	}
	
	public AnnotationEventDispatcherConfig eventFilter(final Class<?> eventFilter) {
		setEventFilter(eventFilter);
		return this;
	}
	
}
