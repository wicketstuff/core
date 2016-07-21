package org.wicketstuff.yui.markup.html.menu2.action;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.IPageLink;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.model.IModel;
import org.wicketstuff.yui.markup.html.menu2.IYuiMenuAction;

public class PageAction extends PageLink implements IYuiMenuAction {
	

	private IModel label = null;
	
	public PageAction( IModel label, Class page ) {
		super( LINK_ID, page );
		this.label = label;
	}
	
	public PageAction( IModel label, IPageLink pageLink ) {
		super( LINK_ID, pageLink );
		this.label = label;
	}
	
	@Deprecated
	public PageAction(IModel label, Page page) {
		super( LINK_ID, page );
		this.label = label;
	}


	public IModel getName() {
		return label;
	}

}
