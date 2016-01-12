package org.wicketstuff.yui.markup.html.menu2.action;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.markup.html.menu2.IYuiMenuAction;

public class BookmarkableLinkAction extends BookmarkablePageLink
		implements IYuiMenuAction {
	
	private static final long serialVersionUID = 1L;
	
	private Model name;
	
	public BookmarkableLinkAction( Class pageClass, Model name) {
		super( LINK_ID, pageClass );
		this.name = name;
	}
	
	public BookmarkableLinkAction(Class pageClass, PageParameters parameters, Model name ) {
		super( LINK_ID, pageClass, parameters );
		this.name = name;
	}

	public IModel getName() {
		return name;
	}

}
