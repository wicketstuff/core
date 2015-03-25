package org.wicketstuff.pageserializer.ui.components;

import org.wicketstuff.pageserializer.ui.events.AppEvent;

import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

import com.google.inject.Inject;

public class MainPanelFx extends VBox {
	
	@Inject
	public MainPanelFx(MainMenu menu) {
		getChildren().add(menu);
		
		addEventHandler(AppEvent.APP, new EventHandler<AppEvent>() {
			@Override
			public void handle(AppEvent event) {
//				if (event instanceof InspectUrlEventFx) {
//					EnterUrlLayout.this.fireEvent(new ShowSceneWithEventFx(inspectUrl.get()));
//				}
			}
		});
	}
}