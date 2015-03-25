package org.wicketstuff.pageserializer.ui.components;

import org.wicketstuff.pageserializer.ui.events.AppEvent;
import org.wicketstuff.pageserializer.ui.events.QuitEventFx;

import com.google.inject.Inject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class MainMenu extends MenuBar {

	@Inject
	public MainMenu(QuitAppMenuItem quitItemX) {
		final Menu fileMenu = new Menu("File");
		MenuItem quitItem = new MenuItem("Quit");
		quitItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_ANY));
		quitItem.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Fire event: quit");
				fireEvent(new QuitEventFx());
			}
		});
		fileMenu.getItems().add(quitItem);
		fileMenu.getItems().add(quitItemX);
		
		MenuItem about = new MenuItem("About");
		about.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Fire event: about");
				fireEvent(new Foo());
			}
		});
		fileMenu.getItems().add(about);
		final Menu inspectMenu = new Menu("Options");
		final Menu helpMenu = new Menu("Help");

		getMenus().addAll(fileMenu, inspectMenu, helpMenu);
	}
	
	static class Foo extends AppEvent {
		
	}

}