package org.wicketstuff.yui.markup.html.sortable;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;

public class Draggable extends AbstractBehavior
{
	private static final long serialVersionUID = 1L;
	Component component;

	public Draggable()
	{

	}

	@Override
	public void bind(final Component component)
	{
		this.component = component;
		component.setOutputMarkupId(true);
	}

	@Override
	public boolean getStatelessHint(Component component)
	{
		return false;
	}

	public Component getComponent()
	{
		return component;
	}

}
