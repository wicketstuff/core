package org.wicketstuff.minis;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.wicketstuff.minis.behavior.mootip.MootipPanel;

public class MooPanel extends MootipPanel
{
	private static final long serialVersionUID = 1L;

	public MooPanel()
	{
		super();
		add(new Label("counter", new AbstractReadOnlyModel<String>()
		{
			private static final long serialVersionUID = 1L;

			private int counter = 0;

			@Override
			public String getObject()
			{
				counter++;
				return counter + "";
			}
		}));
	}
}
