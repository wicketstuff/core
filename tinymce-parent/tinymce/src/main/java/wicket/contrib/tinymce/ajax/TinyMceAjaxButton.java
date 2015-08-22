package wicket.contrib.tinymce.ajax;

import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;

/**
 * An AjaxButton with the TinyMceAjaxSubmitModifier
 * 
 * @author Sander van Faassen
 */
public abstract class TinyMceAjaxButton extends AjaxButton
{
	/** */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param form
	 */
	public TinyMceAjaxButton(String id, Form form)
	{
		super(id, form);
		addTinyMceAjaxSubmitModifier();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 */
	public TinyMceAjaxButton(String id)
	{
		super(id);
		addTinyMceAjaxSubmitModifier();
	}

	/**
	 * Add the TinyMceAjaxSubmitModifier to this AjaxButton
	 */
	private final void addTinyMceAjaxSubmitModifier()
	{
		add(new TinyMceAjaxSubmitModifier());
	}
}
