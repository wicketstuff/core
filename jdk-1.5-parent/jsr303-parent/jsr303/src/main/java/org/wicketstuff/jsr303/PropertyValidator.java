package org.wicketstuff.jsr303;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.AbstractPropertyModel;
import org.apache.wicket.validation.INullAcceptingValidator;
import org.apache.wicket.validation.IValidatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.jsr303.util.Assert;

public class PropertyValidator<T> implements INullAcceptingValidator<T>, Serializable
{
	public static class Exclude extends Behavior
	{
		private static final long serialVersionUID = 1L;
	}

	public void validate(final IValidatable<T> validatable)
	{
		// skip, if propertyExpression is empty
		if (propertyExpression == null || propertyExpression.trim().length() == 0)
			return;

		// skip, if marked as excluded
		if (hasExclusionBehaviour())
			return;

		final T value = validatable.getValue();

		final Set<?> violations = JSR303Validation.getValidator().validateValue(beanClass,
			propertyExpression, value);
		for (final Object v : violations)
		{
			validatable.error(wrap((ConstraintViolation<?>)v).createError());
		}
	}

	private <V> ViolationErrorBuilder.Property<V> wrap(ConstraintViolation<V> violation)
	{
		return new ViolationErrorBuilder.Property<V>(violation);
	}

	private static final long serialVersionUID = 1L;

	private final Class<?> beanClass;
	private final String propertyExpression;

	private final FormComponent<T> fc;

	public PropertyValidator(final AbstractPropertyModel<?> apm, FormComponent<T> componentToApplyTo)
	{
		this.fc = componentToApplyTo;
		Assert.parameterNotNull(apm, "apm");
		this.beanClass = apm.getTarget().getClass();
		this.propertyExpression = apm.getPropertyExpression();
	}

	private boolean hasExclusionBehaviour()
	{
		List<? extends Behavior> behaviors = fc.getBehaviors();
		for (Behavior iBehavior : behaviors)
		{
			if (iBehavior instanceof PropertyValidator.Exclude)
			{
				return true;
			}
		}

		return false;
	}

	private static transient volatile Logger _transient_logger = LoggerFactory.getLogger(PropertyValidator.class);

	public static final Logger log()
	{
		if (_transient_logger == null)
		{
			_transient_logger = LoggerFactory.getLogger(PropertyValidator.class);
		}
		return _transient_logger;
	}
}
