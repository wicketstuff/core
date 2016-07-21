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
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.SharedResourceReference;

import wicket.contrib.tinymce.TinyMceBehavior;

/**
 * This plugin adds spellchecker functionality to TinyMCE by providing a new button that performs an
 * AJAX call to a backend wicket resource. The spell checking is performed by the Jazzy spellchecker
 * <a href="http://jazzy.sourceforge.net/">Jazzy</a>.
 * 
 * @author Iulian Costan (iulian.costan@gmail.com)
 */
public class SpellCheckPlugin extends Plugin
{
	private static final long serialVersionUID = 1L;

	private static final String resourceKey = "spellcheck_rpc";

	private PluginButton spellCheckButton;

	/**
	 * Construct spellchecker plugin.
	 */
	public SpellCheckPlugin()
	{
		super("spellchecker");

		spellCheckButton = new PluginButton("spellchecker", this);

		// add spellchecker resource
		Application.get()
			.getSharedResources()
			.add(TinyMceBehavior.class, resourceKey, null, null, null, new JazzySpellChecker());
	}

	/**
	 * @return button the spellcheker button
	 */
	public PluginButton getSpellCheckButton()
	{
		return spellCheckButton;
	}

	@Override
	protected void definePluginSettings(StringBuffer buffer)
	{
		define(buffer, "spellchecker_languages", "+English=en");
		
		define(buffer, "spellchecker_rpc_url", RequestCycle.get().urlFor(new SharedResourceReference(TinyMceBehavior.class, resourceKey), null).toString());
	}
}
