package org.wicketstuff.jquery.demo;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.wicketstuff.jquery.block.BlockOptions;
import org.wicketstuff.jquery.block.BlockingAjaxLink;

@SuppressWarnings("serial")
public class Page4Block extends PageSupport
{

	public Page4Block()
	{

		WebMarkupContainer body = new WebMarkupContainer("demo");
		add(body);

		BlockOptions options = new BlockOptions();
		options.setMessage("Hello!!!");


		body.add(new BlockingAjaxLink<Void>("ajaxlink", "hello!")
		{
			@Override
			public void doClick(AjaxRequestTarget target)
			{
				try
				{
					Thread.sleep(3000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				info("Clicked link: " + this.toString());
				target.addChildren(getPage(), FeedbackPanel.class);
			}
		});

		body.add(new BlockingAjaxLink<Void>("block2", "hello!")
		{
			@Override
			public void doClick(AjaxRequestTarget target)
			{
				try
				{
					Thread.sleep(3000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				info("Clicked link: " + this.toString());
				target.addChildren(getPage(), FeedbackPanel.class);
			}

			@Override
			public CharSequence getBlockElementsSelector()
			{
				return "div.blockMe";
			}
		});

		BlockOptions opts = new BlockOptions();
		opts.setFadeIn(5000);
		opts.setFadeOut(0);

		body.add(new BlockingAjaxLink<Void>("block3", opts)
		{
			@Override
			public void doClick(AjaxRequestTarget target)
			{
				try
				{
					Thread.sleep(5000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				info("Clicked link: " + this.toString());
				target.addChildren(getPage(), FeedbackPanel.class);
			}
		});
	}

}
