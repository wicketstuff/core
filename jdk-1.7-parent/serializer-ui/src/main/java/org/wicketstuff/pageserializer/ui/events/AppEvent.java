package org.wicketstuff.pageserializer.ui.events;

import javafx.event.Event;
import javafx.event.EventType;

public abstract class AppEvent extends Event {
	public static final EventType<AppEvent> APP = new EventType<>(ANY, "APP");
	
	public AppEvent() {
		super(APP);
	}
}