package com.googlecode.wicket.jquery.ui.samples.component;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractFormDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;

public abstract class UploadDialog extends AbstractFormDialog<FileUpload>
{
	private static final long serialVersionUID = 1L;

	protected final DialogButton btnUpload = new DialogButton(SUBMIT, Model.of("Upload!")) {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isIndicating()
		{
			return true;
		}
	};

	protected final DialogButton btnCancel = new DialogButton(CANCEL, LBL_CANCEL);

	private Form<?> form;
	private FeedbackPanel feedback;
	private FileUploadField field;

	public UploadDialog(String id, String title)
	{
		super(id, title, new Model<FileUpload>(), true);

		// Form //
		this.form = new Form<Integer>("form");
		// this.form.setMultiPart(true);
		this.add(this.form);

		// Upload File //
		this.field = new FileUploadField("file");
		this.form.add(this.field);

		// FeedbackPanel //
		this.feedback = new JQueryFeedbackPanel("feedback");
		this.form.add(this.feedback);
	}

	@Override
	protected List<DialogButton> getButtons()
	{
		return Arrays.asList(this.btnUpload, this.btnCancel);
	}

	@Override
	public DialogButton getSubmitButton()
	{
		return this.btnUpload;
	}

	@Override
	public Form<?> getForm()
	{
		return this.form;
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target)
	{
		this.setModelObject(this.field.getFileUpload());
	}

	@Override
	protected void onOpen(IPartialPageRequestHandler handler)
	{
		// re-attach the feedback panel to clear previously displayed error message(s)
		handler.add(this.feedback);
	}

	@Override
	public void onError(AjaxRequestTarget target)
	{
		target.add(this.feedback);
	}
}
