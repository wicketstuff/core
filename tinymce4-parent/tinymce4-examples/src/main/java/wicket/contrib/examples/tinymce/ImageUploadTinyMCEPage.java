package wicket.contrib.examples.tinymce;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;
import wicket.contrib.tinymce4.TinyMceBehavior;
import wicket.contrib.tinymce4.image.ImageUploadPanel;
import wicket.contrib.tinymce4.settings.TinyMCESettings;
import wicket.contrib.tinymce4.settings.Toolbar;

/**
 * 
 * @author mikel Date: Apr 26, 2010
 */
public class ImageUploadTinyMCEPage extends TinyMCEBasePage
{

	private static final long serialVersionUID = 1L;

	public ImageUploadTinyMCEPage()
	{
		TinyMCESettings settings = new TinyMCESettings(TinyMCESettings.Theme.modern);
		
		settings.addToolbar(new Toolbar(
				"toolbar",
				"insertfile undo redo | styleselect | bold italic | image | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | imageupload"));
		settings.addPlugins("imageupload");
		settings.addPlugins("image");
		
		TextArea<String> textArea = new TextArea<>("ta", new Model<String>(TEXT));
		textArea.add(new TinyMceBehavior(settings));
		
		add(textArea);
		add(new ImageUploadPanel("uploadPanel"));
	}

	private static final String TEXT = "<p><img src=\"/logo.jpg\" alt=\" \" hspace=\"5\" vspace=\"5\" width=\"250\" height=\"48\" align=\"right\" />"
		+ "TinyMCE is a platform independent web based Javascript HTML <strong>WYSIWYG</strong> editor control released as Open Source under LGPL by Moxiecode Systems AB. "
		+ "It has the ability to convert HTML TEXTAREA fields or other HTML elements to editor instances. TinyMCE is very easy to integrate into other Content Management Systems.</p>"
		+ "<p>We recommend <a href=\"http://www.getfirefox.com\" target=\"_blank\">Firefox</a> and <a href=\"http://www.google.com\" target=\"_blank\">Google</a> <br /></p>";

}
