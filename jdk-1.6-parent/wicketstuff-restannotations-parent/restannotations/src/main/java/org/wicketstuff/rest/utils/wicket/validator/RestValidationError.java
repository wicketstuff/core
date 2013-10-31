package org.wicketstuff.rest.utils.wicket.validator;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.form.ValidationErrorFeedback;
import org.apache.wicket.validation.IErrorMessageSource;
import org.apache.wicket.validation.IValidationError;

public class RestValidationError implements IValidationError {
	/** list of message keys to try against the <code>IErrorMessageSource</code> */
	private final List<String> keys;

	/** variables map to use in variable substitution */
	private final Map<String, Object> vars;
	
	private final String field;

	public RestValidationError(List<String> keys, Map<String, Object> vars, String field) {
		this.keys = keys;
		this.vars = vars;
		this.field = field;
	}

	@Override
	public Serializable getErrorMessage(IErrorMessageSource messageSource) {
		String errorMessage = null;

		if (keys != null)
		{
			// try any message keys ...
			for (String key : keys)
			{
				errorMessage = messageSource.getMessage(key, vars);
				if (errorMessage != null)
				{
					break;
				}
			}
		}
		
		return new ValidationErrorFeedback(this, errorMessage);
	}

	public List<String> getKeys() 
	{
		return keys;
	}

	public Map<String, Object> getVars() 
	{
		return vars;
	}

	public String getField() 
	{
		return field;
	}
}
