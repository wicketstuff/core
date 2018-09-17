/*
 *  Copyright 2011 Inaiat H. Moraes.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.wicketstuff.jqplot.lib.renderer.plugin;

import org.wicketstuff.jqplot.lib.JqPlotResources;
import org.wicketstuff.jqplot.lib.renderer.Renderer;

/**
 * A plugin for a jqPlot to render an axis as a series of date values.  This renderer has no options beyond those supplied by the Axis class.  
 * It supplies it's own tick formatter, so the tickOptions.formatter option should not be overridden.
 *
 * Dates can be passed into the axis in almost any recognizable value and will be parsed.  They will be rendered on the axis in the format specified by 
 * tickOptions.formatString.  e.g. tickOptions.formatString = '%Y-%m-%d'.
 *
 * Accecptable format codes are:
 *  
 * <pre>
 Code    Result                  Description
            == Years ==
%Y      2008                Four-digit year
%y      08                  Two-digit year
            == Months ==
%m      09                  Two-digit month
%#m     9                   One or two-digit month
%B      September           Full month name
%b      Sep                 Abbreviated month name
            == Days ==
%d      05                  Two-digit day of month
%#d     5                   One or two-digit day of month
%e      5                   One or two-digit day of month
%A      Sunday              Full name of the day of the week
%a      Sun                 Abbreviated name of the day of the week
%w      0                   Number of the day of the week (0 = Sunday, 6 = Saturday)
%o      th                  The ordinal suffix string following the day of the month
            == Hours ==
%H      23                  Hours in 24-hour format (two digits)
%#H     3                   Hours in 24-hour integer format (one or two digits)
%I      11                  Hours in 12-hour format (two digits)
%#I     3                   Hours in 12-hour integer format (one or two digits)
%p      PM                  AM or PM
            == Minutes ==
%M      09                  Minutes (two digits)
%#M     9                   Minutes (one or two digits)
            == Seconds ==
%S      02                  Seconds (two digits)
%#S     2                   Seconds (one or two digits)
%s      1206567625723       Unix timestamp (Seconds past 1970-01-01 00:00:00)
            == Milliseconds ==
%N      008                 Milliseconds (three digits)
%#N     8                   Milliseconds (one to three digits)
            == Timezone ==
%O      360                 difference in minutes between local time and GMT
%Z      Mountain Standard Time  Name of timezone as reported by browser
%G      -06:00              Hours and minutes between GMT
            == Shortcuts ==
%F      2008-03-26          %Y-%m-%d
%T      05:06:30            %H:%M:%S
%X      05:06:30            %H:%M:%S
%x      03/26/08            %m/%d/%y
%D      03/26/08            %m/%d/%y
%#c     Wed Mar 26 15:31:00 2008  %a %b %e %H:%M:%S %Y
%v      3-Sep-2008          %e-%b-%Y
%R      15:31               %H:%M
%r      3:31:00 PM          %I:%M:%S %p
            == Characters ==
%n      \n                  Newline
%t      \t                  Tab
%%      %                   Percent Symbol
</pre> 
 *    
 * @author inaiat
 */
@Deprecated
public class DateAxisRenderer implements Renderer {

    private static final long serialVersionUID = -6281926919874791228L;
    private Boolean sortMergedLabels;
    private Renderer tickRenderer;

    /**
     * @return the sortMergedLabels
     */
    public Boolean getSortMergedLabels() {
        return sortMergedLabels;
    }

    /**
     * @param sortMergedLabels the sortMergedLabels to set
     */
    public void setSortMergedLabels(Boolean sortMergedLabels) {
        this.sortMergedLabels = sortMergedLabels;
    }

    /**
     * @return the tickRenderer
     */
    public Renderer getTickRenderer() {
        return tickRenderer;
    }

    /**
     * @param tickRenderer the tickRenderer to set
     */
    public void setTickRenderer(Renderer tickRenderer) {
        this.tickRenderer = tickRenderer;
    }

    public JqPlotResources resource() {
        return JqPlotResources.DateAxisRenderer;
    }
}
