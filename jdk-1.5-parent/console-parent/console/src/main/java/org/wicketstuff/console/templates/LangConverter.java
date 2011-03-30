package org.wicketstuff.console.templates;

import java.util.Locale;

import org.apache.wicket.util.convert.converter.AbstractConverter;
import org.apache.wicket.util.string.Strings;

public final class LangConverter extends AbstractConverter<Lang> {
	public Lang convertToObject(final String value, final Locale locale) {
		return Lang.valueOf(value);
	}

	@Override
	public String convertToString(final Lang value, final Locale locale) {
		if (value == null) {
			return null;
		} else {
			return Strings.capitalize(value.name().toLowerCase());
		}
	}

	@Override
	protected Class<Lang> getTargetType() {
		return Lang.class;
	}
}