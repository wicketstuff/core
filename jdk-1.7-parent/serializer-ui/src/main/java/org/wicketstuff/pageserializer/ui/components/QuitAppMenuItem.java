package org.wicketstuff.pageserializer.ui.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

public class QuitAppMenuItem extends MenuItem {
	
	public QuitAppMenuItem() {
		super("Quuiiit");
		//setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_ANY));
		setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Fire event: quit");
//				QuitAppMenuItem.this.fireEvent(new QuitEventFx());
			}
		});
	}
	
	
}