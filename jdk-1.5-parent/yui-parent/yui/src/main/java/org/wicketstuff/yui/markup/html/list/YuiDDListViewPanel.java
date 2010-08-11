package org.wicketstuff.yui.markup.html.list;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.yui.behavior.dragdrop.YuiDDTarget;
import org.wicketstuff.yui.markup.html.list.YuiDDListView;

/**
 * A YuiDDListView that is also a DDTarget....
 * 
 * @author josh
 * 
 * @param <T>
 */
@SuppressWarnings("serial")
public class YuiDDListViewPanel<T> extends Panel
{

	private WebMarkupContainer container;

	private WebMarkupContainer listContainer;

	private YuiDDListView<T> listview;

	/**
	 * 
	 * @param id
	 */
	public YuiDDListViewPanel(String id, final List<T> list)
	{
		super(id);
		setOutputMarkupId(true);

		// dd target
		add(container = new WebMarkupContainer("ddTarget"));
		container.setOutputMarkupId(true);
		container.add(new YuiDDTarget(getGroupId())
		{

			@SuppressWarnings("unchecked")
			@Override
			public void onDrop(AjaxRequestTarget target, Component component)
			{
				if (component instanceof ListItem)
				{
					ListItem<?> listItem = (ListItem<?>)component;
					list.add((T)listItem.getModelObject());
				}
				target.addComponent(container);
				onAjaxUpdate(target);
			}

		});

		// list container
		container.add(listContainer = new WebMarkupContainer("list"));
		listContainer.setOutputMarkupId(true);

		listContainer.add(listview = new YuiDDListView<T>("items", list)
		{

			@Override
			protected void populateItem(ListItem<T> item)
			{
				super.populateItem(item);
				item.add(newListItem("item", item));
			}

			@Override
			protected void onAjaxUpdate(AjaxRequestTarget target)
			{
				target.addComponent(listContainer);
				YuiDDListViewPanel.this.onAjaxUpdate(target);
			}

			@Override
			protected String getGroupId()
			{
				return YuiDDListViewPanel.this.getGroupId();
			}
		});
	}

	protected void onAjaxUpdate(AjaxRequestTarget target)
	{
	}

	/**
	 * 
	 * @return
	 */
	protected String getGroupId()
	{
		return "grp_" + getMarkupId();
	}

	/**
	 * 
	 * @param id
	 * @param item
	 * @return
	 */
	protected Component newListItem(String id, ListItem<T> item)
	{
		return new EmptyPanel(id);
	}

	@SuppressWarnings("unchecked")
	public List<T> getList()
	{
		return (List<T>)listview.getList();
	}
}
