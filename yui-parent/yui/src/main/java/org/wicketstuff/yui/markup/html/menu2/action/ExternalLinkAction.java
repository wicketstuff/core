package org.wicketstuff.yui.markup.html.menu2.action;

import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.markup.html.menu2.IYuiMenuAction;

public class ExternalLinkAction extends ExternalLink
		implements IYuiMenuAction {
	
	private static final long serialVersionUID = 1L;
	
	private IModel name;
	
	public ExternalLinkAction(IModel href, IModel label ) {
		super( LINK_ID, href );
		this.name = label;
	}
	
	public ExternalLinkAction(String href, String label ) {
		super( LINK_ID, href );
		this.name = new Model( label );
	}

	public IModel getName() {
		return name;
	}
	
	public void onClick() {
		// no op
	}

}
