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
package org.wicketstuff.dashboard.widgets.justgage.option;

import java.lang.reflect.Field;

/**
 * @author Decebal Suiu
 */
public class JavaScriptOptionsRenderer {

	public static void renderOptions(Object optionsProvider, StringBuilder builder) {
		for (Field field : optionsProvider.getClass().getDeclaredFields()) {
		    // check if field has Option annotation
			Option option;
		    if ((option = field.getAnnotation(Option.class)) != null) {
		    	String name = option.name();
		    	if ("".equals(name)) {
		    		name = field.getName();
		    	}
		    	Object value = null;
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}
				try {
					value = field.get(optionsProvider);
				} catch (Exception e) {
					// TODO
					e.printStackTrace();
				}
		    	boolean required = option.required();

				if ((value == null) && !required ) {
					continue; // jump to next option 
				}

				String optionValue;
				if (field.getType().equals(String.class)) {
					optionValue = "\"" + value + "\"";
				} else {
					optionValue = value.toString();
				}
				
				builder.append(name + ':' + optionValue + ','); 
		    }
		}
		
		char lastChar = builder.charAt(builder.length() - 1);
		if (',' == lastChar) {
			builder.deleteCharAt(builder.length() - 1); // delete last ","	
		}
	}
	
}
