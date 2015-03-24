package org.wicketstuff.pageserializer.ui;

import org.wicketstuff.pageserializer.common.analyze.ISerializedObjectTreeProcessor;

public interface UI {

	public void stopUI();

	public ISerializedObjectTreeProcessor treeProcessor();
}
