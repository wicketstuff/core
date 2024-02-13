package wicket.contrib.tinymce6.ajax;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.Model;

/**
 * Modifier to modify the onclick method so that the tinyMce data will be updated to the model
 * <p>
 * Solution found at: http://dwairi.blogspot.com/2006/12/tinymce-ajax.html <BR>
 * For more details see:
 * <a href="https://www.tiny.cloud/docs/tinymce/latest/apis/tinymce.root/#triggerSave">triggerSave
 * Documentation</a>
 * <BR>
 *
 * @author Sander van Faassen
 */
public class TinyMceAjaxSubmitModifier extends AttributeModifier {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Trigger that has to be called
	 */
	public static final String SAVE_TRIGGER = "tinymce.triggerSave();";

	/**
	 * Constructor
	 */
	public TinyMceAjaxSubmitModifier() {
		super("onclick", Model.of(SAVE_TRIGGER));
	}

	/**
	 * @see org.apache.wicket.AttributeModifier#newValue(java.lang.String, java.lang.String)
	 */
	@Override
	protected String newValue(final String currentValue, final String replacementValue) {
		// Call the trigger, before submitting the form
		String result = replacementValue;

		if (currentValue != null) {
			result = result + currentValue;
		}
		return result;
	}
}
