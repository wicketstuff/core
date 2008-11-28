package wicket.contrib.examples.tinymce;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;

import wicket.contrib.tinymce.TinyMceBehavior;
import wicket.contrib.tinymce.settings.ContextMenuPlugin;
import wicket.contrib.tinymce.settings.DateTimePlugin;
import wicket.contrib.tinymce.settings.DirectionalityPlugin;
import wicket.contrib.tinymce.settings.EmotionsPlugin;
import wicket.contrib.tinymce.settings.FlashPlugin;
import wicket.contrib.tinymce.settings.FullScreenPlugin;
import wicket.contrib.tinymce.settings.IESpellPlugin;
import wicket.contrib.tinymce.settings.PastePlugin;
import wicket.contrib.tinymce.settings.PreviewPlugin;
import wicket.contrib.tinymce.settings.PrintPlugin;
import wicket.contrib.tinymce.settings.SavePlugin;
import wicket.contrib.tinymce.settings.SearchReplacePlugin;
import wicket.contrib.tinymce.settings.SpellCheckPlugin;
import wicket.contrib.tinymce.settings.TablePlugin;
import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.ZoomPlugin;

/**
 * @author syca
 */
public class FullFeaturedTinyMCEPage extends TinyMCEBasePage {

    public FullFeaturedTinyMCEPage() {
        TinyMCESettings settings = new TinyMCESettings(TinyMCESettings.Theme.advanced);

        ContextMenuPlugin contextMenuPlugin = new ContextMenuPlugin();
        settings.register(contextMenuPlugin);

        // first toolbar
        SavePlugin savePlugin = new SavePlugin();
        settings.add(savePlugin.getSaveButton(), TinyMCESettings.Toolbar.first, TinyMCESettings.Position.before);
        settings.add(TinyMCESettings.newdocument, TinyMCESettings.Toolbar.first, TinyMCESettings.Position.before);
        settings.add(TinyMCESettings.separator, TinyMCESettings.Toolbar.first, TinyMCESettings.Position.before);
        settings.add(TinyMCESettings.fontselect, TinyMCESettings.Toolbar.first, TinyMCESettings.Position.after);
        settings.add(TinyMCESettings.fontsizeselect, TinyMCESettings.Toolbar.first, TinyMCESettings.Position.after);

        // second toolbar
        PastePlugin pastePlugin = new PastePlugin();
        SearchReplacePlugin searchReplacePlugin = new SearchReplacePlugin();
        DateTimePlugin dateTimePlugin = new DateTimePlugin();
        dateTimePlugin.setDateFormat("Date: %m-%d-%Y");
        dateTimePlugin.setTimeFormat("Time: %H:%M");
        PreviewPlugin previewPlugin = new PreviewPlugin();
        ZoomPlugin zoomPlugin = new ZoomPlugin();
        settings.add(TinyMCESettings.cut, TinyMCESettings.Toolbar.second, TinyMCESettings.Position.before);
        settings.add(TinyMCESettings.copy, TinyMCESettings.Toolbar.second, TinyMCESettings.Position.before);
        settings.add(pastePlugin.getPasteButton(), TinyMCESettings.Toolbar.second, TinyMCESettings.Position.before);
        settings.add(pastePlugin.getPasteTextButton(), TinyMCESettings.Toolbar.second, TinyMCESettings.Position.before);
        settings.add(pastePlugin.getPasteWordButton(), TinyMCESettings.Toolbar.second, TinyMCESettings.Position.before);
        settings.add(TinyMCESettings.separator, TinyMCESettings.Toolbar.second, TinyMCESettings.Position.before);
        settings.add(searchReplacePlugin.getSearchButton(), TinyMCESettings.Toolbar.second,
                TinyMCESettings.Position.before);
        settings.add(searchReplacePlugin.getReplaceButton(), TinyMCESettings.Toolbar.second,
                TinyMCESettings.Position.before);
        settings.add(TinyMCESettings.separator, TinyMCESettings.Toolbar.second, TinyMCESettings.Position.before);
        settings.add(TinyMCESettings.separator, TinyMCESettings.Toolbar.second, TinyMCESettings.Position.after);
        settings.add(dateTimePlugin.getDateButton(), TinyMCESettings.Toolbar.second, TinyMCESettings.Position.after);
        settings.add(dateTimePlugin.getTimeButton(), TinyMCESettings.Toolbar.second, TinyMCESettings.Position.after);
        settings.add(TinyMCESettings.separator, TinyMCESettings.Toolbar.second, TinyMCESettings.Position.after);
        settings.add(previewPlugin.getPreviewButton(), TinyMCESettings.Toolbar.second, TinyMCESettings.Position.after);
        settings.add(zoomPlugin.getZoomButton(), TinyMCESettings.Toolbar.second, TinyMCESettings.Position.after);
        settings.add(TinyMCESettings.separator, TinyMCESettings.Toolbar.second, TinyMCESettings.Position.after);
        settings.add(TinyMCESettings.forecolor, TinyMCESettings.Toolbar.second, TinyMCESettings.Position.after);
        settings.add(TinyMCESettings.backcolor, TinyMCESettings.Toolbar.second, TinyMCESettings.Position.after);

        // third toolbar
        TablePlugin tablePlugin = new TablePlugin();
        EmotionsPlugin emotionsPlugin = new EmotionsPlugin();
        IESpellPlugin iespellPlugin = new IESpellPlugin();
        FlashPlugin flashPlugin = new FlashPlugin();
        PrintPlugin printPlugin = new PrintPlugin();
        FullScreenPlugin fullScreenPlugin = new FullScreenPlugin();
        DirectionalityPlugin directionalityPlugin = new DirectionalityPlugin();
        settings.add(tablePlugin.getTableControls(), TinyMCESettings.Toolbar.third, TinyMCESettings.Position.before);
        settings.add(emotionsPlugin.getEmotionsButton(), TinyMCESettings.Toolbar.third, TinyMCESettings.Position.after);
        settings.add(iespellPlugin.getIespellButton(), TinyMCESettings.Toolbar.third, TinyMCESettings.Position.after);
        settings.add(flashPlugin.getFlashButton(), TinyMCESettings.Toolbar.third, TinyMCESettings.Position.after);
        settings.add(TinyMCESettings.separator, TinyMCESettings.Toolbar.third, TinyMCESettings.Position.after);
        settings.add(printPlugin.getPrintButton(), TinyMCESettings.Toolbar.third, TinyMCESettings.Position.after);
        settings.add(TinyMCESettings.separator, TinyMCESettings.Toolbar.third, TinyMCESettings.Position.after);
        settings
                .add(directionalityPlugin.getLtrButton(), TinyMCESettings.Toolbar.third, TinyMCESettings.Position.after);
        settings
                .add(directionalityPlugin.getRtlButton(), TinyMCESettings.Toolbar.third, TinyMCESettings.Position.after);
        settings.add(TinyMCESettings.separator, TinyMCESettings.Toolbar.third, TinyMCESettings.Position.after);
        settings.add(fullScreenPlugin.getFullscreenButton(), TinyMCESettings.Toolbar.third,
                TinyMCESettings.Position.after);

        // fourth toolbar
        SpellCheckPlugin spellCheckPlugin = new SpellCheckPlugin();
        settings.add(spellCheckPlugin.getSpellCheckButton(), TinyMCESettings.Toolbar.fourth,
                TinyMCESettings.Position.after);

        // other settings
        settings.setToolbarAlign(TinyMCESettings.Align.left);
        settings.setToolbarLocation(TinyMCESettings.Location.top);
        settings.setStatusbarLocation(TinyMCESettings.Location.bottom);
        settings.setVerticalResizing(true);

        TextArea textArea = new TextArea("ta", new Model(TEXT));
        textArea.add(new TinyMceBehavior(settings));
        add(textArea);
    }

    private static final String TEXT = "<p><img src=\"logo.jpg\" alt=\" \" hspace=\"5\" vspace=\"5\" width=\"250\" height=\"48\" align=\"right\" />"
            + "TinyMCE is a platform independent web based Javascript HTML <strong>WYSIWYG</strong> editor control released as Open Source under LGPL by Moxiecode Systems AB. "
            + "It has the ability to convert HTML TEXTAREA fields or other HTML elements to editor instances. TinyMCE is very easy to integrate into other Content Management Systems.</p>"
            + "<p>We recommend <a href=\"http://www.getfirefox.com\" target=\"_blank\">Firefox</a> and <a href=\"http://www.google.com\" target=\"_blank\">Google</a> <br /></p>";

}
