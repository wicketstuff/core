package org.wicketstuff.pageserializer.ui;

import java.util.ArrayList;

import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.common.analyze.ImmutableTree;
import org.wicketstuff.pageserializer.common.analyze.ObjectId;

public class StartWithDummyGenerator {

	public static void main(String[] args) throws Exception
	{
		final UI ui = SerializerUIStarter.startUI();
		
		Thread generator = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					ui.treeProcessor().process(generated());
					do {
						Thread.sleep(60000);
						ui.treeProcessor().process(generated());
					} while (true);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					throw new RuntimeException(e);
				}
			}
		});
		generator.setDaemon(true);
		generator.start();
	}

	protected static ISerializedObjectTree generated() {
		return new ImmutableTree(new ObjectId(1), String.class, "foo", 17, new ArrayList<ISerializedObjectTree>());
	}
}
