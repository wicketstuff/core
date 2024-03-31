package org.wicketstuff.tinymce6.ajax;

import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;

/**
 * An AjaxSubmitLink with the TinyMceAjaxSubmitModifier
 *
 * @author Sander van Faassen
 */
public abstract class TinyMceAjaxSubmitLink extends AjaxSubmitLink
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 *            id
	 * @param form
	 *            form
	 */
	public TinyMceAjaxSubmitLink(String id, Form form)
	{
		super(id, form);
		add(new TinyMceAjaxSubmitModifier());
	}

	/**
	 * @param id
	 *            id
	 */
	public TinyMceAjaxSubmitLink(String id)
	{
		super(id);
		add(new TinyMceAjaxSubmitModifier());
	}
}
