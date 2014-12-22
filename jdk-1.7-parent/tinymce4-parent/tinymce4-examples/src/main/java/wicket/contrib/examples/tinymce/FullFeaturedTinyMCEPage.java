package wicket.contrib.examples.tinymce;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;
import wicket.contrib.tinymce4.TinyMceBehavior;
import wicket.contrib.tinymce4.settings.TinyMCESettings;
import wicket.contrib.tinymce4.settings.Toolbar;

/**
 * @author syca
 */
public class FullFeaturedTinyMCEPage extends TinyMCEBasePage
{

	private static final long serialVersionUID = 1L;

	public FullFeaturedTinyMCEPage()
	{
		TinyMCESettings settings = new TinyMCESettings(TinyMCESettings.Theme.modern);
		String plugins = "advlist autolink autosave link image lists charmap print preview hr anchor pagebreak spellchecker " +
                "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking " +
                "table contextmenu directionality emoticons template textcolor paste fullpage colorpicker";

		String[] pluginNames = plugins.split(" ");
		
		for (String name : pluginNames) 
		{
			settings.addPlugins(name);
		}
		
		settings.setMenuBar(false);
        
		settings.addToolbar(new Toolbar("toolbar1", 
										"newdocument fullpage | bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | styleselect formatselect fontselect fontsizeselect"));
		settings.addToolbar(new Toolbar("toolbar2", 
										"cut copy paste | searchreplace | bullist numlist | outdent indent blockquote | undo redo | link unlink anchor image media code | inserttime preview | forecolor backcolor"));
		settings.addToolbar(new Toolbar("toolbar3", 
				"table | hr removeformat | subscript superscript | charmap emoticons | print fullscreen | ltr rtl | spellchecker | visualchars visualblocks nonbreaking template pagebreak restoredraft"));
		
        // custom settings
        String styleFormats = "style_formats : ["
                + "{title : 'Bold text', inline : 'b'},"
                + "{title : 'Red text', inline : 'span', styles : {color : '#ff0000'}},"
                + "{title : 'Red header', block : 'h1', styles : {color : '#ff0000'}},"
                + "{title : 'Example 1', inline : 'span', classes : 'example1'},"
                + "{title : 'Example 2', inline : 'span', classes : 'example2'},"
                + "{title : 'Table styles'},"
                + "{title : 'Table row 1', selector : 'tr', classes : 'tablerow1'}]";
        settings.addCustomSetting(styleFormats);
        
        //settings.addCustomSetting("content_css : \"../../../../../content.css\"");

		TextArea<String> textArea = new TextArea<>("ta", new Model<String>(TEXT));
		textArea.add(new TinyMceBehavior(settings));
		add(textArea);
	}

	private static final String TEXT = "<p><img src=\"../../../../logo.jpg\" alt=\" \" hspace=\"5\" vspace=\"5\" width=\"250\" height=\"48\" align=\"right\" />"
		+ "TinyMCE is a platform independent web based Javascript HTML <strong>WYSIWYG</strong> editor control released as Open Source under LGPL by Moxiecode Systems AB. "
		+ "It has the ability to convert HTML TEXTAREA fields or other HTML elements to editor instances. TinyMCE is very easy to integrate into other Content Management Systems.</p>"
		+ "<p>We recommend <a href=\"http://www.getfirefox.com\" target=\"_blank\">Firefox</a> and <a href=\"http://www.google.com\" target=\"_blank\">Google</a> <br /></p>";

}
