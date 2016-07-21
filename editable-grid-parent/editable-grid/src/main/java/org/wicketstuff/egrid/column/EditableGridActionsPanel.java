package org.wicketstuff.egrid.column;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.wicketstuff.egrid.component.EditableDataTable;
import org.wicketstuff.egrid.component.EditableGridSubmitLink;
import org.wicketstuff.egrid.model.GridOperationData;
import org.wicketstuff.egrid.model.OperationType;

/**
 * 
 * @author Nadeem Mohammad
 * 
 */
public abstract class EditableGridActionsPanel<T> extends Panel
{
	public final static MetaDataKey<Boolean> EDITING = new MetaDataKey<Boolean>()
	{
		private static final long serialVersionUID = 1L;
	};

	private static final long serialVersionUID = 1L;

	protected abstract void onSave(AjaxRequestTarget target);

	protected abstract void onError(AjaxRequestTarget target);

	protected abstract void onCancel(AjaxRequestTarget target);

	protected abstract void onDelete(AjaxRequestTarget target);

	public EditableGridActionsPanel(String id, final Item<ICellPopulator<T>> cellItem)
	{
		super(id);

		@SuppressWarnings("unchecked")
		final Item<T> rowItem = cellItem.findParent(Item.class);

		add(newEditLink(rowItem));
		add(newSaveLink(rowItem));
		add(newCancelLink(rowItem));
		add(newDeleteLink(rowItem));
	}

	private EditableGridSubmitLink newSaveLink(final Item<T> rowItem)
	{
		return new EditableGridSubmitLink("save", rowItem)
		{

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible()
			{
				return isThisRowBeingEdited(rowItem);
			}

			@Override
			protected void onSuccess(AjaxRequestTarget target)
			{
				rowItem.setMetaData(EDITING, Boolean.FALSE);
				send(getPage(), Broadcast.BREADTH, rowItem);
				target.add(rowItem);
				onSave(target);

			}

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				EditableGridActionsPanel.this.onError(target);
			}
		};
	}

	private AjaxLink<String> newDeleteLink(final Item<T> rowItem)
	{
		return new AjaxLink<String>("delete")
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
			{
				super.updateAjaxAttributes(attributes);
				AjaxCallListener listener = new AjaxCallListener();
				listener.onPrecondition("if(!confirm('Do you really want to delete?')){return false;}");
				attributes.getAjaxCallListeners().add(listener);
			}

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				EditableDataTable eventTarget = rowItem.findParent(EditableDataTable.class);
				send(getPage(), Broadcast.BREADTH, new GridOperationData<T>(OperationType.DELETE,
					(T)rowItem.getDefaultModelObject(), eventTarget));
				target.add(eventTarget);
				onDelete(target);
			}

			@Override
			public boolean isVisible() {
				return EditableGridActionsPanel.this.allowDelete(rowItem);
			}
		};
	}

	private AjaxLink<String> newCancelLink(final Item<T> rowItem)
	{
		return new AjaxLink<String>("cancel")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				rowItem.setMetaData(EDITING, Boolean.FALSE);
				send(getPage(), Broadcast.BREADTH, rowItem);
				target.add(rowItem);
				onCancel(target);
			}

			@Override
			public boolean isVisible()
			{
				return isThisRowBeingEdited(rowItem);
			}
		};
	}

	private AjaxLink<String> newEditLink(final Item<T> rowItem)
	{
		return new AjaxLink<String>("edit")
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				rowItem.setMetaData(EDITING, Boolean.TRUE);
				send(getPage(), Broadcast.BREADTH, rowItem);
				target.add(rowItem);
			}

			@Override
			public boolean isVisible()
			{
				return !isThisRowBeingEdited(rowItem);
			}
		};
	}

	private boolean isThisRowBeingEdited(Item<T> rowItem)
	{
		return rowItem.getMetaData(EDITING);
	}
	
	protected boolean allowDelete(final Item<T> rowItem) {
		return true;
	}
}
