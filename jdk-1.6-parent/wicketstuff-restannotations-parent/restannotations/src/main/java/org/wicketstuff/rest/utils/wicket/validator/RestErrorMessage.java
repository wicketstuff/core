package org.wicketstuff.rest.utils.wicket.validator;

import java.io.Serializable;

import org.apache.wicket.validation.IErrorMessageSource;
import org.apache.wicket.validation.IValidationError;

public class RestErrorMessage implements Serializable {
	private final String message;
	private final String field;

	public RestErrorMessage(IValidationError error, IErrorMessageSource messageSource) 
	{
		this(error, messageSource, "");
	}
	
	public RestErrorMessage(RestValidationError error, IErrorMessageSource messageSource) 
	{
		this(error, messageSource, error.getField());
	}
	
	protected RestErrorMessage(IValidationError error, IErrorMessageSource messageSource, String field) 
	{
		this.message = error.getErrorMessage(messageSource).toString();
		this.field = field;
	}

	public String getMessage() 
	{
		return message;
	}

	public String getField() 
	{
		return field;
	}

}
