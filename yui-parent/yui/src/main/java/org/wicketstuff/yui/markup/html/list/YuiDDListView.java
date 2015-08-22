package org.wicketstuff.yui.markup.html.list;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.version.undo.Change;
import org.wicketstuff.yui.behavior.dragdrop.YuiDDList;
import org.wicketstuff.yui.helper.CollectionsHelper;

/**
 * A ListView with each <code>li</code> bounded to a YuiListDD which will make
 * it reorderable.
 * 
 * Note :- the javascript works only with <code>&lt;li&gt;</code> <br/>
 * <br/>
 * onAjaxUpdate needs to be called to update the container since AjaxUpdate
 * cannot be called on ListView
 * 
 * <pre>
 * 
 * Example HTML:
 * 
 * 		&lt;ul&gt;
 * 	  	    &lt;li wicket:id=&quot;items&quot; &gt;
 * 		        &lt;label wicket:id=&quot;item&quot;&gt;label&lt;/label&gt;
 * 	  	    &lt;/li&gt;
 * 		&lt;/ul&gt;
 * 
 * Example Java:
 * 
 * 		add(new YuiDDListView(&quot;items&quot;, listData)
 * 		{
 * 			public void populateItem(final ListItem item)
 * 			{
 * 			   	super.populateItem(item); 
 * 				item.add(new Label(&quot;item&quot;, item.getModelObjectAsString()));
 * 			}
 * 		});
 * 
 * </pre>
 * 
 * @author josh
 * 
 * @param <T>
 */
public abstract class YuiDDListView<T> extends ListView<T>
{

	private static final long serialVersionUID = 1L;

	public YuiDDListView(String id, List<T> list)
	{
		super(id, list);
		setOutputMarkupId(true);
	}

	public YuiDDListView(String id, IModel<List<T>> model)
	{
		super(id, model);
		setOutputMarkupId(true);
	}

	@Override
	protected void populateItem(final ListItem<T> item)
	{
		item.add(new YuiDDList(getGroupId())
		{
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			protected void onDrop(AjaxRequestTarget target, int index, Component dragOverItem)
			{

				if (dragOverItem == null)
				{
					removeItem(item);
				}
				else
				{
					T srcItem = item.getModelObject();
					T destItem = ((ListItem<T>)dragOverItem).getModelObject();

					visitTarget(dragOverItem, index, srcItem);

					// remove the item from the list if it was dropped on
					// anther list item

					if (!getList().contains(destItem))
					{
						removeItem(item);
					}
				}
			}
		});
	}

	/**
	 * 
	 * @param dragOverItem
	 * @param pos
	 * @param item
	 */
	protected void visitTarget(final Component dragOverItem, final int pos, final T item)
	{
		dragOverItem.visitParents(YuiDDListView.class, new IVisitor<Component>()
		{

			@SuppressWarnings("unchecked")
			public Object component(Component component)
			{
				YuiDDListView<T> destList = (YuiDDListView<T>)component;
				destList.onDrop(pos, item);
				return STOP_TRAVERSAL;
			}
		});
	}

	/**
	 * two lists of the same group can interact with each
	 * 
	 * @return
	 */
	protected String getGroupId()
	{
		return "DDList_" + getMarkupId();
	}

	/**
	 * from removeLink()
	 */
	protected void removeItem(final ListItem<T> item)
	{

		addStateChange(new Change()
		{

			private static final long serialVersionUID = 1L;

			final int oldIndex = getList().indexOf(item.getModelObject());
			final T removedObject = item.getModelObject();

			@SuppressWarnings("unchecked")
			@Override
			public void undo()
			{
				((List<T>)getList()).add(oldIndex, removedObject);
			}

		});

		item.modelChanging();

		// Remove item and invalidate listView
		getList().remove(item.getModelObject());

		YuiDDListView.this.modelChanged();
		YuiDDListView.this.removeAll();

		onAjaxUpdate(AjaxRequestTarget.get());
	}

	/**
	 * 
	 * when an Item is dropped on the list, it could be a new item or it could
	 * be an existing item.
	 * 
	 * @param pos
	 * @param destItem
	 */

	@SuppressWarnings("unchecked")
	protected void onDrop(int pos, T destItem)
	{

		int source = getList().indexOf(destItem);

		if (source < 0)
		{
			((List<T>)getList()).add(pos, destItem);
		}
		else
		{
			CollectionsHelper.rotateInto(getList(), source, pos);
		}
		onAjaxUpdate(AjaxRequestTarget.get());
	}

	/**
	 * IMPT : to refresh the containing markup this is called when a item is
	 * dropped on a list and also when it is removed from a list
	 * 
	 * @param target
	 */
	protected abstract void onAjaxUpdate(AjaxRequestTarget target);
}
