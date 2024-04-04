package wicket.contrib.examples.tinymce;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;

import wicket.contrib.tinymce.TinyMceBehavior;
import wicket.contrib.tinymce.image.ImageUploadPanel;
import wicket.contrib.tinymce.settings.Button;
import wicket.contrib.tinymce.settings.ImageUploadPlugin;
import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.TinyMCESettings.Toolbar;

/**
 * 
 * @author mikel Date: Apr 26, 2010
 */
public class ImageUploadTinyMCEPage extends TinyMCEBasePage
{

	private static final long serialVersionUID = 1L;

	public ImageUploadTinyMCEPage()
	{
		TinyMCESettings settings = new TinyMCESettings(TinyMCESettings.Theme.advanced);
		settings.disableButton(Button.styleselect);
		settings.disableButton(Button.hr);
		settings.disableButton(Button.removeformat);
		settings.disableButton(Button.visualaid);
		settings.disableButton(Button.sub);
		settings.disableButton(Button.sup);
		settings.disableButton(Button.charmap);

		settings.add(Button.fontselect, TinyMCESettings.Toolbar.first,
			TinyMCESettings.Position.after);
		settings.add(Button.fontsizeselect, TinyMCESettings.Toolbar.first,
			TinyMCESettings.Position.after);
		settings.add(Button.forecolor, TinyMCESettings.Toolbar.first,
			TinyMCESettings.Position.after);
		settings.add(Button.backcolor, TinyMCESettings.Toolbar.first,
			TinyMCESettings.Position.after);

		settings.setToolbarButtons(Toolbar.second, null);
		settings.setToolbarButtons(Toolbar.third, null);
		settings.setToolbarButtons(Toolbar.fourth, null);

		settings.setToolbarAlign(TinyMCESettings.Align.left);
		settings.setToolbarLocation(TinyMCESettings.Location.top);
		settings.setStatusbarLocation(TinyMCESettings.Location.bottom);
		ImageUploadPanel imageUploadPanel = new ImageUploadPanel("uploadPanel");
		ImageUploadPlugin plugin = new ImageUploadPlugin(imageUploadPanel.getImageUploadBehavior());
		settings.add(plugin.getImageUploadButton(), TinyMCESettings.Toolbar.first,
			TinyMCESettings.Position.after);
		add(imageUploadPanel);
		TextArea<String> textArea = new TextArea<String>("ta", new Model<String>(TEXT));
		textArea.add(new TinyMceBehavior(settings));
		add(textArea);
	}

	private static final String TEXT = "<p><img src=\"/logo.jpg\" alt=\" \" hspace=\"5\" vspace=\"5\" width=\"250\" height=\"48\" align=\"right\" />"
		+ "TinyMCE is a platform independent web based Javascript HTML <strong>WYSIWYG</strong> editor control released as Open Source under LGPL by Moxiecode Systems AB. "
		+ "It has the ability to convert HTML TEXTAREA fields or other HTML elements to editor instances. TinyMCE is very easy to integrate into other Content Management Systems.</p>"
		+ "<p>We recommend <a href=\"http://www.getfirefox.com\" target=\"_blank\">Firefox</a> and <a href=\"http://www.google.com\" target=\"_blank\">Google</a> <br /></p>";

}
