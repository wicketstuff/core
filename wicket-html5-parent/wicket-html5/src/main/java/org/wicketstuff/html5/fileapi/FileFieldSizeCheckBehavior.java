package org.wicketstuff.html5.fileapi;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Bytes;

/**
 * <p>
 * A {@link Behavior} that can be added to {@link FileUploadField}-s to provide a convenient and
 * fast size checking for users using FileApi supporting browsers. Using this has the benefit that
 * the user does not have to upload the whole file data to receive the error about it exceeding the
 * size limit. In browsers not supporting the FileApi this class does not change or break anything
 * (so it is safe for IE users).
 * </p>
 * <p>
 * Works with single or multiple enabled file inputs.
 * </p>
 * <p>
 * Longer explanation:<br />
 * Today (2011 first half) browsers do not read the http response until they have fully written out
 * the http request. This means that if you post a form with files in it and wicket stops reading
 * the input because lets say the files are too big, the servlet container will still have to read
 * and throw away the rest of the request before it can send back the response to the client. This
 * means that a lot of user time, network bandwidth and server cpu time is wasted. Adding this
 * behavior cures this problem for those users with a FileApi supporting browsers. This class does
 * not replace the standard wicket size validation (this is only a convenience feature). It will
 * still be done at upload.
 * </p>
 * 
 * <p>
 * Usage is very simple (similar to an AjaxButton), just add it to the field and specify what should
 * happen when validation passes or fails
 * 
 * <pre>
 * <code>
 * final FileUploadField uploadField = new FileUploadField("file");
 * 
 * // add our FileApi based size check, errors are reported trough a
 * // feedback panel
 * uploadField.add(new FileFieldSizeCheckBehaviour() {
 * 	private static final long serialVersionUID = 7228537141239670625L;
 * 
 * 	&#64;Override
 * 	protected void onSubmit(AjaxRequestTarget target, FileList fileList) {
 * 		target.add(feedback);
 * 	}
 * 
 * 	&#64;Override
 * 	protected void onError(AjaxRequestTarget target, FileList fileList) {
 * 		target.add(feedback);
 * 	}
 * });
 * </code>
 * </pre>
 * 
 * </p>
 * 
 * @author akiraly
 */
public abstract class FileFieldSizeCheckBehavior extends FileFieldChangeBehavior
{
	private static final long serialVersionUID = -3780833214149694593L;

	/**
	 * Copy of {@link Form}#UPLOAD_TOO_LARGE_RESOURCE_KEY field.
	 */
	private static final String UPLOAD_TOO_LARGE_RESOURCE_KEY = "uploadTooLarge";

	/**
	 * Default constructor. Calls same constructor in parent.
	 */
	public FileFieldSizeCheckBehavior()
	{
	}

	/**
	 * Constructor. Calls same constructor in parent.
	 * 
	 * @param maxNumOfFiles
	 *            maximum number of files that are processed from the ajax request, non-negative
	 */
	public FileFieldSizeCheckBehavior(int maxNumOfFiles)
	{
		super(maxNumOfFiles);
	}

	protected FileFieldSizeCheckBehavior(String event, int maxNumOfFiles)
	{
		super(event, maxNumOfFiles);
	}

	@Override
	protected void onEvent(AjaxRequestTarget target, FileList fileList)
	{
		Bytes maxSize = getMaxSize();
		if (maxSize.lessThan(fileList.getSize()))
		{
			addErrorMsg(target, fileList);
			onError(target, fileList);
		}
		else
			onSubmit(target, fileList);
	}

	/**
	 * Retrieves the max size limit from the bound form component's parent form.
	 * 
	 * @return max size limit in bytes
	 */
	public Bytes getMaxSize()
	{
		return getFormComponent().getForm().getMaxSize();
	}

	@Override
	protected void onBind()
	{
		super.onBind();

		if (!(getComponent() instanceof FormComponent))
			throw new WicketRuntimeException("Behavior " + getClass().getName() +
				" can only be added to an instance of a FormComponent");
	}

	/**
	 * Helper method to get the bound {@link FormComponent}.
	 * 
	 * @return form component owning this behavior
	 */
	protected final FormComponent<?> getFormComponent()
	{
		return (FormComponent<?>)getComponent();
	}

	/**
	 * Adds an error message about the exceeded file size limit. By default it mimics the upload
	 * error handling of Form. In o.a.w.markup.html.form.Form look for #handleMultiPart() and
	 * #onFileUploadException().
	 * 
	 * @param target
	 *            wicket object representing the ajax response, not-null
	 * @param fileList
	 *            list of files chosen in the upload field, not-null
	 */
	protected void addErrorMsg(AjaxRequestTarget target, FileList fileList)
	{
		FormComponent<?> component = getFormComponent();
		Bytes maxSize = getMaxSize();
		final Map<String, Object> model = new HashMap<String, Object>();
		model.put("maxSize", maxSize);
		final String defaultValue = "Upload must be less than " + maxSize;
		String msg = component.getString(component.getId() + "." + UPLOAD_TOO_LARGE_RESOURCE_KEY,
			Model.ofMap(model), defaultValue);
		component.error(msg);
	}

	/**
	 * Called when size validation passed.
	 * 
	 * @param target
	 *            wicket object representing the ajax response, not-null
	 * @param fileList
	 *            list of files chosen in the upload field, not-null
	 */
	protected abstract void onSubmit(AjaxRequestTarget target, FileList fileList);

	/**
	 * Called when size validation failed.
	 * 
	 * @param target
	 *            wicket object representing the ajax response, not-null
	 * @param fileList
	 *            list of files chosen in the upload field, not-null
	 */
	protected abstract void onError(AjaxRequestTarget target, FileList fileList);

}
