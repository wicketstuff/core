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

import org.apache.wicket.markup.html.form.ValidationErrorFeedback;
import org.apache.wicket.validation.IValidationError;

/**
 * The Class RestErrorMessage.
 */
public class RestErrorMessage extends ValidationErrorFeedback {

	/**
         * 
         */
        private static final long serialVersionUID = 1L;
	
        /** The field. */
	private final String field;

	/**
	 * Instantiates a new rest error message.
	 *
	 * @param error the error
	 * @param message the message
	 * @param field the field
	 */
	public RestErrorMessage(IValidationError error, Serializable message, String field)
	{
		super(error, message);
		this.field = field;
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
