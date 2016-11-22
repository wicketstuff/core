package com.googlecode.wicket.kendo.ui;

import java.util.List;
import java.util.Locale;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;

import com.googlecode.wicket.kendo.ui.resource.KendoCultureResourceReference;

/**
 * {@link HeaderItem} in charge of setting the {@code kendo.culture} and adding relevant dependencies <br/>
 * Usage:<br/>
 * <code>
 * <pre>
 * public void renderHead(IHeaderResponse response)
 * {
 * 	super.renderHead(response);
 * 	
 * 	response.render(new KendoCultureHeaderItem(KendoCulture.FR_FR));
 * }
 * <br/>
 * This will results to:
 * <code>
 * <pre>
 * &lt;script type="text/javascript" src="./resource/com.googlecode.wicket.kendo.ui.resource.KendoCultureResourceReference/cultures/kendo.culture.fr-FR.js"&gt;&lt;/script&gt;
 * &lt;script type="text/javascript" id="kendo-culture"&gt;
 * 	kendo.culture('fr-FR');
 * &lt;/script&gt;
 * </pre>
 * </code>
 * 
 * @author Patrick Davids - Patrick1701
 *
 */
public class KendoCultureHeaderItem extends JavaScriptContentHeaderItem
{
	private static final long serialVersionUID = 1L;

	private final String culture;

	/**
	 * Constructor
	 * 
	 * @param locale the {@link Locale}, ie: Locale.FRENCH
	 */
	public KendoCultureHeaderItem(Locale locale)
	{
		this(locale.toLanguageTag()); // java7
	}

	/**
	 * Constructor
	 * 
	 * @param culture the {@link KendoCulture}
	 */
	public KendoCultureHeaderItem(KendoCulture culture)
	{
		this(culture.toString());
	}

	/**
	 * Constructor
	 * 
	 * @param culture the culture, ie: 'fr' or 'fr-FR'
	 */
	public KendoCultureHeaderItem(String culture)
	{
		super(String.format("kendo.culture('%s');", culture), "kendo-culture", null);

		this.culture = culture;
	}

	@Override
	public List<HeaderItem> getDependencies()
	{
		List<HeaderItem> dependencies = super.getDependencies();
		dependencies.add(JavaScriptHeaderItem.forReference(new KendoCultureResourceReference(this.culture)));

		return dependencies;
	}

	// Helpers //

	/**
	 * Gets a new {@link KendoCultureHeaderItem} from a {@code Locale} culture, with a fallback to the {@code Locale} 's language
	 * 
	 * @param locale the {@code Locale}
	 * @return a new {@link KendoCultureHeaderItem}
	 */
	public static HeaderItem of(Locale locale)
	{
		if (locale != null)
		{
			return KendoCultureHeaderItem.of(locale.toLanguageTag(), locale.getLanguage());
		}

		return null;
	}

	/**
	 * Gets a new {@link KendoCultureHeaderItem} from the first valid specified culture
	 * 
	 * @param cultures the array of cultures
	 * @return a new {@link KendoCultureHeaderItem}
	 */
	public static HeaderItem of(String... cultures)
	{
		String culture = KendoCulture.get(cultures);

		if (culture != null)
		{
			return new PriorityHeaderItem(new KendoCultureHeaderItem(culture));
		}

		return null;
	}
}
