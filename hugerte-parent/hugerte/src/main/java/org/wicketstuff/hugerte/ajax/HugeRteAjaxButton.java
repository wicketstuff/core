package org.wicketstuff.hugerte.ajax;

import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;

/**
 * An AjaxButton with the HugeRteAjaxSubmitModifier
 *
 * @author Sander van Faassen
 */
public abstract class HugeRteAjaxButton extends AjaxButton
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id
	 *            id
	 * @param form
	 *            form
	 */
	public HugeRteAjaxButton(String id, Form form)
	{
		super(id, form);
		add(new HugeRteAjaxSubmitModifier());
	}

	/**
	 * Constructor
	 *
	 * @param id
	 *            id
	 */
	public HugeRteAjaxButton(String id)
	{
		super(id);
		add(new HugeRteAjaxSubmitModifier());
	}
}
