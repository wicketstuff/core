package org.wicketstuff.hugerte.ajax;

import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;

/**
 * An AjaxSubmitLink with the HugeRteAjaxSubmitModifier
 *
 * @author Sander van Faassen
 */
public abstract class HugeRteAjaxSubmitLink extends AjaxSubmitLink
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
	public HugeRteAjaxSubmitLink(String id, Form form)
	{
		super(id, form);
		add(new HugeRteAjaxSubmitModifier());
	}

	/**
	 * @param id
	 *            id
	 */
	public HugeRteAjaxSubmitLink(String id)
	{
		super(id);
		add(new HugeRteAjaxSubmitModifier());
	}
}
