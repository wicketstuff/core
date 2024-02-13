package wicket.contrib.tinymce6.ajax;

import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;

/**
 * An AjaxButton with the TinyMceAjaxSubmitModifier
 *
 * @author Sander van Faassen
 */
public abstract class TinyMceAjaxButton extends AjaxButton {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id   id
	 * @param form form
	 */
	public TinyMceAjaxButton(String id, Form form) {
		super(id, form);
		add(new TinyMceAjaxSubmitModifier());
	}

	/**
	 * Constructor
	 *
	 * @param id id
	 */
	public TinyMceAjaxButton(String id) {
		super(id);
		add(new TinyMceAjaxSubmitModifier());
	}
}
