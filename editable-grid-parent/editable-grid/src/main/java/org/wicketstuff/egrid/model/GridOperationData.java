package org.wicketstuff.egrid.model;

import org.wicketstuff.egrid.component.EditableDataTable;

import java.io.Serializable;
/**
 * 
 * @author Nadeem Mohammad
 *
 */
public class GridOperationData<D> implements Serializable
{

	private static final long serialVersionUID = 1L;
	private OperationType operationType;
	private D data;
	private EditableDataTable target;

	public GridOperationData(final OperationType newOperationType, final D newData, EditableDataTable target)
	{
		this.operationType = newOperationType;
		this.data			 = newData;
		this.target        = target;
	}
	
	public OperationType getOperationType()
	{
		return operationType;
	}
	public void setOperationType(OperationType operationType)
	{
		this.operationType = operationType;
	}
	public D getData()
	{
		return data;
	}
	public void setData(D data)
	{
		this.data = data;
	}
	public EditableDataTable getTarget()
	{
		return target;
	}
}
