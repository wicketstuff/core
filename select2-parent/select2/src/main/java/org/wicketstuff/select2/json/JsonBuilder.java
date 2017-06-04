/*
 * Copyright 2012 Igor Vaynberg
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
package org.wicketstuff.select2.json;

import org.apache.wicket.ajax.json.JSONStringer;

/**
 * A JSONWriter that writes and allows access to the underlying {@link StringBuilder}. One of the
 * advantages of this class is that it can expose Json as a {@link CharSequence} instead of a
 * {@link String} so no extra memory allocations are necessary for code that can use a
 * {@link CharSequence} .
 * 
 * @author igor
 * @deprecated Use {@link JSONStringer} instead
 */
@Deprecated
public class JsonBuilder extends JSONStringer
{
	/**
	 * @return underlying {@link StringBuilder} as a {@link CharSequence}.
	 */
	public CharSequence toJson()
	{
		return toString();
	}

}
