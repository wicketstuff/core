package com.inmethod.grid.column;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * Backing Panel for the LinkColumn to do the actual "work"
 * of properly displaying a link
 * @author Tom Burton
 *         Panel for Displaying a Link in a cell.
 */
public abstract class LinkPanel<M, I> extends Panel //implements IRenderable
{
  private String label;

  /**
   * Creates a new LinkPanel
   * @param id Panel Id
   * @param Label link display text
   * @see Component#Component(String)
   */
  public LinkPanel(String id, String Label)
  {
    super(id);
    label = Label;
  }

 /**
  * Creates a new LinkPanel
  * @param id Panel Id
  * @param Label link display text
  * @param model backing model used by the panel
  * @see Component#Component(String, IModel)
  */
  public LinkPanel(String id, String Label, IModel<I> model)
  {
    super(id, model);
    label = Label;
  }

  /**
   * Called just before a component is rendered.
   * {@inheritDoc}
   */
  @Override
  protected void onBeforeRender()
  { //TODO: should this be moved to onInitialize()?
    Link link = new Link<Void>("link")
                    {
                        /** Called when a link is clicked. */
                        @Override
                        public void onClick()
                        {
                          LinkPanel.this.onClick();
                        }
                    };
    link.add(new Label("label",label));

    if ( null != get("link") ) { this.replace(link); }
    else { this.add(link); }

    super.onBeforeRender();
  }

  /**
   * Renders the output for given cell model. The implementation must take care
   * of proper escaping
   * (e.g. translating &lt; to &amp;lt;, etc.) where appropriate.
   *
   * @ param rowModel model for given row
   * @ param response
   * /
  public void render(IModel rowModel, Response response)
  {
    /*
    if ( null !=  this.findMarkupStream()
      && null != this.findMarkupStream().getTag() )
    { response.write(this.findMarkupStream().getTag()); }
    * /
    response.write();
    super.render();
  }
  */

  /** override this function to do the actual work when a link is clicked. */
  public abstract void onClick(); // { }
}
