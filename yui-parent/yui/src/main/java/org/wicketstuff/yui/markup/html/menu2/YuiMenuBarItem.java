package org.wicketstuff.yui.markup.html.menu2;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;

@SuppressWarnings("unchecked")
public class YuiMenuBarItem extends AbstractYuiMenuItem
{

	private static final long serialVersionUID = 1L;

	private static final String MENU_ITEM_ID = "menu";

	private static final String LINK_ID = "link";

	private static final String LINK_LABEL_ID = "linkLabel";

	private boolean firstOfType = false;

	YuiMenuBarItem(String label)
	{
		this(label, null);
	}

	YuiMenuBarItem(final IYuiMenuAction action)
	{
		super(MENU_ITEM_ID);
		AbstractLink link = null;

		if (action instanceof AbstractLink)
		{
			link = (AbstractLink)action;
			if (link.getId().equals(LINK_ID) == false)
			{
				throw new RuntimeException("Link's id needs to be 'link' ");
			}
		}
		else if (action instanceof IYuiMenuAjaxAction)
		{
			link = new AjaxFallbackLink<String>(LINK_ID, action.getName())
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target)
				{
					if (action instanceof IYuiMenuAjaxAction && target != null)
					{
						((IYuiMenuAjaxAction)action).onClick(target, LINK_ID);
					}
					else
					{
						action.onClick();
					}
				}
			};
		}
		else
		{
			link = new Link<String>(LINK_ID, action.getName())
			{

				private static final long serialVersionUID = 1L;

				@Override
				public void onClick()
				{
					action.onClick();
				}
			};
		}
		getItemContainer().add(link);
		link.add(new Label(LINK_LABEL_ID, action.getName()).setRenderBodyOnly(true));
		newSubMenu("emptyMenu").setVisible(false);
	}

	YuiMenuBarItem(final String label, AbstractLink link)
	{
		super(MENU_ITEM_ID);

		WebMarkupContainer l = link;
		if (l == null)
		{
			l = new WebMarkupContainer(LINK_ID);
		}

		if (l.getId().equals(LINK_ID) == false)
		{
			throw new RuntimeException("Link's id needs to be 'link' ");
		}

		getItemContainer().add(l);
		l.add(new Label(LINK_LABEL_ID, new Model<String>(label)).setRenderBodyOnly(true));
		newSubMenu("emptyMenu").setVisible(false);
	}

	@Override
	public String getMenuClass()
	{
		return "yuimenuitem";
	}

	void addFirstOfType()
	{
		if (firstOfType == false)
		{
			getItemContainer().add(
					new AttributeAppender("class", true, new Model("first-of-type"), " "));
			firstOfType = true;
		}
	}

}
