/**
 * 
 */
package org.wicketstuff.jsr303;

import javax.validation.ConstraintViolation;

/**
 * Controls, how to use Messages as resolved by the Validation Framework.
 * 
 * @see Default
 */
public interface ViolationMessageRenderer
{
    String render(ConstraintViolation<?> violation);

    class Default implements ViolationMessageRenderer
    {
        public String render(final ConstraintViolation<?> violation)
        {
            return "'${label}' " + violation.getMessage();
        }
    }
}
