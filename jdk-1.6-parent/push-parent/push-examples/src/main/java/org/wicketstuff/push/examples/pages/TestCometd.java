package org.wicketstuff.push.examples.pages;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.push.ChannelEvent;
import org.wicketstuff.push.IChannelListener;
import org.wicketstuff.push.IChannelTarget;

public class TestCometd extends ExamplePage
{

  private static final String CH_ID = "myChannel";

  @SuppressWarnings("serial")
  private final class PostListener implements IChannelListener
  {
    @Override
    public void onEvent(final String channel, final Map<String, String> datas,
        final IChannelTarget target)
    {

      field.setModel(new Model<String>("updated"));
      target.addComponent(field);
    }
  }

  private final TextField<String> field;
  private String val;

  @SuppressWarnings("serial")
  public TestCometd(final PageParameters parameters)
  {

    final AjaxLink<Void> link = new AjaxLink<Void>("link")
    {

      @Override
      public void onClick(final AjaxRequestTarget target)
      {
        getCometdService().publish(new ChannelEvent(CH_ID));
      }

    };
    add(link);

    field = new TextField<String>("text", new Model<String>(val));
    field.setOutputMarkupId(true);
    getCometdService().addChannelListener(this, CH_ID, new PostListener());
    add(field);
  }
}
