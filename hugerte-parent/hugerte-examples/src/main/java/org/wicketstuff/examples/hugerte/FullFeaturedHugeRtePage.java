package org.wicketstuff.examples.hugerte;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;
import org.wicketstuff.hugerte.HugeRteBehavior;
import org.wicketstuff.hugerte.settings.HugeRtePlugin;
import org.wicketstuff.hugerte.settings.HugeRteSettings;
import org.wicketstuff.hugerte.settings.Toolbar;

/**
 * @author syca
 */
public class FullFeaturedHugeRtePage extends HugeRteBasePage
{

	private static final long serialVersionUID = 1L;

	public FullFeaturedHugeRtePage()
	{
		HugeRteSettings settings = new HugeRteSettings();

		settings.addPlugins(HugeRtePlugin.advlist, HugeRtePlugin.lists, HugeRtePlugin.accordion,
			HugeRtePlugin.autolink);


		settings.addToolbar(new Toolbar("toolbar1",
			"newdocument fullpage | bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | styleselect formatselect fontselect fontsizeselect"));
		settings.addToolbar(new Toolbar("toolbar2",
			"cut copy paste | searchreplace | bullist numlist | outdent indent blockquote | undo redo | link unlink anchor image media code | inserttime preview | forecolor backcolor"));
		settings.addToolbar(new Toolbar("toolbar3",
			"table | hr removeformat | subscript superscript | charmap emoticons | print fullscreen | ltr rtl | spellchecker | visualchars visualblocks nonbreaking template pagebreak restoredraft"));

		// custom settings
		String styleFormats = "style_formats : [" + "{title : 'Bold text', inline : 'b'},"
			+ "{title : 'Red text', inline : 'span', styles : {color : '#ff0000'}},"
			+ "{title : 'Red header', block : 'h1', styles : {color : '#ff0000'}},"
			+ "{title : 'Example 1', inline : 'span', classes : 'example1'},"
			+ "{title : 'Example 2', inline : 'span', classes : 'example2'},"
			+ "{title : 'Table styles'},"
			+ "{title : 'Table row 1', selector : 'tr', classes : 'tablerow1'}]";
		settings.addCustomSetting(styleFormats);

		TextArea<String> textArea = new TextArea<>("ta", new Model<>(TEXT));
		textArea.add(new HugeRteBehavior(settings));
		add(textArea);
	}

	private static final String TEXT = "<p><img src=\"../../../../logo.jpg\" alt=\" \" hspace=\"5\" vspace=\"5\" width=\"250\" height=\"48\" align=\"right\" />"
		+ "HugeRTE is a web based Javascript HTML <strong>WYSIWYG</strong> editor, an MIT-licensed fork of TinyMCE 6. "
		+ "It has the ability to convert HTML TEXTAREA fields or other HTML elements to editor instances.</p>"
		+ "<p>We recommend <a href=\"http://www.getfirefox.com\" target=\"_blank\">Firefox</a> and <a href=\"http://www.google.com\" target=\"_blank\">Google</a> <br /></p>";

}
