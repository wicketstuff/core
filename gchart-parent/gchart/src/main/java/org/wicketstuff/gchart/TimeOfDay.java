/* 
 * Copyright 2017 Dieter Tremel.
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
package org.wicketstuff.gchart;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.wicket.ajax.json.JSONArray;

/**
 * A time of day value as defined in Google charts.
 *
 * @author Dieter Tremel
 */
public class TimeOfDay {

    private int hours;
    private int minutes;
    private int seconds;
    private int milliseconds;

    public TimeOfDay(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = 0;
    }

    public TimeOfDay(int hours, int minutes, int seconds, int milliseconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = milliseconds;
    }

    public TimeOfDay(Calendar cal) {
        // Date part of cal value is ignored
        this.hours = cal.get(Calendar.HOUR_OF_DAY);
        this.minutes = cal.get(Calendar.MINUTE);
        this.seconds = cal.get(Calendar.SECOND);
        this.milliseconds = cal.get(Calendar.MILLISECOND);
    }

    public TimeOfDay(Date date) {
        // Date part of Date value is ignored
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        this.hours = cal.get(Calendar.HOUR_OF_DAY);
        this.minutes = cal.get(Calendar.MINUTE);
        this.seconds = cal.get(Calendar.SECOND);
        this.milliseconds = cal.get(Calendar.MILLISECOND);
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(int milliseconds) {
        this.milliseconds = milliseconds;
    }

    public JSONArray getTimeOfDayArray() {
        JSONArray tofday = new JSONArray();
        tofday.put(hours);
        tofday.put(minutes);
        tofday.put(seconds);
        if (milliseconds != 0) {
            tofday.put(milliseconds);
        }
        return tofday;
    }
}
