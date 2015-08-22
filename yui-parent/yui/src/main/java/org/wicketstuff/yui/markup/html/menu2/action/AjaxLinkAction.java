package org.wicketstuff.yui.markup.html.menu2.action;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.markup.html.menu2.IYuiMenuAction;
import org.wicketstuff.yui.markup.html.menu2.YuiMenuItem;

/**
 * 
 * @author josh
 * 
 */
@SuppressWarnings("serial")
public abstract class AjaxLinkAction extends AjaxFallbackLink<Object> implements IYuiMenuAction, Serializable
{
	String id;

	public AjaxLinkAction(String id)
	{
		super(YuiMenuItem.LINK_ID);
		this.id = id;
	}

	public IModel<String> getName()
	{
		return new Model<String>(id);
	}

	@Override
	public abstract void onClick(AjaxRequestTarget target);
}