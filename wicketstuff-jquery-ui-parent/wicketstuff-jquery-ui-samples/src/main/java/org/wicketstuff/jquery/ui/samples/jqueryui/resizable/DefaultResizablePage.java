package org.wicketstuff.jquery.ui.samples.jqueryui.resizable;

import org.wicketstuff.jquery.core.Options;
import org.wicketstuff.jquery.ui.interaction.resizable.ResizableAdapter;
import org.wicketstuff.jquery.ui.interaction.resizable.ResizableBehavior;

public class DefaultResizablePage extends AbstractResizablePage
{
	private static final long serialVersionUID = 1L;

	public DefaultResizablePage()
	{
		Options options = new Options();
		options.set("minWidth", 200);
		options.set("maxWidth", 720);
		options.set("minHeight", 100);
		options.set("maxHeight", 300);

		this.add(new ResizableBehavior("#resizable", options, new ResizableAdapter()));
	}
}
