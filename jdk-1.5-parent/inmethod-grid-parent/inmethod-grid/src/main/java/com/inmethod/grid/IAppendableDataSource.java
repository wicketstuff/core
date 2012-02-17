package com.inmethod.grid;

/**
 * Created by IntelliJ IDEA.
 * User: Tom B.
 * Date: 9/20/11
 * Time: 10:56 AM
 *
 * @author Tom B.
 * //TODO: make generic when the rest of the library is converted
 */
public interface IAppendableDataSource extends IDataSource
{
  /** Function for appending an item to the end of the result data
   *  @param index the index value to insert the new Item into
   *  @param item Item to append to the end of the result data
   */
  public void InsertRow(int index, Object item);

  public void DeleteRow(int index, Object item);
}
