package org.wicketstuff.jquery.ui.samples.jqueryui.plugins;

import org.wicketstuff.jquery.ui.plugins.emoticons.EmoticonsBehavior;
import org.wicketstuff.jquery.ui.samples.JQuerySamplePage;

public class EmoticonsPage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	public EmoticonsPage()
	{
		this.add(new EmoticonsBehavior("#comments"));
	}
}
