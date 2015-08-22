package org.wicketstuff.yui.markup.html.menu2.action;

import org.apache.wicket.model.IModel;

public class NoOpAction extends AbstractYuiMenuAction {
	
	private static final long serialVersionUID = 1L;

	public NoOpAction( IModel name ) {
		super( name );
	}

	@Override
	public void onClick() {

	}

}
