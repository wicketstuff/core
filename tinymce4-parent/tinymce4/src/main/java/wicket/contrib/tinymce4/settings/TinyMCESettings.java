/*
    This file is part of Wicket-Contrib-TinyMce. See
    <http://http://wicketstuff.org/confluence/display/STUFFWIKI/wicket-contrib-tinymce>

    Wicket-Contrib-TinyMce is free software: you can redistribute it and/
    or modify it under the terms of the GNU Lesser General Public License
    as published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    Wicket-Contrib-TinyMce is distributed in the hope that it will be
    useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with Wicket-Contrib-TinyMce.  If not, see
    <http://www.gnu.org/licenses/>.
 */
package wicket.contrib.tinymce4.settings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wicket.contrib.tinymce4.TinyMceBehavior;

/**
 * Settings class for TinyMCE editor. User can add/remove buttons, enable/disable resizing, change
 * positions, orientation, alignment and much more.
 * 
 * @author Iulian-Corneliu Costan (iulian.costan@gmail.com)
 * @author Frank Bille Jensen (fbille@avaleo.net)
 * @see Plugin
 * @see Button
 */
public class TinyMCESettings implements Serializable
{
	private static final long serialVersionUID = 3L;
	private static final Logger LOG = LoggerFactory.getLogger(TinyMCESettings.class);
	
	public static final ResourceReference TINYMCE_JS_REF = new JavaScriptResourceReference(
			TinyMceBehavior.class, "tinymce/tinymce.js");
	public static final ResourceReference TINYMCE_JS_REF_MIN = new JavaScriptResourceReference(
			TinyMceBehavior.class, "tinymce/tinymce.min.js");
	
	private Theme theme;
	private Language language;
	private Boolean resizing = false;
	private Boolean readOnly = false;
	private Boolean menuBar = true;
	private Boolean inLine = false;

	private Boolean convertUrls = null;
	private Boolean removeScriptHost = null;
	private Boolean relativeUrls = null;
	
	private String blockFormats = null;
	private ResourceReference contentCss = null;
	private String documentBaseUrl;

	private final List<String> plugins;
	private final List<String> customSettings;
	private final List<Toolbar> toolbars;
	
	public TinyMCESettings()
	{
		this(Theme.modern);
	}

	public TinyMCESettings(Theme theme)
	{
		this(theme, selectLang());
	}

	public TinyMCESettings(Theme theme, Language lang)
	{
		this.theme = theme;
		this.language = lang;
		this.plugins = new ArrayList<>();
		this.customSettings = new ArrayList<>();
		this.toolbars = new ArrayList<>();
	}

	private static Language selectLang()
	{
		try
		{
			return Language.valueOf(Session.get().getLocale().getLanguage());
		}
		catch (IllegalArgumentException e)
		{
			return null;
		}
	}

	public Theme getTheme()
	{
		return theme;
	}

	public Language getLanguage()
	{
		return language;
	}

	public String getDocumentBaseUrl()
	{
		return documentBaseUrl;
	}

	public TinyMCESettings setDocumentBaseUrl(String documentBaseUrl)
	{
		this.documentBaseUrl = documentBaseUrl;
		return this;
	}

	public TinyMCESettings addCustomSetting(String customSetting)
	{
		customSettings.add(customSetting);
		return this;
	}

	public String[] getCustomSettings()
	{
		return customSettings.toArray(new String[customSettings.size()]);
	}

	public ResourceReference getContentCss()
	{
		return contentCss;
	}

	public TinyMCESettings setContentCss(ResourceReference contentCss)
	{
		this.contentCss = contentCss;
		return this;
	}

	public String getBlockFormats()
	{
		return blockFormats;
	}

	public TinyMCESettings setBlockFormats(String blockFormats)
	{
		this.blockFormats = blockFormats;
		return this;
	}
        
	public TinyMCESettings setReadOnly(boolean readOnly)
	{
		this.readOnly = readOnly;
		return this;
	}

	public boolean isReadOnly()
	{
		return readOnly;
	}

	public TinyMCESettings setResizing(Boolean resizing)
	{
		this.resizing = resizing;
		return this;
	}

	public Boolean getResizing()
	{
		return resizing;
	}

	/**
	 * This option enables you to control if TinyMCE is to be clever and restore urls to their
	 * original values. URLs are auto converted/messed up by default since the built in browser
	 * logic works this way, there is no way to get the real URL unless you store it away. If you
	 * set this option to false it will try to keep these URLs intact. This option is set to true by
	 * default that means URLs will be forced absolute or relative depending on the state of
	 * relative_urls.
	 * 
	 * @param convertUrls
	 */
	public TinyMCESettings setConvertUrls(boolean convertUrls)
	{
		this.convertUrls = convertUrls;
		return this;
	}

	public Boolean getConvertUrls()
	{
		return convertUrls;
	}

	/**
	 * If this option is enabled the protocol and host part of the URLs returned from the
	 * MCFileManager will be removed. This option is only used if the relative_urls option is set to
	 * false. This option is set to true by default.
	 * 
	 * URL:s will be returned in this format: "/somedir/somefile.htm" instead of the default mode:
	 * "http://www.somesite.com/somedir/somefile.htm".
	 * 
	 * @param removeScriptHost
	 */
	public TinyMCESettings setRemoveScriptHost(Boolean removeScriptHost)
	{
		this.removeScriptHost = removeScriptHost;
		return this;
	}

	public Boolean getRemoveScriptHost()
	{
		return removeScriptHost;
	}

	/**
	 * If this option is set to true, all URLs returned from the MCFileManager will be relative from
	 * the specified document_base_url. If it's set to false all URLs will be converted to absolute
	 * URLs. This option is set to true by default.
	 * 
	 * @param relativeUrls
	 */
	public TinyMCESettings setRelativeUrls(Boolean relativeUrls)
	{
		this.relativeUrls = relativeUrls;
		return this;
	}

	public Boolean getRelativeUrls()
	{
		return relativeUrls;
	}

	/**
	 * Generates the initialisation script. Internal API, do not call.
	 */
	public final String toJavaScript(Mode mode, Collection<Component> components)
	{
		StringBuffer buffer = new StringBuffer();

		// mode
		buffer.append("\n\t")
			.append("mode").append(" : ").append('"')
			.append(mode.getName()).append('"');
		
		if (Mode.exact.equals(mode))
		{
			addElements(components, buffer);
		}
		
		// language
		if (language != null)
		{
			appendSingleConfigElement(buffer, "language", language.toString(), true);
		}
		
		// theme
		appendSingleConfigElement(buffer, "theme", theme.getName(), true);
		
		// other settings
		buffer.append(toJavaScript());

		return buffer.toString();
	}

	public String toJavaScript()
	{
		StringBuffer buffer = new StringBuffer();

		if (convertUrls != null)
		{
			appendSingleConfigElement(buffer, "convert_urls", Boolean.toString(convertUrls));
		}
		
		if (relativeUrls != null)
		{
			appendSingleConfigElement(buffer, "relative_urls", Boolean.toString(relativeUrls));
		}
		
		if (removeScriptHost != null)
		{
			appendSingleConfigElement(buffer, "remove_script_host", Boolean.toString(removeScriptHost));
		}
		
		if (readOnly)
		{
			appendSingleConfigElement(buffer, "readonly", "true"); 
		}
		
		if (contentCss != null)
		{
			String contentCssUrl = RequestCycle.get().urlFor(contentCss, null).toString();
			appendSingleConfigElement(buffer, "content_css", contentCssUrl, true);
		}
		
		if (documentBaseUrl != null)
		{
			appendSingleConfigElement(buffer, "document_base_url", documentBaseUrl, true);
		}
		
		if (!menuBar) 
		{
			appendSingleConfigElement(buffer, "menubar", "false");
		}
		
		if (inLine)
		{
			appendSingleConfigElement(buffer, "inline", "true");
		}

		appendPluginsSettings(buffer);
		appendToolbarsSettings(buffer);
		appendCustomSettings(buffer);

		return buffer.toString();
	}

	private void appendPluginsSettings(StringBuffer buffer) 
	{
		if(plugins.size() < 1)
		{
			return;
		}
		
		buffer.append(",\n\t").append("plugins: [\n\"");
		
		for (String plugin : plugins)
		{
			buffer.append(plugin).append(' ');
		}
		
		buffer.append("\"\n]");
	}

	private void appendToolbarsSettings(StringBuffer buffer) 
	{
		for (Toolbar toolbar : toolbars) 
		{
			appendSingleConfigElement(buffer, toolbar.getId(), toolbar.toString(), true);
		}
	}

	private void appendCustomSettings(StringBuffer buffer)
	{
		for (String line : customSettings)
		{
			buffer.append(",\n\t").append(line);
		}
	}

	private void addElements(Collection<Component> components, StringBuffer buffer)
	{
		if (components.size() > 0)
		{
			buffer.append(",\n\telements : \"");
			Iterator<Component> iterator = components.iterator();
			while (iterator.hasNext())
			{
				Component component = iterator.next();
				buffer.append(component.getMarkupId());
				if (iterator.hasNext())
					buffer.append(", ");
			}
			buffer.append("\"");
		}
		else
			LOG.warn("tinymce is set to \"exact\" mode but there are no components attached");
	}

	public TinyMCESettings addPlugins(String pluginName)
	{
		plugins.add(pluginName);
		return this;
	}
	
	public TinyMCESettings addToolbar(Toolbar toolbar)
	{
		toolbars.add(toolbar);
		return this;
	}

	/**
	 * This class specifies how elements is to be converted into TinyMCE WYSIWYG editor instances.
	 * This option can be set to any of the values below:
	 * <ul>
	 * <li>textareas - converts all textarea elements to editors when the page loads.</li>
	 * <li>exact - exact - Converts only explicit elements, these are listed in the elements option.
	 * </li>
	 * <li>specific_textares - Converts all textarea elements with the a textarea_trigger attribute
	 * set to "true".</li>
	 * </ul>
	 * At this moment, only <b>textareas</b> and <b>exacat</b> modes are supported.
	 */
	public static class Mode extends Enum
	{
		private static final long serialVersionUID = 1L;

		public static final Mode none = new Mode("none");
		public static final Mode exact = new Mode("exact");

		private Mode(String name)
		{
			super(name);
		}
	}

	/**
	 * Language enum
	 */
	public static enum Language
	{
		ar, bg, bs, ca, ch, cs, da, de, el, en, es, et, fa, fi, fr, gl, he, hr, hu, ia, ii, is, it, ja, ko, lt, lv, mk, ms, nb, nl, nn, pl, pt, ro, ru, sc, se, si, sk, sl, sr, sv, tr, tt, tw, uk, vi, zh
	}

	/**
	 * This class enables you to specify what theme to use when rendering the TinyMCE WYSIWYG editor
	 * instances. Two themes are supported:
	 * <ul>
	 * <li>simple - This is the most simple theme for TinyMCE it contains only the basic functions.</li>
	 * <li>advanced - This theme enables users to add/remove buttons and panels .</li>
	 * </ul>
	 */
	public static class Theme extends Enum
	{
		private static final long serialVersionUID = 1L;
		public static final Theme modern = new Theme("modern");		

		private Theme(String name)
		{
			super(name);
		}
	}

	public Boolean getMenuBar() {
		return menuBar;
	}

	public TinyMCESettings setMenuBar(Boolean menuBar)
	{
		this.menuBar = menuBar;
		return this;
	}
	
	public static void appendSingleConfigElement(StringBuffer buffer, String name, String value)
	{
		appendSingleConfigElement(buffer, name, value, false);
	}
	
	public static void appendSingleConfigElement(StringBuffer buffer, String name, String value, 
												   boolean wrapValueWithQuotes)
	{
		String quotes = wrapValueWithQuotes ? "\"" : "";
		
		buffer.append(",\n\t")
		.append(name).append(" : ").append(quotes)
		.append(value).append(quotes);
	}

	public Boolean getInLine()
	{
		return inLine;
	}

	public TinyMCESettings setInLine(Boolean inLine)
	{
		this.inLine = inLine;
		return this;
	}
}
