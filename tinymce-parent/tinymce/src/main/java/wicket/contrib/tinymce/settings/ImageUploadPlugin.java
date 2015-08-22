package wicket.contrib.tinymce.settings;

import wicket.contrib.tinymce.image.ImageUploadPanel.ImageUploadBehavior;

/**
 * Tiny MCE plugin for image upload.
 * 
 * @author Michal Letynski <mikel@mikel.pl>
 */
public class ImageUploadPlugin extends Plugin
{

	private static final long serialVersionUID = 1L;

	private PluginButton imageUploadButton;
	private String callbackName;

	public ImageUploadPlugin(ImageUploadBehavior imageUploadBehavior)
	{
		super("imageupload");
		imageUploadButton = new PluginButton("upload", this);
		callbackName = imageUploadBehavior.getCallbackName();
	}

	/**
	 * @return the imageUploadButton
	 */
	public PluginButton getImageUploadButton()
	{
		return imageUploadButton;
	}

	@Override
	protected void definePluginSettings(StringBuffer pBuffer)
	{
		super.definePluginSettings(pBuffer);
		pBuffer.append(",\n\tuploadimage_callback: \"" + callbackName + "\"");
	}
}