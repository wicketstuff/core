package org.wicketstuff.egrid.model;

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

	public GridOperationData(final OperationType newOperationType, final D newData)
	{
		this.operationType = newOperationType;
		this.data			=  newData;
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
}
