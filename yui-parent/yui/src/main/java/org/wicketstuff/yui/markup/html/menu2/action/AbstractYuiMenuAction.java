package org.wicketstuff.yui.markup.html.menu2.action;

import org.apache.wicket.model.IModel;
import org.wicketstuff.yui.markup.html.menu2.IYuiMenuAction;

public abstract class AbstractYuiMenuAction implements IYuiMenuAction, java.io.Serializable {
	
	private IModel name;
	
	public AbstractYuiMenuAction( IModel name ) {
		this.name = name;
	}

	public IModel getName() {
		return name;
	}

	abstract public void onClick();

}
