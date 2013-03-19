package org.wicketstuff.shiro.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ShiroSecurityConstraints {

	ShiroSecurityConstraint[] value();

}

