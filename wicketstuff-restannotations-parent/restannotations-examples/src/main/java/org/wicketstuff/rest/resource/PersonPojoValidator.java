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
package org.wicketstuff.rest.resource;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.wicketstuff.rest.domain.PersonPojo;
import org.wicketstuff.rest.utils.wicket.validator.RestValidationError;

import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonPojoValidator implements IValidator<PersonPojo> {
    public static final String INVALID_MAIL_KEY = "invalidMail";
    private final Pattern emailPattern = EmailAddressValidator.getInstance().getPattern();

    @Override
    public void validate(IValidatable<PersonPojo> validatable) {

        PersonPojo person = validatable.getValue();
        Matcher matcher = emailPattern.matcher(person.getEmail());

        if (!matcher.matches()) {
            validatable.error(new RestValidationError(Arrays.asList(INVALID_MAIL_KEY),
                    Collections.EMPTY_MAP, "email"));
        }
    }

}