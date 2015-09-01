package com.googlecode.wicket.kendo.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;

import com.googlecode.wicket.kendo.ui.resource.KendoGlobalizeResourceReference;
import com.googlecode.wicket.kendo.ui.settings.KendoUILibrarySettings;

/**
 * {@link HeaderItem} executing client side javascript call
 * 
 * <code>
 * <pre>
 * &lt;script type="text/javascript" id="kendo-culture"&gt;
 * 	kendo.culture('de-DE');
 * &lt;/script&gt;
 * </pre>
 * </code>
 * 
 * Also guarantees dependencies to {@code KendoUILibrarySettings#getJavaScriptReference()} and to particular culture resource reference file
 * 
 * <pre>
 * &lt;script type="text/javascript" src="./resource/com.googlecode.wicket.kendo.ui.resource.KendoGlobalizeResourceReference/kendo.culture.de-DE.js"&gt;&lt;/script&gt;
 * 
 * <pre>
 * 
 * @author Patrick Davids - Patrick1701
 *
 */
public class KendoCultureHeaderItem extends JavaScriptContentHeaderItem
{
	private static final long serialVersionUID = 1L;

	private final Locale locale;

	/**
	 * Constructor
	 * 
	 * @param locale the {@link Locale}
	 */
	public KendoCultureHeaderItem(Locale locale)
	{
		super(String.format("kendo.culture('%s')", locale.toLanguageTag()), "kendo-culture", null);

		this.locale = locale;
	}

	@Override
	public List<HeaderItem> getDependencies()
	{
		List<HeaderItem> dependencies = new ArrayList<HeaderItem>();

		// depends on
		dependencies.add(JavaScriptHeaderItem.forReference(KendoUILibrarySettings.get().getJavaScriptReference()));

		// depends on
		dependencies.add(JavaScriptHeaderItem.forReference(new KendoGlobalizeResourceReference(this.locale)));

		return dependencies;
	}
}
