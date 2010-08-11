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
    String renderPropertyViolation(ConstraintViolation<?> violation);

    String renderBeanViolation(final ConstraintViolation<?> violation);

    class Default implements ViolationMessageRenderer
    {
        public String renderPropertyViolation(final ConstraintViolation<?> violation)
        {
            return "'${label}' " + violation.getMessage();
        }

        public String renderBeanViolation(final ConstraintViolation<?> violation)
        {
            return violation.getMessage();
        }
    }
}
