package org.wicketstuff.html5.fileapi;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.util.lang.Bytes;
import org.wicketstuff.html5.BasePage;

/**
 * Example page for {@link FileFieldSizeCheckBehavior}.
 * 
 * @author akiraly
 */
public class FileApiPage extends BasePage
{
	private static final long serialVersionUID = 8675190555621516422L;

	public FileApiPage()
	{
		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupPlaceholderTag(true);
		add(feedback);

		final Form<Void> form = new Form<Void>("form");
		form.setMaxSize(Bytes.kilobytes(1));
		add(form);

		final FileUploadField uploadField = new FileUploadField("file");

		// add our FileApi based size check, errors are reported trough the
		// feedback
		uploadField.add(new FileFieldSizeCheckBehavior()
		{
			private static final long serialVersionUID = 7228537141239670625L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, FileList fileList)
			{
				target.add(feedback);
			}

			@Override
			protected void onError(AjaxRequestTarget target, FileList fileList)
			{
				target.add(feedback);
			}
		});

		form.add(uploadField);

		// lets make this an ajax upload form, just for fun
		form.add(new AjaxButton("submit")
		{
			private static final long serialVersionUID = 468703108441902441L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				info("Victory! " + uploadField.getFileUploads());

				target.add(feedback);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				target.add(feedback);
			}
		});
	}
}
