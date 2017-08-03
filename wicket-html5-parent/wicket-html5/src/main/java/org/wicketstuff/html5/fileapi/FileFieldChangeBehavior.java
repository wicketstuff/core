package org.wicketstuff.html5.fileapi;

import java.util.Calendar;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.StringValue;

/**
 * <p>
 * A {@link Behavior} that can be used on {@link FileUploadField}-s to receive ajax callback with
 * detailed information about the chosen files when an event occurs (default: "onchange"). When the
 * ajax call is triggered (for example user selects files for upload) the metadata about the file(s)
 * (name, size, type, last modify date) is sent with the ajax call to the server (so the content is
 * not). Subclasses of this class can respond to this event by implementing the
 * {@link #onEvent(AjaxRequestTarget, FileList)} method.
 * </p>
 * <p>
 * Works with single or multiple enabled file inputs.
 * </p>
 * <p>
 * Note that this behaviour only works with browsers implementing the Html5 FileApi (Chrome 11,
 * Firefox 4). It does not change or break browsers not implementing the api.
 * </p>
 * 
 * @author akiraly
 */
public abstract class FileFieldChangeBehavior extends AjaxEventBehavior
{
	private static final long serialVersionUID = -3716216245965207135L;

	protected static final ResourceReference JAVASCRIPT_REF = new JavaScriptResourceReference(
		FileFieldChangeBehavior.class, "fileapi.js");

	/**
	 * Default maximum number of files that are processed from the ajax request: 100.
	 */
	public static final int DEFAULT_MAX_NUM_OF_FILES = 100;

	private final int maxNumOfFiles;

	/**
	 * Default constructor: the monitored event is "onchange" and maxNumOfFiles is
	 * {@link #DEFAULT_MAX_NUM_OF_FILES}.
	 */
	public FileFieldChangeBehavior()
	{
		this(DEFAULT_MAX_NUM_OF_FILES);
	}

	/**
	 * Constructor. The monitored event is "onchange".
	 * 
	 * @param maxNumOfFiles
	 *            maximum number of files that are processed from the ajax request, non-negative
	 */
	public FileFieldChangeBehavior(int maxNumOfFiles)
	{
		this("change", maxNumOfFiles);
	}

	/**
	 * Constructor.
	 * 
	 * @param event
	 *            event for which the ajax call is triggered, not-null
	 * @param maxNumOfFiles
	 *            maximum number of files that are processed from the ajax request, non-negative
	 */
	protected FileFieldChangeBehavior(String event, int maxNumOfFiles)
	{
		super(event);
		this.maxNumOfFiles = maxNumOfFiles;
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		response.render(JavaScriptHeaderItem.forReference(JAVASCRIPT_REF));
	}

	@Override
	protected void onEvent(AjaxRequestTarget target)
	{
		IRequestParameters parameters = RequestCycle.get().getRequest().getPostParameters();

		int num = parameters.getParameterValue("num").toInt();
		if (num > maxNumOfFiles)
			num = maxNumOfFiles;

		FileList fileList = new FileList(num);

		for (int fi = 0; fi < num; fi++)
		{
			String prefix = "file[" + fi + "].";

			String name = parameters.getParameterValue(prefix + "name").toString();
			long size = parameters.getParameterValue(prefix + "size").toLong();
			String type = parameters.getParameterValue(prefix + "type").toString();
			StringValue lastModifiedTimeStr = parameters.getParameterValue(prefix +
				"lastModifiedTime");
			Calendar lastModifiedDate;
			if (!lastModifiedTimeStr.isNull())
			{
				long lastModifiedTime = lastModifiedTimeStr.toLong();
				lastModifiedDate = Calendar.getInstance();
				lastModifiedDate.setTimeInMillis(lastModifiedTime);
			}
			else
				lastModifiedDate = null;
			fileList.set(fi, new Html5File(name, size, type, lastModifiedDate));
		}

		onEvent(target, fileList);
	}

	/**
	 * Called during the ajax callback.
	 * 
	 * @param target
	 *            wicket object representing the ajax response, not-null
	 * @param fileList
	 *            list of files chosen in the upload field, not-null
	 */
	protected abstract void onEvent(AjaxRequestTarget target, FileList fileList);

	@Override
	protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
	{
		super.updateAjaxAttributes(attributes);
		attributes.setMethod(AjaxRequestAttributes.Method.POST);
		AjaxCallListener ajaxCallListener = new AjaxCallListener();
		ajaxCallListener.onPrecondition("return Wicketstuff.fileapi.supports(this)&&Wicket.$('" +
			getComponent().getMarkupId() + "') != null;");

		attributes.getDynamicExtraParameters().add("return Wicketstuff.fileapi.fileFieldToPostBody(Wicket.$(attrs.c));");
	}

	/**
	 * Returns the configured max.
	 * 
	 * @return maximum number of files that are processed from the ajax request, non-negative
	 */
	public int getMaxNumOfFiles()
	{
		return maxNumOfFiles;
	}
}
