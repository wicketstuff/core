package org.wicketstuff.yav;

import java.util.regex.Pattern;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator.ExactLengthValidator;
import org.apache.wicket.validation.validator.StringValidator.LengthBetweenValidator;
import org.apache.wicket.validation.validator.StringValidator.MaximumLengthValidator;
import org.apache.wicket.validation.validator.StringValidator.MinimumLengthValidator;

/**
 * @author Zenika
 */
public class WicketMessageBuilder {

	/**
	 * An index to keep the current rule number (for Yav)
	 */
	private int index;

	public WicketMessageBuilder() {
		this.index = 0;
	}
	
	/**
	 * @param component
	 */
	public String requiredMessage(Component component) {
		return addToBuffer(
				component.getId(),
				"required",
				escapeJavaScriptString(formatMessage(component, "Required")));
	}

	/**
	 * @param formComponent1
	 * @param formComponent2
	 * @return
	 */
	public String equalFieldMessage(FormComponent formComponent1, FormComponent formComponent2) {
		return addToBuffer(		
				formComponent2.getId(),
				"equal|$" + formComponent1.getId(),
				escapeJavaScriptString(formatEqualFieldMessage(formComponent1, formComponent2, "EqualPasswordInputValidator")));
	}

	/**
	 * @param component
	 * @param rangeValidator
	 * @return
	 */
	public String rangeMessage(Component component, RangeValidator rangeValidator) {
		String minValue = rangeValidator.getMinimum().toString();
		String maxValue = rangeValidator.getMaximum().toString();

		return addToBuffer(
				component.getId(),
				"numrange|" + minValue + "-" + maxValue,
				escapeJavaScriptString(formatRangeMessage(component, "NumberValidator.range", minValue, maxValue)));
	}

	/**
	 * @param component
	 * @param lengthBetweenValidator
	 * @return
	 */
	public String lengthBetweenMessage(Component component, LengthBetweenValidator lengthBetweenValidator) {
		String minimum = String.valueOf(lengthBetweenValidator.getMinimum());
		String maximum = String.valueOf(lengthBetweenValidator.getMaximum());
		
		// Add a rule for a minlength with a Yav custom message
		// corresponding to the Wicket range message
		String minMessage = addToBuffer(
			component.getId(),
			"minlength|" + minimum,
			escapeJavaScriptString(formatRangeMessage(component, "NumberValidator.range", minimum, maximum)));
		

		// Add a rule for a maxlength with a Yav custom message
		// corresponding to the Wicket range message
		String maxMessage = addToBuffer(
				component.getId(),
				"maxlength|" + maximum,
				escapeJavaScriptString(formatRangeMessage(component, "NumberValidator.range", minimum, maximum)));

		return minMessage + maxMessage;
	}

	/**
	 * @param component
	 * @param length
	 * @return
	 */
	public String exactLengthMessage(Component component, ExactLengthValidator exactLengthValidator) {
		int length = exactLengthValidator.getLength();
		
		String minMessage = addToBuffer(
			component.getId(),
			"minlength|" + length,
			escapeJavaScriptString(formatExactLengthMessage(component, "StringValidator.exact", length)));

		String maxMessage = addToBuffer(
			component.getId(),
			"maxlength|" + length,
			escapeJavaScriptString(formatExactLengthMessage(component, "StringValidator.exact", length)));
		
		return minMessage + maxMessage;
	}

	/**
	 * @param component
	 * @param minimumLengthValidator
	 * @return
	 */
	public String minimumMessage(Component component, MinimumLengthValidator minimumLengthValidator) {
		String value = String.valueOf(minimumLengthValidator.getMinimum());
		return addToBuffer(
			component.getId(),
			"minlength|" + value,
			escapeJavaScriptString(formatMinimumMessage(component, "StringValidator.minimum", value)));
	}

	/**
	 * @param component
	 * @param maxValue
	 * @return
	 */
	public String maximumMessage(Component component, MaximumLengthValidator maximumLengthValidator) {
		String value = String.valueOf(maximumLengthValidator.getMaximum());
		return addToBuffer(
			component.getId(),
			"maxlength|" + value,
			escapeJavaScriptString(formatMaximumMessage(component, "StringValidator.maximum", value)));
	}

	/**
	 * @param component
	 * @param patternValidator
	 * @return
	 */
	public String patternMessage(Component component, PatternValidator patternValidator) {
		return addToBuffer(
			component.getId(),
			"regexp|" + patternValidator.getPattern(),
			escapeJavaScriptString(formatPatternMessage(component, "PatternValidator", patternValidator.getPattern())));
	}

	/**
	 * @param component
	 * @return
	 */
	public String emailMessage(Component component) {
		return addToBuffer(
				component.getId(),
				"email",
				escapeJavaScriptString(formatMessage(component, "EmailAddressValidator")));
	}

	/**
	 * @param formComponent
	 * @param converterClassName
	 * @return
	 */
	public String typeConverterDateMessage(FormComponent formComponent, String converterClassName) {
		return addToBuffer(
				formComponent.getId(), 
				"date",
				escapeJavaScriptString(formatTypeMessage(formComponent, converterClassName)));
	}

	/**
	 * @param formComponent
	 * @param converterClassName
	 * @return
	 */
	public String typeConverterIntegerMessage(FormComponent formComponent, String converterClassName) {
		return addToBuffer(
				formComponent.getId(), 
				"integer",
				escapeJavaScriptString(formatTypeMessage(formComponent, converterClassName)));
	}

	/**
	 * @param formComponent
	 * @param converterClassName
	 * @return
	 */
	public String typeConverterDecimalMessage(FormComponent formComponent, String converterClassName) {
		return addToBuffer(
				formComponent.getId(), 
				"double",
				escapeJavaScriptString(formatTypeMessage(formComponent, converterClassName)));
	}

	/**
	 * @param component
	 * @param wicketMsgId
	 * @return
	 */
	private String getWicketMessage(Component component, String wicketMsgId) {
		String result;
		try {
			result = component.getString(component.getId() + "." + wicketMsgId);
		} catch (Exception ex1) {
			try {
				result = component.getString(wicketMsgId);
			} catch (Exception ex2) {
				result = "";
			}
		}
		return result;
	}

	/**
	 * @param formComponent
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getWicketConverterMessage(FormComponent formComponent) {
		Class clazz = formComponent.getType();
		IConverter converter = formComponent.getConverter(clazz);
		
		String wicketMessage = getWicketMessage(formComponent, converter.getClass().getSimpleName());
		if (wicketMessage == null || (wicketMessage.length() == 0)) {
			wicketMessage = getWicketMessage(formComponent, "IConverter");
		}
		
		return wicketMessage;
	}

	/**
	 * @param component
	 * @param ruleType
	 * @param parse
	 * @return
	 */
	private String formatMessage(Component component, String ruleType) {
		return replaceInputAndLabelAndNameBy(getWicketMessage(component, ruleType), component.getId());
	}

	/**
	 * @param formComponent
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String formatTypeMessage(FormComponent formComponent, String type) {
		String customMessage = getWicketConverterMessage(formComponent);
		customMessage = replaceInputAndLabelAndNameBy(customMessage, formComponent.getId());
		return customMessage.replace("${type}", type);
	}

	/**
	 * @param component1
	 * @param component2
	 * @param ruleType
	 * @return
	 */
	private String formatEqualFieldMessage(Component component1, Component component2, String ruleType) {
		String customMessage = getWicketMessage(component2, ruleType);
		customMessage = customMessage.replace("${label0}", component1.getId());
		return customMessage.replace("${label1}", component2.getId());
	}

	/**
	 * @param component
	 * @param string
	 * @param minimum
	 * @param maximum
	 * @return
	 */
	private String formatRangeMessage(Component component, String ruleType, String minimumValue, String maximumValue) {
		String customMessage = formatMessage(component, ruleType);
		customMessage = customMessage.replace("${minimum}", minimumValue);
		return customMessage.replace("${maximum}", maximumValue);
	}

	/**
	 * @param component
	 * @param ruleType
	 * @param length
	 * @return
	 */
	private String formatExactLengthMessage(Component component, String ruleType, int length) {
		String customMessage = formatMessage(component, ruleType);
		return customMessage.replace("${exact}", String.valueOf(String.valueOf(length)));
	}

	/**
	 * @param component
	 * @param ruleType
	 * @param minimum
	 * @return
	 */
	private String formatMinimumMessage(Component component, String ruleType, String minimum) {
		String customMessage = formatMessage(component, ruleType);
		return customMessage.replace("${minimum}", minimum);
	}

	/**
	 * @param component
	 * @param ruleType
	 * @param maximum
	 * @return
	 */
	private String formatMaximumMessage(Component component, String ruleType, String maximum) {
		String customMessage = formatMessage(component, ruleType);
		return customMessage.replace("${maximum}", maximum);
	}

	/**
	 * @param component
	 * @param ruleType
	 * @param pattern
	 * @return
	 */
	private String formatPatternMessage(Component component, String ruleType, Pattern pattern) {
		String customMessage = formatMessage(component, ruleType);
		return customMessage.replace("${pattern}", pattern.toString());
	}

	/**
	 * @param string
	 * @param componentId
	 * @return
	 */
	private String replaceInputAndLabelAndNameBy(String string, String componentId) {
		return string.replace("${input}", componentId).replace("${name}", componentId).replace("${label}", componentId);
	}

	/**
	 * @param message
	 * @return
	 */
	private String escapeJavaScriptString(String message) {
		return message.replace("'", "\\'");
	}

	/**
	 * Complete buffer with a string on the Yav format (element name + '|' + rule in the Yav syntax)
	 * @param componentId
	 * @param rule
	 * @param customMessage
	 * @return
	 */
	private String addToBuffer(String componentId, String rule, String customMessage) {
		if (customMessage == null) {
			return addToBuffer(componentId, rule);
		} else {
			return "rules[" + index++ + "]='" + componentId + "|" + rule + "|" + customMessage + "';\n";
		}
	}
	
	/**
	 * @param componentId
	 * @param rule
	 * @return
	 */
	private String addToBuffer(String componentId, String rule) {
		return "rules[" + index++ + "]='" + componentId + "|" + rule + "';\n";
		
	}
}
