package org.wicketstuff.pageserializer.kryo2.inspecting.analyze;

import org.wicketstuff.pageserializer.kryo2.inspecting.ThreadLocalContextSerializationListener;

public class AnalyzingSerializationListener extends ThreadLocalContextSerializationListener<ObjectTreeTracker>
{
	private final IObjectLabelizer labelizer;
	private final ISerializedObjectTreeProcessor treeProcessor;

	public AnalyzingSerializationListener(IObjectLabelizer labelizer,
		ISerializedObjectTreeProcessor treeProcessor)
	{
		this.labelizer = labelizer;
		this.treeProcessor = treeProcessor;
	}

	@Override
	protected ObjectTreeTracker createContext(Object object)
	{
		return new ObjectTreeTracker(labelizer, object);
	}
	
	@Override
	public void begin(ObjectTreeTracker treeTracker, Object object)
	{
	}

	@Override
	public void before(ObjectTreeTracker treeTracker, int position, Object object)
	{
		if (object!=null) treeTracker.newItem(position, object);
	}

	@Override
	public void after(ObjectTreeTracker treeTracker, int position, Object object)
	{
		if (object!=null) treeTracker.closeItem(position, object);
	}

	@Override
	public void end(ObjectTreeTracker treeTracker, Object object)
	{
		treeProcessor.process(treeTracker.end(object));
	}

}
