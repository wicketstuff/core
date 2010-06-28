package org.wicketstuff.push.examples.pages;

import java.util.Map;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.wicketstuff.push.ChannelEvent;
import org.wicketstuff.push.IChannelListener;
import org.wicketstuff.push.IChannelTarget;

public class TestCometd extends ExamplePage {

  private static final String CH_ID = "myChannel";

  @SuppressWarnings("serial")
  private final class PostListener implements IChannelListener {
    @SuppressWarnings("unchecked")
    public void onEvent(final String channel, final Map datas,
        final IChannelTarget target) {
      field.setModel(new Model("updated"));
      target.addComponent(field);
    }
  }

  private final TextField field;
	private String val;

  @SuppressWarnings("serial")
  public TestCometd(final PageParameters parameters)
	{
		final AjaxLink link = new AjaxLink("link"){

			@Override
      public void onClick(final AjaxRequestTarget target) {
			  getCometdService().publish(new ChannelEvent(CH_ID));
			}

		};
		add(link);

		field = new TextField("text", new Model(val));
		field.setOutputMarkupId(true);
		getCometdService().addChannelListener(this, CH_ID, new PostListener());
		add(field);
	}
}
