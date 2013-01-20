package com.googlecode.wicket.jquery.ui.samples.component;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.IClusterable;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.component.TabDialog.TabItem;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractFormDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;

public abstract class TabDialog extends AbstractFormDialog<TabItem>
{
	private static final long serialVersionUID = 1L;
	protected final DialogButton btnSubmit = new DialogButton("Add");
	protected final DialogButton btnCancel = new DialogButton(LBL_CANCEL);

	private Form<?> form;
	private FeedbackPanel feedback;

	public TabDialog(String id, String title)
	{
		super(id, title, new CompoundPropertyModel<TabItem>(new TabItem()), true);

		// Form //
		this.form = new Form<Integer>("form");
		this.add(this.form);

		// FeedbackPanel //
		this.feedback = new JQueryFeedbackPanel("feedback");
		this.form.add(this.feedback);

		// TabItem Inputs //
		this.form.add(new TextField<String>("title").setRequired(true));
		this.form.add(new TextArea<String>("content"));
	}

	// Properties //
	@Override
	protected List<DialogButton> getButtons()
	{
		return Arrays.asList(this.btnSubmit, this.btnCancel);
	}

	@Override
	protected DialogButton getSubmitButton()
	{
		return this.btnSubmit;
	}

	@Override
	public Form<?> getForm()
	{
		return this.form;
	}

	// Event //
	@Override
	protected void onOpen(AjaxRequestTarget target)
	{
		//re-attach the feedback panel to clear previously displayed error message(s)
		target.add(this.feedback);
	}

	@Override
	public void onError(AjaxRequestTarget target)
	{
		target.add(this.feedback);
	}

	// Bean //
	public static class TabItem implements IClusterable
	{
		private static final long serialVersionUID = 1L;

		private String title;
		private String content;

		public TabItem()
		{
		}

		public String getTitle()
		{
			return this.title;
		}

		public void setTitle(String title)
		{
			this.title = title;
		}

		public String getContent()
		{
			return this.content;
		}

		public void setContent(String content)
		{
			this.content = content;
		}
	}
}
