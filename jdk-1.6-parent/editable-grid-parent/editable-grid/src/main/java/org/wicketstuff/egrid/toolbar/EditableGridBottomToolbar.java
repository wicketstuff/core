package org.wicketstuff.egrid.toolbar;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IFormSubmitter;
import org.apache.wicket.markup.html.form.IFormVisitorParticipant;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.egrid.column.AbstractEditablePropertyColumn;
import org.wicketstuff.egrid.column.EditableCellPanel;
import org.wicketstuff.egrid.component.EditableDataTable;
import org.wicketstuff.egrid.component.EditableGridSubmitLink;
/**
 * 
 * @author Nadeem Mohammad
 *
 */
public abstract class EditableGridBottomToolbar<T, S> extends AbstractEditableGridToolbar
{

	private static final long serialVersionUID 	= 1L;
	private static final String CELL_ID 		= "cell";
	private static final String CELLS_ID 		= "cells";

	private T newRow							= null;
	
	protected abstract void onAdd(AjaxRequestTarget target, T newRow);

	public EditableGridBottomToolbar(EditableDataTable<?, ?> table, Class<T> clazz)
	{
		super(table);
		createNewInstance(clazz);		
		MarkupContainer td = new WebMarkupContainer("td");
		td.add(new AttributeModifier("colspan", table.getColumns().size() - 1));
		AddToolBarForm addToolBarForm = new AddToolBarForm("addToolbarForm");
		td.add(addToolBarForm);
		add(td);
		add(newAddButton(addToolBarForm));
	}

	protected void onError(AjaxRequestTarget target) {	}

	//TODO: use Objenesis instead of the following

	private void createNewInstance(Class<T> clazz) 
	{
		try
		{
			newRow = (T) clazz.newInstance();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}
	
	private class AddToolBarForm extends Form<T> implements IFormVisitorParticipant
	{

		private static final long serialVersionUID = 1L;

		public AddToolBarForm(String id)
		{
			super(id);
			add(newEditorComponents());
		}
		public boolean processChildren()
		{
			IFormSubmitter submitter = getRootForm().findSubmittingButton();
            return submitter != null && submitter.getForm() == this;
		}		
	}

	private Component newAddButton(WebMarkupContainer encapsulatingContainer)
	{
		return new EditableGridSubmitLink("add", encapsulatingContainer) {

			private static final long serialVersionUID = 1L;		
			@SuppressWarnings("unchecked")
			@Override
			protected void onSuccess(AjaxRequestTarget target)
			{
				onAdd(target, newRow);
				createNewInstance((Class<T>) newRow.getClass());
				target.add(getTable());
				
			}
			@Override
			protected void onError(AjaxRequestTarget target)
			{				
				EditableGridBottomToolbar.this.onError(target);
			}
		};
	}
	
	private Loop newEditorComponents()
	{
		final List<AbstractEditablePropertyColumn<T, S>> columns = getEditableColumns();
		return new Loop(CELLS_ID, columns.size())
		{

			private static final long serialVersionUID 	= 	1L;

			protected void populateItem(LoopItem item)
			{
				addEditorComponent(item, getEditorColumn(columns, item.getIndex()));
			}
		};
	}

	private void addEditorComponent(LoopItem item, AbstractEditablePropertyColumn<T, S> toolBarCell)
	{
		item.add(newCell(toolBarCell));		
	}

	@SuppressWarnings("unchecked")
	private List<AbstractEditablePropertyColumn<T, S>> getEditableColumns()
	{
		 List<AbstractEditablePropertyColumn<T, S>> columns = new ArrayList<AbstractEditablePropertyColumn<T, S>>();
		 for (IColumn<?, ?> column : getTable().getColumns()) {
			if (column instanceof AbstractEditablePropertyColumn)
			{
				columns.add((AbstractEditablePropertyColumn<T, S>)column);
			}
			
		}
		 
		 return columns;
	}	

	private Component newCell(AbstractEditablePropertyColumn<T, S> editableGridColumn)
	{
		EditableCellPanel panel 			= editableGridColumn.getEditableCellPanel(CELL_ID);
		FormComponent<?> editorComponent 	= panel.getEditableComponent();
		editorComponent.setDefaultModel(new PropertyModel<T>(newRow , editableGridColumn.getPropertyExpression()));
		return panel;
	}

	private AbstractEditablePropertyColumn<T, S> getEditorColumn(final List<AbstractEditablePropertyColumn<T, S>> editorColumn, int index)
	{
		return editorColumn.get(index);
	}
}
