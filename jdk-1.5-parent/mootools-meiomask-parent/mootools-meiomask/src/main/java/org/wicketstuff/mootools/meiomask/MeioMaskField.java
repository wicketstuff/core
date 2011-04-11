/*
 * Copyright 2011 inaiat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.mootools.meiomask;

import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Date;

import javax.swing.text.MaskFormatter;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.ConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.mootools.meiomask.behavior.MeioMaskBehavior;

/**
 *
 * @author inaiat
 */
public class MeioMaskField<T> extends TextField<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeioMaskField.class);
    private final MaskFormatter maskFormatter = new MaskFormatter();
    private final MaskType maskType;

    public MeioMaskField(String id, MaskType mask) {
        this(id, mask, (IModel<T>) null);
    }

    public MeioMaskField(String id, MaskType mask, boolean valueContainsLiteralCharacters) {
        this(id, mask, null, null, valueContainsLiteralCharacters);
    }

    public MeioMaskField(String id, MaskType mask, IModel<T> model) {
        this(id, mask, null, model, false);
    }

    public MeioMaskField(String id, MaskType mask, String options) {
        this(id, mask, options, null, false);
    }

    public MeioMaskField(String id, MaskType mask, String options, IModel<T> model) {
        this(id, mask, options, model, false);
    }

    public MeioMaskField(String id, MaskType mask, String options, IModel<T> model, boolean valueContainsLiteralCharacters) {
        this(id, mask, options, model, valueContainsLiteralCharacters, null);
    }

    public MeioMaskField(String id, MaskType mask, String options, IModel<T> model, boolean valueContainsLiteralCharacters, Class<T> type) {
        super(id, model, type);
        this.maskType = mask;
        LOGGER.debug("Initializing maskfield with id {} ...", id);
        LOGGER.debug("  Mask name: {}, mask: {}", mask.getMaskName(), mask.getMask());
        LOGGER.debug("  Options: {}", options);
        LOGGER.debug("  Type: {}", type);
        LOGGER.debug("  ValueContainsLiteralCharacters: {}", valueContainsLiteralCharacters);
        try {
            maskFormatter.setMask(mask.getMask());
            maskFormatter.setValueClass(String.class);
            maskFormatter.setAllowsInvalid(true);
            maskFormatter.setValueContainsLiteralCharacters(valueContainsLiteralCharacters);
        } catch (ParseException parseException) {
            throw new WicketRuntimeException(parseException);
        }

        add(new MeioMaskBehavior(mask, options));
        setOutputMarkupId(true);

        LOGGER.debug("Maskfield {} initialized.", id);
    }

    @Override
    public String getInput() {
        String input = super.getInput();

        if (input.trim().length() == 0 || isUnMaskableTypes(getType(), this.maskType)) {
            // Do nothing
            return input;
        } else if (isNumberFormat(getType())) { // Remove special characters
            DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(getLocale());
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) != formatSymbols.getGroupingSeparator()) {
                    builder.append(input.charAt(i));
                }
            }
            return builder.toString();
        } else { // Unmask values
            try {
                LOGGER.debug("Value to Converter {}", input);
                return (String) maskFormatter.stringToValue(input);
            } catch (ParseException ex) {
                throw newConversionException(input, ex);
            }
        }
    }

    /**
     * I don't know if this is a best place to convert mask (with String type), please if you
     * find other way... talk to me
     * @param value
     * @return
     * @throws ConversionException 
     */
    @Override
    protected T convertValue(String[] value) throws ConversionException {
        if (value != null && value.length > 0 && maskType.getMask()!=null && value[0].trim().length() > 0) {
            try {
                String valueToConverter = value[0];
                LOGGER.debug("Value to Converter {}", valueToConverter);
                value[0] = (String) maskFormatter.stringToValue(valueToConverter);
            } catch (ParseException ex) {
                throw newConversionException(value[0], ex);
            }
        }
        return super.convertValue(value);
    }

    private ConversionException newConversionException(String value, Throwable cause) {
        return new ConversionException(cause).setResourceKey("PatternValidator").setVariable("input", value).setVariable("pattern", maskFormatter.getMask());
    }

    protected boolean isNumberFormat(Class<?> type) {
        return (Number.class.isAssignableFrom(type) && this.maskType.getMask() == null);
    }
    
    private boolean isUnMaskableTypes(Class<?> type, MaskType mask) {
        return Date.class.isAssignableFrom(type) || mask == MaskType.RegexpEmail || mask == MaskType.RegexpIp;
    }
}
