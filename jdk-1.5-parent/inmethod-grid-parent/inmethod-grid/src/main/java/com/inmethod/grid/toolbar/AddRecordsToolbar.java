package com.inmethod.grid.toolbar;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import com.inmethod.grid.datagrid.DataGrid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: tfburton
 * Date: 9/19/11
 * Time: 12:51 PM
 * TODO: make generic (when rest of package is made generic)
 */
public class AddRecordsToolbar extends AbstractToolbar
{
  private static final Logger log = LoggerFactory
                                          .getLogger(AddRecordsToolbar.class);

  private static final IModel<String> ADD_BUTTON_MODEL =
             new ResourceModel("datagrid.add-new-item", "Add New Item");

  private DataGrid grid;
  private Object defaultObject;

  public AddRecordsToolbar(DataGrid grid, IModel model, Object defaultObject)
  {
    this(grid,model,defaultObject,ADD_BUTTON_MODEL);
  }

  public AddRecordsToolbar(DataGrid datagrid, IModel model, Object defaultObject,
                           IModel<String> labelModel)
  {
    super(datagrid, model);
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

                                target.addComponent(findParent(DataGrid.class)
                                                        .getParent());
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
    log.error("Pre: " + pre + " Post: " + post);
  }

  /** function to allow easy overrides for returning custom Data when adding
   *  new records to the table
   * @return the object to use for populating new rows of the data-view
   */
  protected Object getNewData()
  {
    if ( defaultObject == null ) { log.error("ERROR: defaultObject is null"); }
    return defaultObject;
  }


}
