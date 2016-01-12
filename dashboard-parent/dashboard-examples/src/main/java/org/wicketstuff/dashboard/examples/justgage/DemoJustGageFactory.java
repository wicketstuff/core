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
package org.wicketstuff.dashboard.examples.justgage;

import org.wicketstuff.dashboard.widgets.justgage.JustGage;
import org.wicketstuff.dashboard.widgets.justgage.JustGageFactory;
import org.wicketstuff.dashboard.widgets.justgage.JustGageWidget;

/**
 * @author Decebal Suiu
 */
public class DemoJustGageFactory implements JustGageFactory{

	@Override
	public JustGage createJustGage(JustGageWidget widget) {
		return new JustGage().setValue(4)
				.setMin(0)
				.setMax(10)
				.setTitle("Beers")
				.setGaugeColor("#F78000")
				.setLabel("average");
	}
	
}
