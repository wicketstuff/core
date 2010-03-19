/*
 * Copyright (C) 2005 Iulian-Corneliu Costan
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package wicket.contrib.tinymce.settings;

import org.apache.wicket.Application;

import wicket.contrib.tinymce.TinyMceBehavior;

/**
 * This plugin adds spellchecker functionality to TinyMCE by providing a new button that performs a AJAX call to a
 * backend wicket resource. In order to use this plugin you have to download jazzy spellchecker <a
 * href="http://jazzy.sourceforge.net/">Jazzy</a>.
 * 
 * @author Iulian Costan (iulian.costan@gmail.com)
 */
public class SpellCheckPlugin extends Plugin {
    private static final long serialVersionUID = 1L;

    private static final String resourceKey = "tiny_mce/plugins/spellchecker/tinyspell.php";

    private PluginButton spellCheckButton;

    /**
     * Construct spellchecker plugin.
     */
    public SpellCheckPlugin() {
        super("spellchecker");

        spellCheckButton = new PluginButton("spellchecker", this);

        // add spellchecker resource
        Application.get().getSharedResources().add(TinyMceBehavior.class, resourceKey, null, null,
                new JazzySpellChecker());
    }

    /**
     * @return button the spellcheker button
     */
    public PluginButton getSpellCheckButton() {
        return spellCheckButton;
    }

    protected void definePluginSettings(StringBuffer buffer) {
        define(buffer, "spellchecker_languages", "+English=en");
    }

}
