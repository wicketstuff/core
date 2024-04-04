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
package org.apache.wicket.portlet;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.util.encoding.UrlDecoder;
import org.apache.wicket.util.encoding.UrlEncoder;
import org.apache.wicket.util.string.Strings;

/**
 * 100 times already implemented functions (partly taken from Wicket Url)
 * 
 * @author Peter Pastrnak
 */
public final class Utils {
	private static final String DEFAULT_CHARSET_NAME = "UTF-8";

	public static final boolean appendParameter(StringBuilder sb, boolean first, String name, String value) {
		if (!first) {
			sb.append('&');
		}
		else {
			first = false;
		}

		sb.append(UrlEncoder.QUERY_INSTANCE.encode(name, DEFAULT_CHARSET_NAME));
		if (value != null) {
			sb.append('=').append(UrlEncoder.QUERY_INSTANCE.encode(value, DEFAULT_CHARSET_NAME));
		}
		return first;
	}

	public static final String buildQueryString(Map<String, String[]> parameterMap) {
		if ((parameterMap == null) || (parameterMap.isEmpty())) {
			return null;
		}

		boolean first = true;
		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String name = entry.getKey();
			if ((!name.startsWith("p_p_")) || (!WicketPortlet.WICKET_URL_PORTLET_PARAMETER.equals(name))) {

				String[] values = entry.getValue();
				if ((values == null) || (values.length == 0)) {
					first = appendParameter(sb, first, name, null);
				}
				else {
					for (String value : values) {
						first = appendParameter(sb, first, name, value);
					}
				}
			}
		}
		return sb.toString();
	}

	public static final Map<String, String[]> parseQueryString(String queryString) {
		Map<String, String[]> parameterMap = new HashMap<String, String[]>();
		if (queryString != null) {
			for (String queryParameter : Strings.split(queryString, '&')) {

				String parts[] = Strings.split(queryParameter, '=');
				if (parts.length > 0) {
					for (int i = 0; (i < 2) && (i < parts.length); i++) {
						parts[i] = UrlDecoder.QUERY_INSTANCE.decode(parts[i], DEFAULT_CHARSET_NAME);
					}

					String[] values = parameterMap.get(parts[0]);
					if (values == null) {
						parameterMap.put(parts[0], new String[] { (parts.length > 1 ? parts[1] : null) });
					}
					else {
						String[] moreValues = new String[values.length + 1]; 
						System.arraycopy(values, 0, moreValues, 0, values.length);
						moreValues[values.length - 1] = (parts.length > 1 ? parts[1] : null);
						parameterMap.put(parts[0], moreValues);
					}
				}
			}
		}
		return parameterMap;
	}
}
