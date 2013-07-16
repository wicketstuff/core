package com.googlecode.wicket.jquery.ui.samples.pages.test;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.CssContentHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.AbstractKendoPage;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.FragmentDialog;

public class TestPage extends AbstractKendoPage
{
	private static final long serialVersionUID = 1L;

	public TestPage()
	{
		this.init();
	}

	private void init()
	{
		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		// DropDownList //
		final AbstractDialog<?> dialog = new MyDialog("dialog") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(AjaxRequestTarget target, DialogButton button)
			{
			}
		};

		this.add(dialog);
	}

	abstract class MyDialog extends FragmentDialog<String> {

		private static final long serialVersionUID = 1L;

		public MyDialog(String id)
		{
			super(id, "title");
		}

		@Override
		protected Fragment newFragment(String id)
		{
			return new Fragment(id, "dialog-fragment", TestPage.this);
		}

		@Override
		public void renderHead(IHeaderResponse response)
		{
			super.renderHead(response);

			// @see: http://api.jqueryui.com/dialog/
			response.render(new CssContentHeaderItem(".no-close .ui-dialog-titlebar-close { display: none; }", "dialog-noclose", ""));
		}

		@Override
		protected void onInitialize()
		{
			super.onInitialize();

			// @see: http://api.jqueryui.com/dialog/#method-open
			this.add(new JQueryBehavior(JQueryWidget.getSelector(this), "dialog") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void renderScript(JavaScriptHeaderItem script, IHeaderResponse response)
				{
					super.renderScript(script, response);
//					response.render(new PriorityHeaderItem(script));
				}

				@Override
				protected String $()
				{
					return this.$(Options.asString("open"));
				}
			});
		}


		@Override
		public void onConfigure(JQueryBehavior behavior)
		{
			super.onConfigure(behavior);

//			behavior.setOption("autoOpen", true);
			behavior.setOption("closeOnEscape", false);
			behavior.setOption("dialogClass", Options.asString("no-close"));
		}
	}
}
