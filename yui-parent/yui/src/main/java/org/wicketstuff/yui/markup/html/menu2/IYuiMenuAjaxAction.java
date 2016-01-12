package org.wicketstuff.yui.markup.html.menu2;

import org.apache.wicket.ajax.AjaxRequestTarget;

public interface IYuiMenuAjaxAction extends IYuiMenuAction
{

	void onClick(AjaxRequestTarget target, String targetId);

}
