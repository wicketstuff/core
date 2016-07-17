package wicket.contrib.tinymce.image;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Image upload content panel which represents panel for adding images.
 * 
 * @author Michal Letynski <mikel@mikel.pl>
 */
public class ImageUploadContentPanel extends Panel
{

	private static final long serialVersionUID = -1794953981259227822L;
	private static final Logger log = LoggerFactory.getLogger(ImageUploadContentPanel.class);
	private static final FileExtensionValidator FILE_EXTENSION_VALIDATOR = new FileExtensionValidator();
	private final String uploadFolderPath;
	
	public ImageUploadContentPanel(String pId, String customUploadFolderPath)
	{
		super(pId);
		setOutputMarkupId(true);
		this.uploadFolderPath = customUploadFolderPath;
		Form<?> form = new Form<Void>("form");
		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		form.add(feedback);
		final FileUploadField fileUploadField = new FileUploadField("file");
		fileUploadField.setLabel(new ResourceModel("required.label"));
		fileUploadField.setRequired(true);
		fileUploadField.add(FILE_EXTENSION_VALIDATOR);
		form.add(fileUploadField);
		form.add(new AjaxButton("uploadButton", form)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget pTarget)
			{
				FileUpload fileUpload = fileUploadField.getFileUpload();
				String fileName = fileUpload.getClientFileName();
				try
				{
					File currentEngineerDir = new File(uploadFolderPath);
					if (!currentEngineerDir.exists())
					{
						currentEngineerDir.mkdir();
					}
					fileUpload.writeTo(new File(currentEngineerDir, fileName));
				}
				catch (Exception ex)
				{
					log.error("Can't upload attachment: " + ex.getMessage(), ex);
					ImageUploadContentPanel.this.error("Can't upload attachment");
					pTarget.add(feedback);
					return;
				}
				finally
				{
					fileUpload.closeStreams();
				}
				ImageFileDescription imageFileDescription = new ImageFileDescription(fileName);
				imageFileDescription.setContentType(fileUpload.getContentType());
				onImageUploaded(imageFileDescription, pTarget);
			}

			@Override
			protected void onError(AjaxRequestTarget pTarget)
			{
				pTarget.add(feedback);
			}
		});
		add(form);
			
	}	
	
	public ImageUploadContentPanel(String pId)
	{
		this(pId, ImageUploadHelper.getTemporaryDirPath());		
	}

	private static class FileExtensionValidator implements IValidator<List<FileUpload>>
	{
		private static final long serialVersionUID = -8116224338791429342L;
		public static final List<String> extensions = Arrays.asList("jpg", "gif", "jpeg", "png",
			"bmp");

		public void validate(IValidatable<List<FileUpload>> pValidatables)
		{
			for (FileUpload image : pValidatables.getValue())
			{
				String extension = FilenameUtils.getExtension(image.getClientFileName());
				if (extension != null && !extensions.contains(extension.toLowerCase()))
				{
					ValidationError error = new ValidationError();
					error.addKey("WrongExtensionValidator");
					error.setVariable("extensions", extensions.toString());
					pValidatables.error(error);
				}
			}
		}
	}

	/**
	 * Method invoked after image upload.
	 * 
	 * @param pTarget
	 *            - ajax target
	 * @param pImage
	 *            - image file description
	 */
	public void onImageUploaded(ImageFileDescription pImage, AjaxRequestTarget pTarget)
	{
	}
}
