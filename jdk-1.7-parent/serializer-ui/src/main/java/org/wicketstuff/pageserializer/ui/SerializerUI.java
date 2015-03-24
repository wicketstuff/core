/*
 * Copyright 2015 mosmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.pageserializer.ui;

import com.cathive.fx.guice.GuiceApplication;
import com.google.inject.Module;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author mosmann
 */
public class SerializerUI extends GuiceApplication {

    @Override
    public void init(List<Module> modules) throws Exception {
        
    }

    @Override
    public void start(Stage stage) throws Exception {
stage.setScene(new Scene(new Button("foo"), 800, 600));
        stage.show();
        /*
        stage.addEventHandler(AppEvent.APP, new EventHandler<AppEvent>() {
        	@Override
        	public void handle(AppEvent event) {
        		boolean found=false;
        		
        		if (event instanceof QuitEventFx) {
        			found=true;
	        		System.out.println("Quit called... ");
	        		try {
	        			stage.hide();
						SerializerUI.this.stop();
		        		System.out.println("Quit done!");
					} catch (Exception e) {
						throw new RuntimeException("quit called",e);
					}
	        	}
        		if (event instanceof ShowSceneWithEventFx) {
        			stage.setScene(new Scene(((ShowSceneWithEventFx) event).root()));
        		}
        		if (!found) {
	        		System.out.println("What?"+event);
	        	}
        		
        	}
		});    
                */
                }

       public static void start(String[] args) {
        launch(args);
    }

}
