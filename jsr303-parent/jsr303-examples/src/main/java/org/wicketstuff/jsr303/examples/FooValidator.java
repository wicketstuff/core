/**
 * 
 */
package org.wicketstuff.jsr303.examples;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// just a STUPID example !
public class FooValidator implements ConstraintValidator<FooConstraint, FieldBundle>
{
    public void initialize(final FooConstraint constraintAnnotation)
    {
    }

    public boolean isValid(final FieldBundle value, final ConstraintValidatorContext context)
    {
        final String field1 = value.getField1();
        final String field2 = value.getField2();
        if ((field1 == null) || (field2 == null))
        {
            return false;
        }
        return field1.equals(field2);
    }

}