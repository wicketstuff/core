/**
 * 
 */
package org.wicketstuff.jsr303.examples;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author doc
 */
@Constraint(validatedBy =
{ FooValidator.class })
@Retention(RetentionPolicy.RUNTIME)
@Target(
{ ElementType.TYPE })
public @interface FooConstraint {

    String message() default "{example.FooConstraint.notEqual}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
