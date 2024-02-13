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
package wicket.contrib.tinymce6.settings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wicket.contrib.tinymce6.TinyMceBehavior;

/**
 * Settings class for TinyMCE editor. User can add/remove buttons, enable/disable resizing, change
 * positions, orientation, alignment and much more.
 *
 * @author Iulian-Corneliu Costan (iulian.costan@gmail.com)
 * @author Frank Bille Jensen (fbille@avaleo.net)
 * @see Button
 */
public class TinyMCESettings implements Serializable
{
	private static final Logger LOG = LoggerFactory.getLogger(TinyMCESettings.class);
	private static final String NEWLINE_TAB = "\n\t";

	public static final ResourceReference TINYMCE_JS_REF_MIN = new JavaScriptResourceReference(
		TinyMceBehavior.class, "tinymce/tinymce.min.js");

	private final Language language;
	private boolean resizing = false;
	private boolean readOnly = false;
	private boolean menuBar = true;
	private boolean statusBar = false;
	private boolean inLine = false;

	private Boolean convertUrls = null;
	private boolean typeaheadUrls = false;
	private Boolean removeScriptHost = null;
	private Boolean relativeUrls = null;
	private Boolean browserSpellcheck = null;

	private String blockFormats = null;
	private String contentCss = null;
	private String documentBaseUrl;

	private final List<TinyMCE6Plugin> plugins;
	private final List<String> customSettings;
	private final Map<String, Toolbar> toolbars;

	public TinyMCESettings()
	{
		this(selectLang());
	}

	public TinyMCESettings(Language lang)
	{
		this.language = lang;
		this.plugins = new ArrayList<>();
		this.customSettings = new ArrayList<>();
		this.toolbars = new HashMap<>();
	}

	private static Language selectLang()
	{
		return Language.fromLocale(Session.get().getLocale());
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

	public String getContentCss()
	{
		return contentCss;
	}

	/**
	 * Set the Editor content.css. There are 4 CSS provided: dark, default, document, writer
	 *
	 * @param contentCss
	 * @return
	 */
	public TinyMCESettings setContentCss(String contentCss)
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
	 * <p>
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
	 * Set Browser-Spellcheck to true or false (default).
	 *
	 * @param browserSpellcheck
	 *            true or false
	 * @return this
	 */
	public TinyMCESettings setBrowserSpellcheck(boolean browserSpellcheck)
	{
		this.browserSpellcheck = browserSpellcheck;

		return this;
	}

	/**
	 * Generates the initialisation script. Internal API, do not call.
	 */
	public final String toJavaScript(Collection<Component> components)
	{
		final var builder = new StringBuilder();
		addElements(components, builder);

		// language
		if (language != null && language != Language.en_GB) // en_GB is default and does not need a
		// specific JS
		{
			appendSingleConfigElement(builder, "language", language.toString(), true);
		}

		// theme
		appendSingleConfigElement(builder, "theme", "silver", true);

		// other settings
		builder.append(toJavaScript());

		return builder.toString();
	}

	public String toJavaScript()
	{
		final var builder = new StringBuilder();

		if (convertUrls != null)
		{
			appendSingleConfigElement(builder, "convert_urls", Boolean.toString(convertUrls));
		}

		if (relativeUrls != null)
		{
			appendSingleConfigElement(builder, "relative_urls", Boolean.toString(relativeUrls));
		}

		if (removeScriptHost != null)
		{
			appendSingleConfigElement(builder, "remove_script_host",
				Boolean.toString(removeScriptHost));
		}

		if (Boolean.TRUE.equals(readOnly))
		{
			appendSingleConfigElement(builder, "readonly", "true");
		}

		if (contentCss != null)
		{
			appendSingleConfigElement(builder, "content_css", contentCss, true);
		}

		if (documentBaseUrl != null)
		{
			appendSingleConfigElement(builder, "document_base_url", documentBaseUrl, true);
		}

		if (Boolean.FALSE.equals(menuBar))
		{
			appendSingleConfigElement(builder, "menubar", "false");
		}

		if (Boolean.FALSE.equals(statusBar))
		{
			appendSingleConfigElement(builder, "statusbar", "false");
		}

		if (Boolean.TRUE.equals(inLine))
		{
			appendSingleConfigElement(builder, "inline", "true");
		}

		if (Boolean.TRUE.equals(browserSpellcheck))
		{
			appendSingleConfigElement(builder, "browser_spellcheck", "true");
		}

		if (Boolean.FALSE == typeaheadUrls)
		{
			appendSingleConfigElement(builder, "typeahead_urls", "true");
		}

		appendPluginsSettings(builder);
		appendToolbarsSettings(builder);
		appendCustomSettings(builder);

		return builder.toString();
	}

	private void appendPluginsSettings(StringBuilder buffer)
	{
		if (plugins.isEmpty())
		{
			return;
		}

		buffer.append("," + NEWLINE_TAB).append("plugins: [");
		buffer.append(plugins.stream().map(p -> "'" + p + "'").collect(Collectors.joining(",")));
		buffer.append("]");
	}

	private void appendToolbarsSettings(StringBuilder buffer)
	{
		for (Toolbar toolbar : toolbars.values())
		{
			appendSingleConfigElement(buffer, toolbar.getId(), toolbar.toString(), true);
		}
	}

	private void appendCustomSettings(StringBuilder buffer)
	{
		for (String line : customSettings)
		{
			buffer.append("," + NEWLINE_TAB).append(line);
		}
	}

	private void addElements(Collection<Component> components, StringBuilder buffer)
	{
		if (!components.isEmpty())
		{
			buffer.append(NEWLINE_TAB + "selector : \"");
			buffer.append(components.stream().map(c -> '#' + c.getMarkupId())
				.collect(Collectors.joining(", ")));
			buffer.append("\"");
		}
		else
		{
			LOG.warn("tinymce is set to \"exact\" mode but there are no components attached");
		}
	}

	/**
	 * Add {@link TinyMCE6Plugin}s
	 *
	 * @param plugin
	 *            Varargs of {@link TinyMCE6Plugin} to add to the settings.
	 * @return this
	 */
	public TinyMCESettings addPlugins(TinyMCE6Plugin... plugin)
	{
		plugins.addAll(Arrays.asList(plugin));

		return this;
	}

	/**
	 * Add a Toolbar to the settings. If the ID of the Toolbar is already in the settings. The
	 * toolbar gets overwritten
	 *
	 * @param toolbar
	 *            Toolbar.
	 * @return this
	 */
	public TinyMCESettings addToolbar(Toolbar toolbar)
	{
		toolbars.put(toolbar.getId(), toolbar);
		return this;
	}

	/**
	 * Language enum
	 */
	public enum Language
	{
		ar, ar_SA, az, be, bg_BG, bn_BD, bs, ca, cs, cs_CZ, cy, da, de, de_AT, dv, el, en_CA,
		/**
		 * default, no js needed
		 */
		en_GB, eo, es, es_MX, et, eu, fa, fa_IR, fi, fo, fr_CH, fr_FR, ga, gd, gl, he_IL, hi_IN, hr, hu_HU, hy, id, is_IS, it, ja, ka_GE, kab, kk, km_KH, ko, ko_KR, ku, ku_IQ, lb, lt, lv, mk_MK, ml, ml_IN, mn_MN, nb_NO, nl, pl, pt_BR, pt_PT, ro, ru, ru_RU, si_LK, sk, sl_SI, sr, sv_SE, ta, ta_IN, tg, th_TH, tr, tr_TR, tt, ug, uk, uk_UA, vi, vi_VN, zh_CN, zh_TW;

		private static final Map<String, Language> unmappedLocales = new HashMap<>();

		static
		{
			unmappedLocales.put("bg", Language.bg_BG);
			unmappedLocales.put("en", Language.en_GB);
			unmappedLocales.put("fr", Language.fr_FR);
			unmappedLocales.put("hi", Language.hi_IN);
			unmappedLocales.put("hu", Language.hu_HU);
			unmappedLocales.put("is", Language.is_IS);
			unmappedLocales.put("iw", Language.he_IL);
			unmappedLocales.put("mk", Language.mk_MK);
			unmappedLocales.put("nb", Language.nb_NO);
			unmappedLocales.put("no", Language.nb_NO);
			unmappedLocales.put("pt", Language.pt_PT);
			unmappedLocales.put("sl", Language.sl_SI);
			unmappedLocales.put("sv", Language.sv_SE);
			unmappedLocales.put("th", Language.th_TH);
			unmappedLocales.put("zh", Language.zh_CN);
		}

		public static Language fromLocale(final Locale locale)
		{
			Language language = fromString(locale.getLanguage() + "_" + locale.getCountry());
			if (language != null)
			{
				return language;
			}
			language = fromString(locale.getLanguage());
			if (language != null)
			{
				return language;
			}
			language = fromString(locale.getISO3Language());
			if (language != null)
			{
				return language;
			}
			return unmappedLocales.get(locale.getLanguage());
		}

		private static Language fromString(final String string)
		{
			try
			{
				return Language.valueOf(string);
			}
			catch (IllegalArgumentException e)
			{
				return null;
			}
		}
	}

	public Boolean getMenuBar()
	{
		return menuBar;
	}

	/**
	 * Controls if the Menubar is shown. Default is true
	 *
	 * @param menuBar
	 * @return this
	 */
	public TinyMCESettings setMenuBar(boolean menuBar)
	{
		this.menuBar = menuBar;
		return this;
	}

	/**
	 * Controls if the statusbar is shown, default is false
	 *
	 * @param statusBar
	 * @return this
	 */
	public TinyMCESettings setStatusBar(boolean statusBar)
	{
		this.statusBar = statusBar;
		return this;
	}

	/**
	 * Controls if the typeahead URL Feature should be active, default is false.
	 *
	 * @param typeaheadUrls
	 * @return this
	 */
	public TinyMCESettings setTypeaheadUrls(boolean typeaheadUrls)
	{
		this.typeaheadUrls = typeaheadUrls;
		return this;
	}

	/**
	 * Appends a config to the buffer
	 *
	 * @param builder
	 *            {@link StringBuilder}
	 * @param name
	 *            name of the setting
	 * @param value
	 *            value of the setting
	 */
	public static void appendSingleConfigElement(StringBuilder builder, String name, String value)
	{
		appendSingleConfigElement(builder, name, value, false);
	}

	/**
	 * Appends a config to the buffer. Wraps the value in quotes of wrapValueWithQuotes is true.
	 *
	 * @param buffer
	 *            {@link StringBuilder}
	 * @param name
	 *            name of the setting
	 * @param value
	 *            value of the setting
	 * @param wrapValueWithQuotes
	 *            if the value should be wrapped in quotes
	 */
	public static void appendSingleConfigElement(StringBuilder buffer, String name, String value,
		boolean wrapValueWithQuotes)
	{
		String quotes = wrapValueWithQuotes ? "\"" : "";

		buffer.append("," + NEWLINE_TAB).append(name).append(" : ").append(quotes).append(value)
			.append(quotes);
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
