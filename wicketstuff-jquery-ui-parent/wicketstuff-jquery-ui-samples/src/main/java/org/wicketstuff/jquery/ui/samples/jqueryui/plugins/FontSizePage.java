/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.jqueryui.plugins;

import org.wicketstuff.jquery.core.Options;
import org.wicketstuff.jquery.ui.plugins.FontSizeBehavior;
import org.wicketstuff.jquery.ui.samples.JQuerySamplePage;

public class FontSizePage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	public FontSizePage()
	{
		Options options = new Options();
		options.set("sizeChange", 3);

		this.add(new FontSizeBehavior("#paragraph", options));
	}
}
