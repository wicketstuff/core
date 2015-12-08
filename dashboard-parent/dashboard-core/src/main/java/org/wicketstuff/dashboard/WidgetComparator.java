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
package org.wicketstuff.dashboard;

import java.util.Comparator;

/**
 * @author Decebal Suiu
 */
public class WidgetComparator implements Comparator<Widget> {

	@Override
	public int compare(Widget o1, Widget o2) {
		WidgetLocation l1 = o1.getLocation();
		WidgetLocation l2 = o2.getLocation();
		if (l1.getColumn() != l2.getColumn()) {
			return (l1.getColumn() - l2.getColumn());
		} else {
			return (l1.getRow() - l2.getRow());
		}
	}

}
