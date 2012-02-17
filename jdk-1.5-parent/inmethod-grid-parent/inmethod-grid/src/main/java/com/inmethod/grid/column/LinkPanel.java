package com.inmethod.grid.column;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * Created by IntelliJ IDEA.
 * User: Tom Burton
 * Date: 1/3/12
 * Time: 2:11 PM
 *
 * @author Tom Burton
 *         Panel for Displaying a Link in a cell.
 */
public abstract class LinkPanel<M, I> extends Panel //implements IRenderable
{
  private String label;

  /** @see Component#Component(String) */
  public LinkPanel(String id, String Label)
  {
    super(id);
    label = Label;
  }

  /** @see Component#Component(String, IModel) */
  public LinkPanel(String id, String Label, IModel<I> model)
  {
    super(id, model);
    label = Label;
  }

  /**
   * Called just before a component is rendered.
   * <p>
   * <strong>NOTE</strong>: If you override this, you *must* call
   * super.onBeforeRender() within
   * your implementation.
   *
   * Because this method is responsible for cascading {@link #onBeforeRender()}
   * call to its
   * children it is strongly recommended that super call is made at the end of
   * the override.
   * </p>
   *
   * @ see Component#callOnBeforeRenderIfNotVisible()
   */
  @Override
  protected void onBeforeRender()
  {
    this.add(new Link<Void>("link")
                 {
                    /** Called when a link is clicked. */
                    @Override
                    public void onClick()
                    {
                      LinkPanel.this.onClick();
                    }
                 }.add(new Label("label",label)));

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
