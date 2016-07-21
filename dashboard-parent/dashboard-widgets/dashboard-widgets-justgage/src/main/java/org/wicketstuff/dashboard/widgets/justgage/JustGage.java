/*
 * Copyright 2012 Decebal Suiu
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.wicketstuff.dashboard.widgets.justgage;

import org.wicketstuff.dashboard.widgets.justgage.option.Option;

/**
 * @author Decebal Suiu
 */
public class JustGage {

	// This field is set by JustGageWidgetView !!! 
	@Option(required = true)
	private String id;
	
	@Option(required = true)
	private String title;
	
	@Option(required = true)
	private int value;
	
	@Option
	private int min;
	
	@Option
	private int max;
	
	@Option
	private String gaugeColor;
	
	@Option
	private String label;
	
	public String getId() {
		return id;
	}

	public JustGage setId(String id) {
		this.id = id;
		return this;
	}

	public JustGage setValue(int value) {
		this.value = value;
		return this;
	}
	
	public int getValue() {
		return value;
	}

	public JustGage setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public String getTitle() {
		return title;
	}

	public JustGage setMin(int min) {
		this.min = min;
		return this;
	}

	public int getMin() {
		return min;
	}

	public JustGage setMax(int max) {
		this.max = max;
		return this;
	}
	
	public int getMax() {
		return max;
	}

	public String getGaugeColor() {
		return gaugeColor;
	}

	public JustGage setGaugeColor(String gaugeColor) {
		this.gaugeColor = gaugeColor;
		return this;
	}

	public String getLabel() {
		return label;
	}

	public JustGage setLabel(String label) {
		this.label = label;
		return this;
	}

}
