/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.rest.utils.wicket.validator;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.wicket.validation.IErrorMessageSource;
import org.apache.wicket.validation.IValidationError;

/**
 * The Class RestValidationError.
 */
public class RestValidationError implements IValidationError {
	
	/**
         * 
         */
        private static final long serialVersionUID = 1L;

	/** list of message keys to try against the <code>IErrorMessageSource</code>. */
	private final List<String> keys;

	/** variables map to use in variable substitution. */
	private final Map<String, Object> vars;
	
	/** the field that failed the validation. */
	private final String field;

	/**
	 * Instantiates a new rest validation error.
	 *
	 * @param keys the keys
	 * @param vars the vars
	 * @param field the field
	 */
	public RestValidationError(List<String> keys, Map<String, Object> vars, String field) {
		this.keys = keys;
		this.vars = vars;
		this.field = field;
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.validation.IValidationError#getErrorMessage(org.apache.wicket.validation.IErrorMessageSource)
	 */
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
		
		return new RestErrorMessage(this, errorMessage, field);
	}
    
	
	/**
	 * Gets the keys.
	 *
	 * @return the keys
	 */
	public List<String> getKeys() 
	{
		return keys;
	}

	/**
	 * Gets the vars.
	 *
	 * @return the vars
	 */
	public Map<String, Object> getVars() 
	{
		return vars;
	}

	/**
	 * Gets the field.
	 *
	 * @return the field
	 */
	public String getField() 
	{
		return field;
	}
}
