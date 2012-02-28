package com.inmethod.grid.toolbar;

import java.io.*;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import com.inmethod.grid.IDataSource;
import com.inmethod.grid.datagrid.DataGrid;

/**
 * Created by IntelliJ IDEA.
 * User: Tom Burton
 * Date: 9/19/11
 * Time: 12:51 PM
 *
 * @param <D>
 *            datasource model object type = grid type
 * @param <T>
 *            row/item model object type - must be serializable for "deep copy"
 *
 * @Author Tom Burton
 */
public class AddRecordsToolbar<D extends IDataSource<T>, T extends Serializable>
       extends AbstractToolbar<D, T>
{
  private static final IModel<String> ADD_BUTTON_MODEL =
             new ResourceModel("datagrid.add-new-item", "Add New Item");

  private DataGrid<D, T> grid; //todo: should this only get from super?

  private final T defaultObject; //final makes sure the default doesn't change

  public AddRecordsToolbar(DataGrid<D, T> grid, IModel<T> model,
                           final T defaultObject)
  { this(grid, model, defaultObject, ADD_BUTTON_MODEL); }

  public AddRecordsToolbar(DataGrid<D, T> datagrid, IModel<T> model,
                           final T defaultObject,
                           IModel<String> labelModel)
  {
    super(datagrid, labelModel);
    this.grid = datagrid;
    //ensure OutputMarkupId is set so the button will refresh properly
    datagrid.setOutputMarkupId(true);
    if (null != datagrid.getParent() )
    { datagrid.getParent().setOutputMarkupId(true); }
    this.defaultObject = defaultObject;
    Form form = new Form<Void>("addForm");
    add(form);
    AjaxButton addButton = new AjaxButton("add")
                           {
                              @Override
                              protected void onSubmit(AjaxRequestTarget target,
                                                      Form<?> form)
                              {
                                insert();
                                //target.add(findParent(DataGrid.class).getParent());
                              }

                              @Override
                              protected void onError(AjaxRequestTarget target, Form<?> form)
                              { //TODO: should this be a feedbackPanel check?
                                return;
                              }
                           };
    addButton.setLabel(labelModel);
    form.add(addButton);
  }

  /** inserts data into the table */
  protected void insert()
  {
    int pre = 0;
    int post = 0;
    pre = grid.getTotalRowCount();
    grid.insertRow(getNewData());
    post = grid.getTotalRowCount();
    grid.update();
    //log.error("Pre: " + pre + " Post: " + post);
  }

  /** function to allow easy overrides for returning custom Data when adding
   *  new records to the table
   * @return the object to use for populating new rows of the data-view
   */
  protected T getNewData()
  {
    //if ( defaultObject == null ) { log.error("ERROR: defaultObject is null"); }
    if ( defaultObject == null )
    { //note: should this return null instead?
      throw new WicketRuntimeException("Can't deep copy a null object.");
    }
    Object obj;
    ObjectOutputStream oos;
    ByteArrayOutputStream byteArray;
    try
    {
      oos = new ObjectOutputStream(byteArray = new ByteArrayOutputStream());
      oos.writeObject(defaultObject);
      ObjectInputStream ois =
            new ObjectInputStream(new ByteArrayInputStream(byteArray.toByteArray()));
      obj = ois.readObject();
      return (T)obj;
    }
    catch(IOException ioe) { } //error writing output stream
    catch(ClassNotFoundException cnf ) {} //should never happen
    return defaultObject; //returns on Error or not serializable
  }

}
