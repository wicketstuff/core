package com.googlecode.wicket.kendo.ui;

import java.util.Locale;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;

import com.googlecode.wicket.kendo.ui.resource.KendoMessageResourceReference;

/**
 * {@link HeaderItem} in charge of adding the messages file corresponding to the supplied {@link KendoMessage}<br>
 * Usage:<br>
 * <pre><code>
 * public void renderHead(IHeaderResponse response)
 * {
 * 	super.renderHead(response);
 *
 * 	response.render(new KendoMessageHeaderItem(KendoMessage.FR_FR));
 * }
 * </code></pre>
 * <br>
 * This will results to:
 * <pre><code>
 * &lt;script type="text/javascript" src="./resource/com.googlecode.wicket.kendo.ui.resource.KendoMessageResourceReference/messages/kendo.messages.fr-FR.js"&gt;&lt;/script&gt;
 * </code></pre>
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class KendoMessageHeaderItem extends JavaScriptReferenceHeaderItem
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param locale the {@link Locale}, ie: Locale.FRENCH
	 */
	public KendoMessageHeaderItem(Locale locale)
	{
		this(locale.toLanguageTag()); // java7
	}

	/**
	 * Constructor
	 *
	 * @param message the {@link KendoMessage}
	 */
	public KendoMessageHeaderItem(KendoMessage message)
	{
		this(message.toString());
	}

	/**
	 * Constructor
	 *
	 * @param culture the culture, ie: 'fr-FR'
	 */
	public KendoMessageHeaderItem(String culture)
	{
		super(new KendoMessageResourceReference(culture), null, "kendo-messages");
	}

	// Helpers //

	/**
	 * Gets a new {@link KendoMessageHeaderItem} from a {@link Locale} culture
	 *
	 * @param locale the {@code Locale}
	 * @return a new {@link KendoMessageHeaderItem}
	 */
	public static HeaderItem of(Locale locale)
	{
		if (locale != null)
		{
			return KendoMessageHeaderItem.of(locale.toLanguageTag());
		}

		return null;
	}

	/**
	 * Gets a new {@link KendoMessageHeaderItem} from the first valid specified culture
	 *
	 * @param cultures the array of cultures
	 * @return a new {@link KendoMessageHeaderItem}
	 */
	public static HeaderItem of(String... cultures)
	{
		String culture = KendoMessage.get(cultures);

		if (culture != null)
		{
			return new PriorityHeaderItem(new KendoMessageHeaderItem(culture));
		}

		return null;
	}
}
