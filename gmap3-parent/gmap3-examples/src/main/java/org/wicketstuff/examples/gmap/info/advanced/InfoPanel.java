package org.wicketstuff.examples.gmap.info.advanced;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

public class InfoPanel extends Panel
{
  private static final long serialVersionUID = 1L;
  private int counter;
  private Label label;

  public InfoPanel(String id, int i)
  {

    super(id);
    add(new Label("label", ""+i));
    add(label = new Label("counterLbl", new PropertyModel<Integer>(this, "counter")));
    label.setOutputMarkupPlaceholderTag(true);
    add(new AjaxLink<Void>("link")
    {
      private static final long serialVersionUID = 1L;
      @Override
      public void onClick(AjaxRequestTarget target)
      {
        counter++;
        target.add(label);
      }
    });
  }

  public int getCounter()
  {
    return counter;
  }

}
