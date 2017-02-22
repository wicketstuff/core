/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.wicket.kendo.ui.dataviz.chart;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Event listener shared by the {@link Chart} widget and the {@link ChartBehavior}
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public interface IChartListener
{
	/**
	 * Indicates whether the 'seriesClick' event is enabled.<br>
	 * If true, the {@link #onSeriesClick(AjaxRequestTarget, String, String, String, long)} event will be triggered by clicking an event or a free event slot.
	 * 
	 * @return {@code false} by default
	 */
	boolean isSeriesClickEventEnabled();

	/**
	 * Triggered when a series is clicked
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param seriesName the series name
	 * @param seriesField the series field
	 * @param category the current category of the point
	 * @param value the current value
	 */
	void onSeriesClick(AjaxRequestTarget target, String seriesName, String seriesField, String category, long value);
}
