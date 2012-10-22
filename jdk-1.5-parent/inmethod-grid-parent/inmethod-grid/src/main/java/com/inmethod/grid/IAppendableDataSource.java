package com.inmethod.grid;

/**
 * Extends the {@link IDataSource} interface with methods necessary to support
 * dynamically adding data to the provider
 *
 * @author Tom Burton
 */
public interface IAppendableDataSource<T> extends IDataSource<T>
{
  /** Function for appending an item to the end of the result data
   *  @param index the index value to insert the new Item into
   *  @param item Item to append to the end of the result data
   */
  public void InsertRow(int index, T item);

  /**
   * Function for removing Data from the Provider at the specified index
   *  @param index the index value to remove the data at
   *  @param item Item to remove from the provider. (for confirmation)
   */
  public void DeleteRow(int index, T item);
}
