package wicket.contrib.tinymce4.ajax;

import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;

/**
 * An AjaxSubmitLink with the TinyMceAjaxSubmitModifier
 * 
 * @author Sander van Faassen
 */
public abstract class TinyMceAjaxSubmitLink extends AjaxSubmitLink
{
	/** */
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 * @param form
	 */
	public TinyMceAjaxSubmitLink(String id, Form form)
	{
		super(id, form);
		addTinyMceAjaxSubmitModifier();
	}

	/**
	 * @param id
	 */
	public TinyMceAjaxSubmitLink(String id)
	{
		super(id);
		addTinyMceAjaxSubmitModifier();
	}

	/**
	 * Add the TinyMceAjaxSubmitModifier to this AjaxSubmitLink
	 */
	private void addTinyMceAjaxSubmitModifier()
	{
		add(new TinyMceAjaxSubmitModifier());
	}
}
