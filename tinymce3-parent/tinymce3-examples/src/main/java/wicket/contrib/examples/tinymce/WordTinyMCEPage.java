package wicket.contrib.examples.tinymce;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;

import wicket.contrib.tinymce.TinyMceBehavior;
import wicket.contrib.tinymce.settings.AdvListPlugin;
import wicket.contrib.tinymce.settings.Button;
import wicket.contrib.tinymce.settings.ContextMenuPlugin;
import wicket.contrib.tinymce.settings.DateTimePlugin;
import wicket.contrib.tinymce.settings.DirectionalityPlugin;
import wicket.contrib.tinymce.settings.EmotionsPlugin;
import wicket.contrib.tinymce.settings.FullScreenPlugin;
import wicket.contrib.tinymce.settings.IESpellPlugin;
import wicket.contrib.tinymce.settings.MediaPlugin;
import wicket.contrib.tinymce.settings.PastePlugin;
import wicket.contrib.tinymce.settings.PreviewPlugin;
import wicket.contrib.tinymce.settings.PrintPlugin;
import wicket.contrib.tinymce.settings.SearchReplacePlugin;
import wicket.contrib.tinymce.settings.TablePlugin;
import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.WordcountPlugin;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class WordTinyMCEPage extends TinyMCEBasePage
{

	private static final long serialVersionUID = 1L;

	public WordTinyMCEPage()
	{
		TinyMCESettings settings = new TinyMCESettings(TinyMCESettings.Theme.advanced);

		settings.register(new ContextMenuPlugin());
                settings.register(new WordcountPlugin());
                settings.register(new AdvListPlugin());

		// first toolbar
		settings.add(Button.newdocument, TinyMCESettings.Toolbar.first,
			TinyMCESettings.Position.before);
		settings.add(Button.separator, TinyMCESettings.Toolbar.first,
			TinyMCESettings.Position.before);
		settings.add(Button.fontselect, TinyMCESettings.Toolbar.first,
			TinyMCESettings.Position.after);
		settings.add(Button.fontsizeselect, TinyMCESettings.Toolbar.first,
			TinyMCESettings.Position.after);

		// second toolbar
		PastePlugin pastePlugin = new PastePlugin();
		SearchReplacePlugin searchReplacePlugin = new SearchReplacePlugin();
		DateTimePlugin dateTimePlugin = new DateTimePlugin();
		dateTimePlugin.setDateFormat("Date: %m-%d-%Y");
		dateTimePlugin.setTimeFormat("Time: %H:%M");
		PreviewPlugin previewPlugin = new PreviewPlugin();
		settings.add(Button.cut, TinyMCESettings.Toolbar.second, TinyMCESettings.Position.before);
		settings.add(Button.copy, TinyMCESettings.Toolbar.second, TinyMCESettings.Position.before);
		settings.add(pastePlugin.getPasteButton(), TinyMCESettings.Toolbar.second,
			TinyMCESettings.Position.before);
		settings.add(pastePlugin.getPasteTextButton(), TinyMCESettings.Toolbar.second,
			TinyMCESettings.Position.before);
		settings.add(pastePlugin.getPasteWordButton(), TinyMCESettings.Toolbar.second,
			TinyMCESettings.Position.before);
		settings.add(Button.separator, TinyMCESettings.Toolbar.second,
			TinyMCESettings.Position.before);
		settings.add(searchReplacePlugin.getSearchButton(), TinyMCESettings.Toolbar.second,
			TinyMCESettings.Position.before);
		settings.add(searchReplacePlugin.getReplaceButton(), TinyMCESettings.Toolbar.second,
			TinyMCESettings.Position.before);
		settings.add(Button.separator, TinyMCESettings.Toolbar.second,
			TinyMCESettings.Position.before);
		settings.add(Button.separator, TinyMCESettings.Toolbar.second,
			TinyMCESettings.Position.after);
		settings.add(dateTimePlugin.getDateButton(), TinyMCESettings.Toolbar.second,
			TinyMCESettings.Position.after);
		settings.add(dateTimePlugin.getTimeButton(), TinyMCESettings.Toolbar.second,
			TinyMCESettings.Position.after);
		settings.add(Button.separator, TinyMCESettings.Toolbar.second,
			TinyMCESettings.Position.after);
		settings.add(previewPlugin.getPreviewButton(), TinyMCESettings.Toolbar.second,
			TinyMCESettings.Position.after);
		settings.add(Button.separator, TinyMCESettings.Toolbar.second,
			TinyMCESettings.Position.after);
		settings.add(Button.forecolor, TinyMCESettings.Toolbar.second,
			TinyMCESettings.Position.after);
		settings.add(Button.backcolor, TinyMCESettings.Toolbar.second,
			TinyMCESettings.Position.after);

		// third toolbar
		TablePlugin tablePlugin = new TablePlugin();
		EmotionsPlugin emotionsPlugin = new EmotionsPlugin();
		IESpellPlugin iespellPlugin = new IESpellPlugin();
		MediaPlugin mediaPlugin = new MediaPlugin();
		PrintPlugin printPlugin = new PrintPlugin();
		FullScreenPlugin fullScreenPlugin = new FullScreenPlugin();
		DirectionalityPlugin directionalityPlugin = new DirectionalityPlugin();
		settings.add(tablePlugin.getTableControls(), TinyMCESettings.Toolbar.third,
			TinyMCESettings.Position.before);
		settings.add(emotionsPlugin.getEmotionsButton(), TinyMCESettings.Toolbar.third,
			TinyMCESettings.Position.after);
		settings.add(iespellPlugin.getIespellButton(), TinyMCESettings.Toolbar.third,
			TinyMCESettings.Position.after);
		settings.add(mediaPlugin.getMediaButton(), TinyMCESettings.Toolbar.third,
			TinyMCESettings.Position.after);
		settings.add(Button.separator, TinyMCESettings.Toolbar.third,
			TinyMCESettings.Position.after);
		settings.add(printPlugin.getPrintButton(), TinyMCESettings.Toolbar.third,
			TinyMCESettings.Position.after);
		settings.add(Button.separator, TinyMCESettings.Toolbar.third,
			TinyMCESettings.Position.after);
		settings.add(directionalityPlugin.getLtrButton(), TinyMCESettings.Toolbar.third,
			TinyMCESettings.Position.after);
		settings.add(directionalityPlugin.getRtlButton(), TinyMCESettings.Toolbar.third,
			TinyMCESettings.Position.after);
		settings.add(Button.separator, TinyMCESettings.Toolbar.third,
			TinyMCESettings.Position.after);
		settings.add(fullScreenPlugin.getFullscreenButton(), TinyMCESettings.Toolbar.third,
			TinyMCESettings.Position.after);

		// other settings
		settings.setToolbarAlign(TinyMCESettings.Align.left);
		settings.setToolbarLocation(TinyMCESettings.Location.top);
		settings.setStatusbarLocation(TinyMCESettings.Location.bottom);
		settings.setResizing(true);

		TextArea textArea = new TextArea("ta", new Model(TEXT));
		textArea.add(new TinyMceBehavior(settings));
		add(textArea);
	}

	private static final String TEXT = "<p>Some paragraph</p>" + "<p>Some other paragraph</p>"
		+ "<p>Some <strong>element</strong>, this is to be editor 1. <br />"
		+ "This editor instance has a 100% width to it. </p>"
		+ "<p>Some paragraph. <a href=\"http://www.sourceforge.net/\">Some link</a></p>"
		+ "<img src=\"/logo.jpg\" border=\"0\" /><p>&nbsp;</p>";
}
