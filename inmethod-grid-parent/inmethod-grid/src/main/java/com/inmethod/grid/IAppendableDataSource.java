package com.inmethod.grid;

/** 
 * Extends the {@link IDataSource} interface with methods necessary to support
 * dynamically adding data to the provider
 *
 * @author Tom Burton
 */
public interface IAppendableDataSource<T> extends IDataSource<T>
{
  /** 
   * Function for appending an item to the end of the result data
   * @param index the index value to insert the new Item into
   * @param item Item to append to the end of the result data
   */
  public void insertRow(long index, T item);

  /** 
   * Function for deleting an item from the specified index of the result data
   * @param index the index value to delete the Item from
   * @param item Item to be deleted
   */
  public void deleteRow(long index, T item);
}
