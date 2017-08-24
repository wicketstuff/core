/* 
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
package org.wicketstuff.gchart;

import java.text.DateFormat;
import java.text.Format;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.ajax.json.JsonFunction;
import org.apache.wicket.util.io.IClusterable;

/**
 * Cell object for data values with more features.
 *
 * @author Dieter Tremel
 */
public class DataCell implements IClusterable, Jsonable {

    private static final long serialVersionUID = 1L;

    private Object value;
    private Format format;
    private String formatted;
    private Map<String, Object> properties = null;

    public DataCell(Object value) {
        this.value = value;
        this.formatted = null;
    }

    public DataCell(Object value, Format format) {
        this.value = value;
        this.formatted = null;
        this.format = format;
    }

    public DataCell(Object value, String formatted) {
        this.value = value;
        this.formatted = formatted;
    }

    public DataCell(Object value, String formatted, Map<String, Object> properties) {
        this.value = value;
        this.formatted = formatted;
        this.properties = properties;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Format getFormat() {
        return format;
    }

    /**
     * Format used for calculating the value to a String used for rendering of
     * {@code f} in JavaScript.
     *
     * @param format The format to applied if the formatted value of the
     * instance is null. For Number this can be a DecimalFormat, for Dates for
     * instance a SimpleDateFormat.
     */
    public void setFormat(Format format) {
        this.format = format;
    }

    /**
     * Return the formatted value. If this is null, null is also returned and
     * there should be no rendering of {@code f} in JavaScript. In this case
     * format conversion is done by Google chart lib. See therefor {@link Chart#getLocale()
     * }.
     *
     * If {@link #getFormat() } is not null and the formatted field is null, the
     * format is applied to the value. If formatted field is not null this is
     * returned without change, so a single DataCell can override the Formatter.
     *
     * @return Field formatted if field format is null. If field format is not
     * null and field formatted is null, the format is applied to the value and
     * the result is returned.
     */
    public String getFormatted() {
        if (formatted == null && format != null) {
            // TODO Check if Format crashes if value is null. Should we return null in this case?
            if (format instanceof DateFormat && value instanceof Calendar) {
                return format.format(((Calendar) value).getTime());
            } else {
                return format.format(value);
            }
        } else {
            return formatted;
        }
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    public Map<String, Object> getProperties() {
        if (properties == null) {
            properties = new HashMap<>(8);
        }
        return properties;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject cell = new JSONObject();
        cell.put("v", getJsValue(value));
        final String formattedVal = getFormatted();
        if (formattedVal != null) {
            cell.put("f", formattedVal);
        }
        if (properties != null && !properties.isEmpty()) {
            cell.put("p", new JSONObject(properties));
        }
        return cell;
    }

    /**
     * Create a representation of a Java object suitable for a put operation to
     * a {@link JSONObject}. For simple types like String and Number the object
     * is returned unmodified.
     * <p>
     * Booleans are rendered as String {@code "true"} or {@code "false"}
     * <p>
     * Dates or Calendars are rendered as JsonFunction {@code "Date()"} by use
     * of {@link #createJsDate(java.util.Calendar) }.
     * <p>
     * {@link TimeOfDay} are rendered as JSONArray by use of {@link TimeOfDay#getTimeOfDayArray()
     * }.
     *
     * @param val The object to convert to.
     * @return JSON compatible wrapping of the value or the value itself for
     * simple types.
     */
    public static Object getJsValue(Object val) {
//        if (val == null) {
//            return JSONObject.NULL;
//        }
// use real null value instead
        if (val instanceof Boolean) {
            return ((Boolean) val).toString();
        }
        if (val instanceof Date) {
            return createJsDate((Date) val);
        }
        if (val instanceof Calendar) {
            return createJsDate((Calendar) val);
        }
        if (val instanceof TimeOfDay) {
            return ((TimeOfDay) val).getTimeOfDayArray();
        }
        // if(val instanceof String || val instanceof Number)
        return val;
    }

    /**
     * Convert a Calendar object to a JavaScript {@code Date()} call that
     * generates the same value. Return as a human readable JsonFunction
     * ({@code new Date(year, month[, day[, hour[, minutes[, seconds[, milliseconds]]]]])})
     * for rendering in JSONObjects.
     *
     * @param cal Calender value to render.
     * @return JavaScript Date function for the value.
     */
    public static JsonFunction createExplicitJsDate(Calendar cal) {
        StringBuilder sb = new StringBuilder("new Date(");
        sb.append(cal.get(Calendar.YEAR));
        sb.append(", ").append(cal.get(Calendar.MONTH));
        sb.append(", ").append(cal.get(Calendar.DAY_OF_MONTH));
        sb.append(", ").append(cal.get(Calendar.HOUR_OF_DAY));
        sb.append(", ").append(cal.get(Calendar.MINUTE));
        if ((cal.isSet(Calendar.SECOND) && cal.get(Calendar.SECOND) != 0)
                || (cal.isSet(Calendar.MILLISECOND) && cal.get(Calendar.MILLISECOND) != 0)) {
            sb.append(", ").append(cal.get(Calendar.SECOND));
        }
        if (cal.isSet(Calendar.MILLISECOND) && cal.get(Calendar.MILLISECOND) != 0) {
            sb.append(", ").append(cal.get(Calendar.MILLISECOND));
        }
        sb.append(")");
        return new JsonFunction(sb.toString());
    }

    /**
     * Convert a Calendar object to a JavaScript {@code Date()} call that
     * generates the same value. Return as a
     * JsonFunction({@code new Date(value);}) (milliseconds since epoch) for
     * rendering in JSONObjects .
     *
     * @param cal Calender value to render.
     * @return JavaScript Date function for the value.
     */
    public static JsonFunction createJsDate(Calendar cal) {
        StringBuilder sb = new StringBuilder("new Date(");
        sb.append(cal.getTimeInMillis());
        sb.append(")");
        return new JsonFunction(sb.toString());
    }

    /**
     * Convert a Date object to a JavaScript {@code Date()} call that generates
     * the same value. Return as a
     * JsonFunction({@code new Date(value);})(milliseconds since epoch) for
     * rendering in JSONObjects.
     *
     * @param date Date value to render.
     * @return JavaScript Date function for the value.
     */
    public static JsonFunction createJsDate(Date date) {
        StringBuilder sb = new StringBuilder("new Date(");
        sb.append(date.getTime());
        sb.append(")");
        return new JsonFunction(sb.toString());
    }

}
