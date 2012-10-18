package org.wicketstuff.pageserializer.kryo2.inspecting;

public class AnalyzingSerializationListener implements ISerializationListener {

	ThreadLocal<ObjectTreeTracker> tracker=new ThreadLocal<ObjectTreeTracker>();
	private final IObjectLabelizer labelizer;
	private final ISerializedObjectTreeProcessor treeProcessor;
	
	public AnalyzingSerializationListener(IObjectLabelizer labelizer,ISerializedObjectTreeProcessor treeProcessor) {
		this.labelizer = labelizer;
		this.treeProcessor = treeProcessor;
	}
	
	@Override
	public void begin(Object object) {
		tracker.set(new ObjectTreeTracker(labelizer,object));
	}

	@Override
	public void before(int position, Object object) {
		ObjectTreeTracker treeTracker=tracker.get();
		treeTracker.newItem(position,object);
	}

	@Override
	public void after(int position, Object object) {
		ObjectTreeTracker treeTracker=tracker.get();
		treeTracker.closeItem(position,object);
	}

	@Override
	public void end(Object object) {
		ObjectTreeTracker treeTracker=tracker.get();
		treeProcessor.process(treeTracker.end(object));
		tracker.remove();
		
		
	}

}
