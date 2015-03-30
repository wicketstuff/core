package org.wicketstuff.pageserializer.ui.components;

import java.util.concurrent.atomic.AtomicReference;

import javafx.stage.Stage;

import com.cathive.fx.guice.GuiceApplication;
import com.google.inject.Inject;

public abstract class ControlableGuiceApplication extends GuiceApplication {

	private static AtomicReference<ControlableGuiceApplication> runningInstance=new AtomicReference<ControlableGuiceApplication>();
		
	public ControlableGuiceApplication() {
		runningInstance.compareAndSet(null, this);
		synchronized (runningInstance) {
			runningInstance.notifyAll();
		}
	}

}
