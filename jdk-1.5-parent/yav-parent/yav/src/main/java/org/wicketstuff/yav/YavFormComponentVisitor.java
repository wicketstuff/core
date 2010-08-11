package org.wicketstuff.yav;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IFormVisitorParticipant;
import org.apache.wicket.markup.html.form.FormComponent.IVisitor;
import org.apache.wicket.markup.html.form.validation.EqualInputValidator;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converters.DateConverter;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.apache.wicket.validation.INullAcceptingValidator;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.apache.wicket.validation.validator.StringValidator.ExactLengthValidator;
import org.apache.wicket.validation.validator.StringValidator.LengthBetweenValidator;
import org.apache.wicket.validation.validator.StringValidator.MaximumLengthValidator;
import org.apache.wicket.validation.validator.StringValidator.MinimumLengthValidator;

/**
 * Visitor used to check all the existing validators , and the type of the form components to add the Yav Rules.
 * 
 * @author Zenika
 */
public class YavFormComponentVisitor implements IVisitor {

	/**
	 * The buffer to write the script out
	 */
	private AppendingStringBuffer buffer;
	/**
	 * Form validators associated to the Form.
	 */
	private Collection<IFormValidator> formValidators;
	/**
	 * Wicket Message formatting
	 */
	private WicketMessageBuilder messageBuilder;
	/**
	 * To add only once the Date specifics
	 */
	private boolean addedDateInfo = false;
	/**
	 * To add only once the decimal separator 
	 */
	private boolean addedNumberInfo = false;
	
	/**
	 * Constructor passing on the buffer to write out the script content about the Yav rules from the browsing of the
	 * several Wicket Validators
	 * @param buffer
	 */
	@SuppressWarnings("unchecked")
	public YavFormComponentVisitor(AppendingStringBuffer buffer, Form parentForm) {
		this.formValidators = parentForm.getFormValidators();
		this.messageBuilder = new WicketMessageBuilder();
		this.buffer = buffer;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.FormComponent.IVisitor#formComponent
	 * (org.apache.wicket.markup.html.form.IFormVisitorParticipant)
	 */
	@SuppressWarnings("unchecked")
	public Object formComponent(IFormVisitorParticipant formComponent) {
		if (FormComponent.class.isAssignableFrom(formComponent.getClass())) {
			FormComponent component = (FormComponent) formComponent;

			if (component.isRequired()) {
				buffer.append(messageBuilder.requiredMessage(component));
			}

			// Add a Yav rule for some converters (type validation instead of
			// value validation), one type per field
			addYavRuleOnConverter(component);

			// Iterate over all the validators and add a Yav Rule
			List<IValidator> validators = component.getValidators();
			for (IValidator validator : validators) {
				addYavRuleOnValidator(validator, component);
			}
			
			// Check if this form component is included in a FormValidator
			verifyExistingValidatorOnComponent(component);
		}

		return null;
	}

	/**
	 * @param formComponent
	 */
	@SuppressWarnings("unchecked")
	private void verifyExistingValidatorOnComponent(FormComponent formComponent) {
		for (IFormValidator formValidator : this.formValidators) {
			if (EqualInputValidator.class.isAssignableFrom(formValidator.getClass())) {
				FormComponent[] dependentFormComponents = formValidator.getDependentFormComponents();
				final FormComponent formComponent1 = dependentFormComponents[0];
				final FormComponent formComponent2 = dependentFormComponents[1];

				if (formComponent2.equals(formComponent)) {					
					buffer.append(messageBuilder.equalFieldMessage(formComponent1, formComponent2));
				}
			}
		}
	}

	/**
	 * Method to add Yav rules into the generated JavaScript concerning the Converter associated with the current
	 * component. Needed because in the Wicket chain, the Converter is used before some of the Validators (for instance:
	 * Dates and Numbers) and if the conversion went well, it means that the input is in the right format as it passed
	 * the conversion.
	 * @param converter
	 * @param id
	 */
	private void addYavRuleOnConverter(FormComponent formComponent) {
		Class clazz = formComponent.getType();
		
		if (clazz == null) 
			return;

		String className = clazz.getName();
		
		if (className.equals(Date.class.getName())) {
			overrideDateType(buffer);
			buffer.append(messageBuilder.typeConverterDateMessage(formComponent, clazz.getSimpleName()));
		} else if (
				className.equals(Integer.class.getName()) 
				|| className.equals(Integer.TYPE.getName())
				|| className.equals(Long.class.getName())
				|| className.equals(Long.TYPE.getClass())
				|| className.equals(Short.class.getName())
				|| className.equals(Short.TYPE.getClass())
				|| className.equals(BigInteger.class.getName())) {
			buffer.append(messageBuilder.typeConverterIntegerMessage(formComponent, clazz.getSimpleName()));
		} else if (
				className.equals(Float.class.getName()) 
				|| className.equals(Float.TYPE.getClass())
				|| className.equals(Double.class.getName())
				|| className.equals(Double.TYPE.getClass())
				|| className.equals(BigDecimal.class.getName())) {
			overrideDecimalType(buffer);
			buffer.append(messageBuilder.typeConverterDecimalMessage(formComponent, clazz.getSimpleName()));
		}
	}

	/**
	 * Method to add Yav rules into the generated JavaScript concerning the Validators available.
	 * @param validator
	 * @param componentId
	 */
	@SuppressWarnings("unchecked")
	private void addYavRuleOnValidator(IValidator validator, Component component) {
		if (INullAcceptingValidator.class.isAssignableFrom(validator.getClass())) {
			
			if (StringValidator.class.isAssignableFrom(validator.getClass())) {
				addYavRuleForStringValidatorType(validator, component);
			} else {
				// TODO Later
				// if (DateValidator.class.isAssignableFrom(validator.getClass())) {
				//	
				// }
			}
		} else {
			if (IValidator.class.isAssignableFrom(validator.getClass())) {
				addYavRuleForMinMaxRangeValidatorType(validator, component);
			}
		}
	}

	/**
	 * @param buffer
	 */
	private void overrideDecimalType(AppendingStringBuffer buffer) {
		if (!addedNumberInfo) {
			DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Session.get().getLocale());
				
			buffer.append("yav_config.DECIMAL_SEP='" + decimalFormatSymbols.getDecimalSeparator() + "';\n");
			
			buffer.append("yav_config.THOUSAND_SEP='" + decimalFormatSymbols.getGroupingSeparator() + "';\n");
			
			addedNumberInfo = true;
		}
	}

	/**
	 * @param buffer
	 */
	private void overrideDateType(AppendingStringBuffer buffer) {
		if (!addedDateInfo) {
			IConverter converter = Application.get().getConverterLocator().getConverter(Date.class);

			if (DateConverter.class.isAssignableFrom(converter.getClass())) {
				DateFormat dateFormat = ((DateConverter) converter).getDateFormat(Session.get().getLocale());
				if (SimpleDateFormat.class.isAssignableFrom(dateFormat.getClass())) {
					SimpleDateFormat sdf = (SimpleDateFormat) dateFormat;
					
					String pattern = sdf.toPattern();
					if (pattern.indexOf("yyyy") == -1) {
						pattern = pattern.replace("yy", "yyyy");
					}
					
					buffer.append("yav_config.DATE_FORMAT='" + pattern + "';\n");
				}
			}
		addedDateInfo = true;
		}
	}

	/**
	 * @param validator
	 * @param component
	 */
	@SuppressWarnings("unchecked")
	private void addYavRuleForMinMaxRangeValidatorType(IValidator validator, Component component) {
		if (RangeValidator.class.isAssignableFrom(validator.getClass())) {
			RangeValidator rangeValidator = (RangeValidator) validator;
			
			if (Number.class.isAssignableFrom(rangeValidator.getMinimum().getClass())) {
				buffer.append(messageBuilder.rangeMessage(component, rangeValidator));
			}
		}
	}
	
	/**
	 * Deal with StringValidators. includes:
	 * - length checks
	 * - email addresses
	 * - regex
	 * @param validator
	 * @param component
	 * @param componentId
	 */
	private void addYavRuleForStringValidatorType(IValidator validator, Component component) {		
		if (ExactLengthValidator.class.isAssignableFrom(validator.getClass())) {
			ExactLengthValidator exactLengthValidator = (ExactLengthValidator) validator;
			buffer.append(messageBuilder.exactLengthMessage(component, exactLengthValidator));
		}

		else if (MinimumLengthValidator.class.isAssignableFrom(validator.getClass())) {
			MinimumLengthValidator minimumLengthValidator = (MinimumLengthValidator) validator;
			buffer.append(messageBuilder.minimumMessage(component, minimumLengthValidator));
		}

		else if (MaximumLengthValidator.class.isAssignableFrom(validator.getClass())) {
			MaximumLengthValidator maximumLengthValidator = (MaximumLengthValidator) validator;
			buffer.append(messageBuilder.maximumMessage(component, maximumLengthValidator));
		}

		else if (LengthBetweenValidator.class.isAssignableFrom(validator.getClass())) {
			LengthBetweenValidator lengthBetweenValidator = (LengthBetweenValidator) validator;
			buffer.append(messageBuilder.lengthBetweenMessage(component, lengthBetweenValidator));
		}

		else if (PatternValidator.class.isAssignableFrom(validator.getClass())) {
			PatternValidator patternValidator = (PatternValidator) validator;
			if (EmailAddressValidator.class.isAssignableFrom(validator.getClass())) {
				buffer.append(messageBuilder.emailMessage(component));
			} else {
				buffer.append(messageBuilder.patternMessage(component, patternValidator));
			}
		}
	}

}