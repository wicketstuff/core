package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report;

import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTreeProcessor;


public class RenderTreeProcessor implements ISerializedObjectTreeProcessor {

	private final IReportOutput _reportOutput;
	private final IReportRenderer _renderer;
	
	public RenderTreeProcessor(IReportOutput reportOutput,IReportRenderer renderer) {
		_reportOutput = reportOutput;
		_renderer = renderer;
	}

	@Override
	public void process(ISerializedObjectTree tree) {
		_reportOutput.write(tree, _renderer);
	}

}
