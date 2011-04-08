/*
 * Copyright 2011 Inaiat Henrique.
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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wicketstuff.mootools.meiomask;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.util.convert.converter.AbstractNumberConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Inaiat
 *
 *
 */
public class MeioMaskNumberConverter extends AbstractNumberConverter {

    private final Class<?> type;

    public MeioMaskNumberConverter(Class<?> type) {
        this.type = type;
    }

    @Override
    public NumberFormat getNumberFormat(Locale locale) {
        NumberFormat format = NumberFormat.getInstance(locale);
        format.setGroupingUsed(true);
        return format;
    }

    @Override
    protected Class<?> getTargetType() {
        return type;
    }

    public Object convertToObject(String string, Locale locale) {
        NumberFormat format = getNumberFormat(locale);
        try {
            return format.parseObject(string);
        } catch (ParseException ex) {
            throw new WicketRuntimeException(ex);
        }
    }

    @Override
    public String convertToString(Number value, Locale locale) {
        NumberFormat fmt = getNumberFormat(locale);
        if (fmt != null) {
            fmt.setGroupingUsed(false);
            return fmt.format(value);
        }
        return value.toString();
    }
}