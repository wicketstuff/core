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
import com.google.inject.Inject;
import com.google.inject.Module;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTreeProcessor;
import org.wicketstuff.pageserializer.ui.components.ControlableGuiceApplication;
import org.wicketstuff.pageserializer.ui.components.MainPanelFx;
import org.wicketstuff.pageserializer.ui.events.AppEvent;
import org.wicketstuff.pageserializer.ui.events.QuitEventFx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author mosmann
 */
public class SerializerUI extends ControlableGuiceApplication {

	private static AtomicReference<SerializerUI> runningInstance=new AtomicReference<SerializerUI>();
	private final Logger LOG=LoggerFactory.getLogger(UIAdapter.class);
	
	UIAdapter ui=new UIAdapter();
	Stage currentStage;
	
	@Inject
	MainPanelFx mainPanel;
	
	public SerializerUI() {
		runningInstance.compareAndSet(null, this);
		synchronized (runningInstance) {
			runningInstance.notifyAll();
		}
	}
	
    @Override
    public void init(List<Module> modules) throws Exception {
        
    }

	protected void closeUI() {
		try {
			if (currentStage!=null) {
				currentStage.hide();
			}
			stop();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
    @Override
    public void start(final Stage stage) throws Exception {
    	currentStage=stage;
stage.setScene(new Scene(mainPanel, 800, 600));
        stage.show();
        
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
//        		if (event instanceof ShowSceneWithEventFx) {
//        			stage.setScene(new Scene(((ShowSceneWithEventFx) event).root()));
//        		}
        		if (!found) {
	        		System.out.println("What?"+event);
	        	}
        		
        	}
		});    
                
                }

	class UIAdapter implements UI {

		@Override
		public void stopUI() {
			LOG.warn("try to stop UI");
			
			runningInstance.compareAndSet(SerializerUI.this, null);
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					SerializerUI.this.closeUI();
				}
			});
		}

		@Override
		public ISerializedObjectTreeProcessor treeProcessor() {
			return new ISerializedObjectTreeProcessor() {
				
				@Override
				public void process(ISerializedObjectTree tree) {
					System.out.println("Process: "+tree);
				}
			};
		}

	}
	
	public static UI start(final String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Application.launch(SerializerUI.class,args);				
				
			}
		}).start();
//		Platform.runLater(new Runnable() {
//			
//			@Override
//			public void run() {
//				Application.launch(SerializerUI.class,args);				
//			}
//		});
		synchronized (runningInstance) {
			try {
				runningInstance.wait();
				return runningInstance.get().ui;
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			}
		}
		
//		Thread thread = new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				SerializerUI.launch(args);
//			}
//		});
////		thread.setDaemon(true);
//		thread.start();
//		synchronized (runningInstance) {
//			try {
//				runningInstance.wait();
//				return runningInstance.get().ui;
//			} catch (InterruptedException e) {
//				Thread.currentThread().interrupt();
//				throw new RuntimeException("could not get UI");
//			}
//		}
	}


	   private void walkUp(Node node, Consumer<Node> visitor) {
			visitor.accept(node);
	    	Parent parent = node.getParent();
	    	if (parent!=null) {
	    		walkUp(parent, visitor);
	    	}
	    	else {
	    		Scene scene = node.getScene();
	    	}
	    }
	    
	    private void walkTree(Node node, Consumer<Node> visitor) {
	        visitor.accept(node);
	        if (node instanceof Parent) {
	            for (Node n : ((Parent) node).getChildrenUnmodifiable()) {
	            	walkTree(n, visitor);
	            }
	        }
	    }

	    static interface Consumer<T> {

			void accept(T element);
	    	
	    }
	    
	    static class MyPane extends Pane {
	    	
	    	
	    }

}
