package wicket.contrib.tinymce.settings;

/**
 * Enables the Auto Resize plugin, which automatically resizes the editor to the content inside it.
 * 
 * <a href="http://tinymce.moxiecode.com/wiki.php/Plugin:autoresize">http://
 * tinymce.moxiecode.com/wiki.php/Plugin:autoresize</a>
 * 
 * @author jbrookover
 * 
 */
public class AutoResizePlugin extends Plugin
{

	private static final long serialVersionUID = 1L;

	public AutoResizePlugin()
	{
		super("autoresize");
	}

}