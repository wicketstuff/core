package com.googlecode.wicket.kendo.ui;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;

import com.googlecode.wicket.kendo.ui.resource.KendoMessageResourceReference;

/**
 * {@link HeaderItem} in charge of adding the messages file corresponding to the supplied {@link KendoMessage}<br/>
 * Usage:<br/>
 * <code>
 * <pre>
 * public void renderHead(IHeaderResponse response)
 * {
 * 	super.renderHead(response);
 * 	
 * 	response.render(new KendoMessageHeaderItem(KendoMessage.FR_FR));
 * }
 * <br/>
 * This will results to:
 * <code>
 * <pre>
 * &lt;script type="text/javascript" src="./resource/com.googlecode.wicket.kendo.ui.resource.KendoMessageResourceReference/messages/kendo.messages.fr-FR.js"&gt;&lt;/script&gt;
 * </pre>
 * </code>
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
	 * @param message the {@link KendoMessage}
	 */
	public KendoMessageHeaderItem(KendoMessage message)
	{
		this(message.toString());
	}

	/**
	 * Constructor
	 * 
	 * @param language the language, ie: 'fr-FR'
	 */
	public KendoMessageHeaderItem(String language)
	{
		super(new KendoMessageResourceReference(language), null, "kendo-messages", false, null, null);
	}
}
