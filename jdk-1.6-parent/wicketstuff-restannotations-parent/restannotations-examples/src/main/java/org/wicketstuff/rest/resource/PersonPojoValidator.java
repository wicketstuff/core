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